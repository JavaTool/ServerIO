package org.tool.server.io.netty.client;

import org.tool.server.anthenticate.IEncrypt;
import org.tool.server.io.message.IMessageHandler;
import org.tool.server.io.message.IMessageIdTransform;
import org.tool.server.io.netty.NettyTcpSender;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

@Sharable
public class NettyClientHandler extends ChannelInboundHandlerAdapter {
	
	private final IMessageHandler contentHandler;
	
	private final IMessageIdTransform messageIdTransform;
	
	private final IEncrypt encrypt;

	public NettyClientHandler(IMessageHandler contentHandler, IMessageIdTransform messageIdTransform, IEncrypt encrypt) {
		this.contentHandler = contentHandler;
		this.messageIdTransform = messageIdTransform;
		this.encrypt = encrypt;
	}
	  
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
	    ByteBuf buf = (ByteBuf) msg;
	    if (check(buf)) {
		    Channel channel = ctx.channel();
		    byte[] data = new byte[buf.readableBytes()];
		    buf.readBytes(data);
		    contentHandler.handle(encrypt.deEncrypt(data), new NettyTcpSender(channel, messageIdTransform, encrypt));
	    }
	}
	  
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
	    ctx.close();
	}
	
	private boolean check(ByteBuf msg) {
		return true;
	}
	
}
