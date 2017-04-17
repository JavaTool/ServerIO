package com.fanxing.server.collection.map;

import static java.lang.String.valueOf;

import java.util.List;

import com.fanxing.server.cache.ICache;

public abstract class StringListCachedMap<E> extends StringExtendCachedMap<List<E>> {

	public StringListCachedMap(ICache<String, String, String> cache, String preKey) {
		super(cache, preKey);
	}

	@Override
	public List<E> put(String key, List<E> value) {
		List<E> list = get(key);
		list.clear();
		list.addAll(value);
		return list;
	}

	@Override
	public List<E> get(Object key) {
		return makeObject(makeKey(valueOf(key)));
	}

}
