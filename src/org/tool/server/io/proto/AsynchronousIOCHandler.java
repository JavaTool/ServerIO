package org.tool.server.io.proto;

import org.tool.server.io.message.IMessageIdTransform;
import org.tool.server.thread.AsynchronousMessageProcessorFactory;
import org.tool.server.thread.IMessagePackage;
import org.tool.server.thread.IMessagePackageHandler;
import org.tool.server.thread.IMessageProcessorFactory;

public final class AsynchronousIOCHandler extends BasedIOCHandler {
	
	private static final int LIMIT = 1000;
	
	private static final int EACH_COUNT = 10;

	public AsynchronousIOCHandler(IMessageIdTransform messageIdTransform) {
		super(messageIdTransform);
	}

	@Override
	protected IMessageProcessorFactory<IMessagePackage> createMessageProcessorFactory(IMessagePackageHandler handler) {
		return new AsynchronousMessageProcessorFactory(LIMIT, EACH_COUNT, handler);
	}

}
