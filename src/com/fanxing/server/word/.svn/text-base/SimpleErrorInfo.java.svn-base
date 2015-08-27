package com.fanxing.server.word;

/**
 * 简单的错误信息
 * @author 	fuhuiyuan
 */
public class SimpleErrorInfo implements ErrorInfo {
	
	/**错误码*/
	private int errorCode;
	/**错误信息*/
	private String errorMsg;

	@Override
	public void setError(int errorCode, String errorMsg) {
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	@Override
	public void setError(String errorMsg) {
		setError(NORMAL, errorMsg);
	}

	@Override
	public boolean hasError() {
		return errorMsg != null && errorMsg.length() > 0;
	}
	
	/**
	 * 获得错误码
	 * @return	错误码
	 */
	public int getErrorCode() {
		return errorCode;
	}

	/**
	 * 获得错误信息
	 * @return	错误信息
	 */
	public String getErrorMsg() {
		return errorMsg;
	}

}
