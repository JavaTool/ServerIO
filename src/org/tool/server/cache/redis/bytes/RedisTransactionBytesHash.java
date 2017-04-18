package org.tool.server.cache.redis.bytes;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.tool.server.cache.ICacheHash;

import redis.clients.jedis.Transaction;

class RedisTransactionBytesHash implements ICacheHash<byte[], byte[], byte[]> {
	
	private final Transaction transaction;
	
	public RedisTransactionBytesHash(Transaction transaction) {
		this.transaction = transaction;
	}

	@Override
	public void remove(byte[] key, Object... fields) {
		if (fields != null && fields.length > 0) {
			transaction.hdel(key, (byte[][]) fields);
		}
	}

	@Override
	public boolean contains(byte[] key, byte[] field) {
		return transaction.hexists(key, field).get();
	}

	@Override
	public byte[] get(byte[] key, byte[] field) {
		return transaction.hget(key, field).get();
	}

	@Override
	public List<byte[]> multiGet(byte[] key, Object... fields) {
		return transaction.hmget(key, (byte[][]) fields).get();
	}

	@Override
	public Map<byte[], byte[]> getAll(byte[] key) {
		return transaction.hgetAll(key).get();
	}

	@Override
	public Set<byte[]> fields(byte[] key) {
		return transaction.hkeys(key).get();
	}

	@Override
	public long size(byte[] key) {
		return transaction.hlen(key).get();
	}

	@Override
	public void multiSet(byte[] key, Map<byte[], byte[]> map) {
		if (map != null && map.size() > 0) {
			transaction.hmset(key, map);
		}
	}

	@Override
	public void set(byte[] key, byte[] field, byte[] value) {
		transaction.hset(key, field, value);
	}

	@Override
	public List<byte[]> values(byte[] key) {
		return transaction.hvals(key).get();
	}

	@Override
	public long incrBy(byte[] key, byte[] field, long value) {
		return transaction.hincrBy(key, field, value).get();
	}

}
