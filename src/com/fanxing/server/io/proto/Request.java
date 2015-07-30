package com.fanxing.server.io.proto;

import javax.servlet.http.HttpSession;

/**
 * 请求信息
 * @author 	fuhuiyuan
 */
public class Request {
	
	private static final byte[] NULL_REQUEST = new byte[0];
	/**客户端地址*/
	private final String ip;
	/**请求信息的id*/
	private final String receiveMessageId;
	/**HTTP会话*/
	private final HttpSession session;
	
	public Request(String ip, String receiveMessageId, HttpSession session) {
		this.ip = ip;
		this.receiveMessageId = receiveMessageId;
		this.session = session;
	}
	
	public Request(Request request) {
		this(request.ip, request.receiveMessageId, request.session);
	}

	public String getIp() {
		return ip;
	}

	public String getReceiveMessageId() {
		return receiveMessageId;
	}

	public HttpSession getSession() {
		return session;
	}
	
	/**
	 * 获取消息数据
	 * @return	消息数据
	 */
	public byte[] getByteArray() {
		return NULL_REQUEST;
	}

}
