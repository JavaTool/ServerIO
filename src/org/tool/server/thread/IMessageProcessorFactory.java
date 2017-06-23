package org.tool.server.thread;

public interface IMessageProcessorFactory<T> {
	
	IMessageProcessor<T> create();

}
