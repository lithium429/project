package com.xz.project.core.domain.entity;

import java.io.Serializable;
import com.xz.base.domain.BaseEntity;

public class SmsTemplate extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -1L;
	

	/**
	 * id
	 */
	private java.lang.Integer id;

	/**
	 * name
	 */
	private java.lang.String name;

	/**
	 * create_time
	 */
	private java.util.Date create_time;

	/**
	 * 是否默认
	 */
	private java.lang.Boolean is_default;

	/**
	 * content
	 */
	private java.lang.String content;

	public SmsTemplate() {
	}
	
	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	public java.lang.Integer getId() {
		return this.id;
	}
	
	public void setName(java.lang.String name) {
		this.name = name;
	}

	public java.lang.String getName() {
		return this.name;
	}
	
	public void setCreate_time(java.util.Date create_time) {
		this.create_time = create_time;
	}

	public java.util.Date getCreate_time() {
		return this.create_time;
	}
	
	public void setIs_default(java.lang.Boolean is_default) {
		this.is_default = is_default;
	}

	public java.lang.Boolean getIs_default() {
		return this.is_default;
	}
	
	public void setContent(java.lang.String content) {
		this.content = content;
	}

	public java.lang.String getContent() {
		return this.content;
	}


}