package com.fanxing.server.collection.list;

import static java.lang.Integer.parseInt;
import static java.lang.String.valueOf;

import com.fanxing.server.cache.ICache;

public class IntStringCachedList extends BaseStringCachedList<Integer> {

	public IntStringCachedList(ICache<String, String, String> cache, String key) {
		super(cache, key);
	}

	@Override
	protected Integer toE(String e) {
		return parseInt(e);
	}

	@Override
	protected String eToString(Integer e) {
		return valueOf(e);
	}

}
