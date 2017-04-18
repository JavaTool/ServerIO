package org.tool.server.cache.object.hash;

import java.util.Map;
import java.util.Set;

import org.tool.server.cache.IKVCache;
import org.tool.server.cache.manager.ICacheManager;

public class ObjectHash<K, F, V> implements IObjectHash<K, F, V> {
	
	protected final IKVCache<String, F, V> cache;
	
	protected final Class<V> vclz;
	
	protected final String preKey;
	
	public ObjectHash(ICacheManager cacheManager, Class<F> fclz, Class<V> vclz, String preKey) {
		this.vclz = vclz;
		this.preKey = preKey;
		cache = cacheManager.getCache(String.class, fclz, vclz);
	}

	@Override
	public void hashSet(K key, F field, V value) {
		nativeHashSet(key, field, value);
	}
	
	protected void nativeHashSet(K key, F field, V value) {
		cache.hash().set(makeKey(key), field, value);
	}

	@Override
	public V hashGet(K key, F field) {
		return cache.hash().get(makeKey(key), field);
	}

	@Override
	public void hashDelete(K key, F field) {
		cache.hash().remove(makeKey(key), field);
	}

	@Override
	public Map<F, V> getAll(K key) {
		return cache.hash().getAll(makeKey(key));
	}

	@Override
	public int hashSize(K key) {
		return (int) cache.hash().size(makeKey(key));
	}

	@Override
	public Class<V> getValueClass() {
		return vclz;
	}
	
	protected String makeKey(K key) {
		return preKey + key.toString();
	}

	@Override
	public void hashSet(K key, Map<F, V> values) {
		cache.hash().multiSet(makeKey(key), values);
	}

	@Override
	public void delete(K key) {
		cache.key().delete(makeKey(key));
	}

	@Override
	public Set<F> hashFields(K key) {
		return cache.hash().fields(makeKey(key));
	}

	@Override
	public boolean hashContains(K key, F field) {
		return cache.hash().contains(makeKey(key), field);
	}

	@Override
	public void hashDelete(K key, F[] fields) {
		cache.hash().remove(makeKey(key), fields);
	}

}
