package com.fanxing.server.io.proto;

import io.netty.channel.Channel;

import javax.servlet.ServletContext;

/**
 * 重定向请求
 * @author 	fuhuiyuan
 */
public class RedirectRequest extends Request {
	
	/**消息数据*/
	private final byte[] data;

	public RedirectRequest(String ip, String receiveMessageId, ServletContext servletContext, String sessionId, byte[] data, Channel channel) {
		super(ip, receiveMessageId, servletContext, sessionId, channel);
		this.data = data;
	}

	@Override
	public byte[] getByteArray() {
		return data;
	}

}
