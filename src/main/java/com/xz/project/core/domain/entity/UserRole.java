package com.xz.project.core.domain.entity;
 
import java.io.Serializable;
import com.xz.base.domain.BaseEntity;

public class UserRole extends BaseEntity implements Serializable {

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
	 * role_id
	 */
	private java.lang.Integer role_id;

	/**
	 * role_id
	 */
	private java.lang.String role_name;

	/**
	 * role_id
	 */
	private java.lang.Boolean is_super;

	public UserRole() {
	}
	
	public UserRole(Integer userId, Integer roleId){
		this.user_id = userId;
		this.role_id = roleId;
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
	
	public void setRole_id(java.lang.Integer role_id) {
		this.role_id = role_id;
	}

	public java.lang.Integer getRole_id() {
		return this.role_id;
	}
	
	public void setRole_name(java.lang.String role_name) {
		this.role_name = role_name;
	}

	public java.lang.String getRole_name() {
		return this.role_name;
	}

	public java.lang.Boolean getIs_super() {
		return is_super;
	}

	public void setIs_super(java.lang.Boolean is_super) {
		this.is_super = is_super;
	}


}