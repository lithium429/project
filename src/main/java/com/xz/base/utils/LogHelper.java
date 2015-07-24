/**  
* @Title: LogHelper.java
* @Package com.xz.base.utils
* @Description: 日志辅助类
* @author 万书德
* @date 2013-6-13
* @version V1.0  
*/
package com.xz.base.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description: 日志辅助类
 *
 */
public class LogHelper {
	private static Logger logger = LoggerFactory.getLogger(LogHelper.class); 
	
	public static Logger getLogger(){
		return logger;
	}
}
