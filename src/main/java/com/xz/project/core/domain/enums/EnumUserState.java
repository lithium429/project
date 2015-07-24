/**   
* @Title: EnumUserState.java 
* @Package: com.xz.oa.core.domain.enums 
* @Description: 用户状态枚举
* @author: davidwan
* @date: 2014-8-14 上午9:10:48 
* @version: V1.0   
*/
package com.xz.project.core.domain.enums;

import java.util.HashMap;
import java.util.Map;

public enum EnumUserState {
	正常(1), 禁用(2);

	private final int value;

	private static final Map<Integer, EnumUserState> map = new HashMap<Integer, EnumUserState>();

	static {
		for (EnumUserState obj : EnumUserState.values()) {
			map.put(obj.value, obj);
		}
	}

	private EnumUserState(int value) {
		this.value = value;
	}

	public static EnumUserState fromInt(int i) {
		EnumUserState obj = map.get(Integer.valueOf(i));
		if (obj == null)
			return EnumUserState.正常;
		return obj;
	}

	public int getValue() {
		return value;
	}

	public String toString() {
		return String.valueOf(this.value);
	}
}
