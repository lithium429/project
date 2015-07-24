package com.xz.project.core.web.controller.admin.organization;
 
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xz.base.utils.ConfigValue; 
import com.xz.base.utils.WebUtil;
import com.xz.base.controller.SpringBaseController;
import com.xz.base.model.AutoMapper;
import com.xz.base.model.JsonResult;
import com.xz.base.model.MapperChild;
import com.xz.base.model.PageInfo;
import com.xz.base.model.ZTreeNode; 
import com.xz.project.core.domain.entity.Department;
import com.xz.project.core.service.organization.DepartmentService;

@Controller
@RequestMapping(value = "/admin/department")
public class DepartmentController extends SpringBaseController {

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
		String name = WebUtil.getString(request, "name", "");
		Department entity = new Department();
		if (StringUtils.isNotBlank(name)) {
			// entity.setName(name);
		}
		PageInfo<Department> pageInfo = departmentService.queryPageList(entity, pageIndex, ConfigValue.PAGE_SIZE);
		model.addAttribute("list", pageInfo.getData());
	}

	/**
	 * @Title: add
	 * @Description: 进入添加页面
	 * @param request
	 * @param model
	 * @return String
	 */
	@RequestMapping("/add")
	public String add(HttpServletRequest request,Integer pid, Model model) {
		Department entity=new Department();
		entity.setParent_id(pid);
		model.addAttribute("model", entity);
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
	JsonResult add(Department entity) {
		return departmentService.create(entity);
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
		Department entity = departmentService.findById(id);
		model.addAttribute("model", entity);
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
	JsonResult update(Department entity) {
		return departmentService.modify(entity);
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
		return departmentService.removeById(id);
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
		return departmentService.removeByIds(ids);
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
		Department entity = departmentService.findById(id);
		model.addAttribute("model", entity);
		return getPathView();
	}

	// / <summary>
	// / ajsx异步加载子节点
	// / </summary>
	// / <returns></returns>
	@RequestMapping(value = "getSonNodes.do", method = RequestMethod.POST)
	public @ResponseBody
	List<ZTreeNode> getSonNodes(HttpServletRequest request, Model model) {
		Integer id = WebUtil.getInt(request, "id", 0);
		/*int type = WebUtil.getInt(request, "type", 0);*/

		return departmentService.getJson(id == 0 ? null : id);
	}

	// / <summary>
	// / ajsx异步加载子节点
	// / </summary>
	// / <returns></returns>
	@RequestMapping(value = "getSonNodes_total.do", method = RequestMethod.POST)
	public @ResponseBody
	List<ZTreeNode> getSonNodes_total(HttpServletRequest request, Model model) {
		Integer id = WebUtil.getInt(request, "id", 0);
		/*int type = WebUtil.getInt(request, "type", 0);*/

		return departmentService.getJson_total(id == 0 ? null : id);
	}
	

	/**
	 * @Description 获取部门在线用户树 
	 * @return JsonResult     
	 */
	@RequestMapping("/onlineuser")
	public @ResponseBody
	JsonResult onlineUser() {
		List<ZTreeNode> treeList = departmentService.queryTreeListWithOnlineUsers(new Department(), null);
		return new JsonResult(true, null, treeList);
	}
	/**
	 * @Title: batchDelete
	 * @Description: Ajax批量改变状态
	 * @param ids
	 * @return JsonResult
	 */
	@RequestMapping(value = "/autoDepartment", method = RequestMethod.POST)
	public @ResponseBody
	AutoMapper autoDepartment(HttpServletRequest request, Model model) {
		String query = WebUtil.getString(request, "query", "0");
		try {

			Department s = new Department();
			if (!"0".equals(query)) {
				s.getMap().put("code_key", query);
			}
			s.getMap().put("sort", true);
			List<Department> staffList = departmentService.queryList(s);
			List<MapperChild> data = new ArrayList<MapperChild>();
			List<String> suggestions = new ArrayList<String>();
			if (staffList == null) {
				staffList = new ArrayList<Department>();
			}
			for (Department item : staffList) {
				MapperChild m = new MapperChild();
				m.setId(item.getId());
				m.setName(item.getName());
				data.add(m);
				suggestions.add(item.getName());
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

	@Override
	public String getFModulePath() {
		return "core";
	}

	@Override
	public String getModulePath() {
		return "department";
	}

	@Override
	public String getModuleName() {
		return "模块名称";
	}
}
