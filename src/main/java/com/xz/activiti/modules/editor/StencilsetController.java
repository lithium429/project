package com.xz.activiti.modules.editor;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.ActivitiException;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/acti/editor")
public class StencilsetController {
	
	
	@RequestMapping(value="/stencilset")
	@ResponseBody
	 public String getStencilset() {
		System.out.println("stencilset");
		  InputStream stencilsetStream = this.getClass().getClassLoader().getResourceAsStream("stencilset.json");
	        try {
	            return IOUtils.toString(stencilsetStream);
	        } catch (Exception e) {
	            throw new ActivitiException("Error while loading stencil set", e);
	        }
	    }
}
