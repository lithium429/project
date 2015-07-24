/**  
 * @Title: BaseDaoSqlMapImpl.java
 * @Package com.xz.base.dao.impl
 * @Description: Dao基类接口实现
 * @author 万书德
 * @date 2013-11-20
 * @version V1.0  
 */
package com.xz.base.dao.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession; 
import org.springframework.dao.DataAccessException;

import com.xz.base.dao.BaseDao;
import com.xz.base.model.PageInfo;
import com.xz.base.utils.LogHelper;

/**
 * @Description: Dao基类接口实现
 * 
 */
@SuppressWarnings("unchecked")
public class BaseDaoSqlMapImpl<T> implements BaseDao<T> {

	private static final String ST_NAMESPACE = "entity.";
	private static final String ST_BREAK = ".";
	private static final String ST_INSERT_ENTITY = "insert";
	private static final String ST_DELETE_ENTITY = "delete";
	private static final String ST_UPDATE_ENTITY = "update";
	private static final String ST_SELECT_ENTITY = "select";
	private static final String ST_SELECT_LIST = "List";
	private static final String ST_SELECT_LIST_Sql = "ListSql";
	private static final String ST_SELECT_PAGE_LIST = "PageList";
	private static final String ST_SELECT_COUNT = "Count";
	private static final String ST_SELECT_SORT = "Sort";

	private String className;
	
	@Resource
	protected SqlSession sqlSessionDefault;

	public BaseDaoSqlMapImpl() throws DataAccessException {
		Type entity = getClass().getGenericSuperclass();
		if (ParameterizedType.class.isAssignableFrom(entity.getClass())) {
			this.className = ((Class<?>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]).getSimpleName();
		}
	}

	/* 
	 * 插入单个实体
	 */
	public int insertEntity(T entity) throws DataAccessException {
		return this.sqlSessionDefault.insert(ST_NAMESPACE + className + ST_BREAK + ST_INSERT_ENTITY + className, entity);
	}
	
	/* 
	 *  更新单个或者多个实体 
	 */
	public int updateEntity(T entity) throws DataAccessException {
		return this.sqlSessionDefault.update(ST_NAMESPACE + className + ST_BREAK + ST_UPDATE_ENTITY + className, entity);
	}
	
	/* 
	 * 删除单个或者多个实体
	 */
	public int deleteEntity(T entity) throws DataAccessException {
		return this.sqlSessionDefault.delete(ST_NAMESPACE + className + ST_BREAK + ST_DELETE_ENTITY + className, entity);
	}
 
	/* 
	 * 获取单个实体
	 */
	public T selectEntity(T entity) throws DataAccessException {
		return (T) this.sqlSessionDefault.selectOne(ST_NAMESPACE + className + ST_BREAK + ST_SELECT_ENTITY + className, entity);
	}
 
	/* 
	 * 获取单个实体【指定SqlMapId】
	 */
	public T selectEntity(T entity, String mapId) throws DataAccessException {
		return (T) this.sqlSessionDefault.selectOne(ST_NAMESPACE + className + ST_BREAK + ST_SELECT_ENTITY + className + mapId, entity);
	}
	
	/* 
	 * 获取总数
	 */ 
	public Integer selectEntityCount(T entity) throws DataAccessException { 
		return (Integer) this.sqlSessionDefault.selectOne(ST_NAMESPACE + className + ST_BREAK + ST_SELECT_ENTITY + className + ST_SELECT_COUNT, entity);
	}

	/* 
	 * 获取总数【指定SqlMapId】
	 */ 
	public Integer selectEntityCount(T entity, String mapId) throws DataAccessException {
		return (Integer) this.sqlSessionDefault.selectOne(ST_NAMESPACE + className + ST_BREAK + ST_SELECT_ENTITY + className + ST_SELECT_COUNT + mapId, entity);
	}
	
	/*
	 * 获取排序值
	 */ 
	public Integer selectEntitySort(T entity) throws DataAccessException { 
		return (Integer) this.sqlSessionDefault.selectOne(ST_NAMESPACE + className + ST_BREAK + ST_SELECT_ENTITY + className + ST_SELECT_SORT, entity);
	}
	
	/* 
	 * 获取排序值【指定SqlMapId】
	 */
	public Integer selectEntitySort(T entity, String mapId) throws DataAccessException { 
		return (Integer) this.sqlSessionDefault.selectOne(ST_NAMESPACE + className + ST_BREAK + ST_SELECT_ENTITY + className + ST_SELECT_SORT + mapId, entity);
	}

