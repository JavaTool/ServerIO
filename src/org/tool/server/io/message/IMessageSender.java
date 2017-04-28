package org.tool.server.io.message;

public interface IMessageSender {
	
	/**
	 * session id name.
	 */
	String SESSION_ID = "SESSION_ID";
	
	void send(IMessage message);
	
	void send(int messageId, int serial, long receiveTime);
	
	String getSessionId();
	
	<X> X getAttribute(String key, Class<X> clz);
	
	<X, Y extends X> void setAttribute(String key, Class<X> clz, Y value);

}
