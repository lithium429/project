package com.xz.activiti.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.nutz.json.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="/acti/task")
public class TaskController {
	
	Logger logger=LoggerFactory.getLogger(TaskController.class);
	
	@Resource
	private TaskService taskService;
	
	@RequestMapping(value="")
	public ModelAndView index(){
		ModelAndView mv=new ModelAndView("/private/wf/task");
		
		return mv;
	}
	
	@RequestMapping(value="/list")
	@ResponseBody
	public String list(@RequestParam("page") int curPage, @RequestParam("rows") int pageSize, HttpServletRequest req) {

        TaskQuery taskQuery = taskService.createTaskQuery().orderByTaskCreateTime().desc();
        long total = taskQuery.count();
        List<Task> list = taskQuery.listPage((curPage - 1) * pageSize, pageSize);
        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("total", total);
        obj.put("list", list);
		return Json.toJson(obj);
    }
}
