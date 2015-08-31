package com.fanxing.server.io;

import java.util.concurrent.ScheduledExecutorService;

public interface Scheduler {
	
	void scheduled(ScheduledExecutorService executorService);

}
