/**   
 * @Title: Base64.java 
 * @Package: com.xz.base.utils 
 * @Description: 
 * @author: davidwan
 * @date: 2014-12-5 下午3:48:10 
 * @version: V1.0   
 */
package com.xz.base.utils;

import java.io.UnsupportedEncodingException;
import sun.misc.*;

public class Base64Util {
	// 加密
	public static String encode(String str) {
		byte[] b = null;
		String s = null;
		try {
			b = str.getBytes("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (b != null) {
			s = new BASE64Encoder().encode(b);
		}
		return s;
	}

	// 解密
	public static String decode(String s) {
		byte[] b = null;
		String result = null;
		if (s != null) {
			BASE64Decoder decoder = new BASE64Decoder();
			try {
				b = decoder.decodeBuffer(s);
				result = new String(b, "utf-8");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}
