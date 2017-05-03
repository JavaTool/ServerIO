package org.tool.server.io.dispatch;

import java.util.Collection;

import org.tool.server.io.dispatch.ISender;

public interface SenderThreadPool {
	
	String allocation(ISender sender);
	
	void disconnect(String session);
	
	ISender getSender(String session);
	
	Collection<ISender> getAllSender();

}
