package org.tool.server.cache.redis.bytes;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.tool.server.cache.ICacheValue;

import redis.clients.jedis.Transaction;

class RedisTransactionBytesValue implements ICacheValue<byte[], byte[]> {
	
	private final Transaction transaction;
	
	public RedisTransactionBytesValue(Transaction transaction) {
		this.transaction = transaction;
	}

	@Override
	public void append(byte[] key, byte[] value) {
		transaction.append(key, value);
	}

	@Override
	public long decr(byte[] key, long decrement) {
		return transaction.decrBy(key, decrement).get();
	}

	@Override
	public byte[] get(byte[] key) {
		return transaction.get(key).get();
	}

	@Override
	public List<byte[]> multiGet(Object... keys) {
		return transaction.mget((byte[][]) keys).get();
	}

	@Override
	public long incr(byte[] key, long increment) {
		return transaction.incrBy(key, increment).get();
	}

	@Override
	public void set(byte[] key, byte[] value) {
		transaction.set(key, value);
	}

	@Deprecated
	@Override
	public boolean xSet(byte[] key, byte[] value, boolean exists, long time, TimeUnit timeUnit) {
		throw new UnsupportedOperationException("Redis transaction can not xSet.");
	}

	@Override
	public void multiSet(Map<byte[], byte[]> map) {
		byte[][] keyValues = new byte[map.size() << 1][];
		int index = 0;
		for (byte[] key : map.keySet()) {
			keyValues[index << 1] = key;
			keyValues[(index << 1) + 1] = map.get(key);
			++index;
		}

		transaction.mset(keyValues);
	}

}
