package com.fanxing.server.io.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;

import com.fanxing.server.io.dispatch.IContent;
import com.fanxing.server.io.dispatch.ISender;

public interface INettyContentFactory {
	
	IContent createContent(Channel channel, ByteBuf content, INettyHttpSession httpSession);
	
	IContent createContent(Channel channel, ByteBuf content, ISender sender);

}
