package com.xz.activiti.modules.editor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.util.SystemOutLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.xz.activiti.utils.UrlUtil;


@Controller
@RequestMapping(value="/acti/model" )
public class EditorController {
	Logger logger=LoggerFactory.getLogger(EditorController.class);

	@Resource
	private RepositoryService repositoryService;
	
	@RequestMapping(value="/modeler",method = RequestMethod.GET)
	public ModelAndView modeler(@RequestParam("modelId") String modelId,HttpServletRequest request){
		ModelAndView mv=new ModelAndView("/activiti/template/private/wf/modeler");
		request.setAttribute("modelId", modelId);
		return mv;
		
	}
	
	@RequestMapping(value="/json/{modelId}",method=RequestMethod.GET)
	@ResponseBody
	public ObjectNode getEditorJson(@PathVariable("modelId") String modelId,HttpServletResponse response){
		ObjectNode modelNode = null;

        Model model = repositoryService.getModel(modelId);

        if (model != null) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                if (StringUtils.isNotEmpty(model.getMetaInfo())) {
                    modelNode = (ObjectNode) objectMapper.readTree(model.getMetaInfo());
                } else {
                    modelNode = objectMapper.createObjectNode();
                    modelNode.put(ModelDataJsonConstants.MODEL_NAME, model.getName());
                }
                modelNode.put(ModelDataJsonConstants.MODEL_ID, model.getId());
                ObjectNode editorJsonNode = (ObjectNode) objectMapper.readTree(
                        new String(repositoryService.getModelEditorSource(model.getId()), "utf-8"));
                modelNode.put("model", editorJsonNode);

            } catch (Exception e) {
                logger.error("Error creating model JSON", e);
                throw new ActivitiException("Error creating model JSON", e);
            }
        }
		return modelNode;
	}	
	
	@RequestMapping(value="/save/{modelId}",method=RequestMethod.PUT)
	public void saveModel(@PathVariable("modelId") String modelId,HttpServletRequest req){
		{
	        try {
	            Map<String, Object> map = new HashMap<String, Object>();
	            String body = UrlUtil.readStreamParameter(req.getInputStream());
	            String[] str = StringUtils.split(body, "&");
	            for (int i = 0; i < str.length; i++) {
	                String[] temp = StringUtils.split(str[i], "=");
	                if (temp.length > 1) {
	                    map.put(temp[0], URLDecoder.decode(temp[1], "utf-8"));
	                } else {
	                    map.put(temp[0], "");
	                }
	            }
	            Model model = repositoryService.getModel(modelId);
	            ObjectMapper objectMapper = new ObjectMapper();

	            ObjectNode modelJson = (ObjectNode) objectMapper.readTree(model.getMetaInfo());
	            modelJson.put(ModelDataJsonConstants.MODEL_NAME, UrlUtil.sNull(map.get("name"), ""));
	            modelJson.put(ModelDataJsonConstants.MODEL_DESCRIPTION, UrlUtil.sNull(map.get("description"), ""));
	            model.setMetaInfo(modelJson.toString());
	            model.setName(UrlUtil.sNull(map.get("name"), ""));

	            repositoryService.saveModel(model);
	            

	            repositoryService.addModelEditorSource(model.getId(), UrlUtil.sNull(map.get("json_xml"), "").getBytes("utf-8"));

	            InputStream svgStream = new ByteArrayInputStream(UrlUtil.sNull(map.get("svg_xml"),"").getBytes("utf-8"));
	            TranscoderInput input = new TranscoderInput(svgStream);

	            PNGTranscoder transcoder = new PNGTranscoder();
	            // Setup output
	            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
	            TranscoderOutput output = new TranscoderOutput(outStream);

	            // Do the transformation
	            transcoder.transcode(input, output);
	            final byte[] result = outStream.toByteArray();
	            repositoryService.addModelEditorSourceExtra(model.getId(), result);
	            outStream.close();

	        } catch (Exception e) {
	        	logger.error("Error saving model", e);
	            throw new ActivitiException("Error saving model", e);
	        }
	    }
	}
	
	@RequestMapping(value="/editor/stencilset",method=RequestMethod.GET)
	public String getStencilset() {
        InputStream stencilsetStream = this.getClass().getClassLoader().getResourceAsStream("stencilset.json.bak");
        try {
            return IOUtils.toString(stencilsetStream);
        } catch (Exception e) {
            throw new ActivitiException("Error while loading stencil set", e);
        }
    }
	
	
}
