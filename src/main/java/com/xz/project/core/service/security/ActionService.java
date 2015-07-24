package com.xz.project.core.service.security;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.xz.base.model.JsonResult;
import com.xz.base.model.PageInfo;
import com.xz.project.core.dao.ActionDao;
import com.xz.project.core.dao.RoleDao;
import com.xz.project.core.dao.RoleMenuDao;
import com.xz.project.core.domain.entity.Action;
import com.xz.project.core.domain.entity.Role;
import com.xz.project.core.domain.entity.RoleMenu;

@Service
public class ActionService {

	@Resource
	private ActionDao actionDao;

	@Resource
	private RoleDao roleDao;

	@Resource
	private RoleMenuDao roleMenuDao;

	/**
	 * @Description 根据Id获取实体
	 * @param id
	 * @return Action
	 * @author davidwan
	 */
	public Action findById(Integer id) {
		Action entity = new Action();
		entity.setId(id);
		return actionDao.selectEntity(entity);
	}

	/**
	 * @Description 根据条件获取实体
	 * @param entity
	 * @return Action
	 * @author davidwan
	 */
	public Action find(Action entity) {
		return actionDao.selectEntity(entity);
	}

	/**
	 * @Description 根据条件获取列表
	 * @param entity
	 * @return List<Action>
	 * @author davidwan
	 */
	public List<Action> queryList(Action entity) {
		return actionDao.selectEntityList(entity);
	}

	/**
	 * @Description 根据条件获取分页列表
	 * @param entity
	 * @param pageIndex
	 * @param pageSize
	 * @return PageInfo<Action>
	 * @author davidwan
	 */
	public PageInfo<Action> queryPageList(Action entity, int pageIndex, int pageSize) {
		return actionDao.selectEntityPageList(entity, pageIndex, pageSize);
	}

	/**
	 * @Description 添加
	 * @param entity
	 * @return JsonResult
	 * @author davidwan
	 */
	public JsonResult create(Action entity) {
		Action t = new Action();
		Integer sort = this.actionDao.selectEntitySort(t);
		entity.setSort((sort == null ? 0 : sort) + 1);
		entity.setCreate_time(new Date());
		// 若要获取id，请使用entity.getId();
		int result = actionDao.insertEntity(entity);

		// 添加菜单时，自动为系统管理员添加按钮权限
		Role role = new Role(true);
		role = this.roleDao.selectEntity(role);
		if (role != null) {
			RoleMenu roleMenu = new RoleMenu(role.getId(), entity.getId(), true);
			this.roleMenuDao.insertEntity(roleMenu);
		}

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
	public JsonResult modify(Action entity) {
		int result = actionDao.updateEntity(entity);
		if (result > 0) {
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
		Action entity = new Action();
		entity.setId(id);
		int result = actionDao.deleteEntity(entity);
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
		Action entity = new Action();
		entity.getMap().put("ids", ids.split(","));
		int result = actionDao.deleteEntity(entity);
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
	public JsonResult remove(Action entity) {
		int result = actionDao.deleteEntity(entity);
		if (result > 0) {
			return new JsonResult(true);
		} else {
			return new JsonResult(false);
		}
	}

}
