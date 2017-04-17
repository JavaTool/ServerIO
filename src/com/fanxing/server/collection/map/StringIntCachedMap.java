package com.fanxing.server.collection.map;

import static java.lang.Integer.parseInt;
import static java.lang.String.valueOf;

import com.fanxing.server.cache.ICache;

public class StringIntCachedMap extends StringBaseCachedMap<Integer> {

	public StringIntCachedMap(ICache<String, String, String> cache, String preKey) {
		super(cache, preKey);
	}

	@Override
	protected String valueToString(Integer value) {
		return valueOf(value);
	}

	@Override
	protected Integer stringToValue(String text) {
		return parseInt(text);
	}

}
