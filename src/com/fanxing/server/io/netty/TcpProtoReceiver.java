package com.fanxing.server.io.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fanxing.server.io.proto.MessageHandle;

/**
 * TCP协议接收器
 * @author 	fuhuiyuan
 */
public class TcpProtoReceiver implements Runnable {
	
	private final Logger log;
	/**端口*/
	private int port;
	/**消息接收器*/
	private MessageHandle messageHandle;

	public TcpProtoReceiver(int port, MessageHandle messageHandle) {
		this.port = port;
		this.messageHandle = messageHandle;
		log = LoggerFactory.getLogger(TcpProtoReceiver.class);
	}

	/**
	 * TCP服务器启动函数
	 */
	private void bootStrap() throws Exception {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).option(ChannelOption.SO_BACKLOG, 100).handler(new LoggingHandler(LogLevel.INFO));
			// 注册TCP处理流水线
			b.childHandler(new TCPInitializer(messageHandle));
			// 开始监听
			log.info("TCP启动成功");
			b.bind(port).sync().channel().closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}

	@Override
	public void run() {
		try {
			bootStrap();
		} catch (Exception e) {
			log.error("[TCP StartUp Error]", e);
		}
	}

}
