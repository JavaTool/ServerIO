package com.fanxing.server.io.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import com.fanxing.server.io.proto.MessageHandle;

/**
 * TCP处理管线
 * @author 	fuhuiyuan
 */
public class TCPInitializer extends ChannelInitializer<SocketChannel> {
	
	private final MessageHandle messageHandle;
	
	public TCPInitializer(MessageHandle messageHandle) {
		this.messageHandle = messageHandle;
	}

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		// 粘包处理
		pipeline.addLast("Decoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
		pipeline.addLast("Handler", new TCPHandler(messageHandle));
	}

}
