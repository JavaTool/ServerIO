package com.fanxing.server.message;

import io.netty.channel.Channel;

public interface INetMessage extends IMessage {
	
	String getIP();
	
	String getSessionId();
	
	Channel getChannel();

}
