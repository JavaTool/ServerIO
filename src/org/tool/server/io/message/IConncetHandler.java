package org.tool.server.io.message;

import org.tool.server.io.dispatch.ISender;

public interface IConncetHandler extends IMessageHandler {
	
	void discontected(ISender sender) throws Exception;

}
