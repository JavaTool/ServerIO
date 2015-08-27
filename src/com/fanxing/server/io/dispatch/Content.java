package com.fanxing.server.io.dispatch;

public class Content implements IContent {
	
	private final String sessionId;
	
	private final String messageId;
	
	private final String ip;
	
	private final byte[] datas;
	
	private final ISender sender;
	
	public Content(String sessionId, String messageId, String ip, byte[] datas, ISender sender) {
		this.sessionId = sessionId;
		this.messageId = messageId;
		this.ip = ip;
		this.datas = datas;
		this.sender = sender;
	}

	@Override
	public String getSessionId() {
		return sessionId;
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
	public ISender getSender() {
		return sender;
	}

	@Override
	public String getIp() {
		return ip;
	}

}
