package com.fanxing.server.io;

/**
 * 连接反馈信息
 * @author	fuhuiyuan
 */
public class ConnectBackInfo {

	/**反馈的数据*/
	private final byte[] data;
	/**消息id*/
	private final int messageId;
	
	public ConnectBackInfo(byte[] data, int messageId) {
		this.data = data;
		this.messageId = messageId;
	}

	public byte[] getData() {
		return data;
	}

	public int getMessageId() {
		return messageId;
	}
	
}
