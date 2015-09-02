package com.fanxing.server.word;

import com.fanxing.server.word.auto.ErrorCode;

/**
 * 错误信息接收者
 * @author	fuhuiyuan
 */
public interface ErrorInfo extends ErrorCode {
	
	/**
	 * 设置错误内容
	 * @param 	errorCode
	 * 			错误码
	 * @param 	errorMsg
	 * 			错误消息
	 */
	void setError(int errorCode, String errorMsg);
	/**
	 * 设置错误内容，使用默认的errorCode
	 * @param 	errorMsg
	 * 			错误消息
	 */
	void setError(String errorMsg);
	/**
	 * 获取是否有错误消息
	 * @return	是否有错误消息
	 */
	boolean hasError();

}
