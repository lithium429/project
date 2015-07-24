package com.xz.activiti.cmd;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.xz.project.core.dao.mybatis.ActivitiDaoSqlImpl;
import com.xz.project.core.service.bpm.ActivitiService;
import org.activiti.engine.impl.HistoricActivityInstanceQueryImpl;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.cmd.GetDeploymentProcessDefinitionCmd;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.HistoricActivityInstanceEntity;
import org.activiti.engine.impl.persistence.entity.HistoricIdentityLinkEntity;
import org.activiti.engine.impl.persistence.entity.HistoricTaskInstanceEntity;
import org.activiti.engine.impl.persistence.entity.IdentityLinkEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.nutz.dao.entity.Record;
import org.nutz.lang.Strings;
import org.nutz.lang.util.NutMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xz.activiti.graph.ActivitiHistoryGraphBuilder;
import com.xz.activiti.graph.Edge;
import com.xz.activiti.graph.Graph;
import com.xz.activiti.graph.Node;
import com.xz.project.core.dao.ActivitiDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

public class RollbackTaskCmd implements Command<Map> {

	Logger log=LoggerFactory.getLogger(RollbackTaskCmd.class);

    ApplicationContext context=new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
    ActivitiService dao=context.getBean(ActivitiService.class);
	
    private String taskId;
    private String activityId;
    private String[] users;
    private TaskEntity taskEntity;
    private boolean isParallel = false;
    private boolean isParallelNow = false;
    private String historyTaskId;
    private String firstUserTask;
    private String parallelGatewayInId;
    private String parallelGatewayOutId;
    private List<String> parallelGatewayTasks = new ArrayList<String>();
    private List<String> parallelGatewayTasksNow = new ArrayList<String>();
  //  private Sql sql = Sqls.create("update act_hi_actinst set END_TIME_=START_TIME_,DURATION_=0 where EXECUTION_ID_=@a and act_id_=@b and END_TIME_ is null");
    private Map paramsMap=new HashMap();
    /**
     * 这个taskId是运行阶段task的id.
     */
    public RollbackTaskCmd(String taskId) {
        this(taskId, null, null);
    }

    public RollbackTaskCmd(String taskId, String activityId) {
        this(taskId, activityId, null);
    }

    public RollbackTaskCmd(String taskId, String activityId, String[] users) {
        this.taskId = taskId;
        this.activityId = activityId;
        this.users = users;
    }
	
