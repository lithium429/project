package com.xz.activiti.cmd;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import com.xz.project.core.dao.mybatis.ActivitiDaoSqlImpl;
import com.xz.project.core.service.bpm.ActivitiService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xz.activiti.utils.UrlUtil;
import com.xz.project.core.dao.ActivitiDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class NextTaskCmd implements Command<Map> {

	Logger log=LoggerFactory.getLogger(NextTaskCmd.class);

    ApplicationContext context=new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
    ActivitiService dao=context.getBean(ActivitiService.class);
    ProcessEngine engine=context.getBean(ProcessEngine.class);
    RepositoryService repositoryService=engine.getRepositoryService();
	
    String taskId;
    
    public NextTaskCmd(String taskId) {
        this.taskId = taskId;
    }
	
	
	@Override
	public Map execute(CommandContext commandContext) {
		Map<String,Object> map=new LinkedHashMap<String, Object>();
        TaskEntity taskEntity = Context.getCommandContext()
                .getTaskEntityManager().findTaskById(taskId);
        if (taskEntity == null) {
            log.debug("cannot find task : " + taskId);
            map.put("errcode", 1);
            map.put("errmsg", "cannot find task : " + taskId);
            return map;
        }
        ProcessDefinitionEntity def = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService).getDeployedProcessDefinition(taskEntity.getProcessDefinitionId());
        List<ActivityImpl> activitiList = def.getActivities();
        int type = 0;
        map.put("errcode", 0);
        map.put("errmsg", "");
        getTaskActivitys(taskEntity.getTaskDefinitionKey(), activitiList, type, map);
        return map;
		
	}
	
	public static List<PvmActivity> getTaskActivitys(String activityId, List<ActivityImpl> activityList, int type, Map map) {
        if(activityId.startsWith("join")){
            //如果是前加签、后加签，替换ActivityId
            Pattern p = Pattern.compile("join:\\d+:([a-zA-Z_]+):\\d+\\-\\d+");
            Matcher m = p.matcher(activityId);
            if (m.find()) {
                activityId=m.group(1);
            }
        }
        List<PvmActivity> activitiyIds = new ArrayList<PvmActivity>();
        for (ActivityImpl activityImpl : activityList) {
            String id = activityImpl.getId();

            if (activityId.equals(id)) {
                List<PvmTransition> outgoingTransitions = activityImpl.getOutgoingTransitions();//获取某节点所有线路
                List<LinkedHashMap<String, Object>> list = new ArrayList<LinkedHashMap<String, Object>>();
                for (PvmTransition tr : outgoingTransitions) {
                	 LinkedHashMap<String, Object> map1 = new LinkedHashMap<String, Object>();
                    PvmActivity ac = tr.getDestination();//获取线路的终点节点
                    if (ac.getProperty("type").equals("userTask")) {
                        map.put("type", type++);
                        map1.put("id", ac.getId());
                        map1.put("name", ac.getProperty("name"));
                        String conditionText = UrlUtil.sNull(tr.getProperty("conditionText"),"");
                        if (!StringUtils.isNotBlank(conditionText)) {
                            map1.put("conditionText", conditionText);
                        }
                        list.add(map1);
                    } else if (ac.getProperty("type").equals("exclusiveGateway")) {
                        getTaskActivitys(ac.getId(), activityList, type, map);
                    } else {
                        map.put("type", type++);
                        break;
                    }
                }
                if (list.size() > 0)
                    map.put("list", list);
                break;
            }
        }
        return activitiyIds;
    }

	
}
