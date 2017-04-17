package com.fanxing.server.cache.redis.bytes;

import static com.fanxing.server.cache.ICacheKey.ValueType.valueof;
import static com.fanxing.server.utils.DateUtil.toMilliTime;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.fanxing.server.cache.ICacheKey;

import redis.clients.jedis.Transaction;

class RedisTransactionBytesKey implements ICacheKey<byte[]> {
	
	private final Transaction transaction;
	
	public RedisTransactionBytesKey(Transaction transaction) {
		this.transaction = transaction;;
	}

	@Override
	public void delete(Object... keys) {
		if (keys instanceof Object[]) {
			if (keys.length > 0) {
				byte[][] newKeys = new byte[keys.length][];
				for (int i = 0;i < keys.length;i++) {
					newKeys[i] = (byte[]) keys[i];
				}
				transaction.del(newKeys);
			}
		} else {
			transaction.del((byte[][]) keys);
		}
	}

	@Override
	public boolean exists(byte[] key) {
		return transaction.exists(key).get();
	}

	@Override
	public void expire(byte[] key, long time, TimeUnit timeUnit) {
		transaction.pexpire(key, toMilliTime(time, timeUnit)).get();
	}

	@Override
	public void expireat(byte[] key, long timestamp) {
		transaction.pexpireAt(key, timestamp).get();
	}

	@Override
	public Set<byte[]> keys(byte[] pattern) {
		return transaction.keys(pattern).get();
	}

	@Override
	public void persist(byte[] key) {
		transaction.persist(key).get();
	}

	@Override
	public long ttl(byte[] key) {
		return transaction.pttl(key).get();
	}

	@Override
	public void rename(byte[] key, byte[] newkey) {
		transaction.rename(key, newkey).get();
	}

	@Override
	public ValueType type(byte[] key) {
		return valueof(transaction.type(key).get());
	}

}
