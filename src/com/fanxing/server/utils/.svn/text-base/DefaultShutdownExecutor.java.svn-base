package com.fanxing.server.utils;

import java.util.LinkedList;
import java.util.Queue;

public class DefaultShutdownExecutor implements ShutdownExecutor {
	
	private final Queue<Shutdown> shutdowns;
	
	public DefaultShutdownExecutor() {
		shutdowns = new LinkedList<Shutdown>();
	}

	@Override
	public void shutdown() {
		while (shutdowns.size() > 0) {
			shutdowns.poll().shutdown();
		}
	}

	@Override
	public void addShutdown(Shutdown shutdown) {
		shutdowns.add(shutdown);
	}

}
