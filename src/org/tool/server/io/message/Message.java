package org.tool.server.io.message;

import static java.lang.System.currentTimeMillis;

public abstract class Message implements IMessage {
	
	private final int messageId;
	
	private final int serial;
	
	private final long receiveTime;
	
	public Message(int messageId) {
		this(messageId, 0);
	}
	
	public Message(int messageId, int serial) {
		this.messageId = messageId;
		this.serial = serial;
		receiveTime = currentTimeMillis();
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

}
