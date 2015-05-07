package nuoman.com.framwork.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * 
 * @file : DateUtil.java [V 1.0.0]
 * 
 * @author: 陈建辉
 * 
 * @time : CREAT AT 2013-11-18 下午2:01:32
 * 
 * @TODO : 【DateUtil】
 * 
 */
public class DateUtil {

	public static final long DAY_SECONDS = 24 * 60 * 60 * 1000;

	public static String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";

	public static String yyyy = "yyyy";

	public static String yyyy_MM = "yyyy-MM";

	public static String MM_dd = "MM-dd";

	public static String yyyy_MM_dd = "yyyy-MM-dd";

	public static String MM_dd_yyyy = "MM-dd-yyyy";

	public static String yyyy_MM_dd_HH = "yyyy-MM-dd HH";

	public static String yyyy_MM_dd_HH_mm = "yyyy-MM-dd HH:mm";

	public static String yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";

	public static String yyyyMMddHHmmss = "yyyyMMddHHmmss";

	public static String TIME_PATTERN = "HH:mm:ss";

	/**
	 * Translate string of date with separactor '-' to
	 * <code>java.util.Date</code> type
	 * 
	 * @param str
	 *            string of date .eg:"2006-01-01"
	 * @return <code>java.util.Date</code>
	 */
	public static Date strToDate(String str) {
		Date result = new Date();
		DateFormat format = new SimpleDateFormat(DEFAULT_DATE_PATTERN);
		try {
			result = format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static Date getDateNow() {
		return Calendar.getInstance().getTime();
	}

	public static final Date convertStringToDate(String aMask, String strDate) {
		SimpleDateFormat df = null;
		Date date = null;
		df = new SimpleDateFormat(aMask, Locale.CHINESE);
		try {
			date = df.parse(strDate);
		} catch (ParseException pe) {
			android.util.Log.e("ParseException", pe.getMessage());
		}

		return date;
	}

	public static final String getDateTime(String aMask, Date aDate) {

		SimpleDateFormat df = null;
		String returnValue = "";
		if (aDate == null) {
			android.util.Log.e("warn", "aDate is null!1");
		} else {
			df = new SimpleDateFormat(aMask);
			returnValue = df.format(aDate);
		}
		return returnValue;
	}

	/**
	 * 从系统时间获取指定格式
	 * @param aMask
	 * @param time
	 * @return
	 */
	public static final String getDateTimeForsys(String aMask, Long time) {

		SimpleDateFormat df = null;
		String returnValue = "";
		if (time == null) {
			android.util.Log.e("warn", "aDate is null!1");
		} else {
			df = new SimpleDateFormat(aMask);
			returnValue = df.format(time);
		}
		return returnValue;
	}
	/**
	 * Translate string of date with separator '-' to <code>java.sql.Date</code>
	 * type
	 * 
	 * @param str
	 *            string of date .eg:"2006-01-01"
	 * @return <code>java.sql.Date</code>
	 */
	public static java.sql.Date strToSQLDate(String str) {
		Date temp = strToDate(str);
		java.sql.Date result = new java.sql.Date(temp.getTime());
		return result;
	}

	/**
	 * Translate <code>java.util.Date</code> type to string of date with
	 * separator '-'
	 * 
	 * @param date
	 *            The want translate <code>java.util.Date</code>
	 * @return string of the date
	 */
	public static String dateToStr(Date date) {
		DateFormat format = new SimpleDateFormat(DEFAULT_DATE_PATTERN);
		return format.format(date);
	}

	/**
	 * add number of days of the date
	 * 
	 * @param orig
	 *            the date which want to add
	 * @param addDay
	 *            add number of days
	 * @return
	 */
	public static Date addDay(Date orig, int addDay) {
		Date result = new Date(orig.getTime() + addDay * DAY_SECONDS);
		return result;
	}

	/**
	 * add number of month of the date
	 * 
	 * @param orig
	 *            the date which want to add
	 * @param addMonth
	 *            add number of month
	 * @return
	 */
	public static Date addMonth(Date orig, int addMonth) {

		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.setTime(orig);
		cal.add(Calendar.MONTH, addMonth);

		return cal.getTime();

	}

	/**
	 * subtract number of days of the date
	 * 
	 * @param orig
	 *            orig the date which want to subtract
	 * @param subDay
	 *            subtract number of days
	 * @return
	 */
	public static Date subDay(Date orig, int subDay) {
		Date result = new Date(orig.getTime() - subDay * DAY_SECONDS);
		return result;
	}

	/**
	 * Get number of days which subtract of two date
	 * 
	 * @param start
	 *            Start date
	 * @param end
	 *            End date
	 * @return number of days
	 */
	public static int getSubDay(Date end, Date start) {
		int result = (int) ((end.getTime() - start.getTime()) / DAY_SECONDS);
		return result;
	}

	public static Date getNextDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(GregorianCalendar.DAY_OF_MONTH, 1);
		return (Date) calendar.getTime().clone();
	}

	/**
	 * This method converts a String to a date using the datePattern
	 * 
	 * @param strDate
	 *            the date to convert (in format MM/dd/yyyy)
	 * @return a date object
	 * 
	 * @throws java.text.ParseException
	 */
	public static Date convertStringToDate(String strDate) {
		Date aDate = null;
		aDate = convertStringToDate(getDatePattern(), strDate);
		return aDate;
	}

	/**
	 * This method generates a string representation of a date based on the
	 * System Property 'dateFormat' in the format you specify on input
	 * 
	 * @param aDate
	 *            A date to convert
	 * @return a string representation of the date
	 */
	public static final String convertDateToString(Date aDate) {
		return getDateTime(getDatePattern(), aDate);
	}

	public static int getYearInt() {
		SimpleDateFormat sdf = new SimpleDateFormat(yyyy);
		try {
			return Integer.parseInt(sdf.format(new Date()));
		} catch (Exception e) {
			return 0;
		}
	}

	public static String getStringByDate(Date date, String format) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format(date);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 获取指定日期对应月份的最后一天
	 * 
	 * @param date
	 *            指定日期
	 * @return 月份的最后一天
	 */
	public static Date getLastDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DATE, 1);
		calendar.add(Calendar.DATE, -1);
		return calendar.getTime();
	}

	/**
	 * 获取指定日期对应月份的第一天
	 * 
	 * @param date
	 *            指定日期
	 * @return 月份的第一天
	 */
	public static Date getFirstDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return calendar.getTime();
	}

	public static final String convertDate2String(Date date, String format) {
		SimpleDateFormat df = null;
		String returnValue = "";
		if (date == null) {
			android.util.Log.e("warn", "aDate is null!2");
		} else {
			df = new SimpleDateFormat(format);
			returnValue = df.format(date);
		}
		return returnValue;
	}

	public static String getDatePattern() {
		return DEFAULT_DATE_PATTERN;
	}

	public static String getWeekByNow() {
		Calendar calendar = Calendar.getInstance();
		int week = calendar.get(Calendar.DAY_OF_WEEK);
		String resultStr = "";
		if (week == 2)
			resultStr = "一";
		if (week == 3)
			resultStr = "二";
		if (week == 4)
			resultStr = "三";
		if (week == 5)
			resultStr = "四";
		if (week == 6)
			resultStr = "五";
		if (week == 7)
			resultStr = "六";
		if (week == 1)
			resultStr = "日";
		return "星期" + resultStr;
	}

	public static String getDayOfWeek(Date date) {
		Calendar cd = Calendar.getInstance();
		cd.setTime(date);
		int mydate = cd.get(Calendar.DAY_OF_WEEK);
		String showDate = " ";
		switch (mydate) {
		case 1:
			showDate = "星期日 ";
			break;
		case 2:
			showDate = "星期一 ";
			break;
		case 3:
			showDate = "星期二 ";
			break;
		case 4:
			showDate = "星期三 ";
			break;
		case 5:
			showDate = "星期四 ";
			break;
		case 6:
			showDate = "星期五 ";
			break;
		default:
			showDate = "星期六 ";
			break;
		}
		return showDate;
	}

	// MMddHHmmss

	public static long getMilliseconds(String dateStr) {
		String pattern = "MMddHHmmss";
		Date date = convertStringToDate(pattern, dateStr);
		long d1 = date.getTime();
		long d2 = convertStringToDate(pattern,
				convertDate2String(new Date(), pattern)).getTime();
		long d3 = d2 - d1;
		return d3;
	}

	public static String getRangeTime(long timeInSeconds) {
		long hours, minutes, seconds;
		// 1(hour)*60(minite)*60(seconds)*1000(millisecond)
		hours = timeInSeconds / 3600000;
		timeInSeconds = timeInSeconds - (hours * 3600 * 1000);
		// 1(seconds)*1000(millisecond)
		minutes = timeInSeconds / 60000;
		timeInSeconds = timeInSeconds - (minutes * 60 * 1000);
		// 1(seconds)*1000(millisecond)
		seconds = timeInSeconds / 1000;
		return hours + "小时 " + minutes + "分钟 " + seconds + "秒";
	}

	public static void main(String[] args) {
		long timeInSeconds = DateUtil.getMilliseconds(DateUtil
				.convertDate2String(new Date(108230133), "MMddHHmmss"));
		System.out.println(DateUtil.getRangeTime(timeInSeconds));
	}

}
