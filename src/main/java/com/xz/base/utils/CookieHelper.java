/**  
 * @Title: CookieHelper.java
 * @Package com.rz.pscc.web.util
 * @Description: Cookie辅助类
 * @author 万书德
 * @date 2013-6-17
 * @version V1.0  
 */
package com.xz.base.utils;

import javax.servlet.http.Cookie;

/**
 * @Description:Cookie辅助类
 * 
 */
public class CookieHelper {

	/**
	 * @Title: getCookie
	 * @Description: 根据指定Key从Cookie集合中获取Cookie对象
	 * @param cookies
	 * @param key
	 * @return Cookie
	 */
	public static Cookie getCookie(Cookie[] cookies, String key) {
		if (cookies == null || cookies.length == 0) {
			return null;
		}
		Cookie cookie = null;
		for (Cookie item : cookies) {
			if (item.getName().equals(key)) {
				cookie = item;
				break;
			}
		}
		return cookie;
	}

	/**
	 * @Title: getCookieValue
	 * @Description: 根据指定Key从Cookie集合中获取Cookie对象对应的值
	 * @param cookies
	 * @param key
	 * @return String
	 */
	public static String getCookieValue(Cookie[] cookies, String key) {
		Cookie cookie = getCookie(cookies, key);
		if (cookie != null) {
			return cookie.getValue();
		} else {
			return "";
		}
	}

}
