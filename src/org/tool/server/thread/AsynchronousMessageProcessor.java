package org.tool.server.thread;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.google.common.util.concurrent.AbstractScheduledService;

final class AsynchronousMessageProcessor extends AbstractScheduledService implements IMessageProcessor<IMessagePackage> {
	
	private static final Logger log = LoggerFactory.getLogger(AsynchronousMessageProcessor.class);
	
	private static final long DELAY = 50;
	
	private final int msgLimit;
	
	private final BlockingQueue<IMessagePackage> msgQueue;
	
	private final IMessagePackageHandler handler;
	
	private final String name;
	
	private final List<Runnable> runList;
	
	private int eachLoopProcessCount;
	
	public AsynchronousMessageProcessor(int msgLimit, IMessagePackageHandler handler) {
		this(msgLimit, handler, AsynchronousMessageProcessor.class.getName());
	}
	
	public AsynchronousMessageProcessor(int msgLimit, IMessagePackageHandler handler, String name) {
		this(msgLimit, handler, name, Lists.newLinkedList());
	}
	
	public AsynchronousMessageProcessor(int msgLimit, IMessagePackageHandler handler, String name, List<Runnable> runList) {
		this.msgLimit = msgLimit;
		this.handler = handler;
		this.name = "MessageProcessor_" + name;
		this.runList = runList;
		log.info("Add runnables {}.", Joiner.on(';').join(runList).toString());
		msgQueue = Queues.newLinkedBlockingQueue(msgLimit);
	}

	@Override
	protected String serviceName() {
		return name;
	}

	@Override
	public void put(IMessagePackage msg) {
		if (msg == null) {
			return;
		}
		
		if (msgQueue.size() >= msgLimit) {
			log.error("Full message queue, drop {0}.", msg.getMessageId());
			return;
		}
		
		try {
			msgQueue.add(msg);
		} catch (Exception e) {
			log.error("", e);
		}
	}

	public void setEachLoopProcessCount(int eachLoopProcessCount) {
		this.eachLoopProcessCount = eachLoopProcessCount;
	}

	@Override
	protected void runOneIteration() throws Exception {
		// process messages
		int processCount = 0;
		while (!msgQueue.isEmpty()) {
			IMessagePackage msg = msgQueue.poll();
			if (msg == null) {
				break;
			}
			processCount++;
			handler.handle(msg);

			if (processCount >= eachLoopProcessCount) {
				break;
			}
		}
		// run runnables
		for (Runnable runnable : runList) {
			try {
				runnable.run();
			} catch (Exception e) {
				log.error("", e);
			}
		}
	}

	@Override
	protected Scheduler scheduler() {
		return Scheduler.newFixedDelaySchedule(0, DELAY, TimeUnit.MILLISECONDS);
	}

}
