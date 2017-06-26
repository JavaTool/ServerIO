package org.tool.server.thread;

public final class SynchronizedMessageProcessorFactory implements IMessageProcessorFactory<IMessagePackage> {
	
	private final IMessagePackageHandler handler;
	
	public SynchronizedMessageProcessorFactory(IMessagePackageHandler handler) {
		this.handler = handler;
	}

	@Override
	public IMessageProcessor<IMessagePackage> create() {
		return new SynchronizedMessageProcessor(handler);
	}

}
