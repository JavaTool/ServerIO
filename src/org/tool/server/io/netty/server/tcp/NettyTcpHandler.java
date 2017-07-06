package org.tool.server.io.netty.server.tcp;

import org.tool.server.anthenticate.IEncrypt;
import org.tool.server.io.message.IConncetHandler;
import org.tool.server.io.message.IMessageIdTransform;

import io.netty.buffer.ByteBuf;

final class NettyTcpHandler extends AbstractNettyTcpHandler {

	public NettyTcpHandler(IConncetHandler messageHandler, IMessageIdTransform messageIdTransform, IEncrypt encrypt) {
		super(messageHandler, messageIdTransform, encrypt);
	}

	@Override
	protected boolean check(ByteBuf msg) {
		return true;
	}

}
