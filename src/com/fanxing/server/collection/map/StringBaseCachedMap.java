package com.fanxing.server.collection.map;

import java.util.Set;

import com.fanxing.server.cache.ICache;

public abstract class StringBaseCachedMap<V> extends StringCachedMap<V> {

	public StringBaseCachedMap(ICache<String, String, String> cache, String preKey) {
		super(cache, preKey);
	}

	@Override
	protected Set<String> keys() {
		return cache.hash().fields(preKey);
	}

	@Override
	public V put(String key, V value) {
		cache.hash().set(preKey, key, valueToString(value));
		return value;
	}
	
	protected abstract String valueToString(V value);

	@Override
	protected V makeObject(String key) {
		return stringToValue(cache.hash().get(preKey, key));
	}
	
	protected abstract V stringToValue(String text);

}
