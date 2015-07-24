package com.xz.base.data.source;

public final class DataSourceContextHolder {

	public static String DB_SHARDING_DEFAULT = "db_sharding_default";
	public static String DB_SHARDING_SMS = "db_sharding_sms";

	/**
	 * local thread variable, thread safe
	 */
	private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

	public static void setDataSourceType(String dataSourceType) {
		contextHolder.set(dataSourceType);
	}

	public static String getDataSourceType() {
		return (String) contextHolder.get();
	}

	public static void clear() {
		contextHolder.remove();
	}
}
