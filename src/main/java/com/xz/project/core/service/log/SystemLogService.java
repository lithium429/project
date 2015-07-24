package com.xz.project.core.service.log;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import com.xz.base.model.JsonResult;
import com.xz.base.model.PageInfo;
import com.xz.project.core.dao.SystemLogDao;
import com.xz.project.core.domain.entity.SystemLog;
import com.xz.project.core.service.user.ShiroDbRealm.ShiroUser;

@Service
public class SystemLogService {

	@Resource
	private SystemLogDao systemLogDao;

	/**
	 * @Description 根据Id获取实体
	 * @param id
	 * @return SystemLog
	 * @author davidwan
	 */
	public SystemLog findById(Integer id) {
		SystemLog entity = new SystemLog();
		entity.setId(id);
		return systemLogDao.selectEntity(entity);
	}

	/**
	 * @Description 根据条件获取实体
	 * @param entity
	 * @return SystemLog
	 * @author davidwan
	 */
	public SystemLog find(SystemLog entity) {
		return systemLogDao.selectEntity(entity);
	}

	/**
	 * @Description 根据条件获取列表
	 * @param entity
	 * @return List<SystemLog>
	 * @author davidwan
	 */
	public List<SystemLog> queryList(SystemLog entity) {
		return systemLogDao.selectEntityList(entity);
	}

	/**
	 * @Description 根据条件获取分页列表
	 * @param entity
	 * @param pageIndex
	 * @param pageSize
	 * @return PageInfo<SystemLog>
	 * @author davidwan
	 */
	public PageInfo<SystemLog> queryPageList(SystemLog entity, int pageIndex, int pageSize) {
		return systemLogDao.selectEntityPageList(entity, pageIndex, pageSize);
	}

	/**
	 * @Description 添加
	 * @param entity
	 * @return JsonResult
	 * @author davidwan
	 */
	public void create(int moudule, String operate, String content) {
		Subject currentUser = SecurityUtils.getSubject();
		ShiroUser shiroUser = (ShiroUser) currentUser.getPrincipals().getPrimaryPrincipal();
		SystemLog sLog = new SystemLog();
		sLog.setCreate_time(new Date());
		sLog.setUser_id(shiroUser.getId());
		sLog.setModule(moudule);
		sLog.setOperate(operate);
		sLog.setContent(content);
		systemLogDao.insertEntity(sLog);

	}
	


	/**
	 * @Description 添加
	 * @param entity
	 * @return JsonResult    
	 * @author davidwan 
	 */
	public JsonResult create(SystemLog entity) {
		// 若要获取id，请使用entity.getId();
		int result = systemLogDao.insertEntity(entity);
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
	public JsonResult modify(SystemLog entity) {
		int result = systemLogDao.updateEntity(entity);
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
		SystemLog entity = new SystemLog();
		entity.setId(id);
		int result = systemLogDao.deleteEntity(entity);
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
		SystemLog entity = new SystemLog();
		entity.getMap().put("ids", ids.split(","));
		int result = systemLogDao.deleteEntity(entity);
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
	public JsonResult remove(SystemLog entity) {
		int result = systemLogDao.deleteEntity(entity);
		if (result > 0) {
			return new JsonResult(true);
		} else {
			return new JsonResult(false);
		}
	}

}
