package com.xz.base.data.source;

import java.sql.SQLException;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException { 
		return null;
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException { 
		return false;
	}
	
	@Override
	protected Object determineCurrentLookupKey() {
		return DataSourceContextHolder.getDataSourceType();
	}

}
