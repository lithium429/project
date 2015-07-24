/**   
 * @Title: EnumLogModule.java 
 * @Package: com.xz.oa.core.domain.enums 
 * @Description: 日志模块枚举
 * @author: davidwan
 * @date: 2014-7-23 下午6:23:15 
 * @version: V1.0   
 */
package com.xz.project.core.domain.enums;

import java.util.HashMap;
import java.util.Map;

public enum EnumLogModule {
	用户操作(0),
	角色权限(1), 
	组织架构(2);

	private final int value;

	private static final Map<Integer, EnumLogModule> map = new HashMap<Integer, EnumLogModule>();

	static {
		for (EnumLogModule obj : EnumLogModule.values()) {
			map.put(obj.value, obj);
		}
	}

	private EnumLogModule(int value) {
		this.value = value;
	}

	public static EnumLogModule fromInt(int i) {
		EnumLogModule obj = map.get(Integer.valueOf(i));
		if (obj == null)
			return EnumLogModule.角色权限;
		return obj;
	}
	
	public static Map<Integer, EnumLogModule> getMap() {
		return map;
	}

	public int getValue() {
		return value;
	}

	public String toString() {
		return String.valueOf(this.value);
	}
}
