package com.tasly.deepureflow.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName: DateUtil
 * @author biying
 * @date 2015年8月21日 下午4:52:08
 * @Description: 时间计算工具封装
 * @version 1.0
 */
public class DateUtil {

	private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);
	
	public static final String DATE_STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
	
	public static final String DATE_YMD_FORMAT = "yyyy-MM-dd";
	
	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	/**
	* 得到当前日期<字符串类型>
	* @param dateFormat 日期格式
	* @return 当前日期<字符串类型>
	*/
	public static String getCurrDate(String dateFormat) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		return sdf.format(new Date());
	}

	/**
	 * 
	 * @Title: compareDate 
	 * @Description: 日期对比 
	 * @param date1  日期：20150907
	 * @param date2  日期：20150907
	 * @return: 如果date1>date2 return 1，如果date1 < date2 return -1,否则返回0
	 */
	public static int compareDate(String date1, String date2) {
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		try {
			Date dt1 = df.parse(date1);
			Date dt2 = df.parse(date2);
			if (dt1.getTime() > dt2.getTime()) {
				return 1;
			} else if (dt1.getTime() < dt2.getTime()) {
				return -1;
			} else if(dt1.getTime() == dt2.getTime()) {
				return 0;
			}
		} catch (Exception exception) {
			logger.error("Error",exception);
		}
		return 0;
	}

	/**
	 * 
	 * @Title: compareDate 
	 * @Description: 日期对比 
	 * @param date1  日期：20150907
	 * @param date2  日期：20150907
	 * @return: 如果date1>date2 return 1，如果date1 < date2 return -1,否则返回0
	 */
	public static int compareStringDate(String date1, String date2) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date dt1 = df.parse(date1);
			Date dt2 = df.parse(date2);
			if (dt1.getTime() >= dt2.getTime()) {
				return 1;
			} else if (dt1.getTime() < dt2.getTime()) {
				return -1;
			} else {
				return 0;
			}
		} catch (Exception exception) {
			logger.error("DateUtil.compareStringDate Error:" + exception,exception);
		}
		return 0;
	}
	
	/**
	 * @Title: subMonth
	 * @Description: 传入具体日期 ，返回具体日期减/加 月份
	 * @param date
	 *            传入日期：日期(20150907)
	 * @param subMonth
	 *            加/减月份
	 * @return String 日期： 20150907
	 */
	public static String subMonth(String date, int subMonth) {
		String reStr = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			Date dt = sdf.parse(date);
			Calendar rightNow = Calendar.getInstance();
			rightNow.setTime(dt);

			rightNow.add(Calendar.MONTH, subMonth);
			Date dt1 = rightNow.getTime();
			reStr = sdf.format(dt1);
		} catch (Exception e) {
			logger.error("DateUtil.subMonth Error:" + e);
		}
		return reStr;
	}
	
	/**
	 * @Title: subDay
	 * @Description: 传入具体日期 ，返回具体日期减/加 日期
	 * @param date
	 *            传入日期：日期(2015-09-07)
	 * @param subMonth
	 *            加/减日期
	 * @return String 日期： 2015-09-07
	 */
	public static String subDay(String date, int subDay) {
		String reStr = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_YMD_FORMAT);
			Date dt = sdf.parse(date);
			Calendar rightNow = Calendar.getInstance();
			rightNow.setTime(dt);

			rightNow.add(Calendar.DATE, subDay);
			Date dt1 = rightNow.getTime();
			reStr = sdf.format(dt1);
		} catch (Exception e) {
			logger.error("DateUtil.subMonth Error:" + e);
		}
		return reStr;
	}

	public static String formatDateByFormat(Date date, String format) {
		String result = "";
		if (date != null) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat(format);
				result = sdf.format(date);
			} catch (Exception ex) {
				logger.error("DateUtil.formatDateByFormat Error" + ex);
			}
		}
		return result;
	}

	/**
	 * 
	 * @Title: formatDate
	 * @author biying
	 * @date 2015年8月21日 下午5:01:43 
	 * @return 获取当前时间，精确到毫秒
	 */
	public static String formatDate(Date date) {
		String resultTime = "";
		try {
			resultTime = formatDateByFormat(date, DATE_STANDARD_FORMAT);
		} catch (Exception ex) {
			logger.error("DateUtil.formatDate Error" + ex);
			resultTime = "";
		}
		return resultTime;
	}

	/**
	 * 
	 * @Title: formatDateTime
	 * @author biying
	 * @date 2015年8月21日 下午5:03:54 @ return 获取当前时间，精确到秒 "yyyy-MM-dd HH:mm:ss"
	 */
	public static String formatDateTime(Date date) {

		String resultTime = "";
		try {
			resultTime = formatDateByFormat(date, "yyyy-MM-dd HH:mm:ss");
		} catch (Exception ex) {
			logger.error("DateUtil.formatDateTime Error" + ex);
			resultTime = "";
		}
		return resultTime;
	}

	/**
	 * 
	 * @Title: getReturnsFormTime
	 * @author biying
	 * @date 2015年8月21日 下午5:03:54 @ return 获取当前时间，精确到秒 "yyyy/MM/dd"
	 */
	public static String getReturnsFormTime(Date date) {

		String resultTime = "";
		try {
			resultTime = formatDateByFormat(date, "yyyy/MM/dd");
		} catch (Exception ex) {
			logger.error("DateUtil.getReturnsFormTime Error" + ex);
			resultTime = "";
		}
		return resultTime;
	}

	
	/**
	 * 
	 * @Title: parseDate
	 * @author biying
	 * @date 2015年8月21日 下午5:12:28
	 * @Description: 利用SimpleDateFormat实现日志转换
	 * @param strFormat
	 *            日期格式"yyyy-MM-dd HH:mm:ss"
	 * @param dateValue
	 *            时间 "2013-06-01 00:01:01"
	 *            例如:seDate("yyyy/MM/dd HH:mm:ss","2014/09/08 00:00:00")
	 * 
	 */
	public static Date parseDate(String strFormat, String dateValue)
			throws ParseException {

		if (strFormat.isEmpty() || dateValue.isEmpty()) {
			return null;
		}
		Date dateResult;
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(strFormat);
			dateResult = dateFormat.parse(dateValue);
		} catch (Exception ex) {
			dateResult = null;
			logger.error("DateUtil.parseDate" + ex);
		}
		return dateResult;
	}

	/**
	 * 
	 * @Title: getEndTimeAfterHours
	 * @author biying
	 * @date 2015年8月21日 下午5:20:08
	 * @Description: 获取当前时间后的多少小时后的时间
	 * @param hours
	 *            小时
	 */
	public static String getEndTimeAfterHours(int hours) {

		String resultTime = "";
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.HOUR, hours);
			resultTime = formatDateTime(calendar.getTime());
		} catch (Exception ex) {
			resultTime = "";
			logger.error("DateUtil.getEndTimeAfterHours" + ex);
		}
		return resultTime;
	}

	/**
	 * 
	 * @Title: getEndTimeAfterDays
	 * @author biying
	 * @date 2015年8月21日 下午5:30:09
	 * @Description: 获取多少天后的时间
	 * @param days
	 * @throws
	 */
	public static String getEndTimeAfterDays(int days) {

		String resultTime = "";
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.DAY_OF_MONTH, days);
			resultTime = formatDateTime(calendar.getTime());
		} catch (Exception ex) {
			resultTime = "";
			logger.error("DateUtil.getEndTimeAfterDays" + ex);
		}
		return resultTime;
	}

	/**
	 * 
	 * @Title: getNextDay
	 * @author biying
	 * @date 2015年8月21日 下午5:37:27
	 * @Description: 获取指定日期的下一天
	 * @param datainfo
	 *            日期，例如2014-5-25 例如:getNextDay("2013-09-24")
	 */
	public static String getNextDay(String datainfo) throws ParseException {
		String resultTime = "";
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(parseDate("yyyy-MM-dd", datainfo));
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			resultTime = formatDate(calendar.getTime());
		} catch (Exception ex) {
			resultTime = "";
			logger.error("DateUtil.getNextDay" + ex);
		}
		return resultTime;
	}
	
	public static void main(String[] args) {
//		System.out.println(DateUtil.compareDate(DateUtil.getCurrDate("yyyyMMdd"),
//				 DateUtil.subMonth("20150230", 6)));
	}
}
