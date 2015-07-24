package com.xz.activiti.web;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xz.activiti.utils.ACommonUtil;
import com.xz.base.utils.StringUtil;
import com.xz.project.core.service.bpm.ActivitiService;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ModelQuery;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.nutz.dao.Cnd;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
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

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.xz.activiti.modules.pojo.Wf_model_category;
import com.xz.project.core.dao.ActivitiDao;

@Controller
@RequestMapping(value="/acti/model")
public class ModelController {
	
	Logger logger=LoggerFactory.getLogger(ModelController.class);
	
	@Resource
	private ActivitiService dao;
	
	@Resource
	private RepositoryService repositoryService;
	
	@RequestMapping(value="")
	public ModelAndView index(){
		ModelAndView mv=new ModelAndView("/private/wf/model");
		
		return mv;
	}
	
	@RequestMapping(value="/gotocategoryadd")
	public ModelAndView toaddc(@RequestParam("categoryId") String categoryId, HttpServletRequest req) {
        req.setAttribute("categoryId", categoryId);
        List<Wf_model_category> list=dao.findCategorywf();
        req.setAttribute("list", list);
        ModelAndView mv=new ModelAndView("/private/wf/modelCategoryAdd");
        return mv;
    }
	
	@RequestMapping(value="/addcategory")
	@ResponseBody
	public Map<String, Object> addc(@RequestParam(value = "p_categoryId",required = false) String p_categoryId, @RequestParam("name") String name,HttpServletRequest req) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {

        	Wf_model_category category=new Wf_model_category();

        	category.setName(name);
            if(p_categoryId==null|| StringUtil.equels(p_categoryId,"")){
                String location=dao.findCateMaxLaById("");
                String newCid=dao.findCateMaxId();
                int i = NumberUtils.toInt(newCid) + 1;
                String id=new DecimalFormat("0000")
                        .format(i);
                category.setId(id);
                category.setLocation(Integer.valueOf(location));
            }else{
                String cid=dao.findCateIdFuzzy(p_categoryId);
                if(cid==null||StringUtil.equels(cid,"")){
                    cid=p_categoryId+"0001";
                    category.setId(cid);
                }else{
                    String str = cid.substring(4, cid.length());
                    int i = NumberUtils.toInt(str) + 1;
                    String id=new DecimalFormat("0000")
                            .format(i);
                  //  cid.substring(0,3);
                    category.setId(cid.substring(0,4)+id);
                    String location=dao.findCateMaxLaById(cid);
                    category.setLocation(Integer.valueOf(location));
                }
            }
        	dao.createCate(category);
            map.put("errcode", 0);
            map.put("errmsg", "");
        } catch (Exception e) {
            map.put("errcode", 1);
            map.put("errmsg", e.getMessage());
        }
        return map;
    }




    @RequestMapping(value = "/toupdcategory")
    public ModelAndView toupdatec(@RequestParam("categoryId") String categoryId, HttpServletRequest req) {
        ModelAndView mv=new ModelAndView("/private/wf/modelCategoryUpdate");
        Wf_model_category cate=dao.findCateById(categoryId);
        req.setAttribute("categoryId", categoryId);
        req.setAttribute("p_categoryId", categoryId.length() > 4 ? categoryId.substring(0, categoryId.length() - 4) : "");
        req.setAttribute("name",cate.getName());
        req.setAttribute("list", dao.findCategorywf());
        return mv;
    }
	
	@RequestMapping(value="/updcategory")
	@ResponseBody
	public Map<String, Object> updatec(@RequestParam("categoryId") String categoryId, @RequestParam("name") String name) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
        	Map terMap=new HashMap();
        	terMap.put("categoryId", categoryId);
        	terMap.put("name", name);
            dao.modifyCateById(terMap);
            map.put("errcode", 0);
            map.put("errmsg", "");
        } catch (Exception e) {
            map.put("errcode", 1);
            map.put("errmsg", e.getMessage());
        }
        return map;
    }
	
	@RequestMapping(value="/delcategory")
	@ResponseBody
	 public Map<String, Object> deletec(@RequestParam("categoryId") String categoryId) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
