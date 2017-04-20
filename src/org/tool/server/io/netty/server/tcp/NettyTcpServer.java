package org.tool.server.io.netty.server.tcp;

import static io.netty.channel.ChannelOption.SO_BACKLOG;
import static io.netty.handler.logging.LogLevel.INFO;
import static java.util.concurrent.TimeUnit.SECONDS;

import java.net.InetSocketAddress;

import org.tool.server.io.INetServer;
import org.tool.server.io.dispatch.IContentFactory;
import org.tool.server.io.dispatch.IDispatchManager;
import org.tool.server.io.netty.server.INettyServerConfig;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * TCP协议接收器
 * @author 	fuhuiyuan
 */
public class NettyTcpServer implements INetServer {
	
	private final IDispatchManager dispatchManager;
	
	private final IContentFactory nettyContentFactory;
	
	private final int parentThreadNum;
	
	private final int childThreadNum;
	
	private final int port;
	
	private final int soBacklog;
	
	private final String ip;
	
	private final long readerIdleTime;
	
	private final long writerIdleTime;
	
	private final long allIdleTime;
	
	private ServerBootstrap serverBootstrap;

	public NettyTcpServer(INettyServerConfig config) {
		dispatchManager = config.getDispatchManager();
		nettyContentFactory = config.getNettyContentFactory();
		parentThreadNum = config.getParentThreadNum();
		childThreadNum = config.getChildThreadNum();
		soBacklog = config.getSoBacklog();
		port = config.getPort();
		ip = config.getIp();
		readerIdleTime = config.getReaderIdleTime();
		writerIdleTime = config.getWriterIdleTime();
		allIdleTime = config.getAllIdleTime();
	}

	@Override
	public void bind() throws Exception {
		EventLoopGroup bossGroup = new NioEventLoopGroup(parentThreadNum);
		EventLoopGroup workerGroup = new NioEventLoopGroup(childThreadNum);
		try {
			serverBootstrap = new ServerBootstrap();
			serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).option(SO_BACKLOG, soBacklog)
			.handler(new LoggingHandler(INFO));
			// 注册TCP处理流水线
			serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ChannelPipeline pipeline = ch.pipeline();
					// 读信道空闲,写信道空闲,读，写信道空闲
					pipeline.addLast("idleStateHandler", new IdleStateHandler(readerIdleTime, writerIdleTime, allIdleTime, SECONDS));
					// 粘包处理
					pipeline.addLast("Decoder", new LengthFieldBasedFrameDecoder(Short.MAX_VALUE, 0, 2, 0, 2));
					// 业务逻辑处理
					pipeline.addLast("Handler", new NettyTcpHandler(dispatchManager, nettyContentFactory));
				}
				
			});
			serverBootstrap.bind(new InetSocketAddress(ip, port)).sync().channel().closeFuture().sync();
		} finally {
			shutdown();
		}
	}

	@Override
	public void shutdown() {
		serverBootstrap.group().shutdownGracefully();
		serverBootstrap.childGroup().shutdownGracefully();
	}

	@Override
	public int getPort() {
		return port;
	}

}