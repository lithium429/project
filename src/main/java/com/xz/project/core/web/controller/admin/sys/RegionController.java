package com.xz.project.core.web.controller.admin.sys;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xz.base.utils.LogHelper;
import com.xz.base.utils.WebUtil;
import com.xz.base.model.JsonResult;
import com.xz.project.core.domain.entity.Region;
import com.xz.project.core.service.sys.RegionService;
import com.xz.project.core.web.controller.admin.AdminController;

@Controller
@RequestMapping(value = "/admin/region")
public class RegionController extends AdminController {

	@Resource
	private RegionService regionService;

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
		model.addAttribute("roleMenuList", super.gainRoleMenu(menu_id));
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
		int parent_id = WebUtil.getInt(request, "parent_id", 0);
		int type = WebUtil.getInt(request, "type", 1);
		String name = WebUtil.getString(request, "name", "");
		Region entity = new Region();
		if (StringUtils.isNotBlank(name)) {
			// entity.setName(name);
		}
		entity.setType(type);
		if (parent_id != 0) {
			entity.setParent_id(parent_id);
		}
		if (type != 1 && parent_id == 0) {
			entity.setParent_id(0);
		}
		model.addAttribute("type_str", gainTypeStr(type));
		model.addAttribute("type", type);
		model.addAttribute("list", regionService.queryList(entity));
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
		int parent_id = WebUtil.getInt(request, "parent_id", 0);
		int type = WebUtil.getInt(request, "type", 1);
		model.addAttribute("type", type);
		model.addAttribute("type_str", gainTypeStr(type));
		if (parent_id != 0) {
			model.addAttribute("parent_id", parent_id);
		}
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
	JsonResult add(Region entity) {
		return regionService.create(entity);
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
		Region entity = regionService.findById(id);
		model.addAttribute("type_str", gainTypeStr(entity.getType()));
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
	JsonResult update(Region entity) {
		return regionService.modify(entity);
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
		return regionService.removeById(id);
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
		return regionService.removeByIds(ids);
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
		Region entity = regionService.findById(id);
		model.addAttribute("model", entity);
		return getPathView();
	}

	/**
	 * @Title: view
	 * @Description: 根据父级Id获取区域的集合
	 * @param city_id
	 * @param model
	 * @return Map<String, String>
	 */
	@RequestMapping(value = "getList.do", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, String> getList(int parent_id) {
		try {
			Map<String, String> map = new LinkedHashMap<String, String>();
			Region obj = new Region();
			obj.setParent_id(parent_id);
			List<Region> childList = regionService.queryList(obj);
			if (childList != null && childList.size() > 0) {
				for (Region item : childList) {
					map.put(item.getId().toString() + "|" + item.getId().toString(), item.getName());
				}
			}
			return map;
		} catch (Exception ex) {
			LogHelper.getLogger().error("根据市Id获取县的集合时出错", ex);
			return null;
		}
	}

	/**
	 * @Title: batchDelete
	 * @Description: 验证名称重名
	 * @param ids
	 * @return JsonResult
	 */
	@RequestMapping(value = "/validate_name", method = RequestMethod.POST)
	public @ResponseBody
	boolean validateName(Integer id, String name, Integer parent_id) {
		boolean result = regionService.validateName(id, name, parent_id);
		return result;
	}

	// 获取名称
	public String gainTypeStr(int type) {
		String r = "";
		switch (type) {
		case 1:
			r = "省";
			break;
		case 2:
			r = "市";
			break;
		case 3:
			r = "区/县";
			break;
		case 4:
			r = "乡";
			break;
		}
		return r;
	}

	@Override
	public String getFModulePath() {
		return "core";
	}

	@Override
	public String getModulePath() {
		return "region";
	}

	@Override
	public String getModuleName() {
		return "区域";
	}
}
