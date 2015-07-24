/**   
 * @Title: ConfigReader.java 
 * @Package: com.xz.base.utils 
 * @Description: 配置文件读取器
 * @author: davidwan
 * @date: 2015-2-14 上午10:08:10 
 * @version: V1.0   
 */
package com.xz.base.utils;

import java.util.Properties;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

public class ConfigReader {
	
	/**
	 * 
	 */
	private Properties config;

	public ConfigReader() {

	}

	public ConfigReader(Properties config) {
		this.config = config;
	}

	/**
	 * @Title: readValue
	 * @Description: 读取属性配置文件中的字符串
	 * @param key
	 * @return String
	 */
	public String readValue(String key) {
		try {
			String value = config.getProperty(key);
			return value;
		} catch (Exception ex) {
			LogHelper.getLogger().error("读取配置信息出错", ex);
			return null;
		}
	}

	/**
	 * @Description 读取属性配置文件中的字符串，如果异常则返回默认值
	 * @param key
	 * @param defaultValue
	 * @return String
	 * @author davidwan
	 */
	public String readValue(String key, String defaultValue) {
		try {
			String value = config.getProperty(key);
			if (StringUtils.isBlank(value)) {
				return defaultValue;
			}
			return value;
		} catch (Exception ex) {
			LogHelper.getLogger().error("读取配置信息出错", ex);
			return defaultValue;
		}
	}

	/**
	 * @Description 读取属性配置文件中的int值
	 * @param key
	 * @return int
	 * @author davidwan
	 */
	public int readIntValue(String key) {
		String value = readValue(key);
		return NumberUtils.toInt(value, 0);
	}

	/**
	 * @Description 读取属性配置文件中的int值，如果异常则返回默认值
	 * @param key
	 * @param defaultValue
	 * @return int
	 * @author davidwan
	 */
	public int readIntValue(String key, int defaultValue) {
		String value = readValue(key);
		return NumberUtils.toInt(value, defaultValue);
	}

	/**
	 * @Description 读取属性配置文件中的long值
	 * @param key
	 * @return long
	 * @author davidwan
	 */
	public long readLongValue(String key) {
		String value = readValue(key);
		return NumberUtils.toLong(value, 0l);
	}

	/**
	 * @Description 读取属性配置文件中的long值，如果异常则返回默认值
	 * @param key
	 * @param defaultValue
	 * @return long
	 * @author davidwan
	 */
	public long readLongValue(String key, long defaultValue) {
		String value = readValue(key);
		return NumberUtils.toLong(value, defaultValue);
	}

	/**
	 * @Description 读取属性配置文件中的boolean值
	 * @param key
	 * @return boolean
	 * @author davidwan
	 */
	public boolean readBooleanValue(String key) {
		String value = readValue(key);
		return BooleanUtils.toBoolean(value);
	}
}
