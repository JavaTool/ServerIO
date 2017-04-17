package com.fanxing.server.io.netty.server.http;

import com.fanxing.server.io.dispatch.ISender;

public interface INettyHttpSession {
	
	int getContentLength();
	
	String getId();
	
	int getMessageId();
	
	boolean isKeepAlive();
	
	ISender getSender();

}
