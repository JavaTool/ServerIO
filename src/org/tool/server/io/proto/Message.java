package org.tool.server.io.proto;

public abstract class Message implements IMessage {
	
	private final int messageId;
	
	public Message(int messageId) {
		this.messageId = messageId;
	}

	@Override
	public int getMessageId() {
		return messageId;
	}

}
