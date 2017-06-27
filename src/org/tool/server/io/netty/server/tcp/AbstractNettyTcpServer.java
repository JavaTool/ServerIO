package org.tool.server.io.netty.server.tcp;

import static io.netty.channel.ChannelOption.SO_BACKLOG;

import java.net.InetSocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tool.server.io.INetServer;
import org.tool.server.io.netty.server.INettyServerConfig;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandler;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public abstract class AbstractNettyTcpServer<T extends INettyServerConfig> implements INetServer {
	
	protected static final Logger log = LoggerFactory.getLogger(AbstractNettyTcpServer.class);
	
	private final T config;
	
	private ServerBootstrap serverBootstrap;
	
	public AbstractNettyTcpServer(T config) {
		this.config = config;
	}

	@Override
	public final void bind() throws Exception {
		EventLoopGroup bossGroup = new NioEventLoopGroup(config.getParentThreadNum());
		EventLoopGroup workerGroup = new NioEventLoopGroup(config.getChildThreadNum());
		try {
			serverBootstrap = new ServerBootstrap();
			serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).option(SO_BACKLOG, config.getSoBacklog());
			// 注册TCP处理流水线
			serverBootstrap.childHandler(createChildHandler(config));
			String ip = config.getIp();
			int port = getPort();
			log.info("Bind {}:{}.", ip, port);
			serverBootstrap.bind(new InetSocketAddress(ip, port)).sync().channel().closeFuture().sync();
		} finally {
			shutdown();
		}
	}
	
	protected abstract ChannelHandler createChildHandler(T config);

	@Override
	public final void shutdown() {
		serverBootstrap.group().shutdownGracefully();
		serverBootstrap.childGroup().shutdownGracefully();
	}

	@Override
	public final int getPort() {
		return config.getPort();
	}

}