    /**
     * 退回流程.
     *
     * @return 0-退回成功 1-流程结束 2-下一结点已经通过,不能退回
     */
	@Override
	public Map execute(CommandContext commandContext) {
	    NutMap nutMap = new NutMap();
        // 尝试查找最近的上游userTask
        taskEntity = Context.getCommandContext()
                .getTaskEntityManager().findTaskById(taskId);
        ProcessDefinitionEntity def = Context.getCommandContext().getProcessEngineConfiguration()
                .getDeploymentManager()
                .findDeployedProcessDefinitionById(taskEntity.getProcessDefinitionId());
        List<ActivityImpl> activitiList = def.getActivities();
        isParallelNow = isParallelGatewayTaskNow(taskEntity.getTaskDefinitionKey(), activitiList);
        if (activityId != null) {
            isParallel = isParallelGatewayTask(activityId, activitiList);
            Map map=new HashMap();
            map.put("taskDefKey", activityId);
            map.put("procInstId", taskEntity.getProcessInstanceId());
            historyTaskId = dao.findHiTaInByCon(map);

        } else {
            Map map = this.findNearestUserTask();
            historyTaskId = Strings.sNull(map.get("TASK_ID_"));
            //如果上一节点是并发分支节点，则使用activityId进行回退
            isParallel = isParallelGatewayTask(Strings.sNull(map.get("ACT_ID_")), activitiList);
            if (isParallel) {
                activityId = Strings.sNull(map.get("ACT_ID_"));
            }
        }
        if (Strings.isEmpty(historyTaskId)) {
            log.info("cannot rollback " + taskId);
            nutMap.setv("errcode", 2);
            nutMap.setv("errmsg", "cannot rollback " + taskId);
            return nutMap;
        }

        // 先找到历史任务
        HistoricTaskInstanceEntity historicTaskInstanceEntity = Context
                .getCommandContext().getHistoricTaskInstanceEntityManager()
                .findHistoricTaskInstanceById(historyTaskId);

        // 再反向查找历史任务对应的历史节点
        HistoricActivityInstanceEntity historicActivityInstanceEntity = getHistoricActivityInstanceEntity(historyTaskId);

        log.info("historic activity instance is : " +
                historicActivityInstanceEntity.getId());

        Graph graph = new ActivitiHistoryGraphBuilder(
                historicTaskInstanceEntity.getProcessInstanceId()).build();

        Node node = graph.findById(historicActivityInstanceEntity.getId());

        if (!checkCouldRollback(node)) {
            nutMap.setv("errcode", 2);
            nutMap.setv("errmsg", "cannot rollback " + taskId);
            return nutMap;
        }

        if (this.isSameBranch(historicTaskInstanceEntity)) {
            // 如果退回的目标节点的executionId与当前task的executionId一样，说明是同一个分支
            // 只删除当前分支的task
            this.deleteActiveTask();
        } else {
            // 否则认为是从分支跳回主干
            // 删除所有活动中的task
            this.deleteActiveTasks(historicTaskInstanceEntity
                    .getProcessInstanceId());

            // 获得期望退回的节点后面的所有节点历史
            List<String> historyNodeIds = new ArrayList<String>();
            collectNodes(node, historyNodeIds);
            //this.deleteHistoryActivities(historyNodeIds);
            if(isParallelNow){
            	for(String key:parallelGatewayTasksNow){
            		Map terMap=new HashMap();
            		terMap.put("procInstId", historicTaskInstanceEntity.getProcessInstanceId());
            		terMap.put("parentId", historicTaskInstanceEntity.getExecutionId());
            		terMap.put("actId", key);
            		dao.removeArexecByCon(terMap);
            	}
            }
        }

        // 恢复期望退回的任务和历史
        this.processHistoryTask(historicTaskInstanceEntity,
                historicActivityInstanceEntity, activitiList, def);

        log.info("activiti is rollback " +
                historicTaskInstanceEntity.getName());
        if (isParallel) {
            dao.modifyAhactinst(paramsMap);
        }
        nutMap.setv("isParallel", isParallel);
        nutMap.setv("errcode", 0);
        nutMap.setv("errmsg", "");
        return nutMap;
	}
	
