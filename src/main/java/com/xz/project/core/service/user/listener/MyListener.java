/**   
 * @Title: MyApplicationListener.java 
 * @Package: com.xz.project.core.service.user.listener 
 * @Description: 
 * @author: davidwan
 * @date: 2014-8-27 下午5:00:23 
 * @version: V1.0   
 */
package com.xz.project.core.service.user.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.activiti.engine.ProcessEngineConfiguration;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.xz.base.utils.LogHelper;
import com.xz.project.core.service.user.OnlineUser;

public class MyListener implements ServletContextListener, HttpSessionListener {

	/*
	 * 应用程序启动
	 */
	public void contextInitialized(ServletContextEvent sce) {
		OnlineUser.getInstance().init(sce.getServletContext());
		
	}

	/*
	 * 应用程序终止
	 */
	public void contextDestroyed(ServletContextEvent sce) {

	}

	/*
	 * 会话创建
	 */
	public void sessionCreated(HttpSessionEvent se) {

	}

	/*
	 * 会话结束
	 */
	public void sessionDestroyed(HttpSessionEvent se) {
		// 会话过期，从在线用户列表中移除该会话对应的用户
		try {
			HttpSession session = se.getSession();
			Integer userId = (Integer) session.getAttribute(session.getId());
			OnlineUser.getInstance().remove(userId, se.getSession());
		} catch (Exception ex) {
			LogHelper.getLogger().error("移除在线用户出错", ex);
		}
	}

}
