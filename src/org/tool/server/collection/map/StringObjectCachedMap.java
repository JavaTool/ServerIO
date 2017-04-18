package org.tool.server.collection.map;

import org.tool.server.cache.ICache;

public class StringObjectCachedMap<V> extends StringExtendCachedMap<V> {
	
	private final StringCachedObjectFactory stringCachedObjectFactory;
	
	private final Class<V> clz;

	public StringObjectCachedMap(ICache<String, String, String> cache, String preKey, StringCachedObjectFactory stringCachedObjectFactory, Class<V> clz) {
		super(cache, preKey);
		this.stringCachedObjectFactory = stringCachedObjectFactory;
		this.clz = clz;
	}

	@Override
	protected V makeObject(String key) {
		return stringCachedObjectFactory.createObject(cache, key, clz);
	}

	@Override
	public V put(String key, V value) {
		return stringCachedObjectFactory.createObject(cache, makeKey(key), value);
	}

}
