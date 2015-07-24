package com.xz.project.core.service.bpm;

import com.xz.activiti.modules.pojo.Wf_hi_activity;
import com.xz.activiti.modules.pojo.Wf_model_category;
import com.xz.project.core.dao.ActivitiDao;
import com.xz.project.core.domain.entity.Role;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class ActivitiService {
    @Resource
    ActivitiDao dao;

    public List<Map<String, String>> findPassedNode(String processInstanceId){
        return dao.findPassedNode(processInstanceId);
    }

    public void modifyActinst(Map<String, Object> sqlMap){
        dao.modifyActinst(sqlMap);
    }

    public List<Wf_hi_activity> findAllActivitywh(){
        return dao.findAllActivitywh();
    }

    public void removeActiwh(int id){
        dao.removeActiwh(id);
    }

    public Object findRuTaskByCon(Map map){
        return dao.findRuTaskByCon(map);
    }

    public void removeRuTaskById(String taskId){
        dao.removeRuTaskById(taskId);
    }

    public void removeHiActinst(Map map){
        dao.removeHiActinst(map);
    }

    public String findHiTaInByCon(Map map){
        return dao.findHiTaInByCon(map);
    }

    public String findHiActinstByTaskId(String taskId){
        return dao.findHiActinstByTaskId(taskId);
    }

    public String findHainstById(String historyActivityId){
        return dao.findHainstById(historyActivityId);
    }

    public List<Map> findHainstPartById(
            String id){
        return dao.findHainstPartById(id);
    }

    public List<Wf_model_category> findCategorywf(){
        return dao.findCategorywf();
    }

    public String findCateMaxLaById(String id){
        return dao.findCateMaxLaById(id);
    }

    public void createCate(Wf_model_category category){
        dao.createCate(category);
    }

    public void modifyCateById(Map map){
        dao.modifyCateById(map);
    }

    public void removeCateById(String categoryId){
        dao.removeCateById(categoryId);
    }

    public void modifyArModelById(String categoryId){
        dao.modifyArModelById(categoryId);
    }

    public void createActiwf(Wf_hi_activity hiActivity){
        dao.createActiwf(hiActivity);
    }

    public List<Map> findHiActinstByCon(String taskId){
        return dao.findHiActinstByCon(taskId);
    }

    public void modifyHiAcinst(Map terMap){
        dao.modifyHiAcinst(terMap);
    }

    public List<Map> findHiActinstByProId(String processInstanceId){
        return dao.findHiActinstByProId(processInstanceId);
    }

    public void removeArexecByCon(Map terMap){
        dao.removeArexecByCon(terMap);
    }

    public List<Role> findRoleListByUser(String id){
      return dao.findRoleListByUser(id);
    }

    public Wf_model_category findCateById(String id){
        return dao.findCateById(id);
    }

    public int findArtaskCount(Map map){
        return dao.findArtaskCount(map);
    }

    public void modifyAhactinst(Map map){
        dao.modifyAhactinst(map);
    }

    public String findCateIdFuzzy(String p_categoryId){
        return dao.findCateIdFuzzy(p_categoryId);
    }

    public String findCateMaxId(){
        return dao.findCateMaxId();
    }
}
