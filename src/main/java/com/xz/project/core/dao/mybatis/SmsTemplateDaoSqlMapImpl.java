package com.xz.project.core.dao.mybatis;

import org.springframework.stereotype.Repository;

import com.xz.base.dao.impl.BaseDaoSqlMapImpl;
import com.xz.project.core.dao.SmsTemplateDao;
import com.xz.project.core.domain.entity.SmsTemplate;

@Repository
public class SmsTemplateDaoSqlMapImpl extends BaseDaoSqlMapImpl<SmsTemplate> implements SmsTemplateDao {

}

