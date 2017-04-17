package com.fanxing.server.io.netty.client;

import java.io.DataOutputStream;

import com.fanxing.server.anthenticate.IDataAnthenticate;
import com.fanxing.server.io.dispatch.IContent;
import com.fanxing.server.io.dispatch.IContentFactory;
import com.fanxing.server.io.dispatch.IContentHandler;
import com.fanxing.server.io.netty.NettyTcpSender;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

@Sharable
public class NettyClientHandler extends ChannelInboundHandlerAdapter {
	
	private final IContentHandler contentHandler;
	
	private final IContentFactory contentFactory;
	
	private final int anthencateLength;

	public NettyClientHandler(IContentHandler contentHandler, IContentFactory contentFactory) {
		this.contentHandler = contentHandler;
		this.contentFactory = contentFactory;
		IDataAnthenticate<byte[], DataOutputStream> dataAnthenticate = contentFactory.getDataAnthenticate();
		anthencateLength =  dataAnthenticate == null ? 0 : dataAnthenticate.getAnthenticateLength();
	}
	  
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
	    ByteBuf buf = (ByteBuf) msg;
	    if (check(buf)) {
		    Channel channel = ctx.channel();
		    byte[] data = new byte[buf.readableBytes()];
		    buf.readBytes(data);
		    String ip = channel.remoteAddress().toString();
		    IContent content = contentFactory.createContent(ip, data, new NettyTcpSender(channel, contentFactory.getDataAnthenticate()));
		    contentHandler.handle(content);
	    }
	}
	  
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
	    ctx.close();
	}
	
	private boolean check(ByteBuf msg) {
		if (anthencateLength > 0) {
			byte[] data = new byte[anthencateLength];
			msg.readBytes(data);
			return contentFactory.getDataAnthenticate().read(data);
		} else {
			return true;
		}
	}
	
}
