package com.xz.activiti.test;

import java.text.DecimalFormat;
import java.util.*;

import javax.annotation.Resource;

import com.xz.activiti.ext.CustomUserEntityManagerFactory;
import com.xz.project.core.dao.mybatis.ActivitiDaoSqlImpl;
import com.xz.project.core.domain.entity.Role;
import com.xz.project.core.service.bpm.ActivitiService;
import org.activiti.engine.*;
import org.activiti.engine.identity.Group;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.persistence.entity.HistoricIdentityLinkEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.persistence.entity.UserEntityManager;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.DelegationState;
import org.activiti.engine.task.Task;
import org.activiti.image.impl.DefaultProcessDiagramCanvas;
import org.apache.commons.lang.math.NumberUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xz.activiti.ext.CustomGroupEntityManager;
import com.xz.activiti.ext.CustomUserEntityManager;
import com.xz.activiti.modules.editor.EditorController;
import com.xz.activiti.modules.pojo.Wf_model_category;
import com.xz.activiti.utils.ACommonUtil;
import com.xz.project.core.dao.ActivitiDao;
import com.xz.project.core.domain.entity.User;
import com.xz.project.core.domain.entity.UserRole;
import com.xz.project.core.service.user.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")  
public class DaoTest {
	@Resource
	UserService userService;
	
	@Resource
	ActivitiDao dao;

	@Resource
	RuntimeService runtimeService;

	@Resource
	TaskService taskService;


	Logger logger=LoggerFactory.getLogger(DaoTest.class);
	@Test
	public void daotest1(){
		List<UserRole> list=userService.findRoleIdByUser("1");
		for(UserRole ur:list){
			System.out.println(ur.getRole_id());
		}
	}
	
	@Test
	public void daotest2(){
//		Wf_model_category category=new Wf_model_category();
//    	category.setLocation(3);
//    	category.setName("23213");
//    	category.setId(UUID.randomUUID().toString());
//		dao.createCate(category);
	}
	


	
	@Test 
	public void daotest3(){
		List<Role> list = userService.findRoleListByUser("1");
		System.out.println(list.size());
	}

	@Test
	public void daotest4(){
//		Map variables=new HashMap();
//		variables.put("user", "2");
//		runtimeService.startProcessInstanceByKey("del", variables);
//		Task task = taskService.createTaskQuery().taskAssignee("2").singleResult();
//		System.out.println("::" + task.getProcessInstanceId() + "::" + task.getAssignee());
//		taskService.delegateTask(task.getId(), "3");
//		Task task1 = taskService.createTaskQuery().taskDelegationState(DelegationState.PENDING).taskAssignee("3").singleResult();
//		String owner = task1.getOwner();
//		System.out.println("owner:" + owner + " assignee:" + task1.getAssignee());
//		taskService.resolveTask(task1.getId());
//		Task task2 = taskService.createTaskQuery().taskDelegationState(DelegationState.RESOLVED).taskAssignee("2").singleResult();
//		System.out.println("委派完处理完的任务:"+task2.getAssignee()+"owner:"+task2.getOwner());
//		taskService.complete(task2.getId());
	}

	@Test
	public void daotest5(){
		//ProcessInstance test = runtimeService.startProcessInstanceByKey("test");
//		Task task = taskService.createTaskQuery().taskAssignee("ROLE(2)").singleResult();
//		System.out.println(task.getId());
//		taskService.complete(task.getId());
//		Task task1 = taskService.createTaskQuery().taskAssignee("ROLE(3)").singleResult();
//		System.out.println(task1.getId());
//		taskService.complete(task1.getId());
	}

	@Test
	public void daotest6(){
	}

	@Resource
	ActivitiService service;

}
