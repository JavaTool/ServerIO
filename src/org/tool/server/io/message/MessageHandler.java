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
			// 解析
			int messageId = dis.readInt();
			int serial = dis.readInt(); // 客户端的协议序列号，如果是需要返回消息的协议，则该值原样返回
			log.info("Net {} received : [MessageId : {}] [SessionId : {}] [Ip : {}]", sender.getNetType(), messageId, sender.getSessionId(), sender.getIp());
			byte[] datas = new byte[dis.available()];
			dis.read(datas);
			// 获取或创建消息发送器
			IMessageSender messageSender = sender.getAttribute(MESSAGE_SENDER_NAME, IMessageSender.class);
			if (messageSender == null) {
				messageSender = new MessageSender(sender);
				sender.setAttribute(MESSAGE_SENDER_NAME, IMessageSender.class, messageSender);
			}
			// 处理消息
			handle(messageId, serial, datas, messageSender);
		}
	}
	
	protected abstract void handle(int messageId, int serial, byte[] datas, IMessageSender messageSender) throws Exception;

}
