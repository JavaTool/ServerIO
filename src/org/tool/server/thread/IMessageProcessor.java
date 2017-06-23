package org.tool.server.thread;

public interface IMessageProcessor<T> {
	
	void put(T msg);

}
