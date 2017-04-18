package org.tool.server.cache.redis.bytes;

import static redis.clients.jedis.BinaryClient.LIST_POSITION.BEFORE;

import java.util.List;

import org.tool.server.cache.ICacheList;

import redis.clients.jedis.Transaction;

class RedisTransactionBytesList implements ICacheList<byte[], byte[]> {
	
	private final Transaction transaction;
	
	public RedisTransactionBytesList(Transaction transaction) {
		this.transaction = transaction;
	}

	@Override
	public byte[] headPop(byte[] key) {
		return transaction.lpop(key).get();
	}

	@Override
	public void tailPush(byte[] key, Object... objects) {
		transaction.lpush(key, (byte[][]) objects);
	}

	@Override
	public byte[] get(byte[] key, long index) {
		return transaction.lindex(key, index).get();
	}

	@Override
	public long size(byte[] key) {
		return transaction.llen(key).get();
	}

	@Override
	public void trim(byte[] key, long start, long end) {
		transaction.ltrim(key, start, end);
	}

	@Override
	public List<byte[]> range(byte[] key, long start, long end) {
		return transaction.lrange(key, start, end).get();
	}

	@Override
	public void lrem(byte[] key, byte[] value) {
		transaction.lrem(key, 1, value);
	}

	@Override
	public void insert(byte[] key, long index, byte[] value) {
		transaction.linsert(key, BEFORE, transaction.lindex(key, index).get(), value);
	}

	@Override
	public void set(byte[] key, long index, byte[] value) {
		transaction.lset(key, index, value);
	}

}
