package com.fanxing.server.collection.map;

import static java.lang.Integer.parseInt;
import static java.lang.String.valueOf;

import java.util.Map;

public class IntKeyLoadOnStringCachedMap<V> extends LoadOnStringCachedMap<Integer, V> {
	
	public IntKeyLoadOnStringCachedMap(StringCachedMap<V> stringCachedMap, Map<Integer, V> map) {
		super(stringCachedMap, map);
	}
	
	public IntKeyLoadOnStringCachedMap(StringCachedMap<V> stringCachedMap) {
		super(stringCachedMap);
	}

	@Override
	protected String makeString(Integer key) {
		return valueOf(key);
	}

	@Override
	protected Integer makeK(String key) {
		return parseInt(key);
	}

}
