/**   
 * @Title: AuthorUserService.java 
 * @Package: com.xz.oa.core.service.account 
 * @Description: 
 * @author: davidwan
 * @date: 2014-7-14 上午10:37:30 
 * @version: V1.0   
 */
package com.xz.project.core.service.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.xz.project.core.dao.*;
import org.springframework.stereotype.Service;

import com.xz.base.model.JsonResult;
import com.xz.base.model.PageInfo;
import com.xz.base.utils.SpellUtil;
import com.xz.base.utils.StringUtil;
import com.xz.project.core.domain.entity.Menu;
import com.xz.project.core.domain.entity.Role;
import com.xz.project.core.domain.entity.RoleMenu;
import com.xz.project.core.domain.entity.User;
import com.xz.project.core.domain.entity.UserRole;
import com.xz.project.core.domain.enums.EnumLogModule;
import com.xz.project.core.service.log.SystemLogService;

@Service
public class UserService {
	@Resource
	private UserDao userDao;
	
	@Resource
	private UserRoleDao userRoleDao; 
	
	@Resource
	private DepartmentDao departmentDao;
	
	@Resource
	private RoleMenuDao roleMenuDao;
	
	@Resource
	private RoleDao roleDao;
	
	@Resource
	private SystemLogService systemLogService;

	@Resource
	private ActivitiDao activitiDao;
	 

	/**
	 * @Description 根据用户名获取用户
	 * @param name
	 * @return User
	 * @author davidwan
	 */
	public User findByName(String name) {
		User entity = new User();
		entity.setName(name);
		return userDao.selectEntity(entity);
	}

	/**
	 * @Description 根据Id获取用户
	 * @param id
	 * @return User
	 * @author davidwan
	 */
	public User findById(Integer id) {
		User entity = new User();
		entity.setId(id);
		return userDao.selectEntity(entity);
	}

	/**
	 * @Description 根据条件获取 用户
	 * @param entity
	 * @return User
	 * @author davidwan
	 */
	public User find(User entity) {
		return userDao.selectEntity(entity);
	}

	/**
	 * @Description 根据条件获取列表
	 * @param entity
	 * @return List<User>
	 * @author davidwan
	 */
	public List<User> queryList(User entity) {
		return userDao.selectEntityList(entity);
	}

	/**
	 * @Description 根据条件获取列表
	 * @param entity
	 * @return List<User>
	 * @author davidwan
	 */
	public List<User> queryList(User entity, String mapId) {
		return userDao.selectEntityList(entity, mapId);
	}

	/**
	 * @Description 根据条件获取分页列表
	 * @param entity
	 * @param pageIndex
	 * @param pageSize
	 * @return PageInfo<User>
	 * @author davidwan
	 */
	public PageInfo<User> queryPageList(User entity, int pageIndex, int pageSize) {
		return userDao.selectEntityPageList(entity, pageIndex, pageSize);
	}

	/**
	 * @Description 获取用户菜单
	 * @param userId
	 * @return List<Menu>
	 */
	public Map<Integer, List<Menu>> queryUserMenus(Integer userId) {
		Map<Integer, List<Menu>> menuMap = null;
		RoleMenu entity = new RoleMenu();
		entity.getMap().put("user_id", userId);
		List<RoleMenu> list = roleMenuDao.selectEntityList(entity, "ByUser");
		if (list != null && !list.isEmpty()) {
			Menu menu = new Menu();
			menuMap = menu.groupByLayer(list);
		}

		return menuMap;
	}

