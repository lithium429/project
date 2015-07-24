package com.xz.project.core.service.security;

import java.util.List;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xz.base.model.JsonResult;
import com.xz.base.model.PageInfo;
import com.xz.project.core.dao.RoleMenuDao;
import com.xz.project.core.dao.SystemLogDao;
import com.xz.project.core.domain.entity.RoleMenu;
import com.xz.project.core.domain.enums.EnumLogModule;
import com.xz.project.core.service.log.SystemLogService;

@Service
public class RoleMenuService {

	@Resource
	private RoleMenuDao roleMenuDao;
	@Resource
	private SystemLogDao systemLogDao;
	@Resource
	private SystemLogService systemLogService;

	/**
	 * @Description 根据Id获取实体
	 * @param id
	 * @return RoleMenu
	 * @author davidwan
	 */
	public RoleMenu findById(Integer id) {
		RoleMenu entity = new RoleMenu();
		entity.setId(id);
		return roleMenuDao.selectEntity(entity);
	}

	/**
	 * @Description 根据条件获取实体
	 * @param entity
	 * @return RoleMenu
	 * @author davidwan
	 */
	public RoleMenu find(RoleMenu entity) {
		return roleMenuDao.selectEntity(entity);
	}

	/**
	 * @Description 根据条件获取列表
	 * @param entity
	 * @return List<RoleMenu>
	 * @author davidwan
	 */
	public List<RoleMenu> queryList(RoleMenu entity) {
		return roleMenuDao.selectEntityList(entity);
	}

	/**
	 * @Description 根据条件获取列表
	 * @param entity
	 * @return List<RoleMenu>
	 * @author davidwan
	 */
	public List<RoleMenu> queryList_power(RoleMenu entity) {
		return roleMenuDao.selectEntityList(entity, "_power");
	}

	/**
	 * @Description 根据条件获取分页列表
	 * @param entity
	 * @param pageIndex
	 * @param pageSize
	 * @return PageInfo<RoleMenu>
	 * @author davidwan
	 */
	public PageInfo<RoleMenu> queryPageList(RoleMenu entity, int pageIndex, int pageSize) {
		return roleMenuDao.selectEntityPageList(entity, pageIndex, pageSize);
	}

	/**
	 * @Description 添加
	 * @param entity
	 * @return JsonResult
	 * @author davidwan
	 */
	public JsonResult create(RoleMenu entity) {
		// 若要获取id，请使用entity.getId();
		int result = roleMenuDao.insertEntity(entity);
		if (result > 0) {
			return new JsonResult(true);
		} else {
			return new JsonResult(false);
		}
	}

	/**
	 * @Description 修改
	 * @param entity
	 * @return JsonResult
	 * @author davidwan
	 */
	public JsonResult modify(RoleMenu entity) {
		int result = roleMenuDao.updateEntity(entity);
		if (result > 0) {
			return new JsonResult(true);
		} else {
			return new JsonResult(false);
		}
	}

	/**
	 * @Description 修改 （权限设置）
	 * @param entity
	 * @return JsonResult
	 * @author davidwan
	 */
	public JsonResult modify(String menu_Ids, String action_Ids, int role_id) {
		String[] menuIds = null, actionIds = null;
		if (!"".equals(menu_Ids)) {
			menuIds = menu_Ids.split(",");
		}
		if (!"".equals(action_Ids)) {
			actionIds = action_Ids.split(",");
		}
		// 删除权限
		RoleMenu entity = new RoleMenu();
		entity.setRole_id(role_id);
		roleMenuDao.deleteEntity(entity);
		int result = 0;
		// 菜单权限添加
		if (menuIds != null) {
			for (String item : menuIds) {
				Integer menu_id = Integer.valueOf(item);
				entity = new RoleMenu();
				entity.setRole_id(role_id);
				entity.setMenu_id(menu_id);
				entity.setIs_action(false);
				result = roleMenuDao.insertEntity(entity);
			}
		}
		// 动作权限添加
		if (actionIds != null) {
			for (String temp : actionIds) {
				Integer action_id = Integer.valueOf(temp);
				entity = new RoleMenu();
				entity.setRole_id(role_id);
				entity.setAction_id(action_id);
				entity.setIs_action(true);
				result = roleMenuDao.insertEntity(entity);
			}
		}
		if (result > 0) {
			// 添加操作日志
			systemLogService.create(EnumLogModule.角色权限.getValue(), "权限设置", "权限设置角色ID：" + role_id);
			return new JsonResult(true, "权限设置成功！");
		} else {
			return new JsonResult(false);
		}
	}

	/**
	 * @Description 根据Id删除
	 * @param id
	 * @return JsonResult
	 * @author davidwan
	 */
	public JsonResult removeById(Integer id) {
		RoleMenu entity = new RoleMenu();
		entity.setId(id);
		int result = roleMenuDao.deleteEntity(entity);
		if (result > 0) {
			return new JsonResult(true);
		} else {
			return new JsonResult(false);
		}
	}

	/**
	 * @Description 根据多个Id删除
	 * @param ids
	 * @return JsonResult
	 * @author davidwan
	 */
	public JsonResult removeByIds(String ids) {
		RoleMenu entity = new RoleMenu();
		entity.getMap().put("ids", ids.split(","));
		int result = roleMenuDao.deleteEntity(entity);
		if (result > 0) {
			return new JsonResult(true);
		} else {
			return new JsonResult(false);
		}
	}

	/**
	 * @Description 根据指定条件删除
	 * @param entity
	 * @return JsonResult
	 * @author davidwan
	 */
	public JsonResult remove(RoleMenu entity) {
		int result = roleMenuDao.deleteEntity(entity);
		if (result > 0) {
			return new JsonResult(true);
		} else {
			return new JsonResult(false);
		}
	}

	// 对权限设置中的checkbox设置
	public static Boolean rolePowerSel(List<RoleMenu> powerList, Integer currentId, Boolean isAction) {
		Boolean result = false;
		for (RoleMenu rolepower : powerList) {
			if (isAction) {
				if (currentId.equals(rolepower.getAction_id())) {
					result = true;
					break;
				}
			} else {
				if (currentId.equals(rolepower.getMenu_id())) {
					result = true;
					break;
				}
			}
		}
		return result;
	}

	// 判断权限控制
	public static boolean judgeRoleMenu(List<RoleMenu> rList, String action_name) {
		boolean flag = false;
		if (rList != null) {
			for (RoleMenu item : rList) {
				if (item.getAction_name().equals(action_name)) {
					flag = true;
					break;
				}
			}
		}
		return flag;
	}

}
