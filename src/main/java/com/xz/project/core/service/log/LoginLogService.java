package com.xz.project.core.service.log;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.xz.base.model.JsonResult;
import com.xz.base.model.PageInfo;
import com.xz.project.core.dao.LoginLogDao;
import com.xz.project.core.domain.entity.LoginLog;

@Service
public class LoginLogService {

	@Resource
	private LoginLogDao loginLogDao;

	/**
	 * @Description 根据Id获取实体
	 * @param id
	 * @return LoginLog
	 * @author davidwan
	 */
	public LoginLog findById(Integer id) {
		LoginLog entity = new LoginLog();
		entity.setId(id);
		return loginLogDao.selectEntity(entity);
	}

	/**
	 * @Description 根据条件获取实体
	 * @param entity
	 * @return LoginLog
	 * @author davidwan
	 */
	public LoginLog find(LoginLog entity) {
		return loginLogDao.selectEntity(entity);
	}

	/**
	 * @Description 根据条件获取列表
	 * @param entity
	 * @return List<LoginLog>
	 * @author davidwan
	 */
	public List<LoginLog> queryList(LoginLog entity) {
		return loginLogDao.selectEntityList(entity);
	}

	/**
	 * @Description 根据条件获取分页列表
	 * @param entity
	 * @param pageIndex
	 * @param pageSize
	 * @return PageInfo<LoginLog>
	 * @author davidwan
	 */
	public PageInfo<LoginLog> queryPageList(LoginLog entity, int pageIndex, int pageSize) {
		return loginLogDao.selectEntityPageList(entity, pageIndex, pageSize);
	}

	/**
	 * @Description 添加
	 * @param entity
	 * @return JsonResult
	 * @author davidwan
	 */
	public JsonResult create(LoginLog entity) {
		// 若要获取id，请使用entity.getId();
		int result = loginLogDao.insertEntity(entity);
		if (result > 0) {
			return new JsonResult(true);
		} else {
			return new JsonResult(false);
		}
	}

	/**
	 * @Description 添加
	 * @param entity
	 * @return JsonResult
	 * @author davidwan
	 */
	public JsonResult create(String ip, int user_id, int type) {
		LoginLog entity = new LoginLog();
		entity.setCreate_time(new Date());
		entity.setIp(ip);
		entity.setUser_id(user_id);
		entity.setType(type);
		// 若要获取id，请使用entity.getId();
		int result = loginLogDao.insertEntity(entity);
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
	public JsonResult modify(LoginLog entity) {
		int result = loginLogDao.updateEntity(entity);
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
		LoginLog entity = new LoginLog();
		entity.setId(id);
		int result = loginLogDao.deleteEntity(entity);
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
		LoginLog entity = new LoginLog();
		entity.getMap().put("ids", ids.split(","));
		int result = loginLogDao.deleteEntity(entity);
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
	public JsonResult remove(LoginLog entity) {
		int result = loginLogDao.deleteEntity(entity);
		if (result > 0) {
			return new JsonResult(true);
		} else {
			return new JsonResult(false);
		}
	}

}
