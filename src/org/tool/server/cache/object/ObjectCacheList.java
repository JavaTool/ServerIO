package org.tool.server.cache.object;

import java.util.List;

import org.tool.server.cache.ICacheList;
import org.tool.server.coder.ICoder;

public class ObjectCacheList<K, V, S> extends CoderCache<S> implements ICacheList<K, V> {
	
	private final ICacheList<S, S> cacheValue;
	
	private final Class<V> vclz;

	public ObjectCacheList(ICacheList<S, S> cacheValue, ICoder<S> coder) {
		this(cacheValue, coder, null);
	}

	public ObjectCacheList(ICacheList<S, S> cacheValue, ICoder<S> coder, Class<V> vclz) {
		super(coder);
		this.cacheValue = cacheValue;
		this.vclz = vclz;
	}

	@Override
	public V headPop(K key) {
		return deserializa(cacheValue.headPop(serializa(key)), vclz);
	}

	@Override
	public void tailPush(K key, Object... objects) {
		cacheValue.tailPush(serializa(key), serializa(objects));
	}

	@Override
	public V get(K key, long index) {
		return deserializa(cacheValue.get(serializa(key), index), vclz);
	}

	@Override
	public long size(K key) {
		return cacheValue.size(serializa(key));
	}

	@Override
	public void trim(K key, long start, long end) {
		cacheValue.trim(serializa(key), start, end);
	}

	@Override
	public List<V> range(K key, long start, long end) {
		return deserializa(cacheValue.range(serializa(key), start, end), vclz);
	}

	@Override
	public void lrem(K key, V value) {
		cacheValue.lrem(serializa(key), serializa(value));
	}

	@Override
	public void insert(K key, long index, V value) {
		cacheValue.insert(serializa(key), index, serializa(value));
	}

	@Override
	public void set(K key, long index, V value) {
		cacheValue.set(serializa(key), index, serializa(value));
	}

}
