package org.tool.server.io.jetty;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.nio.ByteBuffer;
import java.util.Map;

import org.eclipse.jetty.websocket.api.Session;
import org.tool.server.io.NetType;
import org.tool.server.io.dispatch.ISender;

import com.google.common.collect.Maps;

public class WebSocketSender implements ISender {
	
	private final Session session;
	
	private final String sessionId;
	
	private final Map<String, Object> attributes;
	
	public WebSocketSender(Session session, String sessionId) {
		this.session = session;
		this.sessionId = sessionId;
		attributes = Maps.newHashMap();
	}
	
	public WebSocketSender(Session session) {
		this(session, session.getUpgradeRequest().getSession().toString());
	}

	@Override
	public void send(byte[] datas, int serial, int messageId) throws Exception {
		ByteBuffer data = ByteBuffer.wrap(packageDatas(datas, serial, messageId));
		session.getRemote().sendBytes(data);
	}
	
	private static byte[] packageDatas(byte[] datas, int serial, int messageId) throws Exception {
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bout);
		dos.writeInt(messageId);
		dos.writeInt(serial); // 客户端的协议序列号，如果是需要返回消息的协议，则该值原样返回
		dos.write(datas);
		return bout.toByteArray();
	}

	@Override
	public <X, Y extends X> void setAttribute(String key, Class<X> clz, Y value) {
		attributes.put(key, value);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <X> X getAttribute(String key, Class<X> clz) {
		return (X) attributes.get(key);
	}

	@Override
	public NetType getNetType() {
		return NetType.WEB_SOCKET;
	}

	@Override
	public String getIp() {
		return session.getRemoteAddress().getHostString();
	}

	@Override
	public String getSessionId() {
		return sessionId;
	}

}
