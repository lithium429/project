package com.xz.project.core.domain.entity;

import java.io.Serializable;
import com.xz.base.domain.BaseEntity;

public class Role extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -1L;

	/**
	 * id
	 */
	private java.lang.Integer id;

	/**
	 * 名称
	 */
	private java.lang.String name;

	/**
	 * 排序
	 */
	private java.lang.Integer sort;

	/**
	 * create_time
	 */
	private java.util.Date create_time;

	/**
	 * 是否超管
	 */
	private java.lang.Boolean is_super;

	/**
	 * role_id
	 */
	private java.lang.Integer role_id;

	public Role() {
	}
	
	public Role(boolean is_super) {
		this.is_super = is_super;
	}

	public Role(String name) {
		this.name = name;
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

	public void setSort(java.lang.Integer sort) {
		this.sort = sort;
	}

	public java.lang.Integer getSort() {
		return this.sort;
	}

	public void setCreate_time(java.util.Date create_time) {
		this.create_time = create_time;
	}

	public java.util.Date getCreate_time() {
		return this.create_time;
	}

	public void setIs_super(java.lang.Boolean is_super) {
		this.is_super = is_super;
	}

	public java.lang.Boolean getIs_super() {
		return this.is_super;
	}

	public void setRole_id(java.lang.Integer role_id) {
		this.role_id = role_id;
	}

	public java.lang.Integer getRole_id() {
		return this.role_id;
	}

}