package org.tool.server.cache.lazy;

import java.util.concurrent.TimeUnit;

import org.tool.server.cache.ICacheValue;

public class LazyCacheValue<V> implements ILazyCacheValue<V> {
	
	private final ICacheValue<String, V> cacheValue;
	
	private final IKey key;
	
	public LazyCacheValue(ICacheValue<String, V> cacheValue, IKey key) {
		this.cacheValue = cacheValue;
		this.key = key;
	}

	@Override
	public void append(V value) {
		cacheValue.append(key.getKey(), value);
	}

	@Override
	public long decr(long decrement) {
		return cacheValue.decr(key.getKey(), decrement);
	}

	@Override
	public V get() {
		return cacheValue.get(key.getKey());
	}

	@Override
	public long incr(long increment) {
		return cacheValue.incr(key.getKey(), increment);
	}

	@Override
	public void set(V value) {
		cacheValue.set(key.getKey(), value);
	}

	@Override
	public void set(V value, boolean exists, long time, TimeUnit timeUnit) {
		cacheValue.xSet(key.getKey(), value, exists, time, timeUnit);
	}

}
