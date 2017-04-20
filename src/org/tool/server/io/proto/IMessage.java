package org.tool.server.io.proto;

public interface IMessage {
	
	int getMessageId();
	
	byte[] toByteArray();

}
