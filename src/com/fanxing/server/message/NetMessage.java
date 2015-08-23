package com.fanxing.server.message;

import io.netty.channel.Channel;

public class NetMessage implements INetMessage {
	
	private final String messageId;
	
	private final byte[] datas;
	
	private final String ip;
	
	private final String sessionId;
	
	private final Channel channel;
	
	public NetMessage(String messageId, byte[] datas, String ip, String sessionId, Channel channel) {
		this.messageId = messageId;
		this.datas = datas;
		this.ip = ip;
		this.sessionId = sessionId;
		this.channel = channel;
	}

	@Override
	public String getMessageId() {
		return messageId;
	}

	@Override
	public byte[] getDatas() {
		return datas;
	}

	@Override
	public String getIP() {
		return ip;
	}

	@Override
	public String getSessionId() {
		return sessionId;
	}

	@Override
	public Channel getChannel() {
		return channel;
	}

}
