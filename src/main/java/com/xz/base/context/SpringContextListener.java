/**   
* @Title: SpringContextListener.java 
* @Package: com.xz.base.context 
* @Description: 
* @author: davidwan
* @date: 2014-8-13 上午10:28:48 
* @version: V1.0   
*/
package com.xz.base.context;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class SpringContextListener implements ServletContextListener{ 
	
	public SpringContextListener() {
        super();
    }
		
	public void contextDestroyed(ServletContextEvent arg0) {
	}
 
	public void contextInitialized(ServletContextEvent event) {
		WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
		SpringContextHolder.setContext(context);
	}

}
