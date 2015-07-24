package com.xz.project.core.dao.mybatis;

import org.springframework.stereotype.Repository;

import com.xz.base.dao.impl.BaseDaoSqlMapImpl;
import com.xz.project.core.dao.RoleDao;
import com.xz.project.core.domain.entity.Role;

@Repository
public class RoleDaoSqlMapImpl extends BaseDaoSqlMapImpl<Role> implements RoleDao {

}

