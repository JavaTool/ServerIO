package com.fanxing.server.shutdown;

import java.util.Queue;

import com.google.common.collect.Lists;

public class DefaultShutdownExecutor implements ShutdownExecutor {
	
	private final Queue<IShutdown> shutdowns;
	
	public DefaultShutdownExecutor() {
		shutdowns = Lists.newLinkedList();
	}

	@Override
	public void shutdown() {
		while (shutdowns.size() > 0) {
			shutdowns.poll().shutdown();
		}
	}

	@Override
	public void addShutdown(IShutdown shutdown) {
		shutdowns.add(shutdown);
	}

}
