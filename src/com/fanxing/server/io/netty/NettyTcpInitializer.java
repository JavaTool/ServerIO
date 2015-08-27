package com.fanxing.server.io.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import com.fanxing.server.io.dispatch.IContentHandler;

/**
 * TCP处理管线
 * @author 	fuhuiyuan
 */
public class NettyTcpInitializer extends ChannelInitializer<SocketChannel> {
	
	private final IContentHandler contentHandler;
	
	private final INettyContentFactory contentFactory;
	
	public NettyTcpInitializer(IContentHandler contentHandler, INettyContentFactory contentFactory) {
		this.contentHandler = contentHandler;
		this.contentFactory = contentFactory;
	}

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		// 粘包处理
		pipeline.addLast("Decoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
		pipeline.addLast("Handler", new NettyTcpHandler(contentHandler, contentFactory));
	}

}
