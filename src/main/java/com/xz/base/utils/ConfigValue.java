/**  
 * @Title: ConfigValue.java
 * @Package com.xz.base.utils
 * @Description: 获取配置属性
 * @author 万书德
 * @date 2013-6-13
 * @version V1.0  
 */
package com.xz.base.utils;

import java.io.*;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import com.google.common.base.Charsets;

/**
 * @Description: 获取配置属性
 * 
 */
public class ConfigValue {

	/**
	 * @Fields config : 属性配置文件对象
	 */
	private static Properties config = null;
	public static String SMSURL_REG = "";
	public static String SMSURL_UPDPSW = "";
	public static String SMSURL_UPDREG = "";
	public static String SMSURL_SELSUM = "";
	public static String SMSURL_SEND = "";
	public static String SMSURL_BATCHSEND = "";
	public static String SMSURL_GET = "";
	public static String SMSURL_CHARGEUP = "";
	public static String SMSURL_UNREG = "";
	public static String SMSURL_CORP = "";
	public static String SMSURL_PSW = "";
	public static Boolean IS_STOPSEND = false;
	
	/**
	 * 配置读取对象 
	 */
	public static ConfigReader reader = null;

	/**
	 * @Fields PAGE_SIZE : 每页显示记录数
	 */
	public static int PAGE_SIZE = 10;

	static {
		InputStream in = ConfigValue.class.getClassLoader().getResourceAsStream("config.properties");
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
		String pageSize = reader.readValue("pageSize");
		if (StringUtils.isNotBlank(pageSize)) {
			PAGE_SIZE = Integer.parseInt(pageSize);
		}
		String smsurl_Reg = reader.readValue("smsurl_Reg");
		String smsurl_UpdPwd = reader.readValue("smsurl_UpdPwd");
		String smsurl_UpdReg = reader.readValue("smsurl_UpdReg");
		String smsurl_SelSum = reader.readValue("smsurl_SelSum");
		String smsurl_Send = reader.readValue("smsurl_Send");
		String smsurl_BatchSend = reader.readValue("smsurl_BatchSend");
		String smsurl_Get = reader.readValue("smsurl_Get");
		String smsurl_ChargeUp = reader.readValue("smsurl_ChargeUp");
		String smsurl_UnReg = reader.readValue("smsurl_UnReg");
		String smsurl_Corp = reader.readValue("smsurl_corp");
		String smsurl_Pwd = reader.readValue("smsurl_psw");
		String is_stop_send = reader.readValue("is_stopSend");
		if (smsurl_Reg != null && !"".equals(smsurl_Reg)) {
			SMSURL_REG = smsurl_Reg;
		}
		if (smsurl_UpdPwd != null && !"".equals(smsurl_UpdPwd)) {
			SMSURL_UPDPSW = smsurl_UpdPwd;
		}
		if (smsurl_UpdReg != null && !"".equals(smsurl_UpdReg)) {
			SMSURL_UPDREG = smsurl_UpdReg;
		}
		if (smsurl_SelSum != null && !"".equals(smsurl_SelSum)) {
			SMSURL_SELSUM = smsurl_SelSum;
		}
		if (smsurl_Send != null && !"".equals(smsurl_Send)) {
			SMSURL_SEND = smsurl_Send;
		}
		if (smsurl_BatchSend != null && !"".equals(smsurl_BatchSend)) {
			SMSURL_BATCHSEND = smsurl_BatchSend;
		}
		if (smsurl_Get != null && !"".equals(smsurl_Get)) {
			SMSURL_GET = smsurl_Get;
		}
		if (smsurl_ChargeUp != null && !"".equals(smsurl_ChargeUp)) {
			SMSURL_CHARGEUP = smsurl_ChargeUp;
		}
		if (smsurl_UnReg != null && !"".equals(smsurl_UnReg)) {
			SMSURL_UNREG = smsurl_UnReg;
		}
		if (smsurl_Corp != null && !"".equals(smsurl_Corp)) {
			SMSURL_CORP = smsurl_Corp;
		}
		if (smsurl_Pwd != null && !"".equals(smsurl_Pwd)) {
			SMSURL_PSW = smsurl_Pwd;
		}
		if (is_stop_send != null && !"".equals(is_stop_send)) {
			IS_STOPSEND = Boolean.valueOf(is_stop_send);
		}
	} 
	
	/**
	 * @Description 读取String配置值 
	 * @param key
	 * @return String     
	 */
	public static String readValue(String key){
		return reader.readValue(key);
	}
	
	/**
	 * @Description 读取String配置值 
	 * @param key
	 * @param defaultValue
	 * @return String     
	 */
	public static String readValue(String key, String defaultValue){
		return reader.readValue(key, defaultValue);
	}
	
	/**
	 * @Description 读取int配置值 
	 * @param key
	 * @param defaultValue
	 * @return int     
	 */
	public static int readIntValue(String key, int defaultValue){
		return reader.readIntValue(key, defaultValue);
	}
	
	/**
	 * @Description 读取long配置值 
	 * @param key
	 * @param defaultValue
	 * @return long     
	 */
	public static long readLongValue(String key, long defaultValue){
		return reader.readLongValue(key, defaultValue);
	}
}
