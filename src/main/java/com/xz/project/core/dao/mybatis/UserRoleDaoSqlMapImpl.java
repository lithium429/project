package com.xz.project.core.dao.mybatis;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.xz.base.dao.impl.BaseDaoSqlMapImpl;
import com.xz.project.core.dao.UserRoleDao;
import com.xz.project.core.domain.entity.UserRole;

@Repository
public class UserRoleDaoSqlMapImpl extends BaseDaoSqlMapImpl<UserRole> implements UserRoleDao {

}