	/**
	 * @Description 添加
	 * @param entity
	 * @return JsonResult
	 * @author davidwan
	 */
	public JsonResult create(User entity) {
		entity.setCreate_time(new Date());
		entity.setPassword("123456");
		entity.entryptPassword();
		entity.setPname(SpellUtil.getFullSpell(entity.getReal_name()));
		entity.setFname(SpellUtil.getFirstOneSpell(entity.getReal_name()));

		// 若要获取id，请使用entity.getId();
		int result = userDao.insertEntity(entity);
		int user_id = entity.getId();
		// 用户角色设置
		UserRole u = new UserRole();
		u.setUser_id(user_id);
		userRoleDao.deleteEntity(u);
		if (entity.getRole_ids() != null) {
			for (String item : entity.getRole_ids().split(",")) {
				u = new UserRole();
				u.setUser_id(user_id);
				u.setRole_id(Integer.valueOf(item));
				userRoleDao.insertEntity(u);
			}
		} 
		
		if (result > 0) {
			//添加操作日志
			systemLogService.create(EnumLogModule.组织架构.getValue(), "添加用户", "添加用户：" + entity.getReal_name());
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
	public JsonResult modify(String log_name, User entity) {
		int result = userDao.updateEntity(entity);

		if (result > 0) {
			if (!"".equals(log_name)) {
				//添加操作日志
				systemLogService.create(EnumLogModule.组织架构.getValue(), log_name + "用户", log_name + "用户ID：" + (entity.getId() == null ? StringUtil.buildIds((String[])entity.getMap().get("ids")) : entity.getId()));
			}
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
	public JsonResult modify_psw(User entity) {
		if (entity.getPassword_old() != null) {
			entity.entryptPassword_old();
			if (!entity.getPassword_hid().equals(entity.getPassword_old())) {
				return new JsonResult(false, "原密码输入不正确！");
			}
		}
		entity.entryptPassword();
		int result = userDao.updateEntity(entity);

		if (result > 0) {
			//添加操作日志
			systemLogService.create(EnumLogModule.组织架构.getValue(), "修改密码", "修改密码用户：" + entity.getId());
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
	public JsonResult modify(User entity, String role_ids) {
		if (entity.getReal_name() != null) {
			entity.setPname(SpellUtil.getFullSpell(entity.getReal_name()));
			entity.setFname(SpellUtil.getFirstOneSpell(entity.getReal_name()));
		}
		int result = userDao.updateEntity(entity);
		int user_id = entity.getId();
		// 用户角色设置
		UserRole u = new UserRole();
		u.setUser_id(user_id);
		userRoleDao.deleteEntity(u);
		if (role_ids != null) {
			for (String item : role_ids.split(",")) {
				u = new UserRole();
				u.setUser_id(user_id);
				u.setRole_id(Integer.valueOf(item));
				userRoleDao.insertEntity(u);
			}
		} 
		
		if (result > 0) {
			//添加操作日志
			systemLogService.create(EnumLogModule.组织架构.getValue(), "修改用户", "修改用户" + entity.getReal_name());
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
		User entity = new User();
		entity.setId(id);
		int result = userDao.deleteEntity(entity);
		if (result > 0) {
			//添加操作日志
			systemLogService.create(EnumLogModule.组织架构.getValue(), "删除用户", "删除用户ID：" + id);
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
		User entity = new User();
		entity.getMap().put("ids", ids.split(","));
		int result = userDao.deleteEntity(entity);
		if (result > 0) {
			//添加操作日志
			systemLogService.create(EnumLogModule.组织架构.getValue(), "批量删除用户", "批量删除用户ID：" + ids);
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
	public JsonResult remove(User entity) {
		int result = userDao.deleteEntity(entity);
		if (result > 0) {
			return new JsonResult(true);
		} else {
			return new JsonResult(false);
		}
	}

	// 验证名称重名
	public boolean validateName(Integer id, String name) {
		User t = new User();
		if (id == null) {
			id = 0;
		}
		t.getMap().put("id", id);
		t.getMap().put("name", name);
		t.getMap().put("name_valid", "true");
		int count = userDao.selectEntityCount(t);
		if (count > 0) {
			return false;
		} else {
			return true;
		}
	}

	public List<UserRole> findRoleIdByUser(String userId) {
		UserRole ur=new UserRole();
		ur.setUser_id(Integer.valueOf(userId));
		List<UserRole> list=userRoleDao.selectEntityList(ur, "ByUser");
		return list;
	}

	public List<Role> findRoleListByUser(String userId){
		List<Role> list=activitiDao.findRoleListByUser(userId);
		return list;
	}
	
}
