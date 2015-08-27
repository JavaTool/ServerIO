package com.fanxing.server.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式工具
 * @author	fuhuiyuan
 */
public abstract class RegexUtil {
	
	/**邮箱格式*/
	private static final String MAIL = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
	/**手机号格式*/
	private static final String MOBILE_NUMBER = "^(((13[0-9])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$";
	
	/**
	 * 匹配
	 * @param 	regex
	 * 			正则表达式
	 * @param 	str
	 * 			字符串
	 * @return	匹配结果
	 */
	public static boolean check(String regex, String str) {
		Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
	}
	
	/**
	 * 检查邮箱格式
	 * @param 	email
	 * 			邮箱
	 * @return	是否正确
	 */
	public static boolean checkEmail(String email) {
        return check(MAIL, email);
	}
	
	/**
	 * 检查手机号格式
	 * @param 	mobileNumber
	 * 			手机号
	 * @return	是否正确
	 */
	public static boolean checkMobileNumber(String mobileNumber) {
		return check(MOBILE_NUMBER, mobileNumber);
	}

}
