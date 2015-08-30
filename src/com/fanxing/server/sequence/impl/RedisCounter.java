package com.fanxing.server.sequence.impl;

import redis.clients.jedis.BinaryJedisCommands;
import redis.clients.jedis.JedisCommands;

import com.fanxing.server.cache.redis.CacheOnJedis;
import com.fanxing.server.sequence.Counter;

public class RedisCounter<B extends BinaryJedisCommands, J extends JedisCommands> implements Counter {
	
	private final CacheOnJedis<B, J> cache;
	
	public RedisCounter(CacheOnJedis<B, J> cache) {
		this.cache = cache;
	}

	@Override
	public long get(String key) {
		J jedis = cache.getJedisCommands();
		try {
			return Long.parseLong(jedis.get(key));
		} finally {
			cache.useFinish(jedis);
		}
	}

	@Override
	public long incr(String key, long value) {
		J jedis = cache.getJedisCommands();
		try {
			return jedis.incrBy(key, value);
		} finally {
			cache.useFinish(jedis);
		}
	}

	@Override
	public long decr(String key, long value) {
		J jedis = cache.getJedisCommands();
		try {
			return jedis.decrBy(key, value);
		} finally {
			cache.useFinish(jedis);
		}
	}

	@Override
	public void delete(String key) {
		J jedis = cache.getJedisCommands();
		try {
			jedis.del(key);
		} finally {
			cache.useFinish(jedis);
		}
	}

}
