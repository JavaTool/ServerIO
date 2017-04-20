package org.tool.server.io.proto;

import org.tool.server.io.dispatch.IContent;

public interface IErrorHandler {
	
	IMessage createErrorResponse(IContent content, String error) throws Exception;

}
