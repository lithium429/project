/**  
 * @Title: JsonResult.java
 * @Package com.rz.web
 * @Description: json结果
 * @author 万书德
 * @date 2013-6-5
 * @version V1.0  
 */
package com.xz.base.model;

import java.io.Serializable;

/**
 * @Description: json结果
 * 
 */
public class JsonResult implements Serializable {
	/**
	 * @Fields serialVersionUID :
	 */
	private static final long serialVersionUID = 1L;

	// 是否成功
	private boolean flag;

	// 信息
	private String msg;

	// 附加信息对象
	private Object obj;

	public JsonResult(boolean flag) {
		this.flag = flag;
		msg = "";
	}

	public JsonResult(boolean flag, String msg) {
		this.flag = flag;
		this.msg = msg;
	}

	public JsonResult(boolean flag, String msg, Object obj) {
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
