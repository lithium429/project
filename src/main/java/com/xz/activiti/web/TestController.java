package com.xz.activiti.web;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xz.activiti.cmd.HistoryTaskCmd;
import com.xz.activiti.cmd.JoinTaskCmd;
import com.xz.activiti.cmd.JumpCmd;
import com.xz.activiti.cmd.RollbackTaskCmd;
import com.xz.base.utils.StringUtil;
import com.xz.project.core.dao.ActivitiDao;
import com.xz.project.core.domain.entity.UserRole;
import com.xz.project.core.service.bpm.ActivitiService;
import com.xz.project.core.service.user.UserService;
import org.activiti.engine.*;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.annotation.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="/acti/test")
public class TestController {
	
	Logger log=LoggerFactory.getLogger(TestController.class);

	@Resource
	IdentityService identityService;

	@Resource
	RepositoryService repositoryService;

	@Resource
	RuntimeService runtimeService;

	@Resource
	ManagementService managementService;

	@Resource
	private TaskService taskService;

	@Resource
	ProcessEngine processEngine;

	@Resource
	UserService userService;

	@Resource
	ActivitiService activitiDao;


	@RequestMapping(value="")
	public ModelAndView index(){
		ModelAndView mv=new ModelAndView();
		
		return mv;
	}
	


	@RequestMapping(value = "/start")
	@ResponseBody
	public String start(@RequestParam("key") String key,@RequestParam(value = "param",required = false) String param, HttpServletRequest req) {
		Map resultMap =new HashMap();
		Map terMap=new HashMap();
		try {
			ProcessInstance processInstance = processEngine.getRuntimeService().startProcessInstanceByKey(key);
			Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
			if(param!=null|| StringUtils.isNoneBlank(param)){

				terMap.put("param", param);
				taskService.setAssignee(task.getId(),"1");

				taskService.complete(task.getId(),terMap);

			}else{
				taskService.setAssignee(task.getId(),"1");
				taskService.complete(task.getId());
			}

			resultMap.put("errcode","0");
			resultMap.put("processInstanceId",processInstance.getId());

		}catch (Exception e){
			resultMap.put("errcode","1");
			resultMap.put("errmsg",e.getMessage());
		}

		return Json.toJson(resultMap, JsonFormat.compact());
	}

	@RequestMapping(value = "/list")
	@ResponseBody
	public String list(@RequestParam("uid") String uid,@RequestParam("category") String category,@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize, HttpServletRequest req) {

		List<UserRole> roles = userService.findRoleIdByUser(uid);
		Map terMap=new HashMap();
		terMap.put("category",category);
		terMap.put("userId", uid);
		List terList=new ArrayList();
		String assignee="";
		for(UserRole role:roles){
			terList.add(role.getRole_id());
			assignee+="OR ASSIGNEE_='ROLE("+role.getRole_id()+")'";
		}
		terMap.put("terList", terList);
		int total = activitiDao.findArtaskCount(terMap);

		log.info("total::"+total);


		//String taskSql="SELECT * FROM act_ru_task WHERE CATEGORY_="+category+"AND ( ASSIGNEE_ = "+uid + assignee+")";
		Sql task_sql= Sqls.create("SELECT * FROM act_ru_task WHERE CATEGORY_=@category AND ( ASSIGNEE_=@userId  $assignee ) ORDER BY create_time_ desc");
		task_sql.params().set("category",category);
		task_sql.params().set("userId", uid);
		task_sql.vars().set("assignee", assignee);

		log.info("task_sql:"+task_sql);

		List<Task> list=taskService.createNativeTaskQuery().sql(task_sql.toString()).listPage((pageNum - 1) * pageSize, pageSize);
		//List<Map> commentList=activitiDao.findHistComment();


		Map resultMap=new HashMap();
		resultMap.put("errcode","0");
		resultMap.put("errmsg","");
		resultMap.put("total",total);
		resultMap.put("list",list);

		return Json.toJson(resultMap, JsonFormat.compact());
	}

	@RequestMapping(value = "/complete")
	@ResponseBody
	public String complete(@RequestParam("taskId") String taskId,@RequestParam("uid") String uid,@RequestParam(value = "param",required = false) String param,@RequestParam(value = "comment",required = false) String comment, HttpServletRequest req) {
		Map map=new HashMap();
		Map terMap=new HashMap();
		try {
			Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
			if(param!=null|| StringUtils.isNoneBlank(param)){
				terMap.put("param",param);
				taskService.setAssignee(taskId,uid);
				taskService.addComment(taskId,task.getProcessInstanceId(),comment);
				taskService.complete(task.getId(),terMap);
			}else{
				taskService.setAssignee(taskId,uid);
				taskService.complete(task.getId());
			}

			map.put("errcode","0");
			map.put("errmsg","success");
		}catch (Exception e){
			map.put("errcode","1");
			map.put("errmsg",e.getMessage());
		}
		return Json.toJson(map, JsonFormat.compact());

	}


	@RequestMapping(value = "/startjoinsign")
	@ResponseBody
	public String startjoinsign(@RequestParam(value = "key") String key, HttpServletRequest req) {
		Map resultMap =new HashMap();
		Map terMap=new HashMap();
		List list=new ArrayList();
		list.add("4");
		list.add("2");
		list.add("3");

		//String users[] =(String[])list.toArray(new String[list.size()]);
		try {
			terMap.put("users",list);
			ProcessInstance processInstance = processEngine.getRuntimeService().startProcessInstanceByKey(key);
			Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
			taskService.setAssignee(task.getId(),"1");
			taskService.complete(task.getId(),terMap);

			resultMap.put("errcode","0");
			resultMap.put("processInstanceId",processInstance.getId());

		}catch (Exception e){
			resultMap.put("errcode","1");
			resultMap.put("errmsg",e.getMessage());
		}

		return Json.toJson(resultMap, JsonFormat.compact());
	}

	@RequestMapping(value = "/historycmd")
	@ResponseBody
	public Object test4(@RequestParam("taskId") String taskId) {
		Command<Map> cmd = new HistoryTaskCmd(taskId);
		return managementService.executeCommand(cmd);
	}

	@RequestMapping(value = "/rollback")
	@ResponseBody
	public Object rollback(@RequestParam("taskId") String taskId,@RequestParam(value = "activityId",required = false) String activityId,HttpServletRequest request){
		Command<Map> cmd= new RollbackTaskCmd(taskId,activityId);
		Map map = managementService.executeCommand(cmd);
		return map;
	}

	@RequestMapping(value = "/jump")
	@ResponseBody
	public Object jumpCmd(@RequestParam("taskId") String taskId,@RequestParam(value = "activityId") String activityId,@RequestParam(value = "jumpOrigin",required = false) String jumpOrigin){
		Command<Map> cmd=new JumpCmd(taskId,activityId);
		Map map = managementService.executeCommand(cmd);
		return map;
	}

	@RequestMapping(value = "/joinTask")
	@ResponseBody
	public Object joinTask(@RequestParam("isAfter") boolean isAfter,@RequestParam("taskId") String taskId,@RequestParam(value = "users",required = false) String json){
		System.out.println(isAfter);

		System.out.println(json);


		Command<Map> cmd= new JoinTaskCmd(isAfter,taskId,new String[]{"5"});
		Map map = managementService.executeCommand(cmd);
		return map;
	}

}