	public void processHistoryTask(
            HistoricTaskInstanceEntity historicTaskInstanceEntity,
            HistoricActivityInstanceEntity historicActivityInstanceEntity, List<ActivityImpl> activitiList, ProcessDefinitionEntity def) {
        ActivityImpl activity = getActivity(historicActivityInstanceEntity);
        boolean async = activity.isAsync();
        if (isParallel) {
            // 把流程指向任务对应的节点
            ExecutionEntity executionEntity = Context.getCommandContext()
                    .getExecutionEntityManager()
                    .findExecutionById(taskEntity.getExecutionId());
            executionEntity.setActivity(getActivityExt(def, parallelGatewayInId));
            executionEntity.setActive(false);
            executionEntity.setConcurrent(false);
            executionEntity.setScope(true);
            Context.getCommandContext().getHistoryManager()
                    .recordActivityStart(executionEntity);
            paramsMap.put("execId",executionEntity.getId());
            paramsMap.put("actId",parallelGatewayInId);
            for (String key : parallelGatewayTasks) {
                if (key.equals(activityId)) {//如果并发节点是要退回的节点
                    ExecutionEntity executionEntity2 = executionEntity.createExecution();
                    executionEntity2.setActivity(activity);
                    executionEntity2.setActive(true);
                    executionEntity2.setConcurrent(true);
                    executionEntity2.setScope(false);
                    Context.getCommandContext().getHistoryManager().recordActivityStart(executionEntity2);
                    // 创建新任务
                    TaskEntity task = TaskEntity.create(new Date());

                    task.setProcessDefinitionId(historicTaskInstanceEntity
                            .getProcessDefinitionId());
                    task.setParentTaskIdWithoutCascade(historicTaskInstanceEntity
                            .getParentTaskId());
                    task.setNameWithoutCascade(historicTaskInstanceEntity.getName());
                    task.setTaskDefinitionKey(historicTaskInstanceEntity
                            .getTaskDefinitionKey());
                    task.setExecutionId(executionEntity2.getId());
                    task.setPriority(historicTaskInstanceEntity.getPriority());
                    task.setProcessInstanceId(historicTaskInstanceEntity
                            .getProcessInstanceId());
                    task.setDescriptionWithoutCascade(historicTaskInstanceEntity
                            .getDescription());
                    task.setCategory(historicTaskInstanceEntity.getCategory());
                    task.setFormKey(historicTaskInstanceEntity.getFormKey());
                    Context.getCommandContext().getTaskEntityManager().insert(task);
                    //还原IdentityLink
                    List<HistoricIdentityLinkEntity> linkEntities=Context
                            .getCommandContext()
                            .getHistoricIdentityLinkEntityManager().findHistoricIdentityLinksByTaskId(historyTaskId);
                    for(HistoricIdentityLinkEntity entity:linkEntities) {
                        IdentityLinkEntity identityLinkEntity = new IdentityLinkEntity();
                        identityLinkEntity.setTask(task);
                        identityLinkEntity.setUserId(entity.getUserId());
                        identityLinkEntity.setGroupId(entity.getGroupId());
                        identityLinkEntity.setType(entity.getType());
                        identityLinkEntity.setProcessDefId(entity.getProcessInstanceId());
                        identityLinkEntity.insert();
                    }

                    // 创建HistoricTaskInstance
                    Context.getCommandContext().getHistoryManager()
                            .recordTaskCreated(task, executionEntity2);
                    Context.getCommandContext().getHistoryManager().recordTaskId(task);
                    // 更新ACT_HI_ACTIVITY里的assignee字段
                    Context.getCommandContext().getHistoryManager()
                            .recordTaskAssignment(task);
                } else {
                    ExecutionEntity executionEntity1 = executionEntity.createExecution();
                    executionEntity1.setActivity(getActivityExt(def, parallelGatewayOutId));
                    executionEntity1.setActive(false);
                    executionEntity1.setConcurrent(true);
                    executionEntity1.setScope(false);
                    Context.getCommandContext().getHistoryManager().recordActivityStart(executionEntity1);

                    paramsMap.put("execId",executionEntity.getId());
                    paramsMap.put("actId",parallelGatewayInId);
                }
            }


        } else {
            if (users == null) {

                // 创建新任务
                TaskEntity task = TaskEntity.create(new Date());
                task.setProcessDefinitionId(historicTaskInstanceEntity
                        .getProcessDefinitionId());
                // task.setId(historicTaskInstanceEntity.getId());
                task.setParentTaskIdWithoutCascade(historicTaskInstanceEntity
                        .getParentTaskId());
                task.setNameWithoutCascade(historicTaskInstanceEntity.getName());
                task.setFormKey(historicTaskInstanceEntity.getFormKey());
                task.setTaskDefinitionKey(historicTaskInstanceEntity
                        .getTaskDefinitionKey());
                task.setExecutionId(historicTaskInstanceEntity.getExecutionId());
                task.setPriority(historicTaskInstanceEntity.getPriority());
                task.setProcessInstanceId(historicTaskInstanceEntity
                        .getProcessInstanceId());
                task.setDescriptionWithoutCascade(historicTaskInstanceEntity
                        .getDescription());
                task.setCategory(historicTaskInstanceEntity.getCategory());

                task.setAssigneeWithoutCascade(historicTaskInstanceEntity.getAssignee());

                Context.getCommandContext().getTaskEntityManager().insert(task);
                //还原IdentityLink
                List<HistoricIdentityLinkEntity> linkEntities=Context
                        .getCommandContext()
                        .getHistoricIdentityLinkEntityManager().findHistoricIdentityLinksByTaskId(historyTaskId);
                for(HistoricIdentityLinkEntity entity:linkEntities) {
                    IdentityLinkEntity identityLinkEntity = new IdentityLinkEntity();
                    identityLinkEntity.setTask(task);
                    identityLinkEntity.setUserId(entity.getUserId());
                    identityLinkEntity.setGroupId(entity.getGroupId());
                    identityLinkEntity.setType(entity.getType());
                    identityLinkEntity.setProcessDefId(entity.getProcessInstanceId());
                    identityLinkEntity.insert();
                }

                // 把流程指向任务对应的节点
                ExecutionEntity executionEntity = Context.getCommandContext()
                        .getExecutionEntityManager()
                        .findExecutionById(historicTaskInstanceEntity.getExecutionId());
                executionEntity
                        .setActivity(activity);

                // 创建HistoricActivityInstance
                Context.getCommandContext().getHistoryManager()
                        .recordActivityStart(executionEntity);

                // 创建HistoricTaskInstance
                Context.getCommandContext().getHistoryManager()
                        .recordTaskCreated(task, executionEntity);
                Context.getCommandContext().getHistoryManager().recordTaskId(task);
                // 更新ACT_HI_ACTIVITY里的assignee字段
                Context.getCommandContext().getHistoryManager()
                        .recordTaskAssignment(task);
            } else {

                // 把流程指向任务对应的节点
                ExecutionEntity executionEntity = Context.getCommandContext()
                        .getExecutionEntityManager()
                        .findExecutionById(taskEntity.getExecutionId());
                executionEntity.setActivity(activity);
                // 创建HistoricActivityInstance
                Context.getCommandContext().getHistoryManager()
                        .recordActivityStart(executionEntity);
                ExecutionEntity executionEntity1 = executionEntity.createExecution();
                executionEntity1.setParentId(executionEntity.getId());
                executionEntity1.setProcessDefinitionId(executionEntity.getProcessDefinitionId());
                executionEntity1.setProcessDefinitionKey(executionEntity.getProcessDefinitionKey());
                executionEntity1.setScope(true);
                executionEntity1.setConcurrent(async);
                Context.getCommandContext().getHistoryManager().recordActivityStart(executionEntity1);
                for (String s : users) {
                    ExecutionEntity executionEntity2 = executionEntity1.createExecution();
                    executionEntity2.setParentId(executionEntity1.getId());
                    executionEntity2.setProcessDefinitionId(executionEntity1.getProcessDefinitionId());
                    executionEntity2.setProcessDefinitionKey(executionEntity1.getProcessDefinitionKey());
                    executionEntity2.setScope(false);
                    executionEntity2.setConcurrent(!async);
                    Context.getCommandContext().getHistoryManager().recordActivityStart(executionEntity2);
                    // 创建新任务
                    TaskEntity task = TaskEntity.create(new Date());
                    task.setProcessDefinitionId(historicTaskInstanceEntity
                            .getProcessDefinitionId());
                    // task.setId(historicTaskInstanceEntity.getId());
                    task.setAssigneeWithoutCascade(s);
                    task.setParentTaskIdWithoutCascade(historicTaskInstanceEntity
                            .getParentTaskId());
                    task.setNameWithoutCascade(historicTaskInstanceEntity.getName());
                    task.setTaskDefinitionKey(historicTaskInstanceEntity
                            .getTaskDefinitionKey());
                    task.setExecutionId(executionEntity2.getId());
                    task.setPriority(historicTaskInstanceEntity.getPriority());
                    task.setProcessInstanceId(historicTaskInstanceEntity
                            .getProcessInstanceId());
                    task.setDescriptionWithoutCascade(historicTaskInstanceEntity
                            .getDescription());
                    task.setCategory(historicTaskInstanceEntity.getCategory());
                    task.setFormKey(historicTaskInstanceEntity.getFormKey());

                    Context.getCommandContext().getTaskEntityManager().insert(task);
                    // 创建HistoricTaskInstance
                    Context.getCommandContext().getHistoryManager()
                            .recordTaskCreated(task, executionEntity2);
                    Context.getCommandContext().getHistoryManager().recordTaskId(task);
                    // 更新ACT_HI_ACTIVITY里的assignee字段
                    Context.getCommandContext().getHistoryManager()
                            .recordTaskAssignment(task);
                }
            }
        }

    }
	
	
	 //判断当前是否是并行分支节点
    public boolean isParallelGatewayTaskNow(String activityId, List<ActivityImpl> activitiList) {
        boolean res = false;
        for (ActivityImpl activityImpl : activitiList) {
            if ("userTask".equals(activityImpl.getProperty("type")) && firstUserTask == null) {
                firstUserTask = activityImpl.getId();//保存第一个任务节点
            } else if ("parallelGateway".equals(activityImpl.getProperty("type"))) {
                List<PvmTransition> outgoingTransitions = activityImpl.getOutgoingTransitions();
                for (PvmTransition pvmTransition : outgoingTransitions) {
                    PvmActivity ac = pvmTransition.getDestination();
                    if (outgoingTransitions.size() > 1 && "userTask".equals(ac.getProperty("type"))) {
                        parallelGatewayTasksNow.add(ac.getId());
                    }
                    if (outgoingTransitions.size() > 1 && activityId.equals(ac.getId())) {
                        res = true;
                    }
                }
                if (res)
                    break;
            }
        }
        return res;
    }
    
