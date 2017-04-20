package org.tool.server.collection.map;

import static java.lang.String.valueOf;

import java.util.List;

import org.tool.server.cache.ICache;
import org.tool.server.collection.list.IntStringCachedList;

public class StringIntListCachedMap extends StringListCachedMap<Integer> {

	public StringIntListCachedMap(ICache<String, String, String> cache, String preKey) {
		super(cache, preKey);
	}

	@Override
	protected List<Integer> makeObject(String key) {
		return new IntStringCachedList(cache, makeKey(valueOf(key)));
	}

}