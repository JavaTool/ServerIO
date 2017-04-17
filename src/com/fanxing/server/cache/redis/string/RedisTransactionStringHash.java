package com.fanxing.server.cache.redis.string;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fanxing.server.cache.ICacheHash;

import redis.clients.jedis.Transaction;

class RedisTransactionStringHash implements ICacheHash<String, String, String> {
	
	private final Transaction transaction;
	
	public RedisTransactionStringHash(Transaction transaction) {
		this.transaction = transaction;
	}

	@Override
	public void remove(String key, Object... fields) {
		if (fields != null && fields.length > 0) {
			transaction.hdel(key, (String[]) fields);
		}
	}

	@Override
	public boolean contains(String key, String field) {
		return transaction.hexists(key, field).get();
	}

	@Override
	public String get(String key, String field) {
		return transaction.hget(key, field).get();
	}

	@Override
	public List<String> multiGet(String key, Object... fields) {
		return transaction.hmget(key, (String[]) fields).get();
	}

	@Override
	public Map<String, String> getAll(String key) {
		return transaction.hgetAll(key).get();
	}

	@Override
	public Set<String> fields(String key) {
		return transaction.hkeys(key).get();
	}

	@Override
	public long size(String key) {
		return transaction.hlen(key).get();
	}

	@Override
	public void multiSet(String key, Map<String, String> map) {
		if (map != null && map.size() > 0) {
			transaction.hmset(key, map);
		}
	}

	@Override
	public void set(String key, String field, String value) {
		transaction.hset(key, field, value);
	}

	@Override
	public List<String> values(String key) {
		return transaction.hvals(key).get();
	}

	@Override
	public long incrBy(String key, String field, long value) {
		return transaction.hincrBy(key, field, value).get();
	}

}
