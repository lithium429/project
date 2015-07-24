/**   
 * @Title: LoginController.java 
 * @Package: com.xz.oa.core.controller 
 * @Description: 
 * @author: davidwan
 * @date: 2014-7-14 下午6:02:19 
 * @version: V1.0   
 */
package com.xz.project.core.web.controller.admin;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.xz.base.utils.ConfigValue;
import com.xz.base.utils.WebUtil;
import com.xz.project.core.domain.enums.EnumUserState; 
import com.xz.project.core.service.log.LoginLogService;
import com.xz.project.core.service.user.ShiroDbRealm.ShiroUser;
import com.xz.project.core.service.user.OnlineUser;
import com.xz.project.core.service.user.UserService;

@Controller
public class LoginController {
	@Resource
	private LoginLogService loginLogService;
	
	@Resource
	private UserService userService;  

	@RequestMapping(value = "/admin/login", method = RequestMethod.GET)
	public String login(Model model) {
		String isDebug = ConfigValue.readValue("isDebug");
		if (StringUtils.isNotBlank(isDebug)) {
			model.addAttribute("userName", "admin");
			model.addAttribute("password", "123456");
		} 
		return "login";
	}

	@RequestMapping(value = "/admin/login", method = RequestMethod.POST)
	public String login(@RequestParam("user_name") String userName,HttpServletRequest request, String password, String captcha, HttpSession session, Model model) {

		try {
			String captchaSession = (String) session.getAttribute("VerifyCode");
			if (captcha != null && captchaSession != null && captchaSession.compareTo(captcha.trim()) != 0) {
				model.addAttribute("displayCaptcha", "true");
				model.addAttribute("errorInfo", "您输入的验证码不正确！");
				model.addAttribute("userName", userName);
				model.addAttribute("password", password);
				return "/login";
			} 

			AuthenticationToken token = new UsernamePasswordToken(userName, password);
			Subject currentUser = SecurityUtils.getSubject();
			currentUser.login(token);

			// 判断登录用户是否被禁用
			ShiroUser shiroUser = (ShiroUser) currentUser.getPrincipal();
			if (shiroUser != null && shiroUser.getState() == EnumUserState.禁用.getValue()) {
				model.addAttribute("displayCaptcha", "true");
				model.addAttribute("errorInfo", "该帐号已被禁用，请联系系统管理员！");
				return "/login";
			}
			session.setAttribute("is_login", true);
			
			// 添加在线用户
			OnlineUser.getInstance().add(shiroUser.getId(), session);
			loginLogService.create(WebUtil.getIpAddr(request), shiroUser.getId(), 1);
			return "redirect:/admin/home/index.do";
		} catch (AuthenticationException ex) {
			model.addAttribute("displayCaptcha", "true");
			model.addAttribute("errorInfo", "用户名或者密码错误！");
			return "/login";
		}
	}
	

	@RequestMapping("/admin/logout")
	public String logout(HttpSession session) {
		Subject currentUser = SecurityUtils.getSubject();
		ShiroUser shiroUser = (ShiroUser) currentUser.getPrincipal();
		// 移除在线用户
		OnlineUser.getInstance().remove(shiroUser.getId(), session);
		currentUser.logout();
		
		return "redirect:/admin/login.do";
	} 

	@RequestMapping("/admin/tlogout")
	public String logoutPage() {
		return "/logout";
	}
}
