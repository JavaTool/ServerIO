package com.fanxing.server.cache.lazy;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.fanxing.server.cache.ICacheKey;
import com.fanxing.server.cache.ICacheKey.ValueType;

public class LazyCacheKey implements ILazyCacheKey {
	
	private final ICacheKey<String> cacheKey;
	
	private String key;
	
	public LazyCacheKey(ICacheKey<String> cacheKey, String preKey) {
		this.cacheKey = cacheKey;
		key = preKey;
	}

	@Override
	public void setKey(String key) {
		this.key += key;
	}

	@Override
	public void delete() {
		cacheKey.delete(key);
	}

	@Override
	public boolean exists() {
		return cacheKey.exists(key);
	}

	@Override
	public void expire(long time, TimeUnit timeUnit) {
		cacheKey.expire(key, time, timeUnit);
	}

	@Override
	public void expireat(long timestamp) {
		cacheKey.expireat(key, timestamp);
	}

	@Override
	public Set<String> keys(String pattern) {
		return cacheKey.keys(pattern);
	}

	@Override
	public void persist() {
		cacheKey.persist(key);
	}

	@Override
	public long ttl() {
		return cacheKey.ttl(key);
	}

	@Override
	public void rename(String newkey) {
		cacheKey.rename(key, newkey);
		key = newkey;
	}

	@Override
	public ValueType type() {
		return cacheKey.type(key);
	}

	@Override
	public String getKey() {
		return key;
	}

}
