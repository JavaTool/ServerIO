package org.tool.server.io.message;

import static java.lang.System.currentTimeMillis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tool.server.io.dispatch.ISender;
import org.tool.server.io.proto.IErrorHandler;

public final class MessageSender implements IMessageSender {
	
	private static final byte[] EMPTY_DATAS = new byte[0];
	
	private static final Logger log = LoggerFactory.getLogger(MessageSender.class);
	
	private static final String LOG_SEND = "Use {} ms send message[{}] : {}.";
	
	private final ISender sender;
	
	private final IErrorHandler errorHandler;
	
	private final IMessageIdTransform messageIdTransform;
	
	public MessageSender(ISender sender, IErrorHandler errorHandler, IMessageIdTransform messageIdTransform) {
		this.sender = sender;
		this.errorHandler = errorHandler;
		this.messageIdTransform = messageIdTransform;
	}

	@Override
	public void send(IMessage message) {
		int messageId = message.getMessageId();
		send(message.toByteArray(), messageId, message.getSerial());
		logSend(messageId, message.toString(), message.getReceiveTime());
	}

	@Override
	public String getSessionId() {
		return sender.getSessionId();
	}

	@Override
	public void send(int messageId, int serial, long receiveTime) {
		send(EMPTY_DATAS, messageId, serial);
		logSend(messageId, String.format(LOG_SERIAL, serial), receiveTime);
	}
	
	private void logSend(int messageId, String content, long receiveTime) {
		long useTime = receiveTime <= 0 ? 0 : (currentTimeMillis() - receiveTime);
		log.info(LOG_SEND, useTime, messageIdTransform.transform(messageId), content);
	}
	
	private void send(byte[] datas, int messageId, int serial) {
		try {
			sender.send(datas, serial, messageId);
		} catch (Exception e) {
			log.error("", e);
		}
	}

	@Override
	public void sendError(int messageId, int serial, String error) {
		send(errorHandler.createErrorResponse(messageId, serial, error));
	}

	@Override
	public String getIp() {
		return sender.getIp();
	}

}
