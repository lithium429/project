package com.xz.project.core.web.controller.admin.security;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xz.base.utils.WebUtil; 
import com.xz.base.model.JsonResult;
import com.xz.project.core.domain.entity.Menu;
import com.xz.project.core.domain.entity.Role;
import com.xz.project.core.domain.entity.RoleMenu;
import com.xz.project.core.service.security.MenuService;
import com.xz.project.core.service.security.RoleMenuService;
import com.xz.project.core.service.security.RoleService;
import com.xz.project.core.web.controller.admin.AdminController;

@Controller
@RequestMapping(value = "/admin/role")
public class RoleController extends AdminController {

	@Resource
	private RoleService roleService;
	@Resource
	private MenuService menuService;
	@Resource
	private RoleMenuService roleMenuService;

	/**
	 * @Description 进入列表页面
	 * @param request
	 * @param model
	 * @return String
	 * @author davidwan
	 */
	@RequestMapping("/list")
	public String list(HttpServletRequest request, Model model) {
		int menu_id = WebUtil.getInt(request, "menu_id", 0);
		model.addAttribute("menu_id", menu_id);
		/* getList(request, model); */
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
		int menu_id = WebUtil.getInt(request, "menu_id", 0);
		getList(request, model);
		model.addAttribute("roleMenuList", super.gainRoleMenu(menu_id));
		return getPath("data_list");
	}

	/**
	 * @Description 获取列表数据
	 * @param request
	 * @param model
	 * @return String
	 * @author davidwan
	 */
	@RequestMapping("/rolePermission")
	public String rolePermission(HttpServletRequest request, Model model) {
		int menu_id = WebUtil.getInt(request, "menu_id", 0);
		int roleId = WebUtil.getInt(request, "roleId", 0);
		Menu m = new Menu();
		m.setIs_enable(true);
		m.setLayer(1);
		m.getMap().put("sort_s", true);
		model.addAttribute("menuList1", menuService.queryListx(m, "1"));
		m.setLayer(2);
		model.addAttribute("menuList2", menuService.queryListx(m, "1"));
		m.setLayer(3);
		model.addAttribute("menuList3", menuService.queryListx(m, "1"));
		RoleMenu r = new RoleMenu();
		r.setRole_id(roleId);
		model.addAttribute("roleMenuList", roleMenuService.queryList(r));
		model.addAttribute("roleMenuList_l", super.gainRoleMenu(menu_id));
		
		Role role = roleService.findById(roleId);
		model.addAttribute("role", role);
		return getPath("rolePermission");
	}

	/**
	 * @Description 获取列表数据
	 * @param request
	 * @param model
	 * @author davidwan
	 */
	private void getList(HttpServletRequest request, Model model) {
		String name = WebUtil.getString(request, "name", "");
		Role entity = new Role();
		if (StringUtils.isNotBlank(name)) {
			// entity.setName(name);
		}
		entity.getMap().put("sort_order", true);
		
		List<Role> list = roleService.queryList(entity);
		model.addAttribute("list", list);
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
		Role r = new Role();
		r.setIs_super(false);
		r.getMap().put("sort_order", true);
		List<Role> list = roleService.queryList(r);
		model.addAttribute("roleList", list);
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
	JsonResult add(Role entity) {
		return roleService.create(entity);
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
		Role entity = roleService.findById(id);
		model.addAttribute("model", entity);

		Role r = new Role();
		r.setIs_super(false);
		r.getMap().put("sort_order", true);
		List<Role> list = roleService.queryList(r);
		model.addAttribute("roleList", list);
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
	JsonResult update(Role entity) {
		return roleService.modify(entity);
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
		return roleService.removeById(id);
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
		return roleService.removeByIds(ids);
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
		Role entity = roleService.findById(id);
		model.addAttribute("model", entity);
		return getPathView();
	}

	/**
	 * @Title: update
	 * @Description: Ajax保存修改信息
	 * @param entity
	 * @return JsonResult
	 */
	@RequestMapping(value = "/rolepower", method = RequestMethod.POST)
	public @ResponseBody
	JsonResult rolepower(HttpServletRequest request) {
		int role_id = WebUtil.getInt(request, "role_id", 0);
		String menu_Ids = WebUtil.getString(request, "MenuIds", "");
		String action_Ids = WebUtil.getString(request, "MenuActionIds", "");

		return roleMenuService.modify(menu_Ids, action_Ids, role_id);
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
		boolean result = roleService.validateName(id, name);
		return result;
	}

	/**
	 * @Title: add
	 * @Description: Ajax设置超管
	 * @param entity
	 * @return JsonResult
	 * 
	 */
	@RequestMapping(value = "/setSuper", method = RequestMethod.POST)
	public @ResponseBody
	JsonResult setSuper() {
		return roleService.modifySuper();
	}

	@Override
	public String getFModulePath() {
		return "core";
	}

	@Override
	public String getModulePath() {
		return "role";
	}

	@Override
	public String getModuleName() {
		return "角色";
	}
}