//            daoCtl.delete(dao,Wf_model_category.class,Cnd.where("id","like",categoryId+"%"));
//            Sql sql=Sqls.create("update act_re_model set CATEGORY_='' where CATEGORY_=@a");
//            sql.params().set("a",categoryId);
//            daoCtl.exeUpdateBySql(dao,sql);
        	dao.removeCateById(categoryId);
        	dao.modifyArModelById(categoryId);
            map.put("errcode", 0);
            map.put("errmsg", "");
        } catch (Exception e) {
            map.put("errcode", 1);
            map.put("errmsg",e.getMessage());
        }
        return map;
    }
	
	@RequestMapping(value="/gotoaddmodel")
	public ModelAndView toadd(@RequestParam("categoryId") String categoryId, HttpServletRequest req) {
        ModelAndView mv=new ModelAndView("/private/wf/modeladd");
        req.setAttribute("categoryId", categoryId);
        List<Wf_model_category> list = dao.findCategorywf();
        req.setAttribute("list", list);
        return mv;
    }
	
	@RequestMapping(value="/addmodel")
	@ResponseBody
	public Map<String, Object> addModel(@RequestParam("categoryId") String categoryId, @RequestParam("name") String name, @RequestParam("key") String key, @RequestParam(value = "description",required = false) String description, HttpServletRequest req) throws UnsupportedEncodingException {
        Map<String, Object> map = new HashMap<String, Object>();
        ObjectMapper objectMapper = new ObjectMapper();

        Model modelData = repositoryService.newModel();

        ObjectNode modelObjectNode = objectMapper.createObjectNode();
        modelObjectNode.put("category", categoryId);
        modelObjectNode.put("key", key);
        modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, name);
        modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
        description = StringUtils.defaultString(description);
        modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
        modelData.setMetaInfo(modelObjectNode.toString());
        modelData.setKey(key);
        modelData.setName(name);
        modelData.setCategory(categoryId);
        modelData.setKey(StringUtils.defaultString(key));

        repositoryService.saveModel(modelData);
        ObjectNode editorNode = objectMapper.createObjectNode();
        editorNode.put("resourceId", modelData.getId());
        ObjectNode properties = objectMapper.createObjectNode();
        properties.put("process_id", key);
        properties.put("process_namespace", categoryId);
        properties.put("name", name);
        properties.put("documentation", description);
        editorNode.put("properties", properties);
        ObjectNode stencilSetNode = objectMapper.createObjectNode();
        stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
        editorNode.put("stencilset", stencilSetNode);
        repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes("utf-8"));
        map.put("errcode", 0);
        map.put("errmsg", "");
        return map;
    }
	
	@RequestMapping(value="/gotocopymodel") //view
	 public ModelAndView tocopy(@RequestParam("modelId") String modelId, HttpServletRequest req) {
        ModelAndView mv=new ModelAndView("/private/wf/modelAdd");
        Model modelData = repositoryService.getModel(modelId);
        req.setAttribute("modelId", modelId);
        req.setAttribute("categoryId", modelData.getCategory());
        req.setAttribute("name", modelData.getName());
        req.setAttribute("key", modelData.getKey());
        NutMap nutMap = Json.fromJson(NutMap.class, Lang.inr(modelData.getMetaInfo()));
        req.setAttribute("description", Strings.sNull(nutMap.getString("description")));
        List<Wf_model_category> list = dao.findCategorywf();
        req.setAttribute("list", list);
        return mv;
    }
	
	@RequestMapping(value="/copymodel")
	@ResponseBody
	public Map<String, Object> copy(@RequestParam("modelId") String modelId, @RequestParam("categoryId") String categoryId, @RequestParam("name") String name, @RequestParam("key") String key, @RequestParam(value = "description",required = false) String description, HttpServletRequest req) throws UnsupportedEncodingException {
        Map<String, Object> map = new HashMap<String, Object>();
        Model modelData = repositoryService.getModel(modelId);
        if (modelData.getKey().equals(key)) {
            map.put("errcode", 1);
            map.put("errmsg", "Key已存在!");
            return map;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        Model modelData2 = repositoryService.newModel();
        ObjectNode modelObjectNode = objectMapper.createObjectNode();
        modelObjectNode.put("category", categoryId);
        modelObjectNode.put("key", key);
        modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, name);
        modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
        description = StringUtils.defaultString(description);
        modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
        modelData2.setMetaInfo(modelObjectNode.toString());
        modelData2.setKey(key);
        modelData2.setName(name);
        modelData2.setCategory(categoryId);
        modelData2.setKey(StringUtils.defaultString(key));
        repositoryService.saveModel(modelData2);
        NutMap nutMap = Json.fromJson(NutMap.class, Lang.inr(new String(repositoryService.getModelEditorSource(modelId), "utf-8")));
        nutMap.put("resourceId", modelData2.getId());
        NutMap properties = nutMap.getAs("properties", NutMap.class);
        properties.put("process_id", key);
        properties.put("process_namespace", categoryId);
        properties.put("name", name);
        properties.put("documentation", description);
        nutMap.put("properties", properties);
        repositoryService.addModelEditorSource(modelData2.getId(), Json.toJson(nutMap, JsonFormat.compact()).getBytes("utf-8"));
        map.put("errcode", 0);
        map.put("errmsg", "");
        return map;
    }
	
	@RequestMapping(value="/deploy/{modelId}")
	@ResponseBody
	public Map<String, Object> deploy(@PathVariable("modelId") String modelId) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            Model modelData = repositoryService.getModel(modelId);
            ObjectNode modelNode = (ObjectNode) new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelData.getId()));
            byte[] bpmnBytes = null;

            BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
            bpmnBytes = new BpmnXMLConverter().convertToXML(model, "utf-8");

            String processName = modelData.getName() + ".bpmn20.xml";
            Deployment deployment = repositoryService.createDeployment().name(modelData.getName()).category(modelData.getCategory()).addString(processName, new String(bpmnBytes)).deploy();
            modelData.setDeploymentId(deployment.getId());
            repositoryService.saveModel(modelData);
            map.put("errcode", 0);
            map.put("errmsg", "部署成功，部署ID=" + deployment.getId());

        } catch (Exception e) {
            map.put("errcode", 1);
            map.put("errmsg", "根据模型部署流程失败：modelId=" + modelId + "\r\n" + e.getMessage());
        }
        return map;
    }
	
	@RequestMapping(value="/export/{modelId}")
	public void export(@PathVariable("modelId")String modelId, HttpServletResponse response) {
        try {
            Model modelData = repositoryService.getModel(modelId);
            BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
            JsonNode editorNode = new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelData.getId()));
            BpmnModel bpmnModel = jsonConverter.convertToBpmnModel(editorNode);
            BpmnXMLConverter xmlConverter = new BpmnXMLConverter();
            byte[] bpmnBytes = xmlConverter.convertToXML(bpmnModel);
            ByteArrayInputStream in = new ByteArrayInputStream(bpmnBytes);
            IOUtils.copy(in, response.getOutputStream());
            String filename = bpmnModel.getMainProcess().getId() + ".bpmn20.xml";
            response.setHeader("Content-Disposition", "attachment; filename=" + filename);
            response.flushBuffer();
        } catch (Exception e) {
            logger.error("导出model的xml文件失败：modelId=" + modelId, e);
        }
    }
	
	@RequestMapping(value="/delete/{modelId}")
	@ResponseBody
	public Map<String, Object> delete(@PathVariable("modelId") String modelId) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            repositoryService.deleteModel(modelId);
            map.put("errcode", 0);
            map.put("errmsg", "删除成功，模型ID=" + modelId);
        } catch (Exception e) {
            map.put("errcode", 1);
            map.put("errmsg", "删除模型失败：modelId=" + modelId + "\r\n" + e.getMessage());
        }
        return map;
    }

    @RequestMapping(value = "/list")
    @ResponseBody
    public Map<String, Object> list(@RequestParam(value = "categoryId",required = false) String categoryId, @RequestParam(value = "page",required = false) int curPage, @RequestParam(value = "rows",required = false) int pageSize, @RequestParam(value = "keyword",required = false) String keyword) {
        List<Model> list;
        long total;
        ModelQuery modelQuery = repositoryService.createModelQuery();
        ModelQuery modelQuery2;
        if (!Strings.isEmpty(categoryId) && !Strings.isEmpty(keyword)) {
            modelQuery2 = modelQuery.modelCategory(categoryId).modelNameLike(keyword);
        } else if (!Strings.isEmpty(categoryId)) {
            modelQuery2 = modelQuery.modelCategory(categoryId);
        } else if (!Strings.isEmpty(keyword)) {
            modelQuery2 = modelQuery.modelNameLike(keyword);
        } else {
            modelQuery2 = modelQuery;
        }
        total = modelQuery2.count();
        list = modelQuery2.orderByCreateTime().desc().listPage((curPage - 1) * pageSize, pageSize);
        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("total", total);
        obj.put("list", list);
        return obj;
    }

}
