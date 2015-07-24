/**   
* @Title: SmsService.java 
* @Package: com.xz.sms.service 
* @Description: 短信接口
* @author: davidwan
* @date: 2014-8-20 下午5:19:08 
* @version: V1.0   
*/
package com.xz.sms.service;

import java.util.List;
import java.util.Map;

import com.xz.sms.model.SendResult;

public interface SmsInterface {
	/**
	 * @Description 单个号码发送 
	 * @param mobile
	 * @param msg
	 * @return SendResult     
	 */
	public SendResult send(String mobile, String msg);
	
	/**
	 * @Description 多个号码，相同内容发送 
	 * @param mobile
	 * @param msg
	 * @return SendResult     
	 */
	public SendResult batchSend(List<String> mobile, String msg);
	
	/**
	 * @Description 多个号码，不同内容发送 
	 * @param mobileMsgMap
	 * @return SendResult     
	 */
	public SendResult batchSend(Map<String, String> mobileMsgMap);
}
