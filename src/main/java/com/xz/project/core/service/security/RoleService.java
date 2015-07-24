package com.xz.project.core.service.security;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xz.base.model.JsonResult;
import com.xz.base.model.PageInfo;
import com.xz.project.core.dao.MenuDao;
import com.xz.project.core.dao.RoleDao;
import com.xz.project.core.dao.RoleMenuDao;
import com.xz.project.core.domain.entity.Action;
import com.xz.project.core.domain.entity.Menu;
import com.xz.project.core.domain.entity.Role;
import com.xz.project.core.domain.entity.RoleMenu;
import com.xz.project.core.domain.enums.EnumLogModule;
import com.xz.project.core.service.log.SystemLogService;

@Service
public class RoleService {

	@Resource
	private RoleDao roleDao;
	@Resource
	private RoleMenuDao roleMenuDao;
	@Resource
	private MenuDao menuDao;
	@Resource
	private SystemLogService systemLogService;

	/**
	 * @Description 根据Id获取实体
	 * @param id
	 * @return Role
	 * @author davidwan
	 */
	public Role findById(Integer id) {
		Role entity = new Role();
		entity.setId(id);
		return roleDao.selectEntity(entity);
	}

	/**
	 * @Description 根据条件获取实体
	 * @param entity
	 * @return Role
	 * @author davidwan
	 */
	public Role find(Role entity) {
		return roleDao.selectEntity(entity);
	}

	/**
	 * @Description 根据条件获取列表
	 * @param entity
	 * @return List<Role>
	 * @author davidwan
	 */
	public List<Role> queryList(Role entity) {
		return roleDao.selectEntityList(entity);
	}

	/**
	 * @Description 根据条件获取分页列表
	 * @param entity
	 * @param pageIndex
	 * @param pageSize
	 * @return PageInfo<Role>
	 * @author davidwan
	 */
	public PageInfo<Role> queryPageList(Role entity, int pageIndex, int pageSize) {
		return roleDao.selectEntityPageList(entity, pageIndex, pageSize);
	}

	/**
	 * @Description 添加
	 * @param entity
	 * @return JsonResult
	 * @author davidwan
	 */
	public JsonResult create(Role entity) {
		entity.setCreate_time(new Date());
		Role t = new Role();
		Integer sort = roleDao.selectEntitySort(t);
		entity.setSort((sort == null ? 0 : sort) + 1);
		// 若要获取id，请使用entity.getId();
		int result = roleDao.insertEntity(entity);
		int role_id = entity.getId();
		if (entity.getRole_id() != null) {
			RoleMenu ru = new RoleMenu();
			ru.setRole_id(entity.getRole_id());
			List<RoleMenu> ruList = roleMenuDao.selectEntityList(ru);
			for (RoleMenu item : ruList) {
				item.setId(null);
				item.setRole_id(role_id);
				roleMenuDao.insertEntity(item);
			}
		}
		if (result > 0) {
			// 添加操作日志
			systemLogService.create(EnumLogModule.角色权限.getValue(), "添加角色", "添加角色：" + entity.getName());
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
	public JsonResult modify(Role entity) {
		int result = roleDao.updateEntity(entity);
		if (result > 0) {
			// 添加操作日志
			systemLogService.create(EnumLogModule.角色权限.getValue(), "修改角色", "修改角色：" + entity.getName());
			return new JsonResult(true);
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
		Role entity = new Role();
		entity.setId(id);
		int result = roleDao.deleteEntity(entity);
		if (result > 0) {
			// 添加操作日志
			systemLogService.create(EnumLogModule.角色权限.getValue(), "删除角色", "删除角色ID：" + id);
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
		Role entity = new Role();
		entity.getMap().put("ids", ids.split(","));
		int result = roleDao.deleteEntity(entity);
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
	public JsonResult remove(Role entity) {
		int result = roleDao.deleteEntity(entity);
		if (result > 0) {
			return new JsonResult(true);
		} else {
			return new JsonResult(false);
		}
	}

	// 验证名称重名
	public boolean validateName(Integer id, String url) {
		Role t = new Role();
		if (id == null) {
			id = 0;
		}
		t.getMap().put("id", id);
		t.getMap().put("name", url);
		t.getMap().put("name_valid", "true");
		int count = roleDao.selectEntityCount(t);
		if (count > 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * @Description 设置超管
	 * @param entity
	 * @return JsonResult
	 * @author davidwan
	 */
	public JsonResult modifySuper() {
		Role entity = new Role();
		entity.setIs_super(true);
		entity = roleDao.selectEntity(entity);
		if (entity == null) {
			entity = new Role();
			entity.setIs_super(true);
			entity.setName("超级管理员");
			entity.setCreate_time(new Date());
			entity.setSort(0);
			roleDao.insertEntity(entity);
		}
		int role_id = entity.getId();
		RoleMenu rm = new RoleMenu();
		rm.setRole_id(role_id);
		roleMenuDao.deleteEntity(rm);
		Menu m = new Menu();
		List<Menu> muList = menuDao.selectEntityList(m, "1");
		for (Menu item : muList) {
			rm = new RoleMenu();
			rm.setRole_id(role_id);
			rm.setMenu_id(item.getId());
			rm.setIs_action(false);
			roleMenuDao.insertEntity(rm);
			if (item.getActions() != null) {
				for (Action temp : item.getActions()) {
					rm = new RoleMenu();
					rm.setRole_id(role_id);
					rm.setAction_id(temp.getId());
					rm.setIs_action(true);
					roleMenuDao.insertEntity(rm);
				}
			}
		}
		return new JsonResult(true);
	}

	/**
	 * @Description 根据名称获取角色
	 * @param name
	 * @return User
	 * @author davidwan
	 */
	public Role findByName(String name) {
		Role entity = new Role();
		entity.setName(name);
		return roleDao.selectEntity(entity);
	}
}
