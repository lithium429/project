package com.xz.project.core.service.organization;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.xz.base.model.JsonResult;
import com.xz.base.model.PageInfo;
import com.xz.base.model.ZTreeNode;
import com.xz.base.utils.ConfigValue;
import com.xz.base.utils.WebUtil;
import com.xz.project.core.dao.CompanyDao;
import com.xz.project.core.domain.entity.Company;

@Service("companyService")
public class CompanyService {

	@Resource
	private CompanyDao companyDao;

	/**
	 * @Description 根据Id获取实体
	 * @param id
	 * @return Company
	 * @author davidwan
	 */
	public Company findById(Integer id) {
		Company entity = new Company();
		entity.setId(id);
		return companyDao.selectEntity(entity);
	}

	/**
	 * @Description 根据条件获取实体
	 * @param entity
	 * @return Company
	 * @author davidwan
	 */
	public Company find(Company entity) {
		return companyDao.selectEntity(entity);
	}

	/**
	 * @Description 根据条件获取列表
	 * @param entity
	 * @return List<Company>
	 * @author davidwan
	 */
	public List<Company> queryList(Company entity) {
		return companyDao.selectEntityList(entity);
	}

	/**
	 * @Description 根据条件获取分页列表
	 * @param entity
	 * @param pageIndex
	 * @param pageSize
	 * @return PageInfo<Company>
	 * @author davidwan
	 */
	public PageInfo<Company> queryPageList(Company entity, int pageIndex, int pageSize) {
		return companyDao.selectEntityPageList(entity, pageIndex, pageSize);
	}

	/**
	 * @Description 添加
	 * @param entity
	 * @return JsonResult
	 * @author davidwan
	 */
	public JsonResult create(Company entity) {
		// 若要获取id，请使用entity.getId();
		int result = companyDao.insertEntity(entity);
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
	public JsonResult modify(Company entity) {
		int result = companyDao.updateEntity(entity);
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
		Company entity = new Company();
		entity.setId(id);
		int result = companyDao.deleteEntity(entity);
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
		Company entity = new Company();
		entity.getMap().put("ids", ids.split(","));
		int result = companyDao.deleteEntity(entity);
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
	public JsonResult remove(Company entity) {
		int result = companyDao.deleteEntity(entity);
		if (result > 0) {
			return new JsonResult(true);
		} else {
			return new JsonResult(false);
		}
	}

	public String getTree() {
		// 获取部门树
		Company c = new Company();
		c = companyDao.selectEntity(c);
		String name = "";
		if (c != null) {
			name = c.getName();
		}
		List<ZTreeNode> tree = new ArrayList<ZTreeNode>();
		ZTreeNode z = new ZTreeNode();
		z.setId(null);
		z.setIsParent(true);
		z.setName(name);
		z.setShowDeleteBtn(false);
		z.setShowAddBtn(true);
		z.setShowEditBtn(false);
		tree.add(z);

		String jsonText = WebUtil.toJson(tree);
		return jsonText;

	}
	
	public String getTree_User() {
		// 获取部门树
		Company c = new Company();
		c = companyDao.selectEntity(c);
		String name = "";
		if (c != null) {
			name = c.getName();
		}
		List<ZTreeNode> tree = new ArrayList<ZTreeNode>();
		ZTreeNode z = new ZTreeNode();
		z.setId(null);
		z.setIsParent(true);
		z.setName(name);
		z.setShowDeleteBtn(false);
		z.setShowAddBtn(true);
		z.setShowEditBtn(false);
		z.setId(0);
		tree.add(z);

		String jsonText = WebUtil.toJson(tree);
		return jsonText;

	}

	/**
	 * @Description 获取单位名称 
	 * @return String     
	 */
	public String findCompanyName() {
		String name = null;
		Company entity = new Company();
		entity = companyDao.selectEntity(entity);
		if (entity != null) {
			name = entity.getName();
		} else {
			name = ConfigValue.readValue("systemName", "芜湖市交通运输局");
		}
		return name;
	}

}
