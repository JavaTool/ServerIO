package org.tool.server.io.message;

public interface IMessageIdTransform {
	
	int transform(String messageId);
	
	String transform(int messageId);

}
