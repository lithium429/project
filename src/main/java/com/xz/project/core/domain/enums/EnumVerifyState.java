/**   
* @Title: EnumVerifyState.java 
* @Package: com.xz.project.core.domain.enums 
* @Description: 审核状态枚举
* @author: davidwan
* @date: 2014-12-6 下午7:18:44 
* @version: V1.0   
*/
package com.xz.project.core.domain.enums;

import java.util.HashMap;
import java.util.Map;

public enum EnumVerifyState {
	待审核(1), 审核通过(2), 审核不通过(3);

	private final int value;

	private static final Map<Integer, EnumVerifyState> map = new HashMap<Integer, EnumVerifyState>();

	static {
		for (EnumVerifyState obj : EnumVerifyState.values()) {
			map.put(obj.value, obj);
		}
	}

	private EnumVerifyState(int value) {
		this.value = value;
	}

	public static EnumVerifyState fromInt(int i) {
		EnumVerifyState obj = map.get(Integer.valueOf(i));
		if (obj == null)
			return EnumVerifyState.待审核;
		return obj;
	}

	public int getValue() {
		return value;
	}

	public String toString() {
		return String.valueOf(this.value);
	}
}
