package org.tool.server.io.message;

public interface IMessageSender {
	
	void send(IMessage message);
	
	void send(int messageId, int serial, long receiveTime);
	
	String getSessionId();

}
