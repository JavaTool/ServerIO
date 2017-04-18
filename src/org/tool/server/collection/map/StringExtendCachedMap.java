package org.tool.server.collection.map;

import java.util.Set;

import org.tool.server.cache.ICache;

public abstract class StringExtendCachedMap<V> extends StringCachedMap<V> {

	public StringExtendCachedMap(ICache<String, String, String> cache, String preKey) {
		super(cache, preKey);
	}
	
	protected Set<String> keys() {
		return cacheKey.keys(makeKey(ALL));
	}

}
