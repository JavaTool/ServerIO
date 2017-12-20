package org.tool.server.io.message;

import static java.lang.System.currentTimeMillis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tool.server.io.dispatch.ISender;
import org.tool.server.io.proto.IErrorHandler;

import com.alibaba.fastjson.JSONObject;

public final class MessageSender implements IMessageSender {
	
	private static final byte[] EMPTY_DATAS = new byte[0];
	
	private static final Logger log = LoggerFactory.getLogger(MessageSender.class);
	
	private static final String LOG_SEND = "Send message[{}({})], serial[{}], size[{}], use {} ms.";
	
	private final ISender sender;
	
	private final IErrorHandler errorHandler;
	
	public MessageSender(ISender sender, IErrorHandler errorHandler) {
		this.sender = sender;
		this.errorHandler = errorHandler;
	}

	@Override
	public void send(IMessage message) {
		send(message.toByteArray(), message.getMessageId(), message.getSerial(), message.getReceiveTime());
		log.info(LOG_SEND, message.getClass().getSimpleName(), JSONObject.toJSONString(message));
	}

	@Override
	public String getSessionId() {
		return sender.getSessionId();
	}

	@Override
	public void send(int messageId, int serial, long receiveTime) {
		send(EMPTY_DATAS, messageId, serial, receiveTime);
	}
	
	private void send(byte[] datas, int messageId, int serial, long receiveTime) {
		try {
			sender.send(datas, serial, messageId, receiveTime <= 0 ? 0 : (currentTimeMillis() - receiveTime));
		} catch (Exception e) {
			log.error("", e);
		}
	}

	@Override
	public void sendError(int messageId, int serial, String error) {
		send(errorHandler.createErrorResponse(messageId, serial, error));
	}

}
