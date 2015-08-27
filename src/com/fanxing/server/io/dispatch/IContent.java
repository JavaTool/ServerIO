package com.fanxing.server.io.dispatch;

public interface IContent {
	
	String getSessionId();
	
	String getMessageId();
	
	String getIp();
	
	byte[] getDatas();
	
	ISender getSender();

}
