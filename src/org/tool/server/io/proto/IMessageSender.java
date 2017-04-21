package org.tool.server.io.proto;

public interface IMessageSender {
	
	void send(IMessage message);
	
	void send(int messageid);
	
	String getSessionId();
	
	<X> X getAttribute(String key, Class<X> clz);

}
