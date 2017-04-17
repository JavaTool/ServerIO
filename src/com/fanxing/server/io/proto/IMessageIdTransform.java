package com.fanxing.server.io.proto;

public interface IMessageIdTransform {
	
	int transform(String messageId);
	
	String transform(int messageId);

}
