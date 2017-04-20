package org.tool.server.io.netty.client;

import org.tool.server.io.INetClient;
import org.tool.server.io.dispatch.IContentFactory;
import org.tool.server.io.dispatch.IContentHandler;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class NettyClient implements INetClient {
	
    protected EventLoopGroup group;
    
	protected Bootstrap bootstrap;
	
	protected Channel socketChannel;
	
	protected final NettyClientHandler nettyClientHandler;
	
	public NettyClient(final IContentHandler contentHandler, final IContentFactory contentFactory) {
		nettyClientHandler = createNettyClientHandler(contentHandler, contentFactory);
	}
	
	protected NettyClientHandler createNettyClientHandler(IContentHandler contentHandler, IContentFactory contentFactory) {
		return new NettyClientHandler(contentHandler, contentFactory);
	}
	
	@Override
	public void send(final byte[] data) {
		ByteBuf clientMessage = Unpooled.buffer(data.length);
		clientMessage.writeInt(data.length);
	    clientMessage.writeBytes(data);
    	socketChannel.writeAndFlush(clientMessage);
	}

	@Override
	public void close() {
		if (group != null) {
			group.shutdownGracefully();
		}
	}

	@Override
	public void connect(String ip, int port) throws Exception {
		group = new NioEventLoopGroup();
		bootstrap = new Bootstrap();
		bootstrap.group(group).channel(NioSocketChannel.class).option(ChannelOption.SO_KEEPALIVE, true).option(ChannelOption.TCP_NODELAY, true);
//		bootstrap.remoteAddress(host, port);
		bootstrap.handler(new ChannelInitializer<SocketChannel>() {
  		  
	        @Override
	        protected void initChannel(SocketChannel ch) throws Exception {
	        	ChannelPipeline pipeline = ch.pipeline();
	    		// 粘包处理
	    		pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
				// 业务逻辑处理
				pipeline.addLast("Handler", nettyClientHandler);
	        }
        
    	});
    	// 发起异步链接操作
    	ChannelFuture channelFuture = bootstrap.connect(ip, port).sync();
    	if (channelFuture.isSuccess()) {
    		socketChannel = channelFuture.channel();
    	}
	}

}