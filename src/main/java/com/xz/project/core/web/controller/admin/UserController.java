/**   
 * @Title: UserController.java 
 * @Package: com.xz.oa.core.controller 
 * @Description: 
 * @author: davidwan
 * @date: 2014-7-14 下午6:02:19 
 * @version: V1.0   
 */
package com.xz.project.core.web.controller.admin;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xz.base.utils.ConfigValue;
import com.xz.base.utils.WebUtil; 
import com.xz.base.model.AutoMapper;
import com.xz.base.model.JsonResult;
import com.xz.base.model.MapperChild;
import com.xz.base.model.PageInfo;
import com.xz.project.core.domain.entity.Department; 
import com.xz.project.core.domain.entity.Role;
import com.xz.project.core.domain.entity.User;
import com.xz.project.core.service.organization.CompanyService;
import com.xz.project.core.service.organization.DepartmentService; 
import com.xz.project.core.service.security.RoleService;
import com.xz.project.core.service.user.OnlineUser;
import com.xz.project.core.service.user.ShiroDbRealm.ShiroUser;
import com.xz.project.core.service.user.UserService;

@Controller
@RequestMapping(value = "/admin/user")
public class UserController extends AdminController {

	@Resource
	private UserService userService;

	@Resource
	private RoleService roleService;

	@Resource
	private CompanyService companyService;

	@Resource
	private DepartmentService departmentService; 

	/**
	 * @Description 进入列表页面
	 * @param request
	 * @param model
	 * @return String
	 * @author davidwan
	 */
	@RequestMapping("/list")
	public String list(HttpServletRequest request, Model model) {
		// 获取角色集合
		model.addAttribute("roleList", getRoleList());

		model.addAttribute("Tree", companyService.getTree_User());
		getList(request, model);
		return getPathList();
	}

	/**
	 * @Description 获取列表数据
	 * @param request
	 * @param model
	 * @return String
	 * @author davidwan
	 */
	@RequestMapping("/datalist")
	public String datalist(HttpServletRequest request, Model model) {

		getList(request, model);
		return getPath("data_list");
	}

	/**
	 * @Description 获取列表数据
	 * @param request
	 * @param model
	 * @author davidwan
	 */
	private void getList(HttpServletRequest request, Model model) {
		int pageIndex = WebUtil.getInt(request, "page_index", 0);
		int pageSize = WebUtil.getInt(request, "page_size", ConfigValue.PAGE_SIZE);
		String key = WebUtil.getString(request, "key", "");
		String role_ids = WebUtil.getString(request, "role_ids", "");
		int department_id = WebUtil.getInt(request, "department_id", 0);
		int state = WebUtil.getInt(request, "state", 0);
		int menu_id = WebUtil.getInt(request, "menu_id", 0);
		User entity = new User();
		if (StringUtils.isNotBlank(key)) {
			entity.getMap().put("key", key);
		}
		if (StringUtils.isNotBlank(role_ids)) {
			entity.getMap().put("role_ids", role_ids.split(","));
		}
		if (department_id != 0) {
			entity.getMap().put("dept_id", department_id);
		}
		if (state != 0) {
			entity.setState(state);
		}
		entity.getMap().put("sort", true);
		PageInfo<User> pageInfo = userService.queryPageList(entity, pageIndex, pageSize);
		model.addAttribute("list", pageInfo.getData());
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("menu_id", menu_id);
		model.addAttribute("roleMenuList", super.gainRoleMenu(menu_id));
	}

	/**
	 * @Title: add
	 * @Description: 进入添加页面
	 * @param request
	 * @param model
	 * @return String
	 */
	@RequestMapping("/add")
	public String add(HttpServletRequest request, Model model) {

		// 获取角色集合
		model.addAttribute("roleList", getRoleList()); 
		// 获取角色部门集合
		model.addAttribute("departmentList", getDepartmentList());
		return getPathAdd();
	}

