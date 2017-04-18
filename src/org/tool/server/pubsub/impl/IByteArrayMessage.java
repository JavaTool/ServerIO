package org.tool.server.pubsub.impl;

public interface IByteArrayMessage {
	
	String getIp();
	
	int getMessageId();
	
	byte[] getBytes();

}
