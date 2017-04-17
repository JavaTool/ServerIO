package com.fanxing.server.cache.object;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.fanxing.server.cache.ICache;
import com.fanxing.server.cache.ICacheKey;
import com.fanxing.server.cache.ICacheValue;

public abstract class ExpireObjectCacheValue<K, F, V> implements ICacheValue<K, V> {
	
	private final ICacheValue<K, V> cacheValue;
	
	private final ICacheKey<K> cacheKey;

	public ExpireObjectCacheValue(ICache<K, F, V> cache) {
		cacheKey = cache.key();
		cacheValue = cache.value();
	}
	
	protected abstract long getExpireTime(K key);

	@Override
	public void append(K key, V value) {
		work(key, () -> cacheValue.append(key, value));
	}

	@Override
	public long decr(K key, long decrement) {
		return work(key, () -> {
			return cacheValue.decr(key, decrement);
		});
	}

	@Override
	public V get(K key) {
		return cacheValue.get(key);
	}

	@Override
	public List<V> multiGet(Object... keys) {
		return cacheValue.multiGet(keys);
	}

	@Override
	public long incr(K key, long increment) {
		return work(key, () -> {
			return cacheValue.incr(key, increment);
		});
	}

	@Override
	public void set(K key, V value) {
		work(key, () -> cacheValue.set(key, value));
	}

	@Override
	public boolean xSet(K key, V value, boolean exists, long time, TimeUnit timeUnit) {
		return work(key, () -> {
			return cacheValue.xSet(key, value, exists, time, timeUnit);
		});
	}

	@Override
	public void multiSet(Map<K, V> map) {
		map.forEach((k, v) -> set(k, v));
	}
	
	private <T> T work(K key, Work<T> call) {
		boolean isCache = cacheKey.exists(key);
		T ret = call.work();
		if (!isCache) {
			cacheKey.expire(key, getExpireTime(key), MILLISECONDS);
		}
		return ret;
	}
	
	private <T> void work(K key, VoidWork<T> call) {
		boolean isCache = cacheKey.exists(key);
		call.work();
		if (!isCache) {
			cacheKey.expire(key, getExpireTime(key), MILLISECONDS);
		}
	}
	
	private static interface Work<T> {
		
		T work();
		
	}
	
	private static interface VoidWork<T> {
		
		void work();
		
	}

	public Set<K> keys(K pattern) {
		return cacheKey.keys(pattern);
	}

	public void delete(Object... keys) {
		cacheKey.delete(keys);
	}
	
	public long ttl(K key) {
		return cacheKey.ttl(key);
	}

}
