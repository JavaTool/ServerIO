package org.tool.server.io.proto;

public interface IMessageSender {
	
	void send(IMessage message) throws Exception;
	
	String getSessionId();

}
