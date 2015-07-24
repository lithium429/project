/**  
 * @author WangMengzhong
 * @date 2013-12-4
 * @QQ 821501431
 * @mail 821501431@qq.com
 * @version V1.0  
 */
package com.xz.project.core.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.xz.base.utils.LogHelper;

public class URLContants {
	/**
	 * @Fields config : 属性配置文件对象
	 */
	private static Properties config = null;

	public static String URL = "ybb.com";
	public static String DOMAIN = "ybb.com";
	public static String ROOT_PATH = "/ybb";
	public static final String ROOT_DIR = "views/web/html/";
	public static final String PAGE_NAME = "index.html";
	public static String CHANNEL_NAMES = "";

	static {
		InputStream in = URLContants.class.getClassLoader().getResourceAsStream("config.properties");
		config = new Properties();
		try {
			config.load(in);
			in.close();
			init();
		} catch (IOException e) {
			LogHelper.getLogger().error("URLContants:constructors 未找到配置文件，" + e.toString());
			System.out.println("未找到配置文件！");
		}
	}

	private static void init() {
		URL = readValue("url");
		DOMAIN = readValue("domain");
		ROOT_PATH = readValue("rootPath");
		CHANNEL_NAMES = readValue("channelNames");
	}

	/**
	 * @Title: readValue
	 * @Description: 读取属性配置文件中的信息
	 * @param key
	 * @return String
	 */
	public static String readValue(String key) {
		try {
			String value = config.getProperty(key);
			return value;
		} catch (Exception e) {
			LogHelper.getLogger().error("URLContants:readValue 读取配置信息出错，" + e.toString());
			return null;
		}
	}
}
