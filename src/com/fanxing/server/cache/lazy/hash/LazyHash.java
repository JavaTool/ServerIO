package com.fanxing.server.cache.lazy.hash;

import java.util.Map;
import java.util.Set;

import com.fanxing.server.cache.lazy.ILazyCache;
import com.fanxing.server.cache.lazy.ILazyCacheHash;
import com.fanxing.server.cache.manager.ICachePlatform;

public class LazyHash<F, V> implements ILazyHash<F, V> {
	
	protected final ILazyCache<F, V> cache;
	
	protected final ILazyCacheHash<F, V> hash;
	
	protected final Class<V> vclz;
	
	public LazyHash(ICachePlatform cachePlatform, Class<F> fclz, Class<V> vclz, String preKey) {
		this.vclz = vclz;
		cache = cachePlatform.createLazyCache(fclz, vclz, preKey);
		hash = cache.hash();
	}

	@Override
	public void hashSet(F field, V value) {
		nativeHashSet(field, value);
	}
	
	protected void nativeHashSet(F field, V value) {
		hash.set(field, value);
	}

	@Override
	public V hashGet(F field) {
		return hash.get(field);
	}

	@Override
	public void hashDelete(F field) {
		hash.remove(field);
	}

	@Override
	public Map<F, V> getAll() {
		return hash.getAll();
	}

	@Override
	public int hashSize() {
		return (int) hash.size();
	}

	@Override
	public Class<V> getValueClass() {
		return vclz;
	}

	@Override
	public void clear() {
		cache.key().delete();
	}

	@Override
	public void hashSet(Map<F, V> values) {
		hash.set(values);
	}

	@Override
	public Set<F> fields() {
		return hash.fields();
	}

	@Override
	public boolean contains(F field) {
		return hash.contains(field);
	}

	@Override
	public boolean exists() {
		return cache.key().exists();
	}

}
