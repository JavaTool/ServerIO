package org.tool.server.thread;

public final class SynchronizedMessageProcessorFactory implements IMessageProcessorFactory<IMessagePackage> {
	
	private final IMessagePackageHandler handler;
	
	public SynchronizedMessageProcessorFactory(IMessagePackageHandler handler) {
		this.handler = handler;
	}

	@Override
	public IMessageProcessor<IMessagePackage> create(String name) {
		return new SynchronizedMessageProcessor(handler);
	}

}
