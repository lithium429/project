package com.xz.activiti.web.history;


import org.activiti.engine.HistoryService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
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
@RequestMapping(value = "/acti/history/node")
public class HistoryActivityController {

    private Logger logger= LoggerFactory.getLogger(HistoryActivityController.class);

    @Resource
    private HistoryService historyService;

    @RequestMapping(value = "")
    public ModelAndView index(){
        ModelAndView mv=new ModelAndView("/private/wf/history/activity");
        return mv;
    }

    @RequestMapping(value = "/list")
    @ResponseBody
    public Map<String, Object> list(@RequestParam("page") int curPage, @RequestParam("rows") int pageSize, HttpServletRequest req) {
        HistoricActivityInstanceQuery historicActivityInstanceQuery = historyService.createHistoricActivityInstanceQuery();
        long total = historicActivityInstanceQuery.count();
        List<HistoricActivityInstance> list = historicActivityInstanceQuery.orderByHistoricActivityInstanceStartTime().desc().listPage((curPage - 1) * pageSize, pageSize);
        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("total", total);
        obj.put("list", list);
        return obj;
    }
}
