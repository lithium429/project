package com.xz.project.core.dao.mybatis;

import org.springframework.stereotype.Repository;

import com.xz.base.dao.impl.BaseDaoSqlMapImpl;
import com.xz.project.core.dao.LoginLogDao;
import com.xz.project.core.domain.entity.LoginLog;

@Repository
public class LoginLogDaoSqlMapImpl extends BaseDaoSqlMapImpl<LoginLog> implements LoginLogDao {

}

