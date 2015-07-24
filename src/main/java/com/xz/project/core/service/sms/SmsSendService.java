package com.xz.project.core.service.sms;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.xz.base.utils.ConfigValue;
import com.xz.project.core.dao.SmsDao;
import com.xz.project.core.domain.entity.Sms;

@Service
public class SmsSendService {

	@Qualifier("restTemplate")
	@Autowired
	private RestTemplate restTemplate;
	
	@Resource
	private SmsDao smsDao; 

	/**
	 * @Title: selSum
	 * @Description: 查询余额
	 * @param corpID
	 *            -帐号
	 * @param pwd
	 *            -密码
	 * @return int（>=0，剩余条数；-1、帐号未注册；-2、其他错误；-3、密码错误）
	 */
	public int selSum(String corpID, String pwd) {
		MultiValueMap<String, String> urlVariables = new LinkedMultiValueMap<String, String>();
		urlVariables.add("CorpID", corpID);
		urlVariables.add("Pwd", pwd);
		String result = restTemplate.postForObject(ConfigValue.SMSURL_SELSUM, urlVariables, String.class);
		int r = Integer.valueOf(result);
		return r;
	}

	/**
	 * @Title: send
	 * @Description: 发送短信
	 * @param corpID
	 *            -帐号
	 * @param pwd
	 *            -密码
	 * @param mobile
	 *            -发送手机号
	 * @param content
	 *            -发送内容
	 * @param cell
	 *            -子号(可为空）
	 * @param sendTime
	 *            -定时发送时间(固定14位长度字符串，比如：20060912152435代表2006年9月12日15时24分35秒，可为空)
	 * @return int（0，发送成功；-1、帐号未注册；-2、其他错误；-3、密码错误；-4、手机号格式不对；-5、余额不足；-6、
	 *         定时发送时间不是有效的时间格式）
	 */
	public int send(String corpID, String pwd, String mobile, String content, String cell, String sendTime) {

		MultiValueMap<String, String> urlVariables = new LinkedMultiValueMap<String, String>();
		urlVariables.add("CorpID", corpID);
		urlVariables.add("Pwd", pwd);
		urlVariables.add("Mobile", mobile);
		urlVariables.add("Content", content);
		urlVariables.add("Cell", cell);
		urlVariables.add("SendTime", sendTime);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.valueOf("application/x-www-form-urlencoded;charset=gbk"));
		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(urlVariables, headers);
		String result = restTemplate.postForObject(ConfigValue.SMSURL_SEND, requestEntity, String.class);
		int r = Integer.valueOf(result);
		return r;
	}

	/**
	 * @Title: send
	 * @Description: 发送短信
	 * @param corpID
	 *            -帐号
	 * @param pwd
	 *            -密码
	 * @param mobile
	 *            -发送手机号
	 * @param content
	 *            -发送内容
	 * @param cell
	 *            -子号(可为空）
	 * @param sendTime
	 *            -定时发送时间(固定14位长度字符串，比如：20060912152435代表2006年9月12日15时24分35秒，可为空)
	 * @return int（0，发送成功；-1、帐号未注册；-2、其他错误；-3、密码错误；-4、手机号格式不对；-5、余额不足；-6、
	 *         定时发送时间不是有效的时间格式）
	 */
	public int sendByrecord(String corpID, String pwd, String mobile, String content, String cell, String sendTime, Sms sms) {

		int r = -1;
		MultiValueMap<String, String> urlVariables = new LinkedMultiValueMap<String, String>();
		urlVariables.add("CorpID", corpID);
		urlVariables.add("Pwd", pwd);
		urlVariables.add("Mobile", mobile);
		urlVariables.add("Content", content);
		urlVariables.add("Cell", cell);
		urlVariables.add("SendTime", sendTime);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.valueOf("application/x-www-form-urlencoded;charset=gbk"));
		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(urlVariables, headers);
		if (!ConfigValue.IS_STOPSEND) {
			String result = restTemplate.postForObject(ConfigValue.SMSURL_SEND, requestEntity, String.class);

			r = Integer.valueOf(result);
			sms.setState(r);
			smsDao.insertEntity(sms);
		}
		return r;
	}

	/**
	 * @Title: batchSend
	 * @Description: 群发短信
	 * @param corpID
	 *            -帐号
	 * @param pwd
	 *            -密码
	 * @param mobile
	 *            -发送手机号(多个号码以逗号分隔,最多支持600个号码)
	 * @param content
	 *            -发送内容
	 * @param cell
	 *            -子号(可为空）
	 * @param sendTime
	 *            -定时发送时间(固定14位长度字符串，比如：20060912152435代表2006年9月12日15时24分35秒，可为空)
	 * @return int（0，发送成功进入待发进程；1、直接发送成功；-1、帐号尚未注册；-2、其他错误；-3、帐号或者密码错误；-4、
	 *         一次提交信息不能超过600个手机号码
	 *         ；-5、企业号帐户余额不足，请先充值再提交短信息！；-6、定时发送时间不是有效的时间格式；-7、
	 *         发送短信内容包含黑字典关键字；-8、发送内容需在3到250个字之间；-9、发送号码为空）
	 */
	public int batchSendByrecord(String corpID, String pwd, String mobile, String content, String cell, String sendTime, Sms sms) {
		int r = -1;
		MultiValueMap<String, String> urlVariables = new LinkedMultiValueMap<String, String>();
		urlVariables.add("CorpID", corpID);
		urlVariables.add("Pwd", pwd);
		urlVariables.add("Mobile", mobile);
		urlVariables.add("Content", content);
		urlVariables.add("Cell", cell);
		urlVariables.add("SendTime", sendTime);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.valueOf("application/x-www-form-urlencoded;charset=gbk"));
		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(urlVariables, headers);
		if (!ConfigValue.IS_STOPSEND) {
			String result = restTemplate.postForObject(ConfigValue.SMSURL_BATCHSEND, requestEntity, String.class);
			r = Integer.valueOf(result);
			sms.setState(r);
			smsDao.insertEntity(sms);
		}
		return r;
	}

	/**
	 * @Title: batchSend
	 * @Description: 群发短信
	 * @param corpID
	 *            -帐号
	 * @param pwd
	 *            -密码
	 * @param mobile
	 *            -发送手机号(多个号码以逗号分隔,最多支持600个号码)
	 * @param content
	 *            -发送内容
	 * @param cell
	 *            -子号(可为空）
	 * @param sendTime
	 *            -定时发送时间(固定14位长度字符串，比如：20060912152435代表2006年9月12日15时24分35秒，可为空)
	 * @return int（0，发送成功进入待发进程；1、直接发送成功；-1、帐号尚未注册；-2、其他错误；-3、帐号或者密码错误；-4、
	 *         一次提交信息不能超过600个手机号码
	 *         ；-5、企业号帐户余额不足，请先充值再提交短信息！；-6、定时发送时间不是有效的时间格式；-7、
	 *         发送短信内容包含黑字典关键字；-8、发送内容需在3到250个字之间；-9、发送号码为空）
	 */
	public int batchSend(String corpID, String pwd, String mobile, String content, String cell, String sendTime) {
		MultiValueMap<String, String> urlVariables = new LinkedMultiValueMap<String, String>();
		urlVariables.add("CorpID", corpID);
		urlVariables.add("Pwd", pwd);
		urlVariables.add("Mobile", mobile);
		urlVariables.add("Content", content);
		urlVariables.add("Cell", cell);
		urlVariables.add("SendTime", sendTime);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.valueOf("application/x-www-form-urlencoded;charset=gbk"));
		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(urlVariables, headers);
		String result = restTemplate.postForObject(ConfigValue.SMSURL_BATCHSEND, requestEntity, String.class);
		int r = Integer.valueOf(result);
		return r;
	} 
}
