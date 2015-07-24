package com.xz.project.core.service.sms;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.xz.base.model.JsonResult;
import com.xz.base.model.PageInfo;
import com.xz.project.core.dao.SmsTemplateDao;
import com.xz.project.core.domain.entity.SmsTemplate;

@Service
public class SmsTemplateService {

	@Resource
	private SmsTemplateDao smsTempletDao;

	/**
	 * @Description 根据Id获取实体
	 * @param id
	 * @return SmsTemplet
	 * @author davidwan
	 */
	public SmsTemplate findById(Integer id) {
		SmsTemplate entity = new SmsTemplate();
		entity.setId(id);
		return smsTempletDao.selectEntity(entity);
	}

	/**
	 * @Description 根据条件获取实体
	 * @param entity
	 * @return SmsTemplet
	 * @author davidwan
	 */
	public SmsTemplate find(SmsTemplate entity) {
		return smsTempletDao.selectEntity(entity);
	}

	/**
	 * @Description 根据条件获取列表
	 * @param entity
	 * @return List<SmsTemplet>
	 * @author davidwan
	 */
	public List<SmsTemplate> queryList(SmsTemplate entity) {
		return smsTempletDao.selectEntityList(entity);
	}

	/**
	 * @Description 根据条件获取分页列表
	 * @param entity
	 * @param pageIndex
	 * @param pageSize
	 * @return PageInfo<SmsTemplet>
	 * @author davidwan
	 */
	public PageInfo<SmsTemplate> queryPageList(SmsTemplate entity, int pageIndex, int pageSize) {
		return smsTempletDao.selectEntityPageList(entity, pageIndex, pageSize);
	}

	/**
	 * @Description 添加
	 * @param entity
	 * @return JsonResult
	 * @author davidwan
	 */
	public JsonResult create(SmsTemplate entity) {
		entity.setCreate_time(new Date());
		if (entity.getIs_default()) {
			SmsTemplate a = new SmsTemplate();
			a.setIs_default(false);
			a.getMap().put("sel_is_default", true);
			smsTempletDao.updateEntity(a);
		}
		// 若要获取id，请使用entity.getId();
		int result = smsTempletDao.insertEntity(entity);
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
	public JsonResult modify(SmsTemplate entity) {
		int result = smsTempletDao.updateEntity(entity);
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
	public JsonResult modifyData(SmsTemplate entity) {
		if (entity.getIs_default()) {
			SmsTemplate a = new SmsTemplate();
			a.setIs_default(false);
			a.getMap().put("sel_is_default", true);
			smsTempletDao.updateEntity(a);
		}
		int result = smsTempletDao.updateEntity(entity);
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
		SmsTemplate entity = new SmsTemplate();
		entity.setId(id);
		int result = smsTempletDao.deleteEntity(entity);
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
		SmsTemplate entity = new SmsTemplate();
		entity.getMap().put("ids", ids.split(","));
		int result = smsTempletDao.deleteEntity(entity);
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
	public JsonResult remove(SmsTemplate entity) {
		int result = smsTempletDao.deleteEntity(entity);
		if (result > 0) {
			return new JsonResult(true);
		} else {
			return new JsonResult(false);
		}
	}

	// 验证名称重名
	public boolean validateName(Integer id, String name) {
		SmsTemplate t = new SmsTemplate();
		if (id == null) {
			id = 0;
		}
		t.getMap().put("id", id);
		t.getMap().put("name_valid", name);
		int count = smsTempletDao.selectEntityCount(t);
		if (count > 0) {
			return false;
		} else {
			return true;
		}
	}

}
