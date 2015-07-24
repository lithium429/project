/**   
 * @Title: HomeController.java 
 * @Package: com.xz.project.core.controller 
 * @Description: 
 * @author: davidwan
 * @date: 2014-7-15 上午10:57:20 
 * @version: V1.0   
 */
package com.xz.project.core.web.controller.admin;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.joda.time.DateTime;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
 
import com.xz.base.utils.DateUtil; 
import com.xz.project.core.domain.entity.Menu;
import com.xz.project.core.domain.entity.User;  
import com.xz.project.core.service.security.MenuService;
import com.xz.project.core.service.user.OnlineUser;
import com.xz.project.core.service.user.UserService;
import com.xz.project.core.service.user.ShiroDbRealm.ShiroUser;

@Controller
@RequestMapping(value = "/admin/home")
public class HomeController extends AdminController {

	@Resource
	private UserService userService;

	@Resource
	private MenuService menuService; 

	/**
	 * @Description 进入首页
	 * @param model
	 * @return String
	 */
	@RequestMapping("index")
	public String index(HttpServletRequest request, Model model, HttpSession session) {
		Subject currentUser = SecurityUtils.getSubject();
		ShiroUser shiroUser = (ShiroUser) currentUser.getPrincipals().getPrimaryPrincipal();
		Integer userId = getCurrentUserId();
		int login_type = 0;
		Boolean is_login = (Boolean) session.getAttribute("is_login");
		Map<Integer, List<Menu>> map = userService.queryUserMenus(userId);
		if (map != null) {
			model.addAttribute("menuListOne", map.get(1));
			model.addAttribute("menuListTwo", map.get(2));
			model.addAttribute("menuListThree", map.get(3));
		}
		if (is_login != null && is_login) {
			session.removeAttribute("is_login");
			User u = userService.findById(userId);
			if (u.getIs_first_login()) {
				login_type = 1;
			} else if (u.getIs_reseted_psw()) {
				login_type = 2;
			}
			if (login_type > 0) {
				u.setIs_first_login(false);
				u.setIs_reseted_psw(false);
				userService.modify("", u);
			}
		}

		DateTime now = DateTime.now();
		model.addAttribute("month", now.getMonthOfYear());
		model.addAttribute("day", now.getDayOfMonth());
		model.addAttribute("weekday", DateUtil.getDayOfWeekForText(now.getYear(), now.getMonthOfYear(), now.getDayOfMonth()));
		model.addAttribute("userId", userId);
		model.addAttribute("login_type", login_type);
		model.addAttribute("real_name", shiroUser.getRealName());

		// 在线用户数量
		Integer onlineUserCount = OnlineUser.getInstance().gainUserCount();
		if (onlineUserCount == 0) {
			return "redirect:/admin/tlogout.do";
		}
		model.addAttribute("onlineUserCount", onlineUserCount);

		return getPath("index");
	}

	@RequestMapping("info")
	public String info(Model model) { 
		return getPath("info");
	}

	@Override
	public String getFModulePath() {
		return "core";
	}

	@Override
	public String getModulePath() {
		return "home";
	}

	@Override
	public String getModuleName() {
		return "首页";
	}
}
