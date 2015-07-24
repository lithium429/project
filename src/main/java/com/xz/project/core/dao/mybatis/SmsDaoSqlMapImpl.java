package com.xz.project.core.dao.mybatis;

import org.springframework.stereotype.Repository;

import com.xz.base.dao.impl.BaseDaoSqlMapImpl;
import com.xz.project.core.dao.SmsDao;
import com.xz.project.core.domain.entity.Sms;

@Repository
public class SmsDaoSqlMapImpl extends BaseDaoSqlMapImpl<Sms> implements SmsDao {

}