	/* 
	 * 获取列表
	 */
	public List<T> selectEntityList(T entity) throws DataAccessException {
		return this.sqlSessionDefault.selectList(ST_NAMESPACE + className + ST_BREAK + ST_SELECT_ENTITY + className + ST_SELECT_LIST, entity);
	}

	/* 
	 * 获取列表【指定SqlMapId】
	 */
	public List<T> selectEntityList(T entity, String mapId) throws DataAccessException {
		return this.sqlSessionDefault.selectList(ST_NAMESPACE + className + ST_BREAK + ST_SELECT_ENTITY + className + ST_SELECT_LIST + mapId, entity);
	} 

	/* 
	 * 获取分页列表
	 */
	public PageInfo<T> selectEntityPageList(T entity, int pageIndex, int pageSize) throws DataAccessException {
		int recordCount = (Integer)this.sqlSessionDefault.selectOne(ST_NAMESPACE + className + ST_BREAK + ST_SELECT_ENTITY + className + ST_SELECT_COUNT, entity);
		int maxPage;
		if (recordCount == 0) {
			maxPage = 0;
		} else if ((recordCount % pageSize) == 0) {
			maxPage = recordCount / pageSize;
		} else {
			maxPage = recordCount / pageSize + 1;
		}
		if (pageIndex > maxPage) {
			pageIndex = maxPage;
		}
		int startIndex = 0;
		if (pageIndex != 0) {
			startIndex = (pageIndex - 1) * pageSize;
		}
		try {
			setFiledValue(entity, pageSize, startIndex);
		} catch (Exception ex) {
			 LogHelper.getLogger().error("反射设置属性值时出错", ex);
		}

		List<T> list = this.sqlSessionDefault.selectList(ST_NAMESPACE + className + ST_BREAK + ST_SELECT_ENTITY + className + ST_SELECT_PAGE_LIST, entity);
		PageInfo<T> pageInfo = new PageInfo<T>(list, pageSize, recordCount, pageIndex);
		return pageInfo;
	}

	/* 
	 * 获取分页列表【指定SqlMapId】
	 */
	public PageInfo<T> selectEntityPageList(T entity, String mapId, int pageIndex, int pageSize) throws DataAccessException {

		int recordCount = (Integer)this.sqlSessionDefault.selectOne(ST_NAMESPACE + className + ST_BREAK + ST_SELECT_ENTITY + className + ST_SELECT_COUNT + mapId, entity);
		int maxPage;
		if (recordCount == 0) {
			maxPage = 0;
		} else if ((recordCount % pageSize) == 0) {
			maxPage = recordCount / pageSize;
		} else {
			maxPage = recordCount / pageSize + 1;
		}
		if (pageIndex > maxPage) {
			pageIndex = maxPage;
		}
		int startIndex = 0;
		if (pageIndex != 0) {
			startIndex = (pageIndex - 1) * pageSize;
		}
		try {
			setFiledValue(entity, pageSize, startIndex);
		} catch (Exception ex) {
			 LogHelper.getLogger().error("反射设置属性值时出错", ex);
		}

		List<T> list = this.sqlSessionDefault.selectList(ST_NAMESPACE + className + ST_BREAK + ST_SELECT_ENTITY + className + ST_SELECT_PAGE_LIST + mapId, entity);
		PageInfo<T> pageInfo = new PageInfo<T>(list, pageSize, recordCount, pageIndex);
		return pageInfo;
	}
 
	private void setFiledValue(T entity, int pageSize, int startIndex) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		Method method = entity.getClass().getMethod("getMap");
		((Map<String, Object>) method.invoke(entity)).put("startIndex", startIndex);
		((Map<String, Object>) method.invoke(entity)).put("pageSize", pageSize);
	}  
	
	/* 
	 * 根据sql获取列表
	 */ 
	public List<T> selectEntityListSql(T entity) throws DataAccessException { 
		List<T> list = this.sqlSessionDefault.selectList(ST_NAMESPACE + className + ST_BREAK + ST_SELECT_ENTITY + className + ST_SELECT_LIST_Sql, entity);
		return list;
	}

	/*
	 * 根据sql获取列表【指定SqlMapId】
	 */ 
	public List<T> selectEntityListSql(T entity, String mapId) throws DataAccessException { 
		List<T> list = this.sqlSessionDefault.selectList(ST_NAMESPACE + className + ST_BREAK + ST_SELECT_ENTITY + className + ST_SELECT_LIST_Sql + mapId, entity);
		return list;
	} 
}