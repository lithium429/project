package com.xz.base.utils;

/**  
 * @author WangMengzhong
 * @date 2013-12-4
 * @QQ 821501431
 * @mail 821501431@qq.com
 * @version V1.0  
 */
import java.io.UnsupportedEncodingException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Joiner;

public class StringUtil {

	/**
	 * 替换字符串
	 * 
	 * @param line
	 * @param oldString
	 * @param newString
	 * @return
	 */
	public static final String replace(String s, String sub, String with) {
		int c = 0;
		int i = s.indexOf(sub, c);
		if (i == -1)
			return s;

		StringBuffer buf = new StringBuffer(s.length() + with.length());

		synchronized (buf) {
			do {
				buf.append(s.substring(c, i));
				buf.append(with);
				c = i + sub.length();
			} while ((i = s.indexOf(sub, c)) != -1);

			if (c < s.length())
				buf.append(s.substring(c, s.length()));
		}
		return buf.toString();
	}

	/**
	 * 数字转换为定长字符串，如果长度小于指定长度，则在字符串前补0字符
	 * 
	 * @param value
	 *            值
	 * @param count
	 *            字符位数
	 * 
	 * @return 字符串
	 */
	public static String fixNumber(long value, int count) {
		String result = String.valueOf(value);
		if (result.length() < count) {
			String zero = "";
			for (int i = result.length(); i < count; i++) {
				zero += "0";
			}
			result = zero + result;
		}
		return result;
	}

	/**
	 * 判断字符串是否为null或着空串（包括空格）
	 * 
	 * @param source
	 * 
	 * @return boolean
	 */
	public static boolean isNullOrEmpty(String source) {
		if (source == null) {
			return true;
		}
		source = source.trim();
		if ("".equals(source)) {
			return true;
		}

		return false;
	}

