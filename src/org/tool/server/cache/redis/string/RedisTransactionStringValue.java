package org.tool.server.cache.redis.string;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.tool.server.cache.ICacheValue;

import redis.clients.jedis.Transaction;

class RedisTransactionStringValue implements ICacheValue<String, String> {
	
	private final Transaction transaction;
	
	public RedisTransactionStringValue(Transaction transaction) {
		this.transaction = transaction;
	}

	@Override
	public void append(String key, String value) {
		transaction.append(key, value);
	}

	@Override
	public long decr(String key, long decrement) {
		return transaction.decrBy(key, decrement).get();
	}

	@Override
	public String get(String key) {
		return transaction.get(key).get();
	}

	@Override
	public List<String> multiGet(Object... keys) {
		return transaction.mget((String[]) keys).get();
	}

	@Override
	public long incr(String key, long increment) {
		return transaction.incrBy(key, increment).get();
	}

	@Override
	public void set(String key, String value) {
		transaction.set(key, value);
	}

	@Deprecated
	@Override
	public boolean xSet(String key, String value, boolean exists, long time, TimeUnit timeUnit) {
		throw new UnsupportedOperationException("Redis transaction can not xSet.");
	}

	@Override
	public void multiSet(Map<String, String> map) {
		String[] keyValues = new String[map.size() << 1];
		int index = 0;
		for (String key : map.keySet()) {
			keyValues[index << 1] = key;
			keyValues[(index << 1) + 1] = map.get(key);
			++index;
		}

		transaction.mset(keyValues);
	}

}
