package com.xz.activiti.web.history;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.history.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Controller
@RequestMapping(value = "/acti/history/flow")
public class HistoryFlowController {

    private Logger logger= LoggerFactory.getLogger(HistoryFlowController.class);

    @Resource
    private RuntimeService runtimeService;

    @Resource
    private HistoryService historyService;

    @RequestMapping(value = "")
    public ModelAndView index(){
        ModelAndView mv=new ModelAndView("/private/wf/history/flow");
        return mv;
    }

    @RequestMapping(value = "/list")
    @ResponseBody
    public Map<String, Object> list(@RequestParam("page") int curPage, @RequestParam("rows") int pageSize, HttpServletRequest req) {
        HistoricProcessInstanceQuery historicProcessInstanceQuery = historyService.createHistoricProcessInstanceQuery().orderByProcessInstanceId().desc();
        long total = historicProcessInstanceQuery.count();
        List<HistoricProcessInstance> list = historicProcessInstanceQuery.orderByProcessInstanceStartTime().desc().listPage((curPage - 1) * pageSize, pageSize);
        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("total", total);
        obj.put("list", list);
        return obj;
    }

    @RequestMapping(value = "/list/task")
    @ResponseBody
    public Map<String, Object> listtask(@RequestParam("processInstanceId") String processInstanceId, @RequestParam("page") int curPage, @RequestParam("rows") int pageSize, HttpServletRequest req) {
        HistoricTaskInstanceQuery historicTaskInstanceQuery = historyService.createHistoricTaskInstanceQuery().processInstanceId(processInstanceId);
        long total = historicTaskInstanceQuery.count();
        List<HistoricTaskInstance> list = historicTaskInstanceQuery.orderByTaskCreateTime().desc().listPage((curPage - 1) * pageSize, pageSize);

        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("total", total);
        obj.put("list", list);
        return obj;
    }

    @RequestMapping(value = "/list/variable")
    @ResponseBody
    public Map<String, Object> listvariable(@RequestParam("processInstanceId") String processInstanceId, @RequestParam("page") int curPage, @RequestParam("rows") int pageSize, HttpServletRequest req) {
        HistoricVariableInstanceQuery historicVariableInstanceQuery = historyService.createHistoricVariableInstanceQuery().processInstanceId(processInstanceId);
        long total = historicVariableInstanceQuery.count();
        List<HistoricVariableInstance> list = historicVariableInstanceQuery.listPage((curPage - 1) * pageSize, pageSize);
        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("total", total);
        obj.put("list", list);
        return obj;
    }

    @RequestMapping(value = "/view/{processInstanceId}")
    public ModelAndView view(@PathVariable("processInstanceId") String processInstanceId,HttpServletRequest request){
        request.setAttribute("processInstanceId",processInstanceId);
        ModelAndView mv=new ModelAndView("/private/wf/history/flowView");
        return mv;
    }
}