	/**
	 * @Title: add
	 * @Description: Ajax保存添加数据
	 * @param entity
	 * @return JsonResult
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public @ResponseBody
	JsonResult add(User entity) {
		return userService.create(entity);
	}

	/**
	 * @Title: update
	 * @Description: 进入添加页面
	 * @param id
	 * @param model
	 * @return String
	 */
	@RequestMapping("/update")
	public String update(Integer id, Model model) {
		User entity = userService.findById(id);
		model.addAttribute("model", entity);
		// 获取角色集合
		model.addAttribute("roleList", getRoleList()); 
		// 获取角色部门集合
		model.addAttribute("departmentList", getDepartmentList());
		return getPathUpdate();
	}

	/**
	 * @Title: update
	 * @Description: Ajax保存修改信息
	 * @param entity
	 * @return JsonResult
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody
	JsonResult update(User entity) {
		return userService.modify(entity, entity.getRole_ids());
	}

	/**
	 * @Title: delete
	 * @Description: Ajax删除
	 * @param id
	 * @return JsonResult
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult delete(Integer id) {
		return userService.removeById(id);
	}

	/**
	 * @Title: batchDelete
	 * @Description: Ajax批量删除
	 * @param ids
	 * @return JsonResult
	 */
	@RequestMapping(value = "/batchdelete", method = RequestMethod.POST)
	public @ResponseBody
	JsonResult batchDelete(String ids) {
		return userService.removeByIds(ids);
	}

	/**
	 * @Description 进入详情页面
	 * @param id
	 * @param model
	 * @return String
	 * @author davidwan
	 */
	@RequestMapping("/view")
	public String view(Integer id, Model model) {
		User entity = userService.findById(id);
		model.addAttribute("model", entity);
		return getPathView();
	}

	/**
	 * @Title: batchDelete
	 * @Description: 验证名称重名
	 * @param ids
	 * @return JsonResult
	 */
	@RequestMapping(value = "/validateName", method = RequestMethod.POST)
	public @ResponseBody
	boolean validateName(Integer id, String name) {
		boolean result = userService.validateName(id, name);
		return result;
	}

	/**
	 * @Title: update
	 * @Description: Ajax改变状态
	 * @param entity
	 * @return JsonResult
	 */
	@RequestMapping(value = "/changeState", method = RequestMethod.POST)
	public @ResponseBody
	JsonResult changeState(int state, int id) {
		User entity = new User();
		entity.setState(state);
		entity.setId(id);
		return userService.modify(state == 2 ? "禁用" : "启用", entity);
	}

	/**
	 * @Title: update
	 * @Description: Ajax批量改变状态
	 * @param entity
	 * @return JsonResult
	 */
	@RequestMapping(value = "/batchchangeState", method = RequestMethod.POST)
	public @ResponseBody
	JsonResult batchchangeState(String ids, int state) {
		User entity = new User();
		entity.getMap().put("ids", ids.split(","));
		entity.setState(state);
		return userService.modify(state == 2 ? "批量禁用" : "批量启用", entity);
	}

	/**
	 * @Title: update
	 * @Description: Ajax批量重置密码
	 * @param entity
	 * @return JsonResult
	 */
	@RequestMapping(value = "/batchreset", method = RequestMethod.POST)
	public @ResponseBody
	JsonResult batchreset(String ids) {
		User entity = new User();
		entity.getMap().put("ids", ids.split(","));
		entity.setPassword("123456");
		entity.entryptPassword();
		entity.setIs_reseted_psw(true);
		return userService.modify("批量重置密码", entity);
	}

	// 获取角色集合
	public List<Role> getRoleList() {
		Role r = new Role();
		r.getMap().put("sort_order", true);
		return roleService.queryList(r);
	}

	// 获取二级部门集合
	public List<Department> getDepartmentList() {
		Department r = new Department();
		return departmentService.queryTreeList(r);
	}

