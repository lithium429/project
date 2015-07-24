package com.xz.project.core.dao.mybatis;

import org.springframework.stereotype.Repository;

import com.xz.base.dao.impl.BaseDaoSqlMapImpl;
import com.xz.project.core.dao.ActionDao;
import com.xz.project.core.domain.entity.Action;

@Repository
public class ActionDaoSqlMapImpl extends BaseDaoSqlMapImpl<Action> implements ActionDao {

}

