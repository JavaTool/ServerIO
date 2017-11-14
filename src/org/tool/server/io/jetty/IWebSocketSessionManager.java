package org.tool.server.io.jetty;

import java.util.Collection;

import org.eclipse.jetty.websocket.api.Session;
import org.tool.server.io.dispatch.ISender;

public interface IWebSocketSessionManager {
	
	String onWebSocketConnect(Session session);
	
	void onWebSocketClose(String sessionId, int statusCode, String reason);
	
	ISender getSender(String sessionId);
	
	Collection<ISender> getAllSenders();

}
