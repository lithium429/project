/**   
 * @Title: AdminController.java 
 * @Package: com.xz.project.core.web.controller.admin 
 * @Description: 后台基控制器
 * @author: davidwan
 * @date: 2015-3-26 上午11:54:58 
 * @version: V1.0   
 */
package com.xz.project.core.web.controller.admin;

import java.util.List;

import javax.annotation.Resource; 

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.xz.base.controller.SpringBaseController; 
import com.xz.project.core.domain.entity.RoleMenu;
import com.xz.project.core.service.security.RoleMenuService;
import com.xz.project.core.service.user.ShiroDbRealm.ShiroUser;

public class AdminController extends SpringBaseController {
	@Resource
	private RoleMenuService roleMenuService;

	/**
	 * @Description 获取当前登录用户Id
	 * @return Integer
	 * @author davidwan
	 */
	protected Integer getCurrentUserId() {
		Subject currentUser = SecurityUtils.getSubject();
		ShiroUser shiroUser = (ShiroUser) currentUser.getPrincipal();
		return shiroUser.getId();
	}

	/**
	 * @Description
	 * @return Integer
	 */
	protected Integer getCurrentUserDeptId() {
		Subject currentUser = SecurityUtils.getSubject();
		ShiroUser shiroUser = (ShiroUser) currentUser.getPrincipal();
		return shiroUser.getDept_id();
	}

	/**
	 * @Description 获取菜单权限
	 * @param menu_id
	 * @return List<RoleMenu>
	 */
	public List<RoleMenu> gainRoleMenu(int menu_id) {
		Subject currentUser = SecurityUtils.getSubject();
		ShiroUser shiroUser = (ShiroUser) currentUser.getPrincipals().getPrimaryPrincipal();
		RoleMenu a = new RoleMenu();
		a.setIs_action(true);
		a.getMap().put("menu_id_l", menu_id);
		a.getMap().put("my_id", shiroUser.getId());
		return roleMenuService.queryList_power(a);
	}

	/* 
	 */
	@Override
	public String getFModulePath() { 
		return null;
	}

	/*  
	 */
	@Override
	public String getModulePath() { 
		return null;
	}

	/*  
	 */
	@Override
	public String getModuleName() {
		return null;
	}
}
