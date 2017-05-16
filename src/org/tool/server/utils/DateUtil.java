package org.tool.server.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * 日期工具
 * @author	fuhuiyuan
 */
public final class DateUtil {
	
	public static final int DAY_HOURS = 24;
	
	public static final int HOUR_MINTUES = 60;
	
	public static final int MINTUE_SECONDS = 60;
	
	public static final int HOUR_SECONDS = HOUR_MINTUES * MINTUE_SECONDS;
	
	public static final int SECOND_MILLIS = 1000;
	
	public static final int DAY_SECONDS = DAY_HOURS * HOUR_MINTUES * MINTUE_SECONDS;
	
	public static final int DAY_MILLIS = DAY_SECONDS * SECOND_MILLIS;
	/**格式化工具*/
	private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private static final DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
	
	private static byte DAY_REFRESH_CLOCK = 3;
	/**
	 * 时间状态：未开始
	 */
	public static final byte TIMESTATE_NOT_START = 0;
	/**
	 * 时间状态：进行中
	 */
	public static final byte TIMESTATE_HAPPENING = 1;
	/**
	 * 时间状态：已结束
	 */
	public static final byte TIMESTATE_CLOSED = 2;
	
	/**
	 * 时间类型：永久
	 */
	public static final int DATETYPE_ENDLESS = -1;
	/**
	 * 时间类型：从服务器开启时计时
	 */
	public static final int DATETYPE_SERVERSTART = 1;
	/**
	 * 时间类型：按自然时间计时
	 */
	public static final int DATETYPE_REALTIME = 2;
	/**
	 * 时间类型：每日固定时间
	 */
	public static final int DATETYPE_FIXEDTIME = 3;
	/**
	 * 时间类型：从创建角色时计时
	 */
	public static final int DATETYPE_ROLECREATE = 4;
	
	private static final String NULL_DATE_FORMAT = "null";
	
	private DateUtil() {}
	
	public static String formatDate(Date date) {
		return date == null ? NULL_DATE_FORMAT : dateFormat.format(date);
	}
	
	public static Date parseDate(String dateStr){
		try {
			return NULL_DATE_FORMAT.equals(dateStr) || dateStr == null ? null : dateFormat.parse(dateStr);
		} catch (ParseException e) {
			return null;
		}
	}
	
	public static Date parseDate(String dateStr, DateFormat format){
		if(format == null)
			return null;
		try {
			return format.parse(dateStr);
		} catch (ParseException e) {
			return null;
		}
	}
	
	public static int parseToDayMillis(String dateStr) {
		String[] infos = dateStr.split(":");
		return (Integer.parseInt(infos[0]) * HOUR_SECONDS + Integer.parseInt(infos[1]) * MINTUE_SECONDS + Integer.parseInt(infos[2])) * SECOND_MILLIS;
	}
	
	public static int parseNowToDayMillis() {
		DateTime time = DateTime.now();
		return time.getMillisOfDay();
	}
	
	public static byte getDayRefreshClock(){
		return DAY_REFRESH_CLOCK;
	}
	
	public static void main(String[] args) {
//		System.out.println(formatDate(getDateOfDayRefresh()));
//		Date date = new Date(DateTime.now().toDate().getTime() - 300000000);
//		System.out.println(getDays(date));
		System.out.println(checkTimeState(3, 0, 0, null, "12:30:00", "18:00:00"));
	}
	
	public static int getMilliSecondStartOfTheNextDay() {
		DateTime time = DateTime.now();
        return DAY_MILLIS - time.getMillisOfDay();
    }
	
	public static long getMilliSecondStartOfThisDay() {
		DateTime time = DateTime.now();
		return time.toDate().getTime() - time.getMillisOfDay();
	}
	
	public static int getMilliSecondStart(byte clock) {
		DateTime time = DateTime.now();
		int clockMills = clock * HOUR_MINTUES * MINTUE_SECONDS * SECOND_MILLIS;
		if (time.getHourOfDay() < clock) { // 今天
			return clockMills - time.getMillisOfDay();
		} else { // 明天
			return getMilliSecondStartOfTheNextDay() + clockMills;
		}
	}
	
