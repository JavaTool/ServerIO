package org.tool.server.cache.redis.string;

import org.tool.server.cache.ICacheHash;
import org.tool.server.cache.ICacheKey;
import org.tool.server.cache.ICacheList;
import org.tool.server.cache.ICacheValue;
import org.tool.server.cache.IKVCache;

import redis.clients.jedis.Transaction;

class RedisTransactionStringCache implements IKVCache<String, String, String> {
	
	private final ICacheKey<String> key;
	
	private final ICacheHash<String, String, String> hash;
	
	private final ICacheList<String, String> list;
	
	private final ICacheValue<String, String> value;

	public RedisTransactionStringCache(Transaction transaction) {
		hash = new RedisTransactionStringHash(transaction);
		key = new RedisTransactionStringKey(transaction);
		list = new RedisTransactionStringList(transaction);
		value = new RedisTransactionStringValue(transaction);
	}

	@Override
	public ICacheKey<String> key() {
		return key;
	}

	@Override
	public ICacheHash<String, String, String> hash() {
		return hash;
	}

	@Override
	public ICacheList<String, String> list() {
		return list;
	}

	@Override
	public ICacheValue<String, String> value() {
		return value;
	}

}