	/**
	 * 判断字符数组是否为null或着空串
	 * 
	 * @param source
	 * 
	 * @return boolean
	 */
	public static boolean isNullOrEmpty(String[] source) {
		if (source == null) {
			return true;
		}
		if (source.length == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 分割字符串，返回字符串数组，如果原串为<code>null</code>，则返回 <code>null</code>。
	 * 
	 * @param source
	 *            字符
	 * @param delimeter
	 *            分隔字符
	 * @return String[] 字符数组
	 */
	public static synchronized String[] split(String source, String delimeter) {
		if (source == null) {
			return new String[0];
		}
		StringTokenizer tokens = new StringTokenizer(source, delimeter);
		List<String> items = new ArrayList<String>();
		while (tokens.hasMoreTokens()) {
			items.add(tokens.nextToken());
		}
		String[] array = new String[items.size()];
		for (int i = 0; i < array.length; i++) {
			array[i] = items.get(i);
		}
		return array;
	}

	/**
	 * oracle in 查询
	 * 
	 * @Title: oracleIn
	 * @param ids
	 * @return
	 */
	public static String oracleIn(String[] ids) {
		if (ids == null)
			return null;
		StringBuffer buffer = new StringBuffer("'");
		for (int i = 0; i < ids.length - 1; i++) {
			buffer.append(ids[i] + ",");
		}
		buffer.append(ids[ids.length - 1]).append("'");
		return buffer.toString();
	}

	/**
	 * int转换为string
	 * 
	 * @Title: intToString
	 * @param value
	 * @return
	 */
	public static String intToString(int value) {
		return String.valueOf(value);
	}

	/**
	 * 去掉null值
	 * 
	 * @Title: removeNull
	 * @param str
	 * @return
	 */
	public static String removeNull(String str) {
		if (null == str)
			return "";
		return str;
	}

	/**
	 * 根据特定的字符将字符串分割，同时返回第一个值
	 * 
	 * @Title: getFirstBySplit
	 * @param src
	 * @param split
	 * @return
	 */
	public static String getFirstBySplit(String src, String split) {
		String result = "";
		if (null != src) {
			int index = src.indexOf(split);
			if (index != -1) {
				result = src.substring(0, index);
			}
		}
		return result;
	}

	/**
	 * 字符串转换为double类型
	 * 
	 * @Title: strToDouble
	 * @param str
	 * @return
	 */
	public static double strToDouble(String str) {
		double res = 0.0;
		if (null != str && !"".equals(str)) {
			res = Double.valueOf(str);
		}
		return res;
	}

	/**
	 * 字符串转换为int类型
	 * 
	 * @Title: strToDouble
	 * @param str
	 * @return
	 */
	public static int strToInt(String str) {
		int res = 0;
		if (null != str && !"".equals(str)) {
			res = Integer.valueOf(str);
		}
		return res;
	}

	/**
	 * 返回加1后的字符串(主要用户Level处理)
	 * 
	 * @param str
	 * @return
	 */
	public static String strNext(String str) {
		int res = 0;
		if (null != str && !"".equals(str)) {
			res = Integer.valueOf(str) + 1;
		}
		return String.valueOf(res);
	}

	/**
	 * 将列表返回为字符串
	 * 
	 * @param strs
	 * @return
	 */
	public static String[] listToStrs(List<String> strs) {
		if (strs.size() == 0 || strs.size() < 0)
			return null;
		String[] temp = new String[strs.size()];
		for (int i = 0; i < strs.size(); i++) {
			temp[i] = strs.get(i);
		}
		return temp;
	}

	/**
	 * 判断值在字符数组中是否存在
	 * 
	 * @param strs
	 * @return
	 */
	public static boolean judgeArray(String[] strArray, String target) {
		boolean r = false;
		if (strArray == null || strArray.length == 0 || target == null) {
			return r;
		}

		for (int i = 0; i < strArray.length; i++) {
			if (strArray[i].equals(target)) {
				r = true;
				break;
			}
		}
		return r;
	}

	/**
	 * 生成Ids语句
	 * 
	 * @param ids
	 * @return
	 */
	public static synchronized String buildIds(String[] ids) {
		return buildIds(ids, true);
	}

	/**
	 * 生成Ids语句
	 * 
	 * @param ids
	 * @param bNeedComma
	 * @return
	 */
	public static String buildIds(String[] ids, boolean bNeedComma) {
		StringBuffer buffer = new StringBuffer();
		if (ids == null)
			return null;
		for (int i = 0; i < ids.length; i++) {
			if (i == 0) {
				if (bNeedComma) {
					buffer.append("'").append(ids[i]).append("'");
				} else {
					buffer.append(ids[i]);
				}
			} else {
				if (bNeedComma) {
					buffer.append(",'").append(ids[i]).append("'");
				} else {
					buffer.append(",").append(ids[i]);
				}
			}
		}
		return buffer.toString();
	}

	public static String getShortTitle(String longTitle, Integer shortLen) {
		String res = "";
		if (longTitle != null && longTitle.length() > 0) {
			if (longTitle.length() > shortLen) {
				res = "<span title=" + longTitle + ">" + longTitle.substring(0, shortLen) + "...</span>";
			} else {
				res = longTitle;
			}
		}
		return res;
	}

	public static Boolean equels(String stra, String strb) {
		return stra.equals(strb);
	}

	/**
	 * @Title: paddingString
	 * @Description: 添加指定数量的字符串
	 * @param text
	 * @param length
	 * @return String
	 */
	public static String paddingString(String text, Integer length) {
		StringBuffer buffer = new StringBuffer();
		if (text == null) {
			return null;
		}
		for (int i = 1; i <= length; i++) {
			buffer.append(text);
		}

		return buffer.toString();
	}

	/**
	 * @Title: splitText
	 * @Description: 获取根据指定分割字符串分割的字符串
	 * @param text
	 *            源字符串
	 * @param delimiter
	 *            分割字符串
	 * @return String
	 */
	public static String splitAfterText(String text, String delimiter) {
		String result = "";
		if (StringUtil.isNullOrEmpty(text)) {
			return result;
		}

		String[] tempArray = text.split(delimiter);
		if (tempArray.length > 1) {
			result = tempArray[1];
		} else {
			result = text;
		}

		return result;
	}

	/**
	 * @Title: findFirstImgSrcFromHtml
	 * @Description: 从html中找到第一张图片的src属性值
	 * @param html
	 * @return String
	 */
	public static String findFirstImgSrcFromHtml(String html) {
		String result = "";
		if (StringUtil.isNullOrEmpty(html)) {
			return result;
		}

		Pattern pattern = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");

		Matcher matcher = pattern.matcher(html);

		if (matcher.find()) {
			result = matcher.group(1);
		}

		return result;
	}

	/**
	 * @Title: GetUTF8String
	 * @Description: 获取UTF-8编码的字符串
	 * @param text
	 * @return String
	 */
	public static String getUTF8String(String text) {
		String result = "";
		if (isNullOrEmpty(text)) {
			return result;
		}

		try {
			byte[] bytes = text.getBytes("ISO-8859-1"); // 以"ISO-8859-1"方式解析searchkey
			result = new String(bytes, "UTF-8"); // 再用"utf-8"格式表示name
		} catch (UnsupportedEncodingException e) {
			result = "";
		}
		return result;
	}

	/**
	 * @Title: showOne
	 * @Description: 按照从左到右的顺序判断两个字符串，哪个不为空，显示哪个
	 * @param one
	 * @param two
	 * @return String
	 */
	public static String showOne(String one, String two) {
		if (!isNullOrEmpty(one)) {
			return one;
		} else if (!isNullOrEmpty(two)) {
			return two;
		} else {
			return null;
		}
	}

	/**
	 * @Title: splitTag
	 * @Description: 分割各种符号链接的字符串
	 * @param tag
	 * @return String[]
	 */
	public static String[] splitTag(String tag) {
		if (isNullOrEmpty(tag)) {
			return null;
		} else {
			return tag.split("[,，;；、\\s+|]");
		}
	}

	/**
	 * 
	 * @Title: flgCont$
	 * @Description: 判断字符串是否包含指定的子串
	 * @param text
	 *            ：原字符串
	 * @param flg
	 *            ：以特殊符合分割的字符串（狗|猪，**）
	 * @return
	 */
	public static boolean flgCont$(String text, String flg) {
		String[] str = splitTag(flg);
		if (str != null) {
			String rt = text.replaceAll("[^\u4e00-\u9fa5]", "");
			for (int n = 0, m = str.length; n < m; n++) {
				if (rt.indexOf(str[n]) != -1) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * @Title: subString
	 * @Description: 根据给定长度截取字符串
	 * @param text
	 * @param length
	 * @return String
	 */
	public static String subString(String text, Integer length) {
		if (isNullOrEmpty(text)) {
			return "";
		}
		if (length == 0) {
			length = 10;
		}
		if (text.length() > length) {
			text = text.substring(0, length);
		}

		return text;
	}

	/**
	 * @Title: findFirstImgSrcFromHtml
	 * @Description: 从html中找到第一张图片的src属性值
	 * @param text
	 * @return String
	 */
	public static String escapeText(String text) {
		if (StringUtil.isNullOrEmpty(text)) {
			return text;
		}

		text = text.replaceAll("\\r", "\\\\u000D");
		text = text.replaceAll("\\n", "\\\\u000A");
		return text;
	}

	/**
	 * @Title: convertToList
	 * @Description: 把字符串转换成Integer类型的列表
	 * @param text
	 * @return List<Integer>
	 */
	public static List<Integer> convertToList(String text) {
		if (isNullOrEmpty(text)) {
			return null;
		}
		List<Integer> result = new ArrayList<Integer>();
		String[] array = text.split(",");
		for (String item : array) {
			result.add(Integer.parseInt(item));
		}
		return result;
	}

	/**
	 * @Title: getFitImage
	 * @Description: 获取合适的图片
	 * @param image_url
	 * @param newName
	 * @return String
	 */
	public static String getFitImage(String image_url, String newName) {
		if (isNullOrEmpty(image_url)) {
			return null;
		}

		if (isNullOrEmpty(newName)) {
			return image_url;
		}

		StringBuffer buffer = new StringBuffer(image_url);
		int startIndex = image_url.lastIndexOf('.');
		buffer.replace(startIndex, startIndex + 1, newName + ".");
		return buffer.toString();
	}

	/**
	 * 获取字符串的Int值
	 * 
	 * @param text
	 * @return
	 */
	public static int getStringIntValue(String text) {
		if (StringUtils.isBlank(text)) {
			return 0;
		}
		byte[] bytes = text.getBytes();
		StringBuffer buffer = new StringBuffer();
		for (byte item : bytes) {
			buffer.append(item);
		}
		int intValue = NumberUtils.toInt(buffer.toString(), 0);
		return intValue;
	}

	/**
	 * @Description 把已逗号连接的文件扩展名转化成以*.扩展名;的字符串形式
	 * @param extText
	 * @return String
	 * @author davidwan
	 */
	public static String buildBrowserFileTypes(String extText) {
		if (StringUtils.isBlank(extText)) {
			return null;
		}
		String[] extArray = extText.split(",");
		StringBuffer buffer = new StringBuffer();
		buffer.append("*.").append(Joiner.on(";*.").join(extArray)).append(";");
		return buffer.toString();
	}

	/**
	 * @Description 判断字符是否是汉字
	 * @param str
	 * @return boolean
	 * @author davidwan
	 */
	public static boolean isChineseChar(String str) {
		boolean temp = false;
		Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
		Matcher m = p.matcher(str);
		if (m.find()) {
			temp = true;
		}
		return temp;
	}

	/**
	 * @Description 以逗号连接的字符串中是否包含特定字符
	 * @param text
	 * @param value
	 * @return boolean
	 */
	public static boolean containsValue(String text, String value) {
		String[] array = text.split(",");
		for (String item : array) {
			if (item.equalsIgnoreCase(value)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @Description 获取不包含
	 * @param fileName
	 * @return String
	 */
	public static String gainFileNameWithoutPath(String fileName) {
		if (StringUtils.isBlank(fileName)) {
			return "";
		}
		int index = fileName.lastIndexOf("\\");
		if (index > -1) {
			return fileName.substring(index + 1);
		}
		return fileName;
	}

	/**
	 * @Description 构造树形下拉名称
	 * @param text
	 *            名称
	 * @param layer
	 *            层级
	 * @return String
	 */
	public static String buildTreeName(String text, Integer layer) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < layer; i++) {
			if (i == (layer - 1)) {
				buffer.append("|---");
			} else {
				buffer.append("&nbsp;&nbsp;&nbsp;&nbsp;");
			}
		}
		buffer.append(text);
		return buffer.toString();
	}

	/**
	 * @Description 根据指定的分隔符分割字符串，并获取目标数组中的指定index元素
	 * @param text
	 * @param separator
	 * @param index
	 * @return String
	 */
	public static String gainSpecifiedElement(String text, String separator, Integer index) {
		if (StringUtils.isBlank(text) || StringUtils.isBlank(separator)) {
			return null;
		}
		String[] array = split(text, separator);
		if (index > array.length - 1) {
			return null;
		}
		return array[index];
	}

	// 获取锚点结果
	public static String getConcat_value(Integer mdz, String md_concat, String results) {
		int result = -1;

		if (md_concat != null && results != null && !"".equals(md_concat)) {
			if (md_concat.contains(String.valueOf(mdz))) {
				String[] tslbArray = md_concat.split(",");
				for (int i = 0; i < tslbArray.length; i++) {
					if (tslbArray[i].equals(String.valueOf(mdz))) {
						result = i;
					}
				}

			}
		}
		if (result != -1) {
			return results.split(",")[result];
		} else {
			return "0";
		}
	}

	/**
	 * @Description 获取百分比
	 * @param text
	 * @param separator
	 * @param index
	 * @return String
	 */
	public static String getPrcent(int a, int b) {
		// 这里的数后面加“D”是表明它是Double类型，否则相除的话取整，无法正常使用
		double percent = (double) a / (double) b;
		// 获取格式化对象
		NumberFormat nt = NumberFormat.getPercentInstance();
		// 设置百分数精确度2即保留两位小数
		nt.setMinimumFractionDigits(2);
		return nt.format(percent);
	}

	/**
	 * @Description 添加指定数量的空格
	 * @param count
	 * @return String
	 */
	public static String paddingSpace(Integer count) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 1; i < count; i++) {
			buffer.append("&nbsp;&nbsp;&nbsp;&nbsp;");
		}
		return buffer.toString();
	}

	/**
	 * @Description 把对象序列化成json字符串
	 * @param obj
	 * @return String
	 */
	public static String toJson(Object obj) {
		if (obj == null) {
			return null;
		}
		return JSON.toJSONString(obj);
	}

	/**
	 * @Description 是否是空字符串
	 * @param value
	 * @return Boolean
	 */
	public static Boolean isBlank(String value) {
		return StringUtils.isBlank(value);
	}

	/**
	 * @Description  转换为UTF-8
	 * @param value
	 * @return String     
	 */
	public static String convertToUTF8(String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		}
		String result = null;
		try {
			result = new String(value.getBytes(Constants.ISO_8859_1), Constants.UTF8);
		} catch (Exception e) {
			LogHelper.getLogger().error("转换为UTF-8编码时出错", e);
		}
		return result;
	}
}
