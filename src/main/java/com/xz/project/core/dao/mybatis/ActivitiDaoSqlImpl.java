package com.xz.project.core.dao.mybatis;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xz.project.core.domain.entity.Role;
import org.springframework.stereotype.Repository;

import com.xz.activiti.modules.pojo.Wf_hi_activity;
import com.xz.activiti.modules.pojo.Wf_model_category;
import com.xz.base.dao.impl.BaseDaoSqlMapImpl;
import com.xz.project.core.dao.ActivitiDao;
@Repository
public class ActivitiDaoSqlImpl extends BaseDaoSqlMapImpl<Map> implements ActivitiDao{

	private final static String MAPPER_NAMESPACE="activiti.sql";
	
	@Override
	public List<Map<String, String>> findPassedNode(String processInstanceId) {
		Map map=new HashMap();
		map.put("pid", processInstanceId);
		return this.sqlSessionDefault.selectList(MAPPER_NAMESPACE+".findPassedNode", map);
	}

	@Override
	public void modifyActinst(Map<String, Object> sqlMap) {
		this.sqlSessionDefault.update(MAPPER_NAMESPACE+".modifyActinst", sqlMap);
	}

	@Override
	public List<Wf_hi_activity> findAllActivitywh() {
		return this.sqlSessionDefault.selectList(MAPPER_NAMESPACE+".findAllActivitywh");
	}

	@Override
	public void removeActiwh(int id) {
		this.sqlSessionDefault.delete(MAPPER_NAMESPACE+".removeActiwh", id);
		
	}

	@Override
	public Object findRuTaskByCon(Map map) {
		return this.sqlSessionDefault.selectOne(MAPPER_NAMESPACE+".findRuTaskByCon", map);
		
	}

	@Override
	public void removeRuTaskById(String taskId) {
		this.sqlSessionDefault.delete(MAPPER_NAMESPACE+".removeRuTaskById", taskId);
		
	}

	@Override
	public void removeHiActinst(Map map) {
		this.sqlSessionDefault.delete(MAPPER_NAMESPACE+".removeHiActinst", map);
		
	}

	@Override
	public String findHiTaInByCon(Map map) {
		return this.sqlSessionDefault.selectOne(MAPPER_NAMESPACE+".findHiTaInByCon",map);
	}

	@Override
	public String findHiActinstByTaskId(String taskId) {
		return this.sqlSessionDefault.selectOne(MAPPER_NAMESPACE+".findHiActinstByTaskId",taskId);
	}

	@Override
	public String findHainstById(String historyActivityId) {
		return this.sqlSessionDefault.selectOne(MAPPER_NAMESPACE+".findHainstById",historyActivityId);
	}

	@Override
	public List<Map> findHainstPartById(String id) {
		return this.sqlSessionDefault.selectList(MAPPER_NAMESPACE+".findHainstPartById", id);
	}

	@Override
	public List<Wf_model_category> findCategorywf() {
		return this.sqlSessionDefault.selectList(MAPPER_NAMESPACE+".findCategorywf");
	}

	@Override
	public String findCateMaxLaById(String id) {

		return this.sqlSessionDefault.selectOne(MAPPER_NAMESPACE+".findCateMaxLaById", id);
	}

	@Override
	public void createCate(Wf_model_category category) {
		this.sqlSessionDefault.insert(MAPPER_NAMESPACE+".createCate", category);
		
	}

	@Override
	public void modifyCateById(Map map) {
		  this.sqlSessionDefault.update(MAPPER_NAMESPACE+".modifyCateById", map);
		
	}

	@Override
	public void removeCateById(String id) {
		this.sqlSessionDefault.delete(MAPPER_NAMESPACE+".removeCateById", id);
	}

	@Override
	public void modifyArModelById(String id) {
		this.sqlSessionDefault.update(MAPPER_NAMESPACE+".modifyArModelById", id);
		
	}

	@Override
	public void createActiwf(Wf_hi_activity hiActivity) {
		this.sqlSessionDefault.insert(MAPPER_NAMESPACE+".createActiwf", hiActivity);
		
	}

	@Override
	public List<Map> findHiActinstByCon(String id) {
		return this.sqlSessionDefault.selectList(MAPPER_NAMESPACE+".findHiActinstByCon", id);
	}

	@Override
	public void modifyHiAcinst(Map terMap) {
		this.sqlSessionDefault.update(MAPPER_NAMESPACE+".modifyHiAcinst", terMap);
		
	}

	@Override
	public List<Map> findHiActinstByProId(String id) {
		return this.sqlSessionDefault.selectList(MAPPER_NAMESPACE+".findHiActinstByProID",id);
	}

	@Override
	public void removeArexecByCon(Map map) {
		this.sqlSessionDefault.delete(MAPPER_NAMESPACE+".removeArexecByCon", map);
		
	}

	@Override
	public List<Role> findRoleListByUser(String id) {
		return this.sqlSessionDefault.selectList(MAPPER_NAMESPACE+".findRoleListByUser",id);
	}

	@Override
	public Wf_model_category findCateById(String id) {
		return this.sqlSessionDefault.selectOne(MAPPER_NAMESPACE+".findCateById",id);
	}

	@Override
	public int findArtaskCount(Map map) {
		return this.sqlSessionDefault.selectOne(MAPPER_NAMESPACE+".findArtaskCount",map);
	}

	@Override
	public void modifyAhactinst(Map map) {
		this.sqlSessionDefault.update(MAPPER_NAMESPACE+".modifyAhactinst",map);
	}

	@Override
	public String findCateIdFuzzy(String id) {
		return this.sqlSessionDefault.selectOne(MAPPER_NAMESPACE+".findCateIdFuzzy",id);
	}

	@Override
	public String findCateMaxId() {
		return this.sqlSessionDefault.selectOne(MAPPER_NAMESPACE+".findCateMaxId");
	}


}
