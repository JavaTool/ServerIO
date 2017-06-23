package org.tool.server.thread;

public final class BaseMessageProcessorFactory implements IMessageProcessorFactory<IMessagePackage> {
	
	private static final int LIMIT = 1000;
	
	private static final int EACH_COUNT = 10;
	
	private final int msgLimit;
	
	private final IMessagePackageHandler handler;
	
	private final int eachLoopProcessCount;
	
	public BaseMessageProcessorFactory(IMessagePackageHandler handler) {
		this(LIMIT, EACH_COUNT, handler);
	}
	
	public BaseMessageProcessorFactory(int msgLimit, int eachLoopProcessCount, IMessagePackageHandler handler) {
		this.msgLimit = msgLimit;
		this.handler = handler;
		this.eachLoopProcessCount = eachLoopProcessCount;
	}

	@Override
	public IMessageProcessor<IMessagePackage> create() {
		BaseMessageProcessor messageProcessor = new BaseMessageProcessor(msgLimit, handler);
		messageProcessor.setEachLoopProcessCount(eachLoopProcessCount);
		messageProcessor.startAsync().awaitRunning();
		return messageProcessor;
	}

}
