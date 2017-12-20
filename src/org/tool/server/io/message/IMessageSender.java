package org.tool.server.io.message;

public interface IMessageSender {
	
	String LOG_SERIAL = "{\"serial\" : %d}";
	
	void send(IMessage message);
	
	void send(int messageId, int serial, long receiveTime);
	
	default void send(int messageId, long receiveTime) {
		send(messageId, 0, receiveTime);
	}
	
	default void send(int messageId) {
		send(messageId, 0, 0);
	}
	
	String getSessionId();
	
	String getIp();
	
	void sendError(int messageId, int serial, String error);

}