	/**
	 * @Description 进入列表页面
	 * @param request
	 * @param model
	 * @return String
	 * @author davidwan
	 */
	@RequestMapping("/sellist")
	public String sellist(HttpServletRequest request, Model model) {
		int key_v = WebUtil.getInt(request, "key_v", 0);
		boolean is_my = WebUtil.getBoolean(request, "is_my", false);
		boolean show_phone = WebUtil.getBoolean(request, "show_phone", false);
		// 获取角色集合
		model.addAttribute("roleList", getRoleList());
		model.addAttribute("key_v", key_v);
		model.addAttribute("is_my", is_my);
		model.addAttribute("show_phone", show_phone);
		model.addAttribute("Tree", companyService.getTree());

		return getPath("sellist");
	}

	/**
	 * @Description 获取列表数据
	 * @param request
	 * @param model
	 * @return String
	 * @author davidwan
	 */
	@RequestMapping("/seldatalist")
	public String seldatalist(HttpServletRequest request, Model model) {
		int pageIndex = WebUtil.getInt(request, "page_index", 0);
		int pageSize = WebUtil.getInt(request, "page_size", ConfigValue.PAGE_SIZE);
		Subject currentUser = SecurityUtils.getSubject();
		ShiroUser shiroUser = (ShiroUser) currentUser.getPrincipal();
		int dept_id = WebUtil.getInt(request, "dept_id", 0);
		int role_id = WebUtil.getInt(request, "role_id", 0);
		int key_v = WebUtil.getInt(request, "key_v", 0);
		boolean is_my = WebUtil.getBoolean(request, "is_my", false);
		boolean show_phone = WebUtil.getBoolean(request, "show_phone", false);
		boolean is_online = WebUtil.getBoolean(request, "is_online", false);

		User entity = new User();
		if (role_id != 0) {
			entity.getMap().put("role_id", role_id);
		}
		if (dept_id != 0) {
			entity.getMap().put("dept_id", dept_id);
		}
		if (!is_my) {
			entity.getMap().put("not_my_id", shiroUser.getId());
		}
		if (is_online) {
			entity.getMap().put("ids", OnlineUser.getInstance().gainUserIdList());
		}
		entity.getMap().put("sort", true);
		PageInfo<User> pageInfo = userService.queryPageList(entity, pageIndex, pageSize);
		model.addAttribute("list", pageInfo.getData());
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("key_v", key_v);
		model.addAttribute("is_my", is_my);
		model.addAttribute("show_phone", show_phone);

		return getPath("seldata_list");
	}

	/**
	 * @Description 获取列表数据
	 * @param request
	 * @param model
	 * @return String
	 * @author davidwan
	 */
	@RequestMapping("/selUserType")
	public String selUserType(HttpServletRequest request, Model model) {
		int type = WebUtil.getInt(request, "type", 0);
		String keyword = WebUtil.getString(request, "keyword", "");
		boolean is_my = WebUtil.getBoolean(request, "is_my", false);
		boolean show_phone = WebUtil.getBoolean(request, "show_phone", false);
		String path = "";
		if (type == 0) {
			User u = new User();
			u.getMap().put("sort", true);
			u.getMap().put("key", keyword);
			model.addAttribute("list", userService.queryList(u));
			path = "seldata_list";
		} else if (type == 1) {
			model.addAttribute("Tree", companyService.getTree());
			path = "sel_dep";
		} else if (type == 2) {
			path = "sel_role";
			model.addAttribute("list", getRoleList());
		} else if (type == 3) {
			path = "sel_online";
		}
		model.addAttribute("is_my", is_my);
		model.addAttribute("show_phone", show_phone);
		return getPath(path);
	}

