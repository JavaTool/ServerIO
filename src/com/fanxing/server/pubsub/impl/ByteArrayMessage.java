package com.fanxing.server.pubsub.impl;

public class ByteArrayMessage implements IByteArrayMessage {
	
	private final byte[] datas;
	
	private final int messageId;
	
	private final String ip;
	
	public ByteArrayMessage(byte[] datas, int messageId, String ip) {
		this.ip = ip;
		this.datas = datas;
		this.messageId = messageId;
	}

	@Override
	public String getIp() {
		return ip;
	}

	@Override
	public int getMessageId() {
		return messageId;
	}

	@Override
	public byte[] getBytes() {
		return datas;
	}

}
