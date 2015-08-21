package com.fanxing.server.io.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

@Sharable
public class NettyClientHandler extends ChannelInboundHandlerAdapter {
	
	private final NettyClientCallback callback;

	public NettyClientHandler(NettyClientCallback callback) {
		this.callback = callback;
	}
	  
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {  
	    ByteBuf buf = (ByteBuf) msg;
	    byte[] data = new byte[buf.readableBytes()];
	    buf.readBytes(data);
	    callback.callback(data);
	}
	  
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
	    ctx.close();
	}
	
}
