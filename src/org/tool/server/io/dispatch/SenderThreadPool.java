package org.tool.server.io.dispatch;

import java.util.Collection;

import org.tool.server.io.message.IMessageSender;

public interface SenderThreadPool {
	
	String allocation(IMessageSender sender);
	
	void disconnect(String session);
	
	IMessageSender getSender(String session);
	
	Collection<IMessageSender> getAllSender();

}
