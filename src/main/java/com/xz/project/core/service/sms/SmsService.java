package com.xz.project.core.service.sms;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.xz.base.model.JsonResult;
import com.xz.base.model.PageInfo;  
import com.xz.sms.model.SendResult;
import com.xz.sms.service.SmsInterface;
import com.xz.project.core.dao.SmsDao;
import com.xz.project.core.domain.entity.Sms;   

@Service("smsService")
public class SmsService{

	@Resource
	private SmsDao smsDao;

	@Resource
	private SmsSendService smsSendService;
	
	private SmsInterface smsInterface;  
	
	public void setSmsInterface(SmsInterface smsInterface) {
		this.smsInterface = smsInterface;
	}

	/**
	 * @Description 根据Id获取实体
	 * @param id
	 * @return Sms    
	 * @author davidwan 
	 */
	public Sms findById(Integer id) {
		Sms entity = new Sms();
		entity.setId(id);
		return smsDao.selectEntity(entity);
	}

	/**
	 * @Description 根据条件获取实体
	 * @param entity
	 * @return Sms    
	 * @author davidwan 
	 */
	public Sms find(Sms entity) {
		return smsDao.selectEntity(entity);
	}
	
	/**
	 * @Description 根据条件获取列表 
	 * @param entity
	 * @return List<Sms>    
	 * @author davidwan 
	 */
	public List<Sms> queryList(Sms entity){
		return smsDao.selectEntityList(entity);
	}
	
	/**
	 * @Description 根据条件获取分页列表 
	 * @param entity
	 * @param pageIndex
	 * @param pageSize
	 * @return PageInfo<Sms>    
	 * @author davidwan 
	 */
	public PageInfo<Sms> queryPageList(Sms entity, int pageIndex, int pageSize){
		return smsDao.selectEntityPageList(entity, pageIndex, pageSize);
	}

	/**
	 * @Description 添加
	 * @param entity
	 * @return JsonResult    
	 * @author davidwan 
	 */
	public JsonResult create(Sms entity) {
		// 若要获取id，请使用entity.getId();
		int result = smsDao.insertEntity(entity);
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
	public JsonResult modify(Sms entity) {
		int result = smsDao.updateEntity(entity);
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
		Sms entity = new Sms();
		entity.setId(id);
		int result = smsDao.deleteEntity(entity);
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
		Sms entity = new Sms();
		entity.getMap().put("ids", ids.split(","));
		int result = smsDao.deleteEntity(entity);
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
	public JsonResult remove(Sms entity) { 
		int result = smsDao.deleteEntity(entity);
		if (result > 0) {
			return new JsonResult(true);
		} else {
			return new JsonResult(false);
		}
	}
	
	public SendResult modifyForSend(String mobile, String msg) {
		 return smsInterface.send(mobile, msg);
	}
	
	public JsonResult modifySend(int id){
		return new JsonResult(true);
	}

}
