package com.xz.project.core.domain.entity;

import java.io.Serializable;
import com.xz.base.domain.BaseEntity;

public class RoleMenu extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -1L;

	/**
	 * id
	 */
	private java.lang.Integer id;

	/**
	 * role_id
	 */
	private java.lang.Integer role_id;

	/**
	 * menu_id
	 */
	private java.lang.Integer menu_id;
	
	/**
	 * 菜单 
	 */
	private Menu menu;

	/**
	 * 动作id
	 */
	private java.lang.Integer action_id;
	
	/**
	 * 动作名称
	 */
	private java.lang.String action_name;

	/**
	 * 是否动作
	 */
	private java.lang.Boolean is_action;
	 
	public RoleMenu() {
	}
	
	public RoleMenu(Integer role_id, Integer menu_id){
		this.role_id = role_id;
		this.menu_id = menu_id;
	}
	
	public RoleMenu(Integer role_id, Integer action_id, boolean is_action){
		this.role_id = role_id;
		this.action_id = action_id;
		this.is_action = is_action;
	}
	
	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	public java.lang.Integer getId() {
		return this.id;
	}

	public void setRole_id(java.lang.Integer role_id) {
		this.role_id = role_id;
	}

	public java.lang.Integer getRole_id() {
		return this.role_id;
	}

	public void setMenu_id(java.lang.Integer menu_id) {
		this.menu_id = menu_id;
	}

	public java.lang.Integer getMenu_id() {
		return this.menu_id;
	}  
	
	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public void setAction_id(java.lang.Integer action_id) {
		this.action_id = action_id;
	}

	public java.lang.Integer getAction_id() {
		return this.action_id;
	} 

	public java.lang.String getAction_name() {
		return action_name;
	}

	public void setAction_name(java.lang.String action_name) {
		this.action_name = action_name;
	}

	public void setIs_action(java.lang.Boolean is_action) {
		this.is_action = is_action;
	}

	public java.lang.Boolean getIs_action() {
		return this.is_action;
	}

}