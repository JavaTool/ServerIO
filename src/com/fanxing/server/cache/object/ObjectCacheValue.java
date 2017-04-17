package com.fanxing.server.cache.object;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.fanxing.server.cache.ICacheValue;
import com.fanxing.server.coder.ICoder;

public class ObjectCacheValue<K, V, S> extends CoderCache<S> implements ICacheValue<K, V> {
	
	private final ICacheValue<S, S> cacheValue;
	
	private final Class<V> vclz;
	
	public ObjectCacheValue(ICacheValue<S, S> cacheValue, ICoder<S> coder) {
		this(cacheValue, coder, null);
	}
	
	public ObjectCacheValue(ICacheValue<S, S> cacheValue, ICoder<S> coder, Class<V> vclz) {
		super(coder);
		this.cacheValue = cacheValue;
		this.vclz = vclz;
	}

	@Override
	public void append(K key, V value) {
		cacheValue.append(serializa(key), serializa(value));
	}

	@Override
	public long decr(K key, long decrement) {
		return cacheValue.decr(serializa(key), decrement);
	}

	@Override
	public V get(K key) {
		return deserializa(cacheValue.get(serializa(key)), vclz);
	}

	@Override
	public List<V> multiGet(Object... keys) {
		return deserializa(cacheValue.multiGet(serializa(keys)), vclz);
	}

	@Override
	public long incr(K key, long increment) {
		return cacheValue.incr(serializa(key), increment);
	}

	@Override
	public void set(K key, V value) {
		cacheValue.set(serializa(key), serializa(value));
	}

	@Override
	public boolean xSet(K key, V value, boolean exists, long time, TimeUnit timeUnit) {
		return cacheValue.xSet(serializa(key), serializa(value), exists, time, timeUnit);
	}

	@Override
	public void multiSet(Map<K, V> map) {
		cacheValue.multiSet(serializa(map));
	}

}
