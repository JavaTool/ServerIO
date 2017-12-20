package org.tool.server.io.jetty;

import javax.servlet.ServletContext;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketListener;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;

public class JettyWebSocketCreator implements WebSocketCreator {
	
	public static final String MANAGER_NAME = IWebSocketSessionManager.class.getSimpleName();

	@Override
	public Object createWebSocket(ServletUpgradeRequest req, ServletUpgradeResponse resp) {
		ServletContext servletContext = req.getHttpServletRequest().getServletContext();
		return new Listener((IWebSocketSessionManager) servletContext.getAttribute(MANAGER_NAME));
	}

	private static class Listener implements WebSocketListener {
		
		private final IWebSocketSessionManager webSocketSessionManager;
		
		private String sessionId;
		
		public Listener(IWebSocketSessionManager webSocketSessionManager) {
			this.webSocketSessionManager = webSocketSessionManager;
		}

		@Override
		public void onWebSocketBinary(byte[] payload, int offset, int len) {
			System.out.print("onWebSocketBinary : ");
			for (int i = offset, end = offset + len;i < end;i++) {
				System.out.print(payload[i] + " ");
			}
			System.out.println();
			try {
				webSocketSessionManager.getSender(sessionId).send(payload, 0, 0);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onWebSocketClose(int statusCode, String reason) {
			System.out.println("onWebSocketClose statusCode : " + statusCode + " ; reason : " + reason);
		}

		@Override
		public void onWebSocketConnect(Session session) {
			System.out.print("onWebSocketConnect ");
			if (session.isSecure()) {
				if (sessionId == null) {
					sessionId = webSocketSessionManager.onWebSocketConnect(session);
					System.out.println("sessionId : " + sessionId);
				} else {
					System.out.println("repeated listener.");
				}
			} else {
				System.out.println("Client is not a wss client.");
			}
		}

		@Override
		public void onWebSocketError(Throwable cause) {
			cause.printStackTrace();
		}

		@Override
		public void onWebSocketText(String message) {
			System.out.println("onWebSocketText : " + message);
			try {
				webSocketSessionManager.getSender(sessionId).send(message.getBytes(), 0, 0);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

}
