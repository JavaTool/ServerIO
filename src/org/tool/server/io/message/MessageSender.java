package org.tool.server.io.message;

import static java.lang.System.currentTimeMillis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tool.server.io.dispatch.ISender;

public final class MessageSender implements IMessageSender {
	
	private static final byte[] EMPTY_DATAS = new byte[0];
	
	private static final Logger log = LoggerFactory.getLogger(MessageSender.class);
	
	private final ISender sender;
	
	public MessageSender(ISender sender) {
		this.sender = sender;
	}

	@Override
	public void send(IMessage message) {
		send(message.toByteArray(), message.getSerial(), message.getMessageId(), message.getReceiveTime());
	}

	@Override
	public String getSessionId() {
		return sender.getSessionId();
	}

	@Override
	public void send(int messageId, int serial, long receiveTime) {
		send(EMPTY_DATAS, serial, messageId, receiveTime);
	}
	
	private void send(byte[] datas, int serial, int messageId, long receiveTime) {
		try {
			sender.send(datas, serial, messageId, receiveTime <= 0 ? 0 : (currentTimeMillis() - receiveTime));
		} catch (Exception e) {
			log.error("", e);
		}
	}

	@Override
	public <X> X getAttribute(String key, Class<X> clz) {
		return sender.getAttribute(key, clz);
	}

	@Override
	public <X, Y extends X> void setAttribute(String key, Class<X> clz, Y value) {
		sender.setAttribute(key, clz, value);
	}

}
