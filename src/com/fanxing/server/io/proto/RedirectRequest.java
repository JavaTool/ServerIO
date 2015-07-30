package com.fanxing.server.io.proto;

import javax.servlet.http.HttpSession;

/**
 * 重定向请求
 * @author 	fuhuiyuan
 */
public class RedirectRequest extends Request {
	
	/**消息数据*/
	private final byte[] data;

	public RedirectRequest(String ip, String receiveMessageId, HttpSession session, byte[] data) {
		super(ip, receiveMessageId, session);
		this.data = data;
	}

	@Override
	public byte[] getByteArray() {
		return data;
	}

}
