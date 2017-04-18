package com.fanxing.server.io.proto;

import com.fanxing.server.io.proto.protocol.MessageIdProto.MessageId;

public class DefaultMessageIdTransform implements IMessageIdTransform {

	@Override
	public int transform(String messageId) {
		return MessageId.valueOf(messageId).getNumber();
	}

	@Override
	public String transform(int messageId) {
		return MessageId.valueOf(messageId).name();
	}

}
