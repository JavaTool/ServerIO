package org.tool.server.io.netty.server.http;

import org.tool.server.io.dispatch.ISender;

public interface INettyHttpSession {
	
	int getContentLength();
	
	String getId();
	
	int getMessageId();
	
	boolean isKeepAlive();
	
	ISender getSender();

}
