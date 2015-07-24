/**  
 * @Title: EntityDao.java
 * @Package com.rz.common.dao
 * @Description: Dao通用接口
 * @author 万书德
 * @date 2013-11-20
 * @version V1.0  
 */
package com.xz.base.dao;

import java.util.List;
import org.springframework.dao.DataAccessException;

import com.xz.base.model.PageInfo;

/**
 * @Description: Dao通用接口
 * 
 */
public interface BaseDao<T> {

	/**
	 * @Description 插入单个实体
	 * @param entity
	 * @return int
	 * @throws DataAccessException    
	 * @author davidwan 
	 */
	public abstract int insertEntity(T entity) throws DataAccessException;

	/**
	 * @Description 更新单个或者多个实体 
	 * @param entity
	 * @return int
	 * @throws DataAccessException     
	 * @author davidwan 
	 */
	public abstract int updateEntity(T entity) throws DataAccessException;

	/**
	 * @Description 删除单个或者多个实体 
	 * @param entity
	 * @return int
	 * @throws DataAccessException   
	 * @author davidwan 
	 */
	public abstract int deleteEntity(T entity) throws DataAccessException;

	/**
	 * @Description 获取单个实体
	 * @param entity
	 * @return T
	 * @throws DataAccessException     
	 * @author davidwan 
	 */
	public abstract T selectEntity(T entity) throws DataAccessException;

	/**
	 * @Description 获取单个实体【指定SqlMapId】
	 * @param entity
	 * @param mapId
	 * @return T
	 * @throws DataAccessException    
	 * @author davidwan 
	 */
	public abstract T selectEntity(T entity, String mapId) throws DataAccessException;

	/**
	 * @Description 获取总数
	 * @param entity
	 * @return Integer
	 * @throws DataAccessException 
	 * @author davidwan 
	 */
	public abstract Integer selectEntityCount(T entity) throws DataAccessException;
	
	/**
	 * @Description 获取总数【指定SqlMapId】
	 * @param entity
	 * @param mapId
	 * @return Integer
	 * @throws DataAccessException 
	 * @author davidwan 
	 */
	public abstract Integer selectEntityCount(T entity, String mapId) throws DataAccessException;


	/**
	 * @Description 获取排序值
	 * @param entity
	 * @return Integer
	 * @throws DataAccessException 
	 * @author davidwan 
	 */
	public abstract Integer selectEntitySort(T entity) throws DataAccessException;
	
	/**
	 * @Description 获取排序值【指定SqlMapId】
	 * @param entity
	 * @param mapId
	 * @return Integer
	 * @throws DataAccessException 
	 * @author davidwan 
	 */
	public abstract Integer selectEntitySort(T entity, String mapId) throws DataAccessException;

	/**
	 * @Description 获取列表
	 * @param entity
	 * @return List<T>    
	 * @throws DataAccessException 
	 * @author davidwan 
	 */
	public abstract List<T> selectEntityList(T entity) throws DataAccessException;

	/**
	 * @Description 获取列表【指定SqlMapId】
	 * @param entity
	 * @param mapId
	 * @return List<T>    
	 * @throws DataAccessException  
	 * @author davidwan 
	 */
	public abstract List<T> selectEntityList(T entity, String mapId) throws DataAccessException;

	/**
	 * @Description 获取分页列表
	 * @param entity
	 * @param pageIndex
	 * @param pageSize
	 * @return PageInfo<T>
	 * @throws DataAccessException  
	 * @author davidwan 
	 */
	public abstract PageInfo<T> selectEntityPageList(T entity, int pageIndex, int pageSize) throws DataAccessException;
	
	/**
	 * @Description 获取分页列表【指定SqlMapId】
	 * @param entity
	 * @param mapId
	 * @param pageIndex
	 * @param pageSize
	 * @return PageInfo<T>
	 * @throws DataAccessException  
	 * @author davidwan 
	 */
	public abstract PageInfo<T> selectEntityPageList(T entity, String mapId, int pageIndex, int pageSize) throws DataAccessException;

	/**
	 * @Description 根据sql获取列表
	 * @param entity
	 * @return List<T>  
	 * @throws DataAccessException  
	 * @author davidwan 
	 */
	public abstract List<T> selectEntityListSql(T entity) throws DataAccessException;
	
	/**
	 * @Description 根据sql获取列表【指定SqlMapId】
	 * @param entity
	 * @param mapId
	 * @return List<T>
	 * @throws DataAccessException  
	 * @author davidwan 
	 */
	public abstract List<T> selectEntityListSql(T entity, String mapId) throws DataAccessException;
}
