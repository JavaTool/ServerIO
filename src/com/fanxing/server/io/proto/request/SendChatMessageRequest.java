package com.fanxing.server.io.proto.request;

import com.fanxing.server.io.proto.Request;
import com.fanxing.server.io.dispatch.ISender;
import com.fanxing.server.io.dispatch.IContent;
import com.fanxing.server.io.proto.protocol.LegionProtos.*;

/**
 * This is a auto make java file, so do not modify me.
 * @author fuhuiyuan
 */
public class SendChatMessageRequest extends Request {

	private CS_SendChatMessage cS_SendChatMessage;

	public SendChatMessageRequest(String ip, String receiveMessageId, String sessionId, ISender sender, CS_SendChatMessage cS_SendChatMessage) {
		super(ip, receiveMessageId, sessionId, sender);
		this.cS_SendChatMessage = cS_SendChatMessage;

	}
	public SendChatMessageRequest(Request request, CS_SendChatMessage cS_SendChatMessage) {
		super(request);
		this.cS_SendChatMessage = cS_SendChatMessage;
	}

	public SendChatMessageRequest(IContent content, CS_SendChatMessage cS_SendChatMessage) {
		super(content);
		this.cS_SendChatMessage = cS_SendChatMessage;
	}

	/**
	 * 
	 * @return	消息
	 */
	public String getMessage() {
		return cS_SendChatMessage.getMessage();
	}

	public CS_SendChatMessage getCS_SendChatMessage() {
		return cS_SendChatMessage;
	}

	@Override
	public byte[] getByteArray() {
		return cS_SendChatMessage.toByteArray();
	}

}
