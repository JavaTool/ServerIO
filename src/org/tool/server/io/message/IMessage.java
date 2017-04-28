package org.tool.server.io.message;

public interface IMessage {
	
	int getMessageId();
	
	int getSerial();
	
	byte[] toByteArray();
	
	long getReceiveTime();

}
