package org.tool.server.sequence.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tool.server.cache.redis.IJedisReources;
import org.tool.server.sequence.ICounter;

import redis.clients.jedis.Jedis;

public class RedisCounter implements ICounter {
	
	protected static final Logger log = LoggerFactory.getLogger(RedisCounter.class);
	
	private final IJedisReources cache;
	
	public RedisCounter(IJedisReources cache) {
		this.cache = cache;
	}

	@Override
	public long getCount(String key) {
		return cache.exec((jedis) -> {
			return Long.valueOf(jedis.get(key));
		}, 0L);
	}

	@Override
	public long incr(String key, long value, long time) {
		return cache.exec((jedis) -> {
			setTime(jedis, key, time);
			return jedis.incrBy(key, value);
		}, 0L);
	}
	
	protected void setTime(Jedis jedis, String key, long time) {
		if (time != NO_TIME && !jedis.exists(key)) {
			jedis.pexpire(key, time);
		}
	}

	@Override
	public long decr(String key, long value, long time) {
		return cache.exec((jedis) -> {
			setTime(jedis, key, time);
			return jedis.decrBy(key, value);
		}, 0L);
	}

	@Override
	public void deleteCount(String key) {
		cache.exec((jedis) -> jedis.del(key));
	}

	@Override
	public long getCount(String key, String name) {
		return cache.exec((jedis) -> {
			return Long.valueOf(jedis.hget(key, name));
		}, 0L);
	}

	@Override
	public long incr(String key, String name, long value, long time) {
		return cache.exec((jedis) -> {
			setTime(jedis, key, time);
			return jedis.hincrBy(key, name, value);
		}, 0L);
	}

	@Override
	public long decr(String key, String name, long value, long time) {
		return incr(key, name, -value, time);
	}

	@Override
	public void deleteCount(String key, String name) {
		cache.exec((jedis) -> jedis.hdel(key, name));
	}

}
