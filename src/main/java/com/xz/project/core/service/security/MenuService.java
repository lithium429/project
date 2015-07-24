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
import com.xz.project.core.domain.entity.Menu;
import com.xz.project.core.domain.entity.Role;
import com.xz.project.core.domain.entity.RoleMenu;

@Service
public class MenuService {

	@Resource
	private MenuDao menuDao;
	
	@Resource
	private RoleDao roleDao;

	@Resource
	private RoleMenuDao roleMenuDao;

	/**
	 * @Description 根据Id获取实体
	 * @param id
	 * @return Menu
	 * @author davidwan
	 */
	public Menu findById(Integer id) {
		Menu entity = new Menu();
		entity.setId(id);
		return menuDao.selectEntity(entity);
	}

	/**
	 * @Description 根据条件获取实体
	 * @param entity
	 * @return Menu
	 * @author davidwan
	 */
	public Menu find(Menu entity) {
		return menuDao.selectEntity(entity);
	}

	/**
	 * @Description 根据条件获取列表
	 * @param entity
	 * @return List<Menu>
	 * @author davidwan
	 */
	public List<Menu> queryList(Menu entity) {
		return menuDao.selectEntityList(entity);
	}

	/**
	 * @Description 根据条件获取列表
	 * @param entity
	 * @return List<Menu>
	 * @author davidwan
	 */
	public List<Menu> queryListx(Menu entity, String x) {
		return menuDao.selectEntityList(entity, x);
	}

	/**
	 * @Description 根据条件获取分页列表
	 * @param entity
	 * @param pageIndex
	 * @param pageSize
	 * @return PageInfo<Menu>
	 * @author davidwan
	 */
	public PageInfo<Menu> queryPageList(Menu entity, int pageIndex, int pageSize) {
		return menuDao.selectEntityPageList(entity, pageIndex, pageSize);
	}

	/**
	 * @Description 添加
	 * @param entity
	 * @return JsonResult
	 * @author davidwan
	 */
	public JsonResult create(Menu entity) {
		entity.setCreate_time(new Date());
		entity.setIs_leaf(true);
		// 检查当前sort是否存在
		Menu m = new Menu(), me = new Menu();
		m.getMap().put("id", 0);
		m.getMap().put("sort", entity.getSort());
		m.getMap().put("sort_max_valid", true);
		this.menuDao.updateEntity(m);
		// 若要获取id，请使用entity.getId();
		int result = menuDao.insertEntity(entity);
		int id = entity.getId();
		// 操作子集id集合
		Integer parentId = entity.getParent_id();
		while (parentId != null) {
			m = new Menu();
			m.setId(parentId);
			me = this.menuDao.selectEntity(m);
			if (me != null) {
				parentId = me.getParent_id();
				if (me.getChild_ids() != null && !"".equals(me.getChild_ids())) {
					m.setChild_ids(me.getChild_ids() + "," + id);
				} else {
					m.setChild_ids(String.valueOf(id));
				}
				m.setIs_leaf(false);
				this.menuDao.updateEntity(m);
			}
		}

		// 添加菜单时，自动为系统管理员添加菜单权限
		Role role = new Role(true);
		role = this.roleDao.selectEntity(role);
		if (role != null) {
			RoleMenu roleMenu = new RoleMenu(role.getId(), id);
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
	public JsonResult modify(Menu entity, Integer old_parent_id) {
		// 检查当前sort是否存在
		Menu m = new Menu();
		int id = entity.getId();
		m.getMap().put("id", id);
		m.getMap().put("sort", entity.getSort());
		m.getMap().put("sort_valid", true);
		if (menuDao.selectEntityCount(m) > 0) {
			// 更新大于等于当前sort的菜单（sort+1）
			m.getMap().put("sort_valid", null);
			m.getMap().put("sort_max_valid", true);
			this.menuDao.updateEntity(m);
		}
		entity.setChild_ids("");
		m = new Menu();
		m.setId(old_parent_id);
		m.setIs_leaf(true);
		this.menuDao.updateEntity(m);
		m = new Menu();
		m.setId(entity.getParent_id());
		m.setIs_leaf(false);
		this.menuDao.updateEntity(m);
		int result = menuDao.updateEntity(entity);
		/*
		 * if (old_parent_id != entity.getParent_id()) { int i = 1;me = new
		 * Menu() while (old_parent_id != null) { m = new Menu();
		 * m.setId(old_parent_id); me = this.menuDao.selectEntity(m); if (me !=
		 * null) { old_parent_id = me.getParent_id();
		 * 
		 * if (i == 1) { m.setChild_ids(""); m.setIs_leaf(true); } else {
		 * m.setChild_ids(me.getChild_ids().substring(0,
		 * me.getChild_ids().indexOf(","))); } this.menuDao.updateEntity(m);
		 * i++; } } }
		 * 
		 * Integer parentId = entity.getParent_id(); while (parentId != null) {
		 * m = new Menu(); m.setId(parentId); me = this.menuDao.selectEntity(m);
		 * if (me != null) { parentId = me.getParent_id(); if (me.getChild_ids()
		 * != null && !"".equals(me.getChild_ids())) {
		 * m.setChild_ids(me.getChild_ids() + "," + id); } else {
		 * m.setChild_ids(String.valueOf(id)); } m.setIs_leaf(false);
		 * this.menuDao.updateEntity(m); } }
		 */
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
	public JsonResult modify(Menu entity) {
		int result = menuDao.updateEntity(entity);
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
		Menu entity = new Menu(), m = new Menu(), me = new Menu();
		entity.setId(id);
		m = this.menuDao.selectEntity(entity);
		me.setId(m.getParent_id());
		me.setIs_leaf(true);

		int result = menuDao.deleteEntity(entity);

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
		Menu entity = new Menu();
		entity.getMap().put("ids", ids.split(","));
		int result = menuDao.deleteEntity(entity);
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
	public JsonResult remove(Menu entity) {
		int result = menuDao.deleteEntity(entity);
		if (result > 0) {
			return new JsonResult(true);
		} else {
			return new JsonResult(false);
		}
	}

	public boolean validateUrl(Integer id, String url) {
		Menu t = new Menu();
		if (id == null) {
			id = 0;
		}
		t.getMap().put("id", id);
		t.getMap().put("url", url);
		t.getMap().put("url_valid", "true");
		int count = menuDao.selectEntityCount(t);
		if (count > 0) {
			return false;
		} else {
			return true;
		}
	}

	/*
	 * public void update_child_ids() { Map<Integer, String> map=new
	 * HashMap<Integer, String>(); Menu t=new Menu(); t. }
	 */

}
