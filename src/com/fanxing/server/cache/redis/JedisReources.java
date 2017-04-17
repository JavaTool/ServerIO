package com.fanxing.server.cache.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;

abstract class JedisReources implements IJedisReources {
	
	protected static final Logger log = LoggerFactory.getLogger(JedisPoolResources.class);

	protected abstract Jedis getJedis();

	protected abstract void useFinish(Jedis jedis);

	@Override
	public <T> T exec(RedisExecutor<T> run, T param) {
		Jedis jedis = getJedis();
		try {
			return run.exec(jedis);
		} catch (Exception e) {
			log.error("", e);
			return param;
		} finally {
			useFinish(jedis);
		}
	}

	@Override
	public void exec(VoidRedisExecutor run) {
		Jedis jedis = getJedis();
		try {
			run.exec(jedis);
		} catch (Exception e) {
			log.error("", e);
		} finally {
			useFinish(jedis);
		}
	}

	@Override
	public void changeDB(int index) {}

	@Override
	public boolean ping() {
		try {
			return "pong".equals(exec(jedis -> {
				return jedis.ping().toLowerCase();
			}, ""));
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}
	}

}
