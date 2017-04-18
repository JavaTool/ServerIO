package org.tool.server.cache.redis.string;

import static redis.clients.jedis.BinaryClient.LIST_POSITION.BEFORE;

import java.util.List;

import org.tool.server.cache.ICacheList;

import redis.clients.jedis.Transaction;

class RedisTransactionStringList implements ICacheList<String, String> {
	
	private final Transaction transaction;
	
	public RedisTransactionStringList(Transaction transaction) {
		this.transaction = transaction;
	}

	@Override
	public String headPop(String key) {
		return transaction.lpop(key).get();
	}

	@Override
	public void tailPush(String key, Object... objects) {
		transaction.lpush(key, (String[]) objects);
	}

	@Override
	public String get(String key, long index) {
		return transaction.lindex(key, index).get();
	}

	@Override
	public long size(String key) {
		return transaction.llen(key).get();
	}

	@Override
	public void trim(String key, long start, long end) {
		transaction.ltrim(key, start, end);
	}

	@Override
	public List<String> range(String key, long start, long end) {
		return transaction.lrange(key, start, end).get();
	}

	@Override
	public void lrem(String key, String value) {
		transaction.lrem(key, 1, value);
	}

	@Override
	public void insert(String key, long index, String value) {
		transaction.linsert(key, BEFORE, transaction.lindex(key, index).get(), value);
	}

	@Override
	public void set(String key, long index, String value) {
		transaction.lset(key, index, value);
	}

}
