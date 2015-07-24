package com.xz.activiti.cmd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.xz.project.core.dao.mybatis.ActivitiDaoSqlImpl;
import com.xz.project.core.service.bpm.ActivitiService;
import org.activiti.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.el.FixedValue;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.runtime.AtomicOperation;
import org.activiti.engine.impl.task.TaskDefinition;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.nutz.lang.util.NutMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.xz.activiti.modules.pojo.Wf_hi_activity;
import com.xz.activiti.utils.CloneUtils;
import com.xz.activiti.utils.UrlUtil;
import com.xz.project.core.dao.ActivitiDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class JoinTaskCmd implements Command<Map> {

    ApplicationContext context=new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
    ActivitiService dao=context.getBean(ActivitiService.class);

	
	Logger log=LoggerFactory.getLogger(JoinTaskCmd.class);
	
    private String taskId;
    private String nowActivityId;
    private String nextActivityId;
    private String[] users;
    private TaskEntity taskEntity;
    private static int SEQUNCE_NUMBER = 0;
    private boolean isAfter;
    private PvmActivity pvmActivity;

    public JoinTaskCmd(boolean isAfter, String taskId, String[] users) {
        this.isAfter = isAfter;
        this.taskId = taskId;
        this.users = users;
    }
	
	
	

	@Override
	public Map execute(CommandContext commandContext) {
		Map map =new LinkedHashMap();
        taskEntity = Context.getCommandContext()
                .getTaskEntityManager().findTaskById(taskId);
        nowActivityId = taskEntity.getTaskDefinitionKey();
        nextActivityId = nowActivityId;
        ProcessDefinitionEntity def = Context.getCommandContext().getProcessEngineConfiguration()
                .getDeploymentManager()
                .findDeployedProcessDefinitionById(taskEntity.getProcessDefinitionId());
		
        if (isAfter) {
            pvmActivity = getNextActivityId(nowActivityId, def.getActivities());
            nextActivityId = pvmActivity.getId();
            if (nextActivityId == null || UrlUtil.sNull(pvmActivity.getProperty("type"),"").equals("endEvent")
                    || UrlUtil.sNull(pvmActivity.getProperty("type"),"").endsWith("Gateway")) {
                //返回错误信息
                map.put("errcode", 1);
                map.put("errmsg", "下一节点不是用户任务，不允许后插");
                return map;
            }
        }
        //获得当前活动
        ActivityImpl prototypeActivity = getActivityExt(def, nowActivityId);
        //活动下个节点活动
        ActivityImpl nextActivity = getActivityExt(def, nextActivityId);
        List<ActivityImpl> activities = new ArrayList<ActivityImpl>();
        List<LinkedHashMap<String, Object>> list = new ArrayList<LinkedHashMap<String, Object>>();
        for (String user : users) {
        	LinkedHashMap<String, Object> info = new LinkedHashMap<String, Object>();
            //生成新活动名
            String activityId = createUniqueActivityId(taskEntity.getProcessInstanceId(), nowActivityId);
            //生成克隆活动
            ActivityImpl clone;
            if (isAfter) {
                clone = createActivity(def, nextActivity,
                        activityId, user);
            } else {
                clone = createActivity(def, prototypeActivity,
                        activityId, user);
            }
            activities.add(clone);
            info.put("num", SEQUNCE_NUMBER);
            info.put("activityId", activityId);
            info.put("user", user);
            info.put("name",taskEntity.getName());
            list.add(info);
        }
        
        //创建连接线
        createActivityChain(activities, nextActivity);
        //创建execution并启动
        ExecutionEntity execution = Context.getCommandContext().getExecutionEntityManager().findExecutionById(taskEntity.getExecutionId());
        execution.setActivity(getActivityExt(def, activities.get(0).getId()));

        execution.performOperation(AtomicOperation.ACTIVITY_START);
        this.deleteActiveTask();
        //数据持久化
        JsonFormat format=new JsonFormat();
        format.setCompact(true);
        format.setIgnoreNull(false);
        
        Wf_hi_activity hiActivity = new Wf_hi_activity();
        hiActivity.setProcessInstanceId(taskEntity.getProcessInstanceId());
        hiActivity.setProcessDefinitionId(taskEntity.getProcessDefinitionId());
        hiActivity.setExecutionId(taskEntity.getExecutionId());
        hiActivity.setNowActivityId(nowActivityId);
        hiActivity.setNextActivityId(nextActivityId);
        hiActivity.setJsonInfo(Json.toJson(new NutMap().addv("list",list),format));
        hiActivity.setIsAfter(isAfter);
        dao.createActiwf(hiActivity);

        map.put("errcode","0");
        map.put("errmsg","success");
		return map;
	}
	
    /**
     * 删除未完成任务.
     */
    public void deleteActiveTask() {
       Context.getCommandContext().getTaskEntityManager()
                .deleteTask(taskEntity, isAfter ? "后加签" : "前加签", false);
    }

	
    public PvmActivity getNextActivityId(String activityId, List<ActivityImpl> activitiList) {
        for (ActivityImpl activityImpl : activitiList) {
            if (activityId.equals(activityImpl.getId())) {
                List<PvmTransition> outgoingTransitions = activityImpl.getOutgoingTransitions();
                return outgoingTransitions.get(0).getDestination();
            }
        }
        return null;
    }
    
    public ActivityImpl getActivityExt(ProcessDefinitionEntity processDefinitionEntity, String id) {
        return processDefinitionEntity
                .findActivity(id);
    }
    protected String createUniqueActivityId(String processInstanceId, String prototypeActivityId) {
        return "join:" + processInstanceId + ":" + prototypeActivityId + ":" + System.currentTimeMillis() + "-"
                + (SEQUNCE_NUMBER++);
    }
    
    protected ActivityImpl createActivity(ProcessDefinitionEntity processDefinition,
            ActivityImpl prototypeActivity, String cloneActivityId, String assignee) {
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
		taskDefinition.setNameExpression(new FixedValue(taskEntity.getName()+ "-后加签"));
		} else {
		taskDefinition.setNameExpression(new FixedValue(taskEntity.getName() + "-前加签"));
		}
		UserTaskActivityBehavior cloneActivityBehavior = Context.getCommandContext().getProcessEngineConfiguration().getActivityBehaviorFactory().createUserTaskActivityBehavior(null,
		taskDefinition);
		clone.setActivityBehavior(cloneActivityBehavior);
		
		return clone;
	}
    
    protected ActivityImpl cloneActivity(ProcessDefinitionEntity processDefinition, ActivityImpl prototypeActivity,
	            String newActivityId, String... fieldNames) {
	ActivityImpl clone = processDefinition.createActivity(newActivityId);
	CloneUtils.copyFields(prototypeActivity, clone, fieldNames);
	
	return clone;
	}
    
    protected TaskDefinition cloneTaskDefinition(TaskDefinition taskDefinition) {
        TaskDefinition newTaskDefinition = new TaskDefinition(taskDefinition.getTaskFormHandler());
        BeanUtils.copyProperties(taskDefinition, newTaskDefinition);
        return newTaskDefinition;
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
