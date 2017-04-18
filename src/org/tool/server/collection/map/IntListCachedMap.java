package org.tool.server.collection.map;

import static java.lang.String.valueOf;

import java.util.List;

import org.tool.server.cache.ICache;

public abstract class IntListCachedMap<E> extends StringExtendCachedMap<List<E>> {

	public IntListCachedMap(ICache<String, String, String> cache, String preKey) {
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
