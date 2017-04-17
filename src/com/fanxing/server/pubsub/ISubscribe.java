package com.fanxing.server.pubsub;

public interface ISubscribe {
	
	void onMessage(String channel, Object message);
	
	int getTimeout();

}