  //判断是否是并行分支节点
    public boolean isParallelGatewayTask(String activityId, List<ActivityImpl> activitiList) {
        boolean res = false;
        for (ActivityImpl activityImpl : activitiList) {
            if ("userTask".equals(activityImpl.getProperty("type")) && firstUserTask == null) {
                firstUserTask = activityImpl.getId();//保存第一个任务节点
            } else if ("parallelGateway".equals(activityImpl.getProperty("type"))) {
                List<PvmTransition> outgoingTransitions = activityImpl.getOutgoingTransitions();
                for (PvmTransition pvmTransition : outgoingTransitions) {
                    PvmActivity ac = pvmTransition.getDestination();
                    if (outgoingTransitions.size() > 1 && "userTask".equals(ac.getProperty("type"))) {
                        parallelGatewayTasks.add(ac.getId());
                    }
                    if (outgoingTransitions.size() > 1 && activityId.equals(ac.getId())) {
                        parallelGatewayInId = activityImpl.getId();
                        List<PvmTransition> out = ac.getOutgoingTransitions();
                        for (PvmTransition o : out) {
                            PvmActivity a = o.getDestination();
                            if ("parallelGateway".equals(a.getProperty("type"))) {
                                parallelGatewayOutId = a.getId();
                            }
                        }
                        res = true;
                    }
                }
                if (res)
                    break;
            }
        }
        return res;
    }
    
