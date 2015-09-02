package com.fanxing.server.io.netty;

import com.fanxing.server.io.dispatch.ISender;

public interface INettyHttpSession {
	
	int getContentLength();
	
	String getId();
	
	String getMessageId();
	
	boolean isKeepAlive();
	
	ISender getSender();

}
