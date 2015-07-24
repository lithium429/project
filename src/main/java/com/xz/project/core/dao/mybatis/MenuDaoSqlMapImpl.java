package com.xz.project.core.dao.mybatis;

import org.springframework.stereotype.Repository;

import com.xz.base.dao.impl.BaseDaoSqlMapImpl;
import com.xz.project.core.dao.MenuDao;
import com.xz.project.core.domain.entity.Menu;

@Repository
public class MenuDaoSqlMapImpl extends BaseDaoSqlMapImpl<Menu> implements MenuDao {

}

