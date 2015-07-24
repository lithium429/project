package com.xz.project.core.domain.entity;

import java.io.Serializable;
import com.xz.base.domain.BaseEntity;

public class Action extends BaseEntity implements Serializable {

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
	 * sort
	 */
	private java.lang.Integer sort;

	/**
	 * create_time
	 */
	private java.util.Date create_time;

	/**
	 * id
	 */
	private java.lang.Integer menu_id;

	/**
	 * id
	 */
	private java.lang.String menu_name;

	public Action() {
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

	public void setMenu_id(java.lang.Integer menu_id) {
		this.menu_id = menu_id;
	}

	public java.lang.Integer getMenu_id() {
		return this.menu_id;
	}

	public void setMenu_name(java.lang.String menu_name) {
		this.menu_name = menu_name;
	}

	public java.lang.String getMenu_name() {
		return this.menu_name;
	}

}