package com.xz.activiti.cmd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.xz.project.core.dao.mybatis.ActivitiDaoSqlImpl;
import com.xz.project.core.service.bpm.ActivitiService;
import org.activiti.engine.impl.ExecutionQueryImpl;
import org.activiti.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.el.FixedValue;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.runtime.AtomicOperation;
import org.activiti.engine.impl.task.TaskDefinition;
import org.nutz.json.Json;
import org.nutz.lang.Strings;
import org.nutz.lang.util.NutMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xz.activiti.modules.pojo.Wf_hi_activity;
import com.xz.activiti.utils.CloneUtils;
import com.xz.activiti.utils.UrlUtil;
import com.xz.project.core.dao.ActivitiDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class JoinTaskInitCmd implements Command<Void> {
	
	Logger log=LoggerFactory.getLogger(JoinTaskInitCmd.class);

    ApplicationContext context=new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
    ActivitiService dao=context.getBean(ActivitiService.class);

	@Override
	public Void execute(CommandContext commandContext) {
		List<Wf_hi_activity> list =dao.findAllActivitywh();
		
		for (Wf_hi_activity hiActivity : list) {
            NutMap map = Json.fromJson(NutMap.class, hiActivity.getJsonInfo());
            List<NutMap> list1 = map.getList("list", NutMap.class);
            long p = 0;
            int num = 0;
            String nowActivityId = null;
            for (NutMap map1 : list1) {
                ExecutionQueryImpl executionQuery = new ExecutionQueryImpl();
                executionQuery.processInstanceId(hiActivity.getProcessInstanceId());
                executionQuery.activityId(Strings.sNull(map1.get("activityId")));
                p = Context.getCommandContext().getExecutionEntityManager().findExecutionCountByQueryCriteria(executionQuery);
                if (p > 0) {//查询是否有活动节点
                    nowActivityId = Strings.sNull(map1.get("activityId"));
                    break;
                }
                num++;//记录当前活动节点序号
            }
            if (p == 0) {//如果活动实例不存在
                dao.removeActiwh(hiActivity.getId());
            } else {
                ProcessDefinitionEntity def = Context.getCommandContext().getProcessEngineConfiguration()
                        .getDeploymentManager()
                        .findDeployedProcessDefinitionById(hiActivity.getProcessDefinitionId());
                init(def, hiActivity, list1, num, nowActivityId);
            }
        }
        return null;
	}
	
	public void init(ProcessDefinitionEntity def, Wf_hi_activity hiActivity, List<NutMap> list, int num, String nowActivityId) {
//        Sql sql=Sqls.create("select id_ from act_ru_task where EXECUTION_ID_=@a and PROC_INST_ID_=@b and PROC_DEF_ID_=@c and TASK_DEF_KEY_=@d");
//        sql.params().set("a", hiActivity.getExecutionId());
//        sql.params().set("b", hiActivity.getProcessInstanceId());
//        sql.params().set("c", hiActivity.getProcessDefinitionId());
//        sql.params().set("d", nowActivityId);
//        String taskId=daoCtl.getStrRowValue(dao,sql);
		Map map=new HashMap();
		map.put("executionId", hiActivity.getExecutionId());
		map.put("procInstID", hiActivity.getProcessInstanceId());
		map.put("procDefId", hiActivity.getProcessDefinitionId());
		map.put("taskDefKey", nowActivityId);
		String taskId =String.valueOf( dao.findRuTaskByCon(map));
        //获得当前活动
        ActivityImpl prototypeActivity = getActivityExt(def, hiActivity.getNowActivityId());
        //活动下个节点活动
        ActivityImpl nextActivity = getActivityExt(def, hiActivity.getNextActivityId());
        //生成克隆活动
        List<ActivityImpl> activities = new ArrayList<ActivityImpl>();
        int i = 0;
        for (NutMap nutMap : list) {
            if (i < num) {
                i++;
                continue;
            }
            //生成新活动名
            String activityId = Strings.sNull(nutMap.get("activityId"));
            //生成克隆活动
            ActivityImpl clone;
            if (hiActivity.getIsAfter()) {
                clone = createActivity(hiActivity.getIsAfter(), def, nextActivity,
                        activityId, Strings.sNull(nutMap.get("user")),Strings.sNull(nutMap.get("name")));
            } else {
                clone = createActivity(hiActivity.getIsAfter(), def, prototypeActivity,
                        activityId, Strings.sNull(nutMap.get("user")),Strings.sNull(nutMap.get("name")));
            }
            activities.add(clone);
        }
        //创建连接线
        createActivityChain(activities, nextActivity);
        //创建execution并启动
        ExecutionEntity execution = Context.getCommandContext().getExecutionEntityManager().findExecutionById(hiActivity.getExecutionId());
        execution.setActivity(getActivityExt(def, activities.get(0).getId()));
        execution.performOperation(AtomicOperation.ACTIVITY_START);
        //删除原任务及历史
        Context.getCommandContext().getHistoricTaskInstanceEntityManager().deleteHistoricTaskInstanceById(taskId);
//        Sql sql1=Sqls.create("delete from act_ru_task where id_=@a");
//        sql1.params().set("a", taskId);
//        daoCtl.exeUpdateBySql(dao, sql1);
//        Sql sql2=Sqls.create("delete from act_hi_actinst where PROC_INST_ID_=@a and task_id_=@b");
//        sql2.params().set("a",hiActivity.getProcessInstanceId());
//        sql2.params().set("b",taskId);
//        daoCtl.exeUpdateBySql(dao,sql2);
        dao.removeRuTaskById(taskId);
        Map map3=new HashMap();
        map.put("procInstId", hiActivity.getProcessInstanceId());
        map.put("taskId", taskId);
        dao.removeHiActinst(map3);
    }
	
	 protected ActivityImpl createActivity(boolean isAfter, ProcessDefinitionEntity processDefinition,
             ActivityImpl prototypeActivity, String cloneActivityId, String assignee,String name) {
		ActivityImpl clone = cloneActivity(processDefinition, prototypeActivity, cloneActivityId, "executionListeners",
		"properties");
		//设置assignee
		UserTaskActivityBehavior activityBehavior = (UserTaskActivityBehavior) (prototypeActivity.getActivityBehavior());
		
		TaskDefinition taskDefinition = cloneTaskDefinition(activityBehavior.getTaskDefinition());
		taskDefinition.setKey(cloneActivityId);
		
		if (assignee != null) {
		taskDefinition.setAssigneeExpression(new FixedValue(assignee));
		}
		if (isAfter) {
		taskDefinition.setNameExpression(new FixedValue(name + "-后加签"));
		} else {
		taskDefinition.setNameExpression(new FixedValue(name + "-前加签"));
		}
		UserTaskActivityBehavior cloneActivityBehavior = Context.getCommandContext().getProcessEngineConfiguration().getActivityBehaviorFactory().createUserTaskActivityBehavior(null,
		taskDefinition);
		clone.setActivityBehavior(cloneActivityBehavior);
		return clone;
	}
	 
	 protected TaskDefinition cloneTaskDefinition(TaskDefinition taskDefinition) {
	        TaskDefinition newTaskDefinition = new TaskDefinition(taskDefinition.getTaskFormHandler());
	        BeanUtils.copyProperties(taskDefinition, newTaskDefinition);
	        return newTaskDefinition;
	    }

	    protected ActivityImpl cloneActivity(ProcessDefinitionEntity processDefinition, ActivityImpl prototypeActivity,
	                                         String newActivityId, String... fieldNames) {
	        ActivityImpl clone = processDefinition.createActivity(newActivityId);
	        CloneUtils.copyFields(prototypeActivity, clone, fieldNames);

	        return clone;
	    }

	    public ActivityImpl getActivityExt(ProcessDefinitionEntity processDefinitionEntity, String id) {
	        return processDefinitionEntity
	                .findActivity(id);
	    }

	    protected void createActivityChain(List<ActivityImpl> activities, ActivityImpl nextActivity) {
	        for (int i = 0; i < activities.size(); i++) {
	            //设置各活动的下线
	            activities.get(i).getOutgoingTransitions().clear();
	            activities.get(i).createOutgoingTransition("flow" + (i + 1))
	                    .setDestination(i == activities.size() - 1 ? nextActivity : activities.get(i + 1));
	        }
	    }
	
}
