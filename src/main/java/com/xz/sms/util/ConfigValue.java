/**  
 * @Title: ConfigValue.java
 * @Package com.xz.base.utils
 * @Description: 获取配置属性
 * @author 万书德
 * @date 2013-6-13
 * @version V1.0  
 */
package com.xz.sms.util;

import java.io.*;
import java.util.Properties;

import com.google.common.base.Charsets;
import com.xz.base.utils.ConfigReader;
import com.xz.base.utils.LogHelper;

/**
 * @Description: 获取配置属性
 * 
 */
public class ConfigValue {

	/**
	 * @Fields config : 属性配置文件对象
	 */
	private static Properties config = null;

	/**
	 * 请求地址
	 */
	public static String SMS_URL = "";

	/**
	 * 发送账号
	 */
	public static String SMS_ACCOUNT = "";

	/**
	 * 发送帐号密码
	 */
	public static String SMS_PASSWORD = "";
	
	/**
	 * 是否调试
	 */
	public static Boolean IS_DEBUG = false;
	
	private static ConfigReader reader = null;

	static {
		InputStream in = ConfigValue.class.getClassLoader().getResourceAsStream("sms/config.properties");
		config = new Properties();
		try {
			config.load(new InputStreamReader(in, Charsets.UTF_8));
			reader = new ConfigReader(config);
			in.close();
			init();
		} catch (IOException ex) {
			LogHelper.getLogger().error("未找到配置文件", ex);
		}
	}

	private static void init() {
		SMS_URL = reader.readValue("url");
		SMS_ACCOUNT = reader.readValue("account");
		SMS_PASSWORD = reader.readValue("password");
		IS_DEBUG = reader.readBooleanValue("isDebug");
	} 
}
