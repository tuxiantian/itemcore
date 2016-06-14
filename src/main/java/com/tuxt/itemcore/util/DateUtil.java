package com.tuxt.itemcore.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * 
 * @author 
 * @since 2015-03-17
 */
public final class DateUtil {
	/** Private Constructor **/
	private DateUtil() {
	}

	/** 日期格式 **/
	public interface DATE_PATTERN {
		String HHMMSS = "HHmmss";
		String HH_MM_SS = "HH:mm:ss";
		String YYYYMMDD = "yyyyMMdd";
		String YYYY_MM_DD = "yyyy-MM-dd";
		String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
		String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
		String YNR="YYYY年MM月dd日";
	}

	/**
	 * 将Date类型转换成String类型
	 * 
	 * @param date
	 *            Date对象
	 * @return 形如:"yyyy-MM-dd HH:mm:ss"
	 */
	public static String date2String(Date date) {
		return date2String(date, DATE_PATTERN.YYYY_MM_DD_HH_MM_SS);
	}

	/**
	 * 将Date按格式转化成String
	 * 
	 * @param date
	 *            Date对象
	 * @param pattern
	 *            日期类型
	 * @return String
	 */
	public static String date2String(Date date, String pattern) {
		if (date == null || pattern == null){
			return null;
		}
		return new SimpleDateFormat(pattern).format(date);
	}
	/**
	 * 将日期按照特定格式转换成字符串
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String getDateYMDHMS() {
		Date date=new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmSS");
		return dateFormat.format(date);
	}

	public static String StringFomat(String dateString){
		SimpleDateFormat format = new SimpleDateFormat(DATE_PATTERN.YYYYMMDD);
		String dateF="";
		try {
			Date date=format.parse(dateString);
			dateF=date2String(date,DATE_PATTERN.YNR);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return dateF;
	}
	/**
	 * 将String类型转换成Date类型
	 * 
	 * @param date
	 *            Date对象
	 * @return
	 */
	public static Date string2Date(String date) {
		SimpleDateFormat format = new SimpleDateFormat(DATE_PATTERN.YYYY_MM_DD_HH_MM_SS);
		try {
			return format.parse(date);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 将String类型转换成Date类型
	 * 
	 * @param date
	 *            Date对象
	 * @return
	 */
	public static Date string2Date(String date,String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		try {
			return format.parse(date);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 传入yyyy-MM-dd格式的字符串返回yyyyMM格式的字符串
	 * @author jiaotd
	 * @since 2015年7月25日 下午4:02:28
	 * @return
	 */
	public static String getYearAndMonth(String str){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = GregorianCalendar.getInstance();
		try {
			calendar.setTime(sdf.parse(str));
		} catch (ParseException e) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		sb.append(calendar.get(Calendar.YEAR));
		if((calendar.get(Calendar.MONTH) + 1) < 10 ){
			sb.append("0").append((calendar.get(Calendar.MONTH) + 1));
		}else{
			sb.append((calendar.get(Calendar.MONTH) + 1));
		}
		return sb.toString();
	}

	public static Date getDateAfterDay(Date date,int afterDay){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		return  calendar.getTime();
	}

	/**
	 * 判断传入的时间是否比当前日大
	 * @author jiaotd
	 * @since 2015年7月26日 下午2:24:55
	 * @param date(yyyy-MM-ss)
	 * @return
	 */
	public static boolean isBeforeToday(String str){
		Date date = string2Date(str,DATE_PATTERN.YYYY_MM_DD);
		Date today = new Date();
		today = string2Date(date2String(today,DATE_PATTERN.YYYY_MM_DD), DATE_PATTERN.YYYY_MM_DD);
		return date.before(today);
	}

	// 得到2个日期之间的月份,返回值List<字符串>
	public static List<String> getMonthsBetween(Date minDate, Date maxDate,String format) {
		ArrayList<String> result = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		if (minDate.after(maxDate)) {
			Date tmp = minDate;
			minDate = new Date(maxDate.getTime());
			maxDate = new Date(tmp.getTime());
		}
		Calendar min = Calendar.getInstance();
		Calendar max = Calendar.getInstance();
		min.setTime(minDate);
		min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);
		max.setTime(maxDate);
		max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);
		Calendar curr = min;
		while (curr.before(max)) {
			result.add(sdf.format(curr.getTime()));
			curr.add(Calendar.MONTH, 1);
		}
		return result;
	}
	public static long getDistanceTimes2(String str1, String str2) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date one;
		Date two;
		long day = 0;
		long hour = 0;
		long min = 0;
		long sec = 0;
		try {
			one = df.parse(str1);
			two = df.parse(str2);
			long time1 = one.getTime();
			long time2 = two.getTime();
			long diff;
			if (time1 < time2) {
				diff = time2 - time1;
			} else {
				diff = time1 - time2;
			}
			day = diff / (24 * 60 * 60 * 1000);
			hour = (diff / (60 * 60 * 1000) - day * 24);
			min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
			sec = (diff/1000-day*24*60*60-hour*60*60-min*60);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// long[] times = {day, hour, min, sec};
		return sec;
	}
	public static boolean isLastDayOfMonth(Date date){
		Calendar calendar = Calendar.getInstance();  
		  
		// 设置日期为本月最大日期  
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE)); 
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		
		try {
			if (format.parse(date2String(calendar.getTime())).compareTo(format.parse(date2String(date)))==0) {
				return true;
			}else {
				return false;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}
	public static boolean isFirstDayOfMonth(Date date){
		Calendar calendar = Calendar.getInstance();  
		  
		// 设置日期为本月最大日期  
		calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DATE)); 
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		
		try {
			if (format.parse(date2String(calendar.getTime())).compareTo(format.parse(date2String(date)))==0) {
				return true;
			}else {
				return false;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}
	public static void datetest1() {
		// 小于当前时间5分钟
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		int timeBef=-5;
		calendar.add(Calendar.MINUTE, timeBef);
		System.out.println(date2String(calendar.getTime(), DATE_PATTERN.YYYYMMDDHHMMSS));
	}
	public static void main(String[] args) {
		//System.out.println(StringFomat("20150610"));
		//System.out.println(getYearAndDay("2015-10-27"));
		//System.out.println(isBeforeToday("2015-7-26"));

		/*System.out.println(date2String(getDateAfterDay(new Date(), 1),DATE_PATTERN.YYYY_MM_DD)+ " 19:00:00" );
		System.out.println(date2String(new Date(),DATE_PATTERN.YYYY_MM_DD)+ " 19:00:00" );*/
		/*System.out.println(isBeforeToday("2015-7-1"));

		System.out.println(date2String(getDateAfterDay(new Date(), 1),DATE_PATTERN.YYYY_MM_DD)+ " 19:00:00" );
		System.out.println(date2String(new Date(),DATE_PATTERN.YYYY_MM_DD)+ " 19:00:00" );
		System.out.println(getMonthsBetween(string2Date("2015-01-1", "yyyy-MM-dd"),
				string2Date("2015-11-1", "yyyy-MM-dd"),"yyyy-MM-dd"));*/
		
System.out.println(date2String(string2Date("2015-08-03 05:03:04"), "yyyyMMddHH:mm:ss"));
	}
}
