package com.fanxing.server.cache.object;

import static com.google.common.collect.Maps.newHashMap;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.fanxing.server.cache.ICache;

public abstract class ExpireStringKeyObjectCacheValue<F, V> extends ExpireObjectCacheValue<String, F, V> {
	
	private final String preKey;

	public ExpireStringKeyObjectCacheValue(ICache<String, F, V> cache, String preKey) {
		super(cache);
		this.preKey = preKey;
	}
	
	protected String makeKey(String key) {
		return preKey + key;
	}

	@Override
	public void append(String key, V value) {
		super.append(makeKey(key), value);
	}

	@Override
	public long decr(String key, long decrement) {
		return super.decr(makeKey(key), decrement);
	}

	@Override
	public V get(String key) {
		return super.get(makeKey(key));
	}

	@Override
	public List<V> multiGet(Object... keys) {
		for (int i = 0;i < keys.length;i++) {
			keys[i] = makeKey(keys[i].toString());
		}
		return super.multiGet(keys);
	}

	@Override
	public long incr(String key, long increment) {
		return super.incr(makeKey(key), increment);
	}

	@Override
	public void set(String key, V value) {
		super.set(makeKey(key), value);
	}

	@Override
	public boolean xSet(String key, V value, boolean exists, long time, TimeUnit timeUnit) {
		return super.xSet(makeKey(key), value, exists, time, timeUnit);
	}

	@Override
	public void multiSet(Map<String, V> map) {
		Map<String, V> keys = newHashMap();
		map.forEach((k, v) -> keys.put(makeKey(k), v));
		super.multiSet(keys);
	}

	@Override
	public Set<String> keys(String pattern) {
		return super.keys(makeKey(pattern));
	}

	@Override
	public void delete(Object... keys) {
		for (int i = 0;i < keys.length;i++) {
			keys[i] = makeKey(keys[i].toString());
		}
		super.delete(keys);
	}

	@Override
	public long ttl(String key) {
		return super.ttl(makeKey(key));
	}

}
