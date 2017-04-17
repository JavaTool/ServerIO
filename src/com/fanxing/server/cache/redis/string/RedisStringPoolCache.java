package com.fanxing.server.cache.redis.string;

import java.util.Date;

import com.fanxing.server.cache.ICacheHash;
import com.fanxing.server.cache.ICacheKey;
import com.fanxing.server.cache.ICacheList;
import com.fanxing.server.cache.ICacheValue;
import com.fanxing.server.cache.IKVCache;
import com.fanxing.server.cache.redis.IJedisReources;
import com.fanxing.server.cache.redis.RedisPoolCache;
import com.fanxing.server.coder.ICoder;
import com.fanxing.server.coder.string.StringCodes;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

public class RedisStringPoolCache extends RedisPoolCache<String> {
	
	private static final ICoder<String> CODER = new ICoder<String>() {
		
		@Override
		public <V> String serializa(V v) throws Exception {
			if (v == null) {
				return null;
			} else {
				@SuppressWarnings("unchecked")
				Class<V> clz = (Class<V>) v.getClass();
				if (Integer.class.equals(clz)) {
					return StringCodes.createIntStringCoder().code((Integer) v);
				} else if (Date.class.equals(clz)) {
					return StringCodes.createDateStringCoder().code((Date) v);
				} else if (String.class.equals(clz)) {
					return StringCodes.createStringStringCoder().code((String) v);
				} else {
					throw new Exception("Unsupport serializa class " + clz);
				}
			}
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public <V> V deserializa(String t, Class<V> clz) throws Exception {
			if (t == null || clz == null) {
				return null;
			} else {
				if (Integer.class.equals(clz)) {
					return (V) StringCodes.createIntStringCoder().decode(t);
				} else if (Date.class.equals(clz)) {
					return (V) StringCodes.createDateStringCoder().decode(t);
				} else if (String.class.equals(clz)) {
					return (V) StringCodes.createStringStringCoder().decode(t);
				} else {
					throw new Exception("Unsupport serializa class " + clz);
				}
			}
		}

		@Override
		public String[] createTArray(int size) {
			return new String[size];
		}
		
	};
	
	public RedisStringPoolCache(IJedisReources jedisReources) {
		super(jedisReources);
	}

	@Override
	protected ICacheKey<String> createCacheKey(IJedisReources jedisReources) {
		return new RedisStringKey(jedisReources);
	}

	@Override
	protected ICacheHash<String, String, String> createCacheHash(IJedisReources jedisReources) {
		return new RedisStringHash(jedisReources);
	}

	@Override
	protected ICacheList<String, String> createCacheList(IJedisReources jedisReources) {
		return new RedisStringList(jedisReources);
	}

	@Override
	protected ICacheValue<String, String> createCacheValue(IJedisReources jedisReources) {
		return new RedisStringValue(jedisReources);
	}

	@Override
	public ICoder<String> getCoder() {
		return CODER;
	}

	@Override
	protected IKVCache<String, String, String> createKVTransaction(Transaction transaction) {
		return new RedisTransactionStringCache(transaction);
	}

	@Override
	protected void watch(Jedis jedis, String... keys) {
		jedis.watch(keys);
	}

}
