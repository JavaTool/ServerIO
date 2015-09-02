package com.fanxing.server.io;

import java.util.concurrent.ScheduledExecutorService;

/**
 * 任务调度器
 * @author 	fuhuiyuan
 */
public interface Scheduler {
	
	/**
	 * 调度
	 * @param 	executorService
	 * 			执行器服务
	 */
	void scheduled(ScheduledExecutorService executorService);

}
