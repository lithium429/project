package com.xz.project.core.dao;


import java.util.List;
import java.util.Map;

import com.xz.project.core.domain.entity.Role;
import org.nutz.dao.entity.Record;

import com.xz.activiti.modules.pojo.Wf_hi_activity;
import com.xz.activiti.modules.pojo.Wf_model_category;
import com.xz.base.dao.BaseDao;

public interface ActivitiDao extends BaseDao<Map> {

	public List<Map<String, String>> findPassedNode(String processInstanceId);

	public void modifyActinst(Map<String, Object> sqlMap);

	public List<Wf_hi_activity> findAllActivitywh();

	public void removeActiwh(int id);

	public Object findRuTaskByCon(Map map);

	public void removeRuTaskById(String taskId);

	public void removeHiActinst(Map map);

	public String findHiTaInByCon(Map map);

	public String findHiActinstByTaskId(String taskId);

	public String findHainstById(String historyActivityId);

	public List<Map> findHainstPartById(
			String id);

	public List<Wf_model_category> findCategorywf();

	public String findCateMaxLaById(String id);

	public void createCate(Wf_model_category category);

	public void modifyCateById(Map map);

	public void removeCateById(String categoryId);

	public void modifyArModelById(String categoryId);

	public void createActiwf(Wf_hi_activity hiActivity);

	public List<Map> findHiActinstByCon(String taskId);

	public void modifyHiAcinst(Map terMap);

	public List<Map> findHiActinstByProId(String processInstanceId);

	public void removeArexecByCon(Map terMap);

	public List<Role> findRoleListByUser(String id);

	public Wf_model_category findCateById(String id);

	public int findArtaskCount(Map map);

	public void modifyAhactinst(Map map);

	public String findCateIdFuzzy(String p_categoryId);

	public String findCateMaxId();
}
