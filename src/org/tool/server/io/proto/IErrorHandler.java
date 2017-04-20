package org.tool.server.io.proto;

public interface IErrorHandler {
	
	IMessage createErrorResponse(Request request, String error) throws Exception;

}
