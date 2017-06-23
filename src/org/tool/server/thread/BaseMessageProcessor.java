package org.tool.server.thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Queues;
import com.google.common.util.concurrent.AbstractScheduledService;

public final class BaseMessageProcessor extends AbstractScheduledService implements IMessageProcessor<IMessagePackage> {
	
	private static final Logger log = LoggerFactory.getLogger(BaseMessageProcessor.class);
	
	private static final long DELAY = 50;
	
	private final int msgLimit;
	
	private final BlockingQueue<IMessagePackage> msgQueue;
	
	private final IMessagePackageHandler handler;
	
	private int eachLoopProcessCount;
	
	public BaseMessageProcessor(int msgLimit, IMessagePackageHandler handler) {
		this.msgLimit = msgLimit;
		this.handler = handler;
		msgQueue = Queues.newLinkedBlockingQueue(msgLimit);
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
	}

	@Override
	protected Scheduler scheduler() {
		return Scheduler.newFixedDelaySchedule(0, DELAY, TimeUnit.MILLISECONDS);
	}

}
