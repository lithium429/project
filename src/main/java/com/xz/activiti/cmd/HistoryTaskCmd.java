package com.xz.activiti.cmd;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import com.xz.project.core.dao.mybatis.ActivitiDaoSqlImpl;
import com.xz.project.core.service.bpm.ActivitiService;
import org.activiti.engine.FormService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xz.project.core.dao.ActivitiDao;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

public class HistoryTaskCmd implements Command<Map> {
	

    ApplicationContext context=new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
    ActivitiService dao=context.getBean(ActivitiService.class);
    ProcessEngine engine=context.getBean(ProcessEngine.class);
    RepositoryService repositoryService=engine.getRepositoryService();

	Logger log=LoggerFactory.getLogger(HistoryTaskCmd.class);
	
	private String taskId;
	
	public HistoryTaskCmd(String taskId) {
        this.taskId = taskId;
    }
	@Override
	public LinkedHashMap execute(CommandContext commandContext){
        LinkedHashMap<String, Object> map=new LinkedHashMap<String, Object>();
        TaskEntity taskEntity = Context.getCommandContext()
                .getTaskEntityManager().findTaskById(taskId);
        if (taskEntity == null) {
            log.debug("cannot find task : " + taskId);
            map.put("errcode", 1);
            map.put("errmsg", "cannot find task : " + taskId);
            return map;
        }
        map.put("errcode", 0);
        map.put("errmsg", "");


        List<Map<String, String>> sqlList=dao.findPassedNode(taskEntity.getProcessInstanceId());
        Map map2=new LinkedHashMap();
        ProcessDefinitionEntity def = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService).getDeployedProcessDefinition(taskEntity.getProcessDefinitionId());
        List<ActivityImpl> activitiList = def.getActivities();
        getTaskActivitys(taskEntity.getTaskDefinitionKey(), activitiList,map2);

        Set<String> key = map2.keySet();

        Map<String,String> realMap=new HashMap();

        for(Map<String,String> keyMap:sqlList){
            keyMap.get("ACT_ID_");
            keyMap.get("ACT_NAME_");
            realMap.put(keyMap.get("ACT_ID_"),keyMap.get("ACT_NAME_"));
        }


        List<LinkedHashMap<String,Object>> list=new ArrayList<LinkedHashMap<String,Object>>();
        for (Iterator it = key.iterator(); it.hasNext();) {
            LinkedHashMap<String,Object> m=new LinkedHashMap<String,Object>();
            String s = (String) it.next();
            //MODIFIY
            if(realMap.get(s)!=null){
                m.put("id",s);
                m.put("name",map2.get(s));
                list.add(m);
            }

        }
        map.put("list",list);
        return map;
    }
	
	
	//获取流程定义的上级所有节点
    
    public static List<PvmActivity> getTaskActivitys(String activityId,List<ActivityImpl> activityList,Map<String,Object> map){
        if(activityId.startsWith("join")){
            //如果是前加签、后加签，替换ActivityId
            Pattern p = Pattern.compile("join:\\d+:([a-zA-Z_]+):\\d+\\-\\d+");
            Matcher m = p.matcher(activityId);
            if (m.find()) {
                activityId=m.group(1);
            }
        }
        List<PvmActivity> activitiyIds=new ArrayList<PvmActivity>();
        for(ActivityImpl activityImpl:activityList){
            String id=activityImpl.getId();
            if(activityId.equals(id)){
                List<PvmTransition> incomingTransitions=activityImpl.getIncomingTransitions();//获取某节点所有线路
                for(PvmTransition tr:incomingTransitions){
                    PvmActivity ac=tr.getDestination();//获取线路的终点节点
                    if(!ac.getProperty("type").equals("userTask")){
                        activitiyIds.addAll(getTaskActivitys(ac.getId(), activityList, map));
                    }else {
                        activitiyIds.add(ac);
                    }
                }
                break;
            }
            map.put(activityImpl.getId(), activityImpl.getProperty("name"));
        }
        return activitiyIds;
    }
	
	
	
	

}
