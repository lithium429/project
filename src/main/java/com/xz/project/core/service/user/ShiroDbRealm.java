/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.xz.project.core.service.user;

import java.io.Serializable;
import java.util.Date; 

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import com.xz.project.core.domain.entity.User;

import org.springside.modules.utils.Encodes;

import com.google.common.base.Objects;

public class ShiroDbRealm extends AuthorizingRealm {

	@Resource
	protected UserService userService;

	/**
	 * 认证回调函数,登录时调用.
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		User user = userService.findByName(token.getUsername());
		if (user != null) {
			byte[] salt = Encodes.decodeHex(user.getSalt());
			ShiroUser shiroUser = new ShiroUser(user);
			return new SimpleAuthenticationInfo(shiroUser, user.getPassword(), ByteSource.Util.bytes(salt), user.getReal_name());
		} else {
			throw new AuthenticationException();
		}
	}

	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
		User user = userService.findByName(shiroUser.name);
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		if (user != null) {
			// info.addRoles(user.getRoleList());
		}
		return info;
	}

	/**
	 * 设定Password校验的Hash算法与迭代次数.
	 */
	@PostConstruct
	public void initCredentialsMatcher() {
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(User.HASH_ALGORITHM);
		matcher.setHashIterations(User.HASH_INTERATIONS);

		setCredentialsMatcher(matcher);
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息.
	 */
	public static class ShiroUser implements Serializable {
		private static final long serialVersionUID = -1373760761780840081L;
		private Integer id;
		private String name;
		private String realName;
		private Integer dept_id;
		private boolean is_allowso;
		private Integer state;
		private String password;
		private Date loginTime;
		private boolean is_super; 
		
		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public ShiroUser() {

		}

		public ShiroUser(User user) {
			this.id = user.getId();
			this.name = user.getName();
			this.realName = user.getReal_name();
			this.dept_id = user.getDept_id();
			this.is_allowso = user.getIs_allowso();
			this.state = user.getState();
			this.loginTime = new Date();
			this.is_super = user.gainIs_super(); 
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getRealName() {
			return realName;
		}

		public void setRealName(String realName) {
			this.realName = realName;
		}

		public Integer getDept_id() {
			return dept_id;
		}

		public void setDept_id(Integer dept_id) {
			this.dept_id = dept_id;
		}

		public boolean getIs_allowso() {
			return is_allowso;
		}

		public void setIs_allowso(boolean is_allowso) {
			this.is_allowso = is_allowso;
		}

		public Integer getState() {
			return state;
		}

		public void setState(Integer state) {
			this.state = state;
		}

		public Date getLoginTime() {
			return loginTime;
		}

		public void setLoginTime(Date loginTime) {
			this.loginTime = loginTime;
		}

		public boolean getIs_super() {
			return is_super;
		}

		public void setIs_super(boolean is_super) {
			this.is_super = is_super;
		}
 
		/**
		 * 本函数输出将作为默认的<shiro:principal/>输出.
		 */
		@Override
		public String toString() {
			return name;
		}

		/**
		 * 重载hashCode,只计算name;
		 */
		@Override
		public int hashCode() {
			return Objects.hashCode(name);
		}

		/**
		 * 重载equals,只计算name;
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			ShiroUser other = (ShiroUser) obj;
			if (name == null) {
				if (other.name != null) {
					return false;
				}
			} else if (!name.equals(other.name)) {
				return false;
			}
			return true;
		}
	}
}
