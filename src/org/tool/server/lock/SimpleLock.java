package org.tool.server.lock;

import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class SimpleLock<T> {
	
	protected static final Logger log = LoggerFactory.getLogger(SimpleLock.class);
	
	private static final int INTERVAL = 100;
	
	private boolean showLog;
	
	private String name;
	
	public abstract <V> V work(T t, long waitTime, Callable<V> call) throws Exception;
	
	public abstract <V> V work(T t, Callable<V> call) throws Exception;

	public boolean isShowLog() {
		return showLog;
	}

	public void setShowLog(boolean showLog) {
		this.showLog = showLog;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	protected void logLock() {
		if (isShowLog()) {
			log.info(getName() + " - locked!");
		}
	}
	
	protected void logUnlock() {
		if (isShowLog()) {
			log.info(getName() + " - unlocked!");
		}
	}
	
	protected long wait(long waitTime, long totalTime) throws Exception {
		Thread.sleep(INTERVAL);
		if (waitTime > 0) {
			totalTime += INTERVAL;
			if (totalTime >= waitTime) {
				throw new Exception("Wait lock time out " + totalTime + "/" + waitTime + ".");
			}
		}
		return totalTime;
	}

}
