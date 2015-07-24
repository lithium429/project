package com.xz.project.core.domain.entity;

import java.io.Serializable;
import com.xz.base.domain.BaseEntity;
import com.xz.project.core.domain.enums.EnumLogModule;

public class SystemLog extends BaseEntity implements Serializable {

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
	 *  
	 */
	private java.lang.Integer module;

	/**
	 * operate
	 */
	private java.lang.String operate;

	/**
	 * content
	 */
	private java.lang.String content;

	/**
	 * create_time
	 */
	private java.util.Date create_time;

	/**
	 * user_name
	 */
	private java.lang.String user_name;

	public SystemLog() {
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

	public void setModule(java.lang.Integer module) {
		this.module = module;
	}

	public java.lang.Integer getModule() {
		return this.module;
	}

	public void setOperate(java.lang.String operate) {
		this.operate = operate;
	}

	public java.lang.String getOperate() {
		return this.operate;
	}

	public void setContent(java.lang.String content) {
		this.content = content;
	}

	public java.lang.String getContent() {
		return this.content;
	}

	public String gainModule() {
		String r = "";
		if (this.module != null) {
			r = EnumLogModule.fromInt(this.module).name();
		}
		return r;
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