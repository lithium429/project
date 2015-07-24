package com.xz.project.core.dao.mybatis;

import org.springframework.stereotype.Repository;

import com.xz.base.dao.impl.BaseDaoSqlMapImpl;
import com.xz.project.core.dao.CompanyDao;
import com.xz.project.core.domain.entity.Company;

@Repository
public class CompanyDaoSqlMapImpl extends BaseDaoSqlMapImpl<Company> implements CompanyDao {

}

