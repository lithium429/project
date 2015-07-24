package com.xz.project.core.domain.entity;

import java.io.Serializable;
import com.xz.base.domain.BaseEntity;

public class Company extends BaseEntity implements Serializable {

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
	 * phone
	 */
	private java.lang.String phone;

	/**
	 * fax
	 */
	private java.lang.String fax;

	/**
	 * zip_code
	 */
	private java.lang.String zip_code;

	/**
	 * address
	 */
	private java.lang.String address;

	/**
	 * site
	 */
	private java.lang.String site;

	/**
	 * email
	 */
	private java.lang.String email;

	/**
	 * info
	 */
	private java.lang.String info;

	public Company() {
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

	public void setPhone(java.lang.String phone) {
		this.phone = phone;
	}

	public java.lang.String getPhone() {
		return this.phone;
	}

	public void setFax(java.lang.String fax) {
		this.fax = fax;
	}

	public java.lang.String getFax() {
		return this.fax;
	}

	public void setZip_code(java.lang.String zip_code) {
		this.zip_code = zip_code;
	}

	public java.lang.String getZip_code() {
		return this.zip_code;
	}

	public void setAddress(java.lang.String address) {
		this.address = address;
	}

	public java.lang.String getAddress() {
		return this.address;
	}

	public void setSite(java.lang.String site) {
		this.site = site;
	}

	public java.lang.String getSite() {
		return this.site;
	}

	public void setEmail(java.lang.String email) {
		this.email = email;
	}

	public java.lang.String getEmail() {
		return this.email;
	}

	public void setInfo(java.lang.String info) {
		this.info = info;
	}

	public java.lang.String getInfo() {
		return this.info;
	}

}