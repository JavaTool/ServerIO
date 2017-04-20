package org.tool.server.cache.redis.string;

import java.util.Date;

import org.tool.server.cache.ICacheHash;
import org.tool.server.cache.ICacheKey;
import org.tool.server.cache.ICacheList;
import org.tool.server.cache.ICacheValue;
import org.tool.server.cache.IKVCache;
import org.tool.server.cache.redis.IJedisReources;
import org.tool.server.cache.redis.RedisPoolCache;
import org.tool.server.coder.ICoder;
import org.tool.server.coder.string.StringCoders;

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
					return StringCoders.createIntStringCoder().code((Integer) v);
				} else if (Date.class.equals(clz)) {
					return StringCoders.createDateStringCoder().code((Date) v);
				} else if (String.class.equals(clz)) {
					return StringCoders.createStringStringCoder().code((String) v);
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
					return (V) StringCoders.createIntStringCoder().decode(t);
				} else if (Date.class.equals(clz)) {
					return (V) StringCoders.createDateStringCoder().decode(t);
				} else if (String.class.equals(clz)) {
					return (V) StringCoders.createStringStringCoder().decode(t);
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