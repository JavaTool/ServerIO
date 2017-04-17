package com.fanxing.server.cache.redis;

import redis.clients.jedis.Jedis;

public class SingleJedisResources extends JedisReources {
	
	private Jedis jedis;
	
	public SingleJedisResources(String host, int port) {
		jedis = new Jedis(host, port);
		log.info("Redis connect, {}.", jedis.toString());
	}

	@Override
	protected Jedis getJedis() {
		return jedis;
	}

	@Override
	protected void useFinish(Jedis jedis) {}

}
