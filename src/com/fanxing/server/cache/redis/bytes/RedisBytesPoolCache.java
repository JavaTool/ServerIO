package com.fanxing.server.cache.redis.bytes;

import com.fanxing.server.cache.ICacheHash;
import com.fanxing.server.cache.ICacheKey;
import com.fanxing.server.cache.ICacheList;
import com.fanxing.server.cache.ICacheValue;
import com.fanxing.server.cache.IKVCache;
import com.fanxing.server.cache.redis.IJedisReources;
import com.fanxing.server.cache.redis.RedisPoolCache;
import com.fanxing.server.coder.ICoder;
import com.fanxing.server.coder.stream.IStreamCoder;
import com.fanxing.server.coder.stream.StreamCoders;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

public class RedisBytesPoolCache extends RedisPoolCache<byte[]> {
	
	private final ICoder<byte[]> coder;

	public RedisBytesPoolCache(IJedisReources jedisReources) {
		this(jedisReources, StreamCoders.newProtoStuffCoder());
	}

	public RedisBytesPoolCache(IJedisReources jedisReources, IStreamCoder streamCoder) {
		super(jedisReources);
		coder = new ICoder<byte[]>() {
			
			@Override
			public <V> byte[] serializa(V v) throws Exception {
				return streamCoder.write(v);
			}
			
			@Override
			public <V> V deserializa(byte[] t, Class<V> clz) throws Exception {
				return streamCoder.read(t);
			}

			@Override
			public byte[][] createTArray(int size) {
				return new byte[size][];
			}
			
		};
	}

	@Override
	protected ICacheKey<byte[]> createCacheKey(IJedisReources jedisReources) {
		return new RedisBytesKey(jedisReources);
	}

	@Override
	protected ICacheHash<byte[], byte[], byte[]> createCacheHash(IJedisReources jedisReources) {
		return new RedisBytesHash(jedisReources);
	}

	@Override
	protected ICacheList<byte[], byte[]> createCacheList(IJedisReources jedisReources) {
		return new RedisBytesList(jedisReources);
	}

	@Override
	protected ICacheValue<byte[], byte[]> createCacheValue(IJedisReources jedisReources) {
		return new RedisBytesValue(jedisReources);
	}

	@Override
	public ICoder<byte[]> getCoder() {
		return coder;
	}

	@Override
	protected IKVCache<byte[], byte[], byte[]> createKVTransaction(Transaction transaction) {
		return new RedisTransactionBytesCache(transaction);
	}

	@Override
	protected void watch(Jedis jedis, byte[]... keys) {
		jedis.watch(keys);
	}

}
