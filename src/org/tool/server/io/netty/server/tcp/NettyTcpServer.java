package org.tool.server.io.netty.server.tcp;

import static java.util.concurrent.TimeUnit.SECONDS;

import org.tool.server.anthenticate.IEncrypt;
import org.tool.server.io.message.IConncetHandler;
import org.tool.server.io.message.IMessageIdTransform;
import org.tool.server.io.netty.server.INettyServerConfig;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * TCP协议接收器
 * @author 	fuhuiyuan
 */
public final class NettyTcpServer extends AbstractNettyTcpServer<INettyServerConfig> {

	public NettyTcpServer(INettyServerConfig config) {
		super(config);
	}

	@Override
	protected ChannelHandler createChildHandler(INettyServerConfig config) {
		final long readerIdleTime = config.getReaderIdleTime();
		final long writerIdleTime = config.getWriterIdleTime();
		final long allIdleTime = config.getAllIdleTime();
		final IConncetHandler messageHandler = config.getConncetHandler();
		final IMessageIdTransform messageIdTransform = config.getMessageIdTransform();
		final IEncrypt encrypt = config.getEncrypt();
		return new ChannelInitializer<SocketChannel>() {

			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ChannelPipeline pipeline = ch.pipeline();
				// 读信道空闲,写信道空闲,读，写信道空闲
				pipeline.addLast("idleStateHandler", new IdleStateHandler(readerIdleTime, writerIdleTime, allIdleTime, SECONDS));
				// 粘包处理
				pipeline.addLast("Decoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
				// 业务逻辑处理
				pipeline.addLast("Handler", new NettyTcpHandler(messageHandler, messageIdTransform, encrypt));
				log.info("Init channel done.");
			}
			
		};
	}

}
