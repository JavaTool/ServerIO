package org.tool.server.io.proto;

import org.tool.server.io.message.IMessageIdTransform;
import org.tool.server.thread.IMessagePackage;
import org.tool.server.thread.IMessagePackageHandler;
import org.tool.server.thread.IMessageProcessorFactory;
import org.tool.server.thread.SynchronizedMessageProcessorFactory;

public final class SynchronizedIOCHanlder extends BasedIOCHandler {

	public SynchronizedIOCHanlder(IMessageIdTransform messageIdTransform) {
		super(messageIdTransform);
	}

	@Override
	protected IMessageProcessorFactory<IMessagePackage> createMessageProcessorFactory(IMessagePackageHandler handler) {
		return new SynchronizedMessageProcessorFactory(handler);
	}

}
