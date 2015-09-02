package com.fanxing.server.io.netty;

import com.fanxing.server.io.dispatch.IContent;
import com.fanxing.server.io.dispatch.IContentHandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

@Sharable
public class NettyClientHandler extends ChannelInboundHandlerAdapter {
	
	private final IContentHandler contentHandler;
	
	private final NettyClientContentFactory contentFactory;

	public NettyClientHandler(IContentHandler contentHandler, NettyClientContentFactory contentFactory) {
		this.contentHandler = contentHandler;
		this.contentFactory = contentFactory;
	}
	  
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {  
	    ByteBuf buf = (ByteBuf) msg;
	    Channel channel = ctx.channel();
	    IContent content = contentFactory.createContent(channel, buf, new NettyTcpSender(channel));
	    contentHandler.handle(content);
	}
	  
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
	    ctx.close();
	}
	
}
