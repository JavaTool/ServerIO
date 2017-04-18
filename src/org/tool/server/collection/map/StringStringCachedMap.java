package org.tool.server.collection.map;

import org.tool.server.cache.ICache;

public class StringStringCachedMap extends StringBaseCachedMap<String> {

	public StringStringCachedMap(ICache<String, String, String> cache, String preKey) {
		super(cache, preKey);
	}

	@Override
	protected String valueToString(String value) {
		return value;
	}

	@Override
	protected String stringToValue(String text) {
		return text;
	}

}
