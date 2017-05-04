package org.tool.server.io.proto;

import org.tool.server.io.message.IMessage;

public interface IErrorHandler {
	
	IMessage createErrorResponse(int messageId, int serial, String error);

}
