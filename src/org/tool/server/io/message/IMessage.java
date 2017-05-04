package org.tool.server.io.message;

public interface IMessage {
	
	int getMessageId();
	
	int getSerial();
	
	void setSerial(int serial);
	
	byte[] toByteArray();
	
	long getReceiveTime();

}
