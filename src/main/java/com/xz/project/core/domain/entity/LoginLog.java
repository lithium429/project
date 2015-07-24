package com.xz.project.core.domain.entity;

import java.io.Serializable;
import com.xz.base.domain.BaseEntity;

public class LoginLog extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -1L;
	

	/**
	 * id
	 */
	private java.lang.Integer id;

	/**
	 * user_id
	 */
	private java.lang.Integer user_id;

	/**
	 * 类型：1.普通web，2.即时通讯客户端
	 */
	private java.lang.Integer type;

	/**
	 * ip
	 */
	private java.lang.String ip;

	/**
	 * create_time
	 */
	private java.util.Date create_time;

	/**
	 * user_name
	 */
	private java.lang.String user_name;

	public LoginLog() {
	}
	
	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	public java.lang.Integer getId() {
		return this.id;
	}
	
	public void setUser_id(java.lang.Integer user_id) {
		this.user_id = user_id;
	}

	public java.lang.Integer getUser_id() {
		return this.user_id;
	}
	
	public void setType(java.lang.Integer type) {
		this.type = type;
	}

	public java.lang.Integer getType() {
		return this.type;
	}
	
	public void setIp(java.lang.String ip) {
		this.ip = ip;
	}

	public java.lang.String getIp() {
		return this.ip;
	}
	
	public void setCreate_time(java.util.Date create_time) {
		this.create_time = create_time;
	}

	public java.util.Date getCreate_time() {
		return this.create_time;
	}

	public java.lang.String getUser_name() {
		return user_name;
	}

	public void setUser_name(java.lang.String user_name) {
		this.user_name = user_name;
	}


}