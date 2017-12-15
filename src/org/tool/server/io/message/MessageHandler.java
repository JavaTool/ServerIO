package org.tool.server.io.message;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tool.server.io.dispatch.ISender;
import org.tool.server.io.proto.BasedIOCHandler;

public abstract class MessageHandler implements IMessageHandler {

	protected static final Logger log = LoggerFactory.getLogger(BasedIOCHandler.class);
	
	private static final String MESSAGE_SENDER_NAME = IMessageSender.class.getName();

	@Override
	public final void handle(byte[] bytes, ISender sender) throws Exception {
		try (DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes))) {
			// 解析消息
			int messageId = dis.readInt();
			int serial = dis.readInt(); // 客户端的协议序列号，如果是需要返回消息的协议，则该值原样返回
			byte[] datas = readAvailable(dis);
			logReceive(messageId, sender);
			// 处理消息
			handle(messageId, serial, datas, getMessageSender(sender));
		}
	}
	
	protected abstract void logReceive(int messageId, ISender sender);
	
	private static byte[] readAvailable(DataInputStream dis) throws Exception {
		byte[] datas = new byte[dis.available()];
		dis.read(datas);
		return datas;
	}
	
	private IMessageSender getMessageSender(ISender sender) {
		IMessageSender messageSender = sender.getAttribute(MESSAGE_SENDER_NAME, IMessageSender.class);
		if (messageSender == null) {
			messageSender = createMessageSender(sender);
			sender.setAttribute(MESSAGE_SENDER_NAME, IMessageSender.class, messageSender);
		}
		return messageSender;
	}
	
	protected abstract IMessageSender createMessageSender(ISender sender);
	
	protected abstract void handle(int messageId, int serial, byte[] datas, IMessageSender messageSender) throws Exception;

}
