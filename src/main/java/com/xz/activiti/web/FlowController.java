package com.xz.activiti.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.nutz.json.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
@Controller
@RequestMapping(value="/acti/flow")
public class FlowController {
	
	Logger logger=LoggerFactory.getLogger(FlowController.class);
	
	@Resource
	private RuntimeService runtimeService;

	@RequestMapping(value="")
	public ModelAndView index(){
		ModelAndView mv=new ModelAndView("/private/wf/flow");
		
		return mv;
	}
	
	@RequestMapping(value="/list")
	@ResponseBody
	public String list(@RequestParam("page") int curPage,@RequestParam("rows") int pageSize,
			HttpServletRequest request){
        ProcessInstanceQuery processInstanceQuery = runtimeService.createProcessInstanceQuery().orderByProcessInstanceId().desc();
        long total = processInstanceQuery.count();
        List<ProcessInstance> list = processInstanceQuery.listPage((curPage - 1) * pageSize, pageSize);
        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("total", total);
        obj.put("list", list);
        String json= Json.toJson(obj);
        return json;
	}
	
	@RequestMapping(value="/end/{processInstanceId}")
	@ResponseBody
	public Map<String, Object> end(@PathVariable("processInstanceId")String processInstanceId) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            runtimeService.deleteProcessInstance(
                    processInstanceId, "end");
            map.put("errcode", 0);
            map.put("errmsg", "");
            return map;
        } catch (Exception e) {
            map.put("errcode", 1);
            map.put("errmsg", e.getMessage());
            return map;
        }
    }
	
	@RequestMapping(value="/suspend/{processInstanceId}")
	@ResponseBody
	public Map<String, Object> suspend(@PathVariable("processInstanceId") String processInstanceId) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            runtimeService.suspendProcessInstanceById(processInstanceId);
            map.put("errcode", 0);
            map.put("errmsg", "");
            return map;
        } catch (Exception e) {
            map.put("errcode", 1);
            map.put("errmsg", e.getMessage());
            return map;
        }
    }
	
	@RequestMapping(value="/active/{processInstanceId}")
	@ResponseBody
	 public Map<String, Object> active(@PathVariable("processInstanceId")String processInstanceId) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            runtimeService.activateProcessInstanceById(processInstanceId);
            map.put("errcode", 0);
            map.put("errmsg", "");
            return map;
        } catch (Exception e) {
            map.put("errcode", 1);
            map.put("errmsg", e.getMessage());
            return map;
        }
    }

	
	
}
