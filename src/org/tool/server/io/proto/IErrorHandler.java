package org.tool.server.io.proto;

public interface IErrorHandler {
	
	Response createErrorResponse(Request request, String error) throws Exception;

}