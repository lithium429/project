/**   
 * @Title: SpringContextHolder.java 
 * @Package: com.xz.base.context 
 * @Description: 
 * @author: davidwan
 * @date: 2014-8-13 上午10:30:25 
 * @version: V1.0   
 */
package com.xz.base.context;

import org.springframework.web.context.WebApplicationContext;

public class SpringContextHolder {

	private static WebApplicationContext context;
	
	private static final Object lockObj = new Object();

	public static WebApplicationContext getContext() {
		return context;
	}

	public static void setContext(WebApplicationContext ctx) {
		if (context == null) {
			synchronized (lockObj) {
				if (context == null) {
					context = ctx;
				}
			}
		}
	}
}
