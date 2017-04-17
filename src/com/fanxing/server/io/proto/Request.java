package com.fanxing.server.io.proto;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fanxing.server.io.dispatch.IContent;
import com.fanxing.server.io.dispatch.ISender;
import com.google.common.collect.Maps;

/**
 * 请求信息
 * @author 	fuhuiyuan
 */
public class Request {
	
	protected static final Logger log = LoggerFactory.getLogger(Request.class);
	
	private static final byte[] NULL_REQUEST = new byte[0];
	/**客户端地址*/
	private final String ip;
	/**请求信息的id*/
	private final int receiveMessageId;
	/**登录密钥*/
	private final String sessionId;
	/**数据映射*/
	private final Map<String, Object> attributes;
	/**数据发送器*/
	private final ISender sender;
	
	public Request(String ip, int receiveMessageId, String sessionId, ISender sender) {
		this.ip = ip;
		this.receiveMessageId = receiveMessageId;
		this.sessionId = sessionId == null ? null : new String(sessionId.getBytes());
		this.sender = sender;
		attributes = Maps.newHashMap();
	}
	
	public Request(Request request) {
		this(request.ip, request.receiveMessageId, request.sessionId, request.sender);
	}
	
	public Request(IContent content) {
		this(content.getIp(), content.getMessageId(), content.getSessionId(), content.getSender());
	}

	public String getRemoteIp() {
		return ip;
	}

	public int getReceiveMessageId() {
		return receiveMessageId;
	}
	
	public String getSessionId() {
		return sessionId;
	}
	
	/**
	 * 获取消息数据
	 * @return	消息数据
	 */
	public byte[] getByteArray() {
		return NULL_REQUEST;
	}
	
	public void setAttribute(String key, Object value) {
		attributes.put(key, value);
	}
	
	public Object getAttribute(String key) {
		return attributes.get(key);
	}
	
	public ISender getSender() {
		return sender;
	}

}
