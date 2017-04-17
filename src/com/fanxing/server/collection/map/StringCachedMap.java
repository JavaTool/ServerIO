package com.fanxing.server.collection.map;

import static com.google.common.collect.Sets.newHashSet;

import java.util.Set;

import com.fanxing.server.cache.ICache;

public abstract class StringCachedMap<V> extends CachedMap<String, V> {
	
	public StringCachedMap(ICache<String, String, String> cache, String preKey) {
		super(cache, preKey);
	}

	@Override
	protected Set<String> keySet(Set<String> keys) {
		Set<String> set = newHashSet();
		keys.forEach(k -> set.add(toK(k)));
		return set;
	}

	@Override
	protected String toK(String key) {
		return key.replace(preKey, "");
	}

}
