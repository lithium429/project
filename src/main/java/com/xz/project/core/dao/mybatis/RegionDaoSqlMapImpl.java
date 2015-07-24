package com.xz.project.core.dao.mybatis;

import org.springframework.stereotype.Repository;

import com.xz.base.dao.impl.BaseDaoSqlMapImpl;
import com.xz.project.core.dao.RegionDao;
import com.xz.project.core.domain.entity.Region;

@Repository
public class RegionDaoSqlMapImpl extends BaseDaoSqlMapImpl<Region> implements RegionDao {

}