    /**
     * 查找最近历史节点
     *
     * @return
     */
    public Map findNearestUserTask() {
        Graph graph = new ActivitiHistoryGraphBuilder(
                taskEntity.getProcessInstanceId()).build();

        String historicActivityInstanceId=dao.findHiActinstByTaskId(taskId);
        Node node = graph.findById(historicActivityInstanceId);

        String previousHistoricActivityInstanceId = this.findIncomingNode(
                graph, node);

        if (previousHistoricActivityInstanceId == null) {
            log.debug(
                    "cannot find previous historic activity instance : " +
                            taskEntity);

            return null;
        }
        List<Map> resultMap=dao.findHainstPartById(previousHistoricActivityInstanceId);
        return resultMap.get(0);
    }
    
    public String findIncomingNode(Graph graph, Node node) {
        for (Edge edge : graph.getEdges()) {
            Node src = edge.getSrc();
            Node dest = edge.getDest();
            String srcType = src.getType();

            if (!dest.getId().equals(node.getId())) {
                continue;
            }

            if ("userTask".equals(srcType)) {
                boolean isSkip = isSkipActivity(src.getId());

                if (isSkip) {
                    return this.findIncomingNode(graph, src);
                } else {
                    return src.getId();
                }
            } else if (srcType.endsWith("Gateway")) {
                return this.findIncomingNode(graph, src);
            } else {
                log.info("cannot rollback, previous node is not userTask : "
                        + src.getId() + " " + srcType + "(" + src.getName()
                        + ")");

                return null;
            }
        }

        log.info("cannot rollback, this : " + node.getId() + " "
                + node.getType() + "(" + node.getName() + ")");

        return null;
    }
    
    public boolean isSkipActivity(String historyActivityId) {
    	String historyTaskId=dao.findHainstById(historyActivityId);
        HistoricTaskInstanceEntity historicTaskInstanceEntity = Context
                .getCommandContext().getHistoricTaskInstanceEntityManager()
                .findHistoricTaskInstanceById(historyTaskId);
        String deleteReason = historicTaskInstanceEntity.getDeleteReason();

        return "跳过".equals(deleteReason);
    }
    
