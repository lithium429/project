/**  
 * @Title: FormatHelper.java
 * @Package com.rz.common.utils
 * @Description: 格式化辅助类
 * @author 万书德
 * @date 2014-3-4
 * @version V1.0  
 */
package com.xz.base.utils;

/**
 * @Description: 格式化辅助类
 * 
 */
public class FormatHelper {

	/**
	 * @Title: formatDecimal
	 * @Description: 格式化数值类型显示到小数点后面两位
	 * @param value
	 * @return String
	 */
	public static String formatDecimal(Double value) {
		if (value == null) {
			return "";
		}
		return String.format("%.2f", value);
	}

}
