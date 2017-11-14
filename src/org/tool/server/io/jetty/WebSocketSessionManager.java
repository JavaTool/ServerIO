package org.tool.server.io.jetty;

import java.util.Collection;
import java.util.concurrent.ConcurrentMap;

import org.eclipse.jetty.websocket.api.Session;
import org.tool.server.io.dispatch.ISender;

import com.google.common.collect.Maps;

public class WebSocketSessionManager implements IWebSocketSessionManager {
	
	private final ConcurrentMap<String, ISender> senders;
	
	public WebSocketSessionManager() {
		this(Maps.newConcurrentMap());
	}
	
	public WebSocketSessionManager(ConcurrentMap<String, ISender> senders) {
		this.senders = senders;
	}

	@Override
	public String onWebSocketConnect(Session session) {
		WebSocketSender sender = new WebSocketSender(session);
		String sessionId = sender.getSessionId();
		senders.put(sessionId, sender);
		return sessionId;
	}

	@Override
	public void onWebSocketClose(String sessionId, int statusCode, String reason) {
		senders.remove(sessionId);
	}

	@Override
	public ISender getSender(String sessionId) {
		return senders.get(sessionId);
	}

	@Override
	public Collection<ISender> getAllSenders() {
		return senders.values();
	}

}
