package com.xz.activiti.web.history;

import org.activiti.engine.HistoryService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.nutz.mvc.annotation.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
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
@RequestMapping(value = "/acti/history/task")
public class HistoryTaskController {

    private Logger logger= LoggerFactory.getLogger(HistoryTaskController.class);

    @Resource
    private HistoryService historyService;

    @RequestMapping(value = "")
    public ModelAndView index(){
        ModelAndView mv=new ModelAndView("/private/wf/history/task");
        return mv;
    }

    @RequestMapping(value = "/list")
    @ResponseBody
    public Map<String, Object> list(@RequestParam("page") int curPage, @RequestParam("rows") int pageSize, HttpServletRequest req) {
        HistoricTaskInstanceQuery historicTaskInstanceQuery = historyService.createHistoricTaskInstanceQuery();//.taskAssignee("5")
        long total = historicTaskInstanceQuery.count();
        List<HistoricTaskInstance> list = historicTaskInstanceQuery.orderByTaskCreateTime().desc().listPage((curPage - 1) * pageSize, pageSize);
        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("total", total);
        obj.put("list", list);
        return obj;
    }
}