	/**
	 * @Title: update
	 * @Description: 进入重置密码页面
	 * @param id
	 * @param model
	 * @return String
	 */
	@RequestMapping("/reset_psw")
	public String reset_psw(Model model) {
		Subject currentUser = SecurityUtils.getSubject();
		ShiroUser shiroUser = (ShiroUser) currentUser.getPrincipal();
		User entity = userService.findById(shiroUser.getId());
		model.addAttribute("model", entity);
		return getPath("reset_psw");
	}

	/**
	 * @Title: update
	 * @Description: Ajax保存重置密码信息
	 * @param entity
	 * @return JsonResult
	 */
	@RequestMapping(value = "/reset_psw", method = RequestMethod.POST)
	public @ResponseBody
	JsonResult reset_psw(User entity) {

		return userService.modify_psw(entity);
	}

	/**
	 * @Title: update
	 * @Description: 进入重置密码页面
	 * @param id
	 * @param model
	 * @return String
	 */
	@RequestMapping("/update_psw")
	public String update_psw(Model model) {
		Subject currentUser = SecurityUtils.getSubject();
		ShiroUser shiroUser = (ShiroUser) currentUser.getPrincipal();
		User entity = userService.findById(shiroUser.getId());
		model.addAttribute("model", entity);
		return getPath("update_psw");
	}

	/**
	 * @Title: update
	 * @Description: Ajax保存重置密码信息
	 * @param entity
	 * @return JsonResult
	 */
	@RequestMapping(value = "/update_psw", method = RequestMethod.POST)
	public @ResponseBody
	JsonResult update_psw(User entity) {

		return userService.modify_psw(entity);
	}

	/**
	 * @Title: update
	 * @Description: 进入添加页面
	 * @param id
	 * @param model
	 * @return String
	 */
	@RequestMapping("/update_user")
	public String update_user(Model model) {

		Subject currentUser = SecurityUtils.getSubject();
		ShiroUser shiroUser = (ShiroUser) currentUser.getPrincipal();
		User entity = userService.findById(shiroUser.getId());
		model.addAttribute("model", entity);
		return getPath("update_user");
	}

	/**
	 * @Title: batchDelete
	 * @Description: Ajax批量改变状态
	 * @param ids
	 * @return JsonResult
	 */
	@RequestMapping(value = "/autoUser", method = RequestMethod.POST)
	public @ResponseBody
	AutoMapper autoStaff(HttpServletRequest request, Model model) {
		String query = WebUtil.getString(request, "query", "0");
		try {

			User s = new User();
			if (!"0".equals(query)) {
				s.getMap().put("real_name", query);
			}
			s.getMap().put("sort", true);
			List<User> staffList = userService.queryList(s);
			List<MapperChild> data = new ArrayList<MapperChild>();
			List<String> suggestions = new ArrayList<String>();
			if (staffList == null) {
				staffList = new ArrayList<User>();
			}
			for (User item : staffList) {
				MapperChild m = new MapperChild();
				m.setId(item.getId());
				m.setName(item.getReal_name());
				data.add(m);
				suggestions.add(item.getReal_name());
			}
			AutoMapper auto = new AutoMapper();
			auto.setData(data);
			auto.setQuery(query);
			auto.setSuggestions(suggestions);
			auto.setSuccess(true);
			// 修改下面组装的括号样式时需要到方法中也需要修改括号样式
			return auto;
		} catch (Exception ex) {
			AutoMapper auto = new AutoMapper();
			auto.setQuery(query);
			auto.setSuggestions(new ArrayList<String>());
			auto.setSuccess(false);
			return auto;
		}
	}

	/**
	 * @Title: update
	 * @Description: Ajax保存修改信息
	 * @param entity
	 * @return JsonResult
	 */
	@RequestMapping(value = "/update_user", method = RequestMethod.POST)
	public @ResponseBody
	JsonResult update_user(User entity) {
		return userService.modify("修改个人资料", entity);
	} 

	@Override
	public String getFModulePath() {
		return "core";
	}

	@Override
	public String getModulePath() {
		return "user";
	}

	@Override
	public String getModuleName() {
		return "用户";
	}
}
