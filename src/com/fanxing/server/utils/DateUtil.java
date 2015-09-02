package com.fanxing.server.utils;

import static java.util.Calendar.MILLISECOND;
import static java.util.Calendar.SECOND;
import static java.util.Calendar.MINUTE;
import static java.util.Calendar.HOUR_OF_DAY;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具
 * @author	fuhuiyuan
 */
public class DateUtil {
	
	public static final int DAY_SECOND = 24 * 60 * 60;
	/**格式化工具*/
	private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	
	public static String formatDate(Date date) {
		return dateFormat.format(date);
	}
	
	public static void main(String[] args) {
		System.out.println(formatDate(new Date()));
	}
	
	public static int getSecondStartOfTheNextDay() {
        Calendar calendar = Calendar.getInstance();
        int ms = calendar.get(MILLISECOND) + calendar.get(SECOND) + calendar.get(MINUTE) + calendar.get(HOUR_OF_DAY);
        ms += calendar.get(SECOND) * 1000;
        ms += calendar.get(MINUTE) * 1000 * 60;
        ms += calendar.get(HOUR_OF_DAY) * 1000 * 60 * 60;
        return DAY_SECOND * 1000 - ms;
    }

}
