package com.fanxing.server.cache.lazy;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fanxing.server.cache.ICacheHash;

public class LazyCacheHash<F, V> implements ILazyCacheHash<F, V> {
	
	private final ICacheHash<String, F, V> cacheHash;
	
	private final IKey key;
	
	public LazyCacheHash(ICacheHash<String, F, V> cacheHash, IKey key) {
		this.cacheHash = cacheHash;
		this.key = key;
	}

	@Override
	public void remove(Object... fields) {
		cacheHash.remove(key.getKey(), fields);
	}

	@Override
	public boolean contains(F field) {
		return cacheHash.contains(key.getKey(), field);
	}

	@Override
	public V get(F field) {
		return cacheHash.get(key.getKey(), field);
	}

	@Override
	public List<V> get(Object... fields) {
		return cacheHash.multiGet(key.getKey(), fields);
	}

	@Override
	public Map<F, V> getAll() {
		return cacheHash.getAll(key.getKey());
	}

	@Override
	public Set<F> fields() {
		return cacheHash.fields(key.getKey());
	}

	@Override
	public long size() {
		return cacheHash.size(key.getKey());
	}

	@Override
	public void set(Map<F, V> map) {
		cacheHash.multiSet(key.getKey(), map);
	}

	@Override
	public void set(F field, V value) {
		cacheHash.set(key.getKey(), field, value);
	}

	@Override
	public List<V> values() {
		return cacheHash.values(key.getKey());
	}

}
