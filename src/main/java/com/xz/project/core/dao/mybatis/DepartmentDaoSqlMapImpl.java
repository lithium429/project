package com.xz.project.core.dao.mybatis;

import org.springframework.stereotype.Repository;

import com.xz.base.dao.impl.BaseDaoSqlMapImpl;
import com.xz.project.core.dao.DepartmentDao;
import com.xz.project.core.domain.entity.Department;

@Repository
public class DepartmentDaoSqlMapImpl extends BaseDaoSqlMapImpl<Department> implements DepartmentDao {

}

