package com.xz.activiti.test;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")  
public class ActivitiTest {
	
	@Resource
	private ProcessEngine engine;
	
	@Resource
	private RepositoryService repositoryService;
	
	@Resource
	private RuntimeService runtimeService;
	
	@Resource
	private TaskService taskService;
	
	@Resource
	private HistoryService historyService;
	
	@Resource
	private ManagementService managementService;
	
	
	@Test
	public void leaveProcess(){
//		Deployment deploy = repositoryService.createDeployment().name("Vacation").addClasspathResource("diagrams/Vacation.bpmn").deploy();
//		System.out.println("id:"+deploy.getId()+"\r\n"+"name:"+deploy.getName());
//
//		ProcessInstance pi = runtimeService.startProcessInstanceByKey("leave");
//		System.out.println("id:"+pi.getId()+",流程实例id:"+pi.getProcessInstanceId()+",流程定义id:"+pi.getProcessDefinitionId());
//		Task task = taskService.createTaskQuery().processInstanceId(pi.getProcessInstanceId()).singleResult();
//		System.out.println("taskID:"+task.getId()+",name"+task.getName());
//		Map<String,Object> map=new HashMap<String,Object>();
//		map.put("day", 3);
//		taskService.complete(task.getId(), map);
//
//		Task task2 = taskService.createTaskQuery().processInstanceId(pi.getProcessInstanceId()).singleResult();
//		if(task2!=null){
//			System.out.println("taskID:"+task2.getId()+",name"+task2.getName());
//			taskService.complete(task2.getId(),map);
//			System.out.println("task over");
//		}else{
//			System.out.println("task over");
//		}
		
	}
	
}
