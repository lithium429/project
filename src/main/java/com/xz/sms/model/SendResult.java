/**   
 * @Title: SendResult.java 
 * @Package: com.xz.sms.model 
 * @Description: 短信发送结果
 * @author: davidwan
 * @date: 2014-8-20 下午5:26:24 
 * @version: V1.0   
 */
package com.xz.sms.model;

public class SendResult {

	// 是否成功
	private boolean flag;

	// 信息
	private String msg;

	// 附加信息对象
	private Object obj;

	public SendResult(boolean flag) {
		this.flag = flag;
		msg = "";
	}

	public SendResult(boolean flag, String msg) {
		this.flag = flag;
		this.msg = msg;
	}

	public SendResult(boolean flag, String msg, Object obj) {
		this.flag = flag;
		this.msg = msg;
		this.obj = obj;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}
}
