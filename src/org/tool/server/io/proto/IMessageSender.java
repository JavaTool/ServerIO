package org.tool.server.io.proto;

public interface IMessageSender {
	
	void send(IMessage message);
	
	void send(int messageid);
	
	String getSessionId();
	
	<X> X getAttribute(String key, Class<X> clz);
	
	<X, Y extends X> void setAttribute(String key, Class<X> clz, Y value);

}
