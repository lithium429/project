package com.xz.project.core.domain.entity;

import java.io.Serializable;
import java.util.Date;

import com.xz.base.domain.BaseEntity;

public class Sms extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -1L;

	/**
	 * id
	 */
	private java.lang.Integer id;

	/**
	 * 发送人
	 */
	private java.lang.String sender_name;

	/**
	 * 接收人
	 */
	private java.lang.String receiver_name;

	/**
	 * 接口类型：待定
	 */
	private java.lang.Integer interface_type;

	/**
	 * 状态：0.成功，1.失败
	 */
	private java.lang.Integer state;

	/**
	 * 手机号码
	 */
	private java.lang.String phone;

	/**
	 * 内容
	 */
	private java.lang.String content;

	/**
	 * 是否是重发
	 */
	private java.lang.Boolean is_retry;

	/**
	 * 重发次数
	 */
	private java.lang.Integer retry_count;

	/**
	 * 创建时间
	 */
	private java.util.Date create_time;

	public Sms() {
	}

	public Sms(String sender_name, String receiver_name, Integer interface_type, Integer state, String phone, String content, boolean is_retry, Integer retry_count, Date create_time) {
		this.sender_name = sender_name;
		this.receiver_name = receiver_name;
		this.interface_type = interface_type;
		this.state = state;
		this.phone = phone;
		this.content = content;
		this.is_retry = is_retry;
		this.retry_count = retry_count;
		this.create_time = create_time;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	public java.lang.Integer getId() {
		return this.id;
	}

	public void setSender_name(java.lang.String sender_name) {
		this.sender_name = sender_name;
	}

	public java.lang.String getSender_name() {
		return this.sender_name;
	}

	public void setReceiver_name(java.lang.String receiver_name) {
		this.receiver_name = receiver_name;
	}

	public java.lang.String getReceiver_name() {
		return this.receiver_name;
	}

	public void setInterface_type(java.lang.Integer interface_type) {
		this.interface_type = interface_type;
	}

	public java.lang.Integer getInterface_type() {
		return this.interface_type;
	}

	public void setState(java.lang.Integer state) {
		this.state = state;
	}

	public java.lang.Integer getState() {
		return this.state;
	}

	public void setPhone(java.lang.String phone) {
		this.phone = phone;
	}

	public java.lang.String getPhone() {
		return this.phone;
	}

	public void setContent(java.lang.String content) {
		this.content = content;
	}

	public java.lang.String getContent() {
		return this.content;
	}

	public void setIs_retry(java.lang.Boolean is_retry) {
		this.is_retry = is_retry;
	}

	public java.lang.Boolean getIs_retry() {
		return this.is_retry;
	}

	public void setRetry_count(java.lang.Integer retry_count) {
		this.retry_count = retry_count;
	}

	public java.lang.Integer getRetry_count() {
		return this.retry_count;
	}

	public void setCreate_time(java.util.Date create_time) {
		this.create_time = create_time;
	}

	public java.util.Date getCreate_time() {
		return this.create_time;
	}

}