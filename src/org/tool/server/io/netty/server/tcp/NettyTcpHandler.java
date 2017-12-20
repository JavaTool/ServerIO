package org.tool.server.io.netty.server.tcp;

import org.tool.server.anthenticate.IEncrypt;
import org.tool.server.io.message.IConncetHandler;

import io.netty.buffer.ByteBuf;

final class NettyTcpHandler extends AbstractNettyTcpHandler {

	public NettyTcpHandler(IConncetHandler messageHandler, IEncrypt encrypt) {
		super(messageHandler, encrypt);
	}

	@Override
	protected boolean check(ByteBuf msg) {
		return true;
	}

}
