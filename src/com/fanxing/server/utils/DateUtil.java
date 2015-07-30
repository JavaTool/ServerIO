package com.fanxing.server.utils;

import java.text.DateFormat;
import java.util.Date;

/**
 * 日期工具
 * @author	fuhuiyuan
 */
public class DateUtil {
	
	/**格式化工具*/
	private static DateFormat dateFormat = DateFormat.getDateInstance();
	
	public static String formatDate(Date date) {
		return dateFormat.format(date);
	}

}
