package org.tool.server.thread;

import java.util.List;

import com.google.common.base.Function;

public final class AsynchronousMessageProcessorFactory implements IMessageProcessorFactory<IMessagePackage> {
	
	private static final int LIMIT = 4000;
	
	private static final int EACH_COUNT = 2000;
	
	private final int msgLimit;
	
	private final IMessagePackageHandler handler;
	
	private final int eachLoopProcessCount;
	
	private final Function<String, List<Runnable>> listSupplier;
	
	public AsynchronousMessageProcessorFactory(IMessagePackageHandler handler, Function<String, List<Runnable>> listSupplier) {
		this(LIMIT, EACH_COUNT, handler, listSupplier);
	}
	
	public AsynchronousMessageProcessorFactory(
			int msgLimit, 
			int eachLoopProcessCount, 
			IMessagePackageHandler handler, 
			Function<String, List<Runnable>> listSupplier) {
		this.msgLimit = msgLimit;
		this.handler = handler;
		this.eachLoopProcessCount = eachLoopProcessCount;
		this.listSupplier = listSupplier;
	}

	@Override
	public IMessageProcessor<IMessagePackage> create(String name) {
		AsynchronousMessageProcessor messageProcessor = new AsynchronousMessageProcessor(msgLimit, handler, name, listSupplier.apply(name));
		messageProcessor.setEachLoopProcessCount(eachLoopProcessCount);
		messageProcessor.startAsync().awaitRunning();
		return messageProcessor;
	}

}
