package com.fanxing.server.cache.redis.bytes;

import com.fanxing.server.cache.ICacheHash;
import com.fanxing.server.cache.ICacheKey;
import com.fanxing.server.cache.ICacheList;
import com.fanxing.server.cache.IKVCache;
import com.fanxing.server.cache.ICacheValue;

import redis.clients.jedis.Transaction;

class RedisTransactionBytesCache implements IKVCache<byte[], byte[], byte[]> {
	
	private final ICacheKey<byte[]> key;
	
	private final ICacheHash<byte[], byte[], byte[]> hash;
	
	private final ICacheList<byte[], byte[]> list;
	
	private final ICacheValue<byte[], byte[]> value;

	public RedisTransactionBytesCache(Transaction transaction) {
		hash = new RedisTransactionBytesHash(transaction);
		key = new RedisTransactionBytesKey(transaction);
		list = new RedisTransactionBytesList(transaction);
		value = new RedisTransactionBytesValue(transaction);
	}

	@Override
	public ICacheKey<byte[]> key() {
		return key;
	}

	@Override
	public ICacheHash<byte[], byte[], byte[]> hash() {
		return hash;
	}

	@Override
	public ICacheList<byte[], byte[]> list() {
		return list;
	}

	@Override
	public ICacheValue<byte[], byte[]> value() {
		return value;
	}

}
