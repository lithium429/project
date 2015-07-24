package com.xz.activiti.cmd;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.xz.project.core.dao.mybatis.ActivitiDaoSqlImpl;
import com.xz.project.core.service.bpm.ActivitiService;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xz.project.core.dao.ActivitiDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class JumpCmd implements Command<Map> {

	Logger log=LoggerFactory.getLogger(JumpCmd.class);

    ApplicationContext context=new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
    ActivitiService dao=context.getBean(ActivitiService.class);
	
    private String activityId;
    private String taskId;
    private String jumpOrigin;
	
    public JumpCmd(String taskId, String activityId) {
        this(taskId, activityId, "jump");
    }
    
    public JumpCmd(String taskId, String activityId, String jumpOrigin) {
        this.activityId = activityId;
        this.taskId = taskId;
        this.jumpOrigin = jumpOrigin;
    }

	@Override
	public Map execute(CommandContext commandContext) {
		Map<String, Object> map=new LinkedHashMap<String, Object>();
		try {
            TaskEntity task = commandContext.getTaskEntityManager()
                    .findTaskById(taskId);

            String executionId = task.getExecutionId();
            for (TaskEntity taskEntity : commandContext.getTaskEntityManager()
                    .findTasksByExecutionId(executionId)) {
                taskEntity.setVariableLocal("跳转原因", jumpOrigin);
                Date now = new Date();
                Map<String, Object> sqlMap=new HashMap<String, Object>();
                sqlMap.put("endTime", now);
                sqlMap.put("duration", now.getTime() - taskEntity.getCreateTime().getTime());
                sqlMap.put("taskId", taskEntity.getId());
                dao.modifyActinst(sqlMap);
                commandContext.getTaskEntityManager().deleteTask(taskEntity,
                        jumpOrigin, false);

            }
            ExecutionEntity executionEntity = commandContext
                    .getExecutionEntityManager().findExecutionById(executionId);
            ProcessDefinitionImpl processDefinition = executionEntity
                    .getProcessDefinition();
            ActivityImpl activity = processDefinition.findActivity(activityId);
            executionEntity.executeActivity(activity);
            map.put("executionId", executionId);
        } catch (Exception e) {
            log.error("JumpCmd", e);
            map.put("errcode", 1);
            map.put("errmsg", e.getMessage());
            return map;
        }
        map.put("errcode", 0);
        map.put("errmsg", "");
        return map;
	}

}