	public static int getMilliSecondStart(DateTime dateTime, byte clock) {
		int clockMills = clock * HOUR_MINTUES * MINTUE_SECONDS * SECOND_MILLIS;
		if (dateTime.getHourOfDay() < clock) { // 今天
			return clockMills - dateTime.getMillisOfDay();
		} else { // 明天
			return DAY_MILLIS - dateTime.getMillisOfDay() + clockMills;
		}
	}
	
	public static void setDayRefreshClock(byte clock) {
		DAY_REFRESH_CLOCK = clock;
	}
	
	public static int getMilliSecondStartOfDayRefresh() {
		return getMilliSecondStart(DAY_REFRESH_CLOCK);
	}
	
	public static int getMilliSecondStartOfDayRefresh(DateTime dateTime) {
		return getMilliSecondStart(dateTime, DAY_REFRESH_CLOCK);
	}
	
	public static int getSecondOfDayRefresh() {
		return DAY_REFRESH_CLOCK * HOUR_MINTUES * MINTUE_SECONDS;
	}
	
	public static int getDayOfWeek() {
		return getDayOfWeek(DateTime.now());
	}
	
	public static int getDayOfWeek(DateTime time) {
		return time.getDayOfWeek();
	}
	
	public static int getHourOfDay() {
		return DateTime.now().getHourOfDay();
	}
	
	public static int getMintueOfHour() {
		return DateTime.now().getMinuteOfHour();
	}
	
	public static int getHourOfDay(Date date) {
		DateTime dateTime = new DateTime(date.getTime());
		return dateTime.getHourOfDay();
	}
	
	public static int getMintueOfHour(Date date) {
		DateTime dateTime = new DateTime(date.getTime());
		return dateTime.getMinuteOfHour();
	}
	
	public static int getSecondOfDay() {
		return DateTime.now().getSecondOfDay();
	}
	
	public static int getMillisSecondOfDay() {
		return DateTime.now().getMillisOfDay();
	}
	
	public static Date getDateOfDayRefresh() {
		int secondStart = getMilliSecondStart(DAY_REFRESH_CLOCK);
		DateTime dateTime = DateTime.now();
		dateTime = dateTime.plusMillis(secondStart);
		dateTime = dateTime.withMillisOfSecond(0);
		dateTime = dateTime.withSecondOfMinute(0);
		dateTime = dateTime.withMinuteOfHour(0);
		return dateTime.toDate();
	}
	
	public static long toMilliTime(long time, TimeUnit timeUnit) {
		switch (timeUnit) {
		case DAYS : 
			return time * DAY_SECONDS * SECOND_MILLIS;
		case HOURS : 
			return time * HOUR_SECONDS * SECOND_MILLIS;
		case MINUTES : 
			return time * MINTUE_SECONDS * SECOND_MILLIS;
		case SECONDS : 
			return time * SECOND_MILLIS;
		case MILLISECONDS : 
			return time;
		default :
			throw new RuntimeException("Unsupport time unit : " + timeUnit);
		}
	}
	
