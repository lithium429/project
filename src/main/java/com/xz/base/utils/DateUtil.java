package com.xz.base.utils;

/**
 * @author WangMengzhong
 * @date: 2013-10-21
 */
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import com.google.common.collect.ImmutableList;

public class DateUtil {

	private static String DAY_TIME_TYPE = "yyyy-MM-dd HH:mm:ss";
	public static final String DAY_TIME_MINUTE_TYPE = "yyyy-MM-dd HH:mm";
	private static String DAY_TIME_TYPE_S = "yyyy-MM-dd 00:00:00";
	//private static String DAY_TIME_TYPE_E = "yyyy-MM-dd 23:59:59";
	private static String DAY_TYPE = "yyyy-MM-dd";
	private static String parrten = "yyyyMMdd";
	private static int weeks = 0;
	private static String FILE_NAME = "yyyyMMddHHmmss";

	// 当前日期
	public static Timestamp getCurrentTime() {
		Date date = new Date();
		String nowTime = new SimpleDateFormat(DAY_TIME_TYPE).format(date);
		return Timestamp.valueOf(nowTime);
	}
	
	// 当前日期
	public static Timestamp getCurrentDate() {
		Date date = new Date();
		String nowTime = new SimpleDateFormat(DAY_TIME_TYPE_S).format(date);
		return Timestamp.valueOf(nowTime);
	}

	// 得到下月的当前天
	public static Timestamp getNextMonthCurrentTime() {
		Calendar lastDate = Calendar.getInstance();

		lastDate.add(Calendar.MONTH, 1);// 减一个月，变为下月的1号
		String nowTime = new SimpleDateFormat(DAY_TIME_TYPE).format(lastDate.getTime());
		return Timestamp.valueOf(nowTime);
	}

