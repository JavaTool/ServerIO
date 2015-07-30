package com.fanxing.server.asynchronous;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 异步对象阻塞管理器
 * @author 	fuhuiyuan
 * @param 	<T>
 * 			对象类型
 */
public class Asynchronous<T> implements Runnable {
	
	private final Logger log;
	/**阻塞队列*/
	private final BlockingQueue<T> queue;
	
	private final BlockingExecuter<T> callback;
	
	public Asynchronous(BlockingExecuter<T> callback) {
		log = LoggerFactory.getLogger(Asynchronous.class);
		this.callback = callback;
		queue = new LinkedBlockingQueue<T>();
	}

	@Override
	public void run() {
		while (true) {
			try {
				callback.setBlockingObject(queue.take());
			} catch (Exception e) {
				log.error("", e);
			}
		}
	}
	
	/**
	 * 添加一个对象
	 * @param 	object
	 * 			对象
	 */
	public void add(T object) {
		queue.add(object);
	}

}
