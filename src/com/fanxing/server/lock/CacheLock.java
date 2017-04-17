package com.fanxing.server.lock;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

import java.util.concurrent.Callable;

import com.fanxing.server.cache.ICache;

public class CacheLock extends SimpleLock<ICache<String, String, String>> {
	
	private final long lockTime;
	
	public CacheLock(long lockTime) {
		this.lockTime = lockTime;
	}

	@Override
	public <V> V work(ICache<String, String, String> t, long waitTime, Callable<V> call) throws Exception {
		long totalTime = 0;
		while (true) {
			if (tryLock(t)) {
				try {
					return call.call();
				} finally {
					t.key().delete(getName());
				}
			} else {
				totalTime = wait(waitTime, totalTime);
			}
		}
	}

	@Override
	public <V> V work(ICache<String, String, String> t, Callable<V> call) throws Exception {
		if (tryLock(t)) {
			try {
				return call.call();
			} finally {
				t.key().delete(getName());
			}
		} else {
			return null;
		}
	}
	
	private boolean tryLock(ICache<String, String, String> t) {
		String key = getClass().getSimpleName() + "_" + getName();
		return t.value().xSet(key, key, false, lockTime, MILLISECONDS);
	}

}