	public static byte checkTimeState(int timeType, int serverStartTime, int serverCloseTime, DateTime startDateTime, String startTime, String closeTime){
		switch(timeType){
		case DATETYPE_ENDLESS:
			return TIMESTATE_HAPPENING;
		case DATETYPE_SERVERSTART: // 从服务器开启之时计时
		case DATETYPE_ROLECREATE: //按玩家创建角色时间计时
			if(startDateTime != null && serverStartTime >= 0){
				 DateTime startDt = startDateTime;
				 if(serverStartTime > 0){
					 startDt = startDt.plusDays(serverStartTime);
				 }
				 DateTime closeDt = serverCloseTime < 0 ? null : startDateTime.plusDays(serverCloseTime);
				 if(startDt.isAfterNow()){
					 return TIMESTATE_NOT_START;
				 }
				 if(closeDt!=null && closeDt.isBeforeNow()){
					 return TIMESTATE_CLOSED;
				 }
				 return TIMESTATE_HAPPENING;
			}
			return timeType == 1 ? TIMESTATE_CLOSED : TIMESTATE_NOT_START;
		case DATETYPE_REALTIME: // 按自然时间计时
			Date startDate = startTime.equals("-1") ? null : parseDate(startTime);
			Date closeDate = closeTime.equals("-1") ? null : parseDate(closeTime);
			Date curDate = new Date();
			if(startDate != null && startDate.after(curDate)){
				return TIMESTATE_NOT_START;
			}
			if(closeDate != null && closeDate.before(curDate)){
				return TIMESTATE_CLOSED;
			}
			return TIMESTATE_HAPPENING;
		case DATETYPE_FIXEDTIME: // 每日固定时间
			DateTime dateTime = DateTime.now();
			String timePre = dateTime.getYear() + "-" + dateTime.getMonthOfYear() + "-" + dateTime.getDayOfMonth() + " ";
			startTime = timePre + startTime;
			closeTime = timePre + closeTime;
			if (DateTime.parse(startTime, formatter).isAfterNow())	 {
				return TIMESTATE_NOT_START;
			}
			if (DateTime.parse(closeTime, formatter).isBeforeNow())	 {
				return TIMESTATE_CLOSED;
			}
			return TIMESTATE_HAPPENING;
		}
		return TIMESTATE_CLOSED;
	}
	
	public static int getCountdown(int timeType, int serverStartTime, int serverCloseTime, DateTime startDateTime, String startTime, String closeTime){
		switch(timeType){
		case DATETYPE_ENDLESS:
			return -1;
		case DATETYPE_SERVERSTART:
		case DATETYPE_ROLECREATE:
			if(startDateTime != null && serverStartTime >= 0){
				DateTime closeDt = serverCloseTime < 0 ? null : startDateTime.plusDays(serverCloseTime);
				if(closeDt==null){
					return -1;
				}else{
					int value = (int)((closeDt.getMillis() - System.currentTimeMillis())/1000L);
					return value < 0 ? 0 : value;
				}
			}
			return 0;
		case DATETYPE_REALTIME:
			Date startDate = startTime.equals("-1") ? null : parseDate(startTime);
			Date closeDate = closeTime.equals("-1") ? null : parseDate(closeTime);
			Date curDate = new Date();
			if(startDate != null && startDate.after(curDate)){
				return 0;
			}
			if(closeDate != null && closeDate.before(curDate)){
				return 0;
			}
			return (int)((closeDate.getTime() - curDate.getTime())/1000L);
		default:
			return 0;
		}
	}
	
	public static int getTotalDays(int timeType, int serverStartTime, int serverCloseTime, String startTime, String closeTime){
		switch(timeType){
		case DATETYPE_ENDLESS:
			return -1;
		case DATETYPE_SERVERSTART:
		case DATETYPE_ROLECREATE:
			return serverCloseTime - serverStartTime;
		case DATETYPE_REALTIME:
			if(closeTime.equals("-1")){
				return -1;
			}
			LocalDate start = startTime.equals("-1") ? new LocalDate() : new LocalDate(parseDate(startTime).getTime());
			LocalDate end = new LocalDate(parseDate(closeTime).getTime());
			return Days.daysBetween(start, end).getDays();
		default:
			return -1;
		}
	}
	
	public static int getDays(Date date) {
		DateTime dateTime = new DateTime(date.getTime());
		LocalDate start = new LocalDate(dateTime.getYear(), dateTime.getMonthOfYear(), dateTime.getDayOfMonth());
		LocalDate end = new LocalDate();
		return Days.daysBetween(start, end).getDays();
	}
	
	public static int getDays(DateTime dateTime){
		return Days.daysBetween(dateTime, DateTime.now()).getDays();
	}
	
	public static int milliSecondToUpSecond(long ms) {
		return (int) (ms / SECOND_MILLIS + 1);
	}
	
	public static int getMinuteOfHour() {
		return DateTime.now().getMinuteOfHour();
	}
	
}
