package org.tool.server.io.message;

import static java.lang.System.currentTimeMillis;

public abstract class Message implements IMessage {
	
	private int serial;
	
	private int threadId;
	
	private long receiveTime;
	
	private final int messageId;
	
	public Message(int messageId) {
		this(messageId, 0);
	}
	
	public Message(int messageId, int serial) {
		this.messageId = messageId;
		setSerial(serial);
		setReceiveTime(currentTimeMillis());
	}

	@Override
	public int getMessageId() {
		return messageId;
	}

	@Override
	public int getSerial() {
		return serial;
	}

	@Override
	public long getReceiveTime() {
		return receiveTime;
	}

	@Override
	public IMessage setSerial(int serial) {
		this.serial = serial;
		return this;
	}
	
	public void setReceiveTime(long receiveTime) {
		this.receiveTime = receiveTime;
	}

	@Override
	public int getThreadId() {
		return threadId;
	}

	@Override
	public IMessage setThreadId(int threadId) {
		this.threadId = threadId;
		return this;
	}

}
