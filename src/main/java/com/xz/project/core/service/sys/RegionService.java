package com.xz.project.core.service.sys;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.xz.base.model.JsonResult;
import com.xz.base.model.PageInfo;
import com.xz.project.core.dao.RegionDao;
import com.xz.project.core.domain.entity.Region;

@Service
public class RegionService {

	@Resource
	private RegionDao regionDao;

	/**
	 * @Description 根据Id获取实体
	 * @param id
	 * @return Region
	 * @author davidwan
	 */
	public Region findById(Integer id) {
		Region entity = new Region();
		entity.setId(id);
		return regionDao.selectEntity(entity);
	}

	/**
	 * @Description 根据条件获取实体
	 * @param entity
	 * @return Region
	 * @author davidwan
	 */
	public Region find(Region entity) {
		return regionDao.selectEntity(entity);
	}

	/**
	 * @Description 根据条件获取列表
	 * @param entity
	 * @return List<Region>
	 * @author davidwan
	 */
	public List<Region> queryList(Region entity) {
		return regionDao.selectEntityList(entity);
	}

	/**
	 * @Description 根据条件获取分页列表
	 * @param entity
	 * @param pageIndex
	 * @param pageSize
	 * @return PageInfo<Region>
	 * @author davidwan
	 */
	public PageInfo<Region> queryPageList(Region entity, int pageIndex, int pageSize) {
		return regionDao.selectEntityPageList(entity, pageIndex, pageSize);
	}

	/**
	 * @Description 添加
	 * @param entity
	 * @return JsonResult
	 * @author davidwan
	 */
	public JsonResult create(Region entity) {
		entity.setCreate_time(new Date());
		Region t = new Region();
		Integer sort = regionDao.selectEntitySort(t);
		entity.setSort((sort == null ? 0 : sort) + 1);
		// 若要获取id，请使用entity.getId();
		int result = regionDao.insertEntity(entity);
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
	public JsonResult modify(Region entity) {
		int result = regionDao.updateEntity(entity);
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
		Region entity = new Region();
		entity.setId(id);
		int result = regionDao.deleteEntity(entity);
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
		Region entity = new Region();
		entity.getMap().put("ids", ids.split(","));
		int result = regionDao.deleteEntity(entity);
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
	public JsonResult remove(Region entity) {
		int result = regionDao.deleteEntity(entity);
		if (result > 0) {
			return new JsonResult(true);
		} else {
			return new JsonResult(false);
		}
	}

	// 验证名称重名
	public boolean validateName(Integer id, String name, Integer parent_id) {
		Region t = new Region();
		if (id == null) {
			id = 0;
		}
		t.getMap().put("id", id);
		t.getMap().put("name_valid", name);
		if (parent_id == null) {
			t.getMap().put("null_parent_id", true);
		} else {
			t.getMap().put("valid_parent_id", parent_id);
		}
		int count = regionDao.selectEntityCount(t);
		if (count > 0) {
			return false;
		} else {
			return true;
		}
	}

}
