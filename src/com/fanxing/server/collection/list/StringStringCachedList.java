package com.fanxing.server.collection.list;

import com.fanxing.server.cache.ICache;

public class StringStringCachedList extends BaseStringCachedList<String> {

	public StringStringCachedList(ICache<String, String, String> cache, String key) {
		super(cache, key);
	}

	@Override
	protected String toE(String e) {
		return e;
	}

	@Override
	protected String eToString(String e) {
		return e;
	}

}
