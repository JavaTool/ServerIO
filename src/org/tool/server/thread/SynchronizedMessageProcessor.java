package org.tool.server.thread;

final class SynchronizedMessageProcessor implements IMessageProcessor<IMessagePackage> {
	
	private final IMessagePackageHandler handler;
	
	public SynchronizedMessageProcessor(IMessagePackageHandler handler) {
		this.handler = handler;
	}

	@Override
	public synchronized void put(IMessagePackage msg) {
		handler.handle(msg);
	}

}
