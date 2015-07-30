package com.fanxing.server.io;

/**
 * HTTP反馈信息
 * @author	fuhuiyuan
 */
public class HttpBackInfo {
	
	/**反馈的数据*/
	private final byte[] data;
	/**连接状态*/
	private final int status;
	/**消息id*/
	private final String messageId;
	
	public HttpBackInfo(byte[] data, int status, String messageId) {
		this.data = data;
		this.status = status;
		this.messageId = messageId;
	}

	public byte[] getData() {
		return data;
	}

	public int getStatus() {
		return status;
	}

	public String getMessageId() {
		return messageId;
	}

}
