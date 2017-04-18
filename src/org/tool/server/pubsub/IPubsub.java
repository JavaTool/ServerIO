package org.tool.server.pubsub;

public interface IPubsub<T> {
	
	void publish(String channel, Object message);
	
	void subscribe(T subscribe, String... channel);

}