    public boolean checkCouldRollback(Node node) {
        // TODO: 如果是catchEvent，也应该可以退回，到时候再说
        for (Edge edge : node.getOutgoingEdges()) {
            Node dest = edge.getDest();
            String type = dest.getType();

            if ("userTask".equals(type)) {
                if (!dest.isActive()) {
                    boolean isSkip = isSkipActivity(dest.getId());

                    if (isSkip) {
                        return checkCouldRollback(dest);
                    } else {
                        // logger.info("cannot rollback, " + type + "("
                        // + dest.getName() + ") is complete.");
                        // return false;
                        return true;
                    }
                }
            } else if (type.endsWith("Gateway")) {
                return checkCouldRollback(dest);
            } else {
                log.info("cannot rollback, " + type + "(" + dest.getName()
                        + ") is complete.");

                return false;
            }
        }

        return true;
    }
    
    public HistoricActivityInstanceEntity getHistoricActivityInstanceEntity(
            String historyTaskId) {
        log.info("historyTaskId : " + historyTaskId);
        String historicActivityInstanceId=dao.findHiActinstByTaskId(historyTaskId);
        log.info("historicActivityInstanceId : " +
                historicActivityInstanceId);

        HistoricActivityInstanceQueryImpl historicActivityInstanceQueryImpl = new HistoricActivityInstanceQueryImpl();
        historicActivityInstanceQueryImpl
                .activityInstanceId(historicActivityInstanceId);

        HistoricActivityInstanceEntity historicActivityInstanceEntity = (HistoricActivityInstanceEntity) Context
                .getCommandContext()
                .getHistoricActivityInstanceEntityManager()
                .findHistoricActivityInstancesByQueryCriteria(
                        historicActivityInstanceQueryImpl, new Page(0, 1))
                .get(0);

        return historicActivityInstanceEntity;
    }
    
    public boolean isSameBranch(
            HistoricTaskInstanceEntity historicTaskInstanceEntity) {
        return taskEntity.getExecutionId().equals(
                historicTaskInstanceEntity.getExecutionId());
    }
    
    /**
     * 删除未完成任务.
     */
    public void deleteActiveTask() {
        Context.getCommandContext().getTaskEntityManager()
                .deleteTask(taskEntity, "回退", false);
        List<Map> list=dao.findHiActinstByCon(taskId);
        Date now=new Date();
        if(list.size()>0){
        	 for(Map map:list){
            	 Date startTime = (Date) map.get("START_TIME_");
                 long duration = now.getTime() - startTime.getTime();
                 Map terMap=new HashMap();
                 terMap.put("endtime", now);
                 terMap.put("duration", duration);
                 terMap.put("id", map.get("ID_"));
                 dao.modifyHiAcinst(terMap);
            }
        }
       
    }
    
    public void deleteActiveTasks(String processInstanceId) {
        Context.getCommandContext().getTaskEntityManager()
                .deleteTasksByProcessInstanceId(processInstanceId, "退回", false);
        List<Map> list=dao.findHiActinstByProId(processInstanceId);
        Date now = new Date();
        if(list.size()>0){
        	 for(Map map:list){
            	 Date startTime = (Date) map.get("START_TIME_");
                 long duration = now.getTime() - startTime.getTime();
                 Map terMap=new HashMap();
                 terMap.put("endtime", now);
                 terMap.put("duration", duration);
                 terMap.put("id", map.get("ID_"));
                 dao.modifyHiAcinst(terMap);
            }
        }
        
    }
    
    public void collectNodes(Node node, List<String> historyNodeIds) {
        log.info("node : " + node.getId() + "," + node.getType() + "," +
                node.getName());

        for (Edge edge : node.getOutgoingEdges()) {
            log.info("edge : " + edge.getName());

            Node dest = edge.getDest();
            historyNodeIds.add(dest.getId());
            collectNodes(dest, historyNodeIds);
        }
    }
    
    public ActivityImpl getActivity(
            HistoricActivityInstanceEntity historicActivityInstanceEntity) {
        ProcessDefinitionEntity processDefinitionEntity = new GetDeploymentProcessDefinitionCmd(
                historicActivityInstanceEntity.getProcessDefinitionId())
                .execute(Context.getCommandContext());
        //退回到指定节点
        if (activityId != null) {
            return processDefinitionEntity
                    .findActivity(activityId);
        }
        return processDefinitionEntity
                .findActivity(historicActivityInstanceEntity.getActivityId());
    }

    public ActivityImpl getActivityExt(ProcessDefinitionEntity processDefinitionEntity, String id) {
        return processDefinitionEntity
                .findActivity(id);
    }
        
}
