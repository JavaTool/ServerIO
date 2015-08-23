package com.fanxing.server.io.proto;

import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

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
	/**登录密钥*/
	private final String sessionId;
	/**环境*/
	private final ServletContext servletContext;
	/**数据映射*/
	private final Map<String, Object> attributes;
	/**Netty客户端频道*/
	private final Channel channel;
	
	public Request(String ip, String receiveMessageId, ServletContext servletContext, String sessionId, Channel channel) {
		this.ip = ip;
		this.receiveMessageId = receiveMessageId;
		this.servletContext = servletContext;
		this.sessionId = sessionId;
		this.channel = channel;
		attributes = new HashMap<String, Object>();
	}
	
	public Request(Request request) {
		this(request.ip, request.receiveMessageId, request.servletContext, request.sessionId, request.channel);
	}

	public String getIp() {
		return ip;
	}

	public String getReceiveMessageId() {
		return receiveMessageId;
	}

	public ServletContext getServletContext() {
		return servletContext;
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
	
	public Channel getChannel() {
		return channel;
	}

}