	// 得到下月的当前天
	public static Timestamp getNextHalfYearCurrentTime() {
		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.MONTH, 6);// 减一个月，变为下月的1号
		String nowTime = new SimpleDateFormat(DAY_TIME_TYPE).format(lastDate.getTime());
		return Timestamp.valueOf(nowTime);
	}

	// 上月第一天
	public static Timestamp getPreviousMonthFirst() {
		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
		lastDate.add(Calendar.MONTH, -1);// 减一个月，变为下月的1号
		String nowTime = new SimpleDateFormat(DAY_TIME_TYPE).format(lastDate.getTime());
		return Timestamp.valueOf(nowTime);
	}

	public static Timestamp getPreviousMonthEnd() {
		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.MONTH, -1);// 减一个月
		lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		lastDate.roll(Calendar.DATE, -1);// 日期回滚一天，也就是本月最后一天
		String nowTime = new SimpleDateFormat(DAY_TIME_TYPE).format(lastDate.getTime());
		return Timestamp.valueOf(nowTime);
	}

	// 获得上周星期日的日期
	public static Timestamp getPreviousWeekSunday() {
		weeks = 0;
		weeks--;
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + weeks);
		Date monday = currentDate.getTime();
		String preMonday = new SimpleDateFormat(DAY_TIME_TYPE).format(monday);
		return Timestamp.valueOf(preMonday);
	}

	// 获得本周第一天的日期
	public static Timestamp getThisWeekStart() {
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus);
		Date monday = currentDate.getTime();
		String nowTime = new SimpleDateFormat(DAY_TIME_TYPE_S).format(monday);
		return Timestamp.valueOf(nowTime);
	}

	/**
	 * @Description 获取本周日日期
	 * @return Timestamp
	 */
	public static Timestamp getThisWeekEnd() {
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 6);
		Date monday = currentDate.getTime();
		String nowTime = new SimpleDateFormat(DAY_TIME_TYPE_S).format(monday);
		return Timestamp.valueOf(nowTime);
	}

	// 获得本月第一天的日期
	public static Timestamp getThisMouthStart() {
		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
		String nowTime = new SimpleDateFormat(DAY_TIME_TYPE_S).format(lastDate.getTime());
		return Timestamp.valueOf(nowTime);
	}

	// 获得本年第一天的日期
	public static Timestamp getThisYearStart() {
		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
		lastDate.add(Calendar.MONTH, -new Date().getMonth());
		String nowTime = new SimpleDateFormat(DAY_TIME_TYPE_S).format(lastDate.getTime());
		return Timestamp.valueOf(nowTime);
	}

	// 获得上周星期一的日期
	public static Timestamp getPreviousWeekday() {
		weeks--;
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 * weeks);
		Date monday = currentDate.getTime();
		String preMonday = new SimpleDateFormat(DAY_TIME_TYPE).format(monday);
		return Timestamp.valueOf(preMonday);
	}

	@SuppressWarnings("rawtypes")
	public static Map getPreviousWeek() {
		weeks = 0;
		weeks--;
		Map<Integer, String> weekMap = new HashMap<Integer, String>();
		int mondayPlus = getMondayPlus();
		for (int i = 0; i < 7; i++) {
			GregorianCalendar currentDate = new GregorianCalendar();
			currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 * weeks + i);
			Date monday = currentDate.getTime();
			String date = new SimpleDateFormat(DAY_TYPE).format(monday);
			weekMap.put(i + 1, date);
		}
		return weekMap;
	}

	@SuppressWarnings("rawtypes")
	public static Map getNextWeek() {
		weeks = 0;
		weeks++;
		Map<Integer, String> weekMap = new HashMap<Integer, String>();

		int mondayPlus = getMondayPlus();
		for (int i = 0; i < 7; i++) {
			GregorianCalendar currentDate = new GregorianCalendar();
			currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 * weeks + i);
			Date monday = currentDate.getTime();
			String date = new SimpleDateFormat(DAY_TYPE).format(monday);
			weekMap.put(i + 1, date);
		}
		return weekMap;
	}

	@SuppressWarnings("rawtypes")
	public static LinkedHashMap getThisWeek() {
		weeks = 0;
		LinkedHashMap<Integer, String> weekMap = new LinkedHashMap<Integer, String>();
		int mondayPlus = getMondayPlus();
		for (int i = 0; i < 7; i++) {
			GregorianCalendar currentDate = new GregorianCalendar();
			currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 * weeks + i);
			Date monday = currentDate.getTime();
			String date = new SimpleDateFormat(DAY_TYPE).format(monday);
			weekMap.put(i + 1, date);
		}
		return weekMap;
	}

	// 获得当前日期与本周日相差的天数
	private static int getMondayPlus() {
		Calendar cd = Calendar.getInstance();
		// 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
		int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一作为第一天所以这里减1
		if (dayOfWeek == 1) {
			return 0;
		} else {
			return 1 - dayOfWeek;
		}
	}

	// 获得当前时间
	public static String getStrTime() {
		String timestr;
		java.text.SimpleDateFormat sdf = new SimpleDateFormat(parrten);
		java.util.Date cday = new java.util.Date();
		timestr = sdf.format(cday);
		return timestr;
	}

	/**
	 * 今天的开始时间
	 * 
	 * @Title: todayStart
	 * @return
	 */
	public static String todayStart() {
		Date date = new Date();
		DateFormat format = new SimpleDateFormat(DAY_TYPE);
		return format.format(date) + " 00:00:00";
	}

	/**
	 * 今天的结束日期
	 * 
	 * @Title: todayEnd
	 * @return
	 */
	public static String todayEnd() {
		Date date = new Date();
		DateFormat format = new SimpleDateFormat(DAY_TYPE);
		return format.format(date) + " 23:59:59";
	}

	public static String parseTimestampToStr(Timestamp time) {
		SimpleDateFormat formatter = new SimpleDateFormat(DAY_TIME_TYPE);
		formatter.format(time);
		return formatter.format(time);
	}

	public static boolean compareDateMiddle(Timestamp startTime, Timestamp endTime, Timestamp curTime) {
		if (startTime.before(curTime) && endTime.after(curTime)) {
			return true;
		}
		return false;
	}

	/**
	 * 获取当前年
	 * 
	 * @Title: getCurYear
	 * @return
	 */
	public static int getCurYear() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * 生成文件名
	 * 
	 * @Title: getFileName
	 * @return
	 */
	public static String getFileName() {
		Date date = new Date();
		String nowTime = new SimpleDateFormat(FILE_NAME).format(date);
		return nowTime;
	}

	/**
	 * 判断当前时间是否在给定的时间段内
	 * 
	 * @Title: compareTime
	 * @param begin
	 * @param end
	 * @param curTime
	 * @return true 表示在内，false表示不在
	 */
	public boolean compareTime(Timestamp begin, Timestamp end, Timestamp curTime) {
		if (curTime.after(begin) && curTime.before(end)) {
			return true;
		}
		return false;
	}

	/**
	 * @param dateStr
	 *            格式 yyyy-MM-dd HH:ss:mm
	 * @return Date
	 */
	public static Date strToDate(String dateStr) {
		return strToDate(dateStr, DAY_TIME_TYPE);
	}

	/**
	 * @param dateStr
	 *            日期字符串,如 ：2012-12-12
	 * @param partn
	 *            匹配格式 ，如yyyy-MM-dd
	 * @return Date
	 */
	public static Date strToDate(String dateStr, String partn) {
		Date date = new Date();
		DateFormat sdf = new SimpleDateFormat(partn);
		try {
			date = sdf.parse(dateStr);
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return date;
	}

	/**
	 * @param date
	 *            Date
	 * @return yyyy-MM-dd HH:mm:ss格式字符串
	 */
	public static String dateToStr(Date date) {
		return dateToStr(date, DAY_TIME_TYPE);
	}

	/**
	 * @param date
	 *            Date
	 * @return yyyy-MM-dd HH:mm:ss格式字符串
	 * @author addBy 王建华
	 */
	public static String dateToStrShort(Date date) {
		return dateToStr(date, DAY_TYPE);
	}

	/**
	 * @param 匹配格式
	 *            ，如yyyy-MM-dd
	 * @return 匹配格式的日期字符串
	 */
	public static String getCurDateStr(String partn) {
		return dateToStr(new Date(), partn);
	}

	/**
	 * @param date
	 *            date
	 * @param partn
	 *            匹配格式 ，如yyyy-MM-dd
	 * @return 匹配字符串
	 */
	public static String dateToStr(Date date, String pattern) {
		String dateStr = "";
		DateFormat sdf = new SimpleDateFormat(pattern);
		try {
			dateStr = sdf.format(date);
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return dateStr;
	}

	/**
	 * @param timeStampStr
	 *            日期字符串，如yyyy-MM-dd
	 * @return TimeStamp
	 */
	public static Timestamp strToTimeStamp(String timeStampStr) {
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		try {
			ts = Timestamp.valueOf(timeStampStr);
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return ts;
	}

	/**
	 * @param timeStamp
	 *            TimeStamp
	 * @return 日期字符串，格式 yyyy-MM-dd HH:ss:mm
	 */
	public static String timeStampToStr(Timestamp timeStamp) {
		return timeStampToStr(timeStamp, DAY_TIME_TYPE);
	}

	/**
	 * @param timeStamp
	 *            TimeStamp
	 * @param dateFormat
	 *            匹配格式 ，如yyyy-MM-dd
	 * @return 匹配格式的日期字符创
	 */
	public static String timeStampToStr(Timestamp timeStamp, String dateFormat) {
		String timeStampStr = "";
		DateFormat sdf = new SimpleDateFormat(dateFormat);
		try {
			timeStampStr = sdf.format(timeStamp);
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return timeStampStr;
	}

	/**
	 * @Description
	 * @param date
	 * @return List<Integer>
	 */
	public static List<Integer> getDaysOfMonth(DateTime date) {
		final ImmutableList.Builder<Integer> dayList = ImmutableList.builder();

		LocalDate firstDay = date.toLocalDate().withDayOfMonth(1);
		final LocalDate nextMonthFirstDay = firstDay.plusMonths(1);
		while (firstDay.isBefore(nextMonthFirstDay)) {
			dayList.add(firstDay.toDateTimeAtStartOfDay().getDayOfMonth());
			firstDay = firstDay.plusDays(1);
		}
		return dayList.build();
	}

	/**
	 * @Description 获取星期几的Int值
	 * @param year
	 * @param month
	 * @param day
	 * @return Integer
	 */
	public static Integer getDayOfWeek(Integer year, Integer month, Integer day) {
		DateTime date = new DateTime(year, month, day, 0, 0, 0);
		return date.getDayOfWeek();
	}

	/**
	 * @Description 获取星期的中文表示
	 * @param year
	 * @param month
	 * @param day
	 * @return String
	 */
	public static String getDayOfWeekForText(Integer year, Integer month, Integer day) {
		DateTime date = new DateTime(year, month, day, 0, 0, 0);
		String[] weekArray = new String[] { "一", "二", "三", "四", "五", "六", "日" };
		return weekArray[date.getDayOfWeek() - 1];
	}

	/**
	 * @Description 获取星期的中文表示
	 * @param dayOfWeek
	 * @return String
	 */
	public static String getDayOfWeekForText(Byte dayOfWeek) {
		String[] weekArray = new String[] { "一", "二", "三", "四", "五", "六", "日" };
		return weekArray[dayOfWeek - 1];
	}
}
