package com.xz.project.core.dao.mybatis;

import org.springframework.stereotype.Repository;

import com.xz.base.dao.impl.BaseDaoSqlMapImpl;
import com.xz.project.core.dao.SystemLogDao;
import com.xz.project.core.domain.entity.SystemLog;

@Repository
public class SystemLogDaoSqlMapImpl extends BaseDaoSqlMapImpl<SystemLog> implements SystemLogDao {

}

