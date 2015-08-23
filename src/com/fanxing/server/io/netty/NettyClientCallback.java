package com.fanxing.server.io.netty;

public interface NettyClientCallback {
	
	void callback(byte[] data) throws Exception;

}
