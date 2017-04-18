package org.tool.server.cache.redis.string;

import static org.tool.server.cache.ICacheKey.ValueType.valueof;
import static org.tool.server.utils.DateUtil.toMilliTime;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.tool.server.cache.ICacheKey;

import redis.clients.jedis.Transaction;

class RedisTransactionStringKey implements ICacheKey<String> {
	
	private final Transaction transaction;
	
	public RedisTransactionStringKey(Transaction transaction) {
		this.transaction = transaction;;
	}

	@Override
	public void delete(Object... keys) {
		if (keys instanceof Object[]) {
			if (keys.length > 0) {
				String[] newKeys = new String[keys.length];
				for (int i = 0;i < keys.length;i++) {
					newKeys[i] = (String) keys[i];
				}
				transaction.del(newKeys);
			}
		} else {
			transaction.del((String[]) keys);
		}
	}

	@Override
	public boolean exists(String key) {
		return transaction.exists(key).get();
	}

	@Override
	public void expire(String key, long time, TimeUnit timeUnit) {
		transaction.pexpire(key, toMilliTime(time, timeUnit)).get();
	}

	@Override
	public void expireat(String key, long timestamp) {
		transaction.pexpireAt(key, timestamp).get();
	}

	@Override
	public Set<String> keys(String pattern) {
		return transaction.keys(pattern).get();
	}

	@Override
	public void persist(String key) {
		transaction.persist(key).get();
	}

	@Override
	public long ttl(String key) {
		return transaction.pttl(key).get();
	}

	@Override
	public void rename(String key, String newkey) {
		transaction.rename(key, newkey).get();
	}

	@Override
	public ValueType type(String key) {
		return valueof(transaction.type(key).get());
	}

}
