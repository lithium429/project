/**   
 * @Title: DebugInterceptor.java 
 * @Package: com.xz.base.utils 
 * @Description: 
 * @author: davidwan
 * @date: 2014-12-5 下午6:29:43 
 * @version: V1.0   
 */
package com.xz.base.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView; 

public class DebugInterceptor implements HandlerInterceptor {
	private boolean debug = true;

	/**
	 * 日志记录器
	 */
	private Logger logger = LoggerFactory.getLogger(getClass());

	/* 
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// TODO Auto-generated method stub
		
		return true;
	}

	/* 
	 */
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	/* 
	 */
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Content-Encoding", Constants.UTF8);
		response.setContentLength(1000);
		
		if (debug) {
			logger.info(response.getHeader("Location"));
		}
		
	}

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	} 

}
