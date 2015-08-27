package com.fanxing.server.sequence.impl;

import redis.clients.jedis.Jedis;

import com.fanxing.server.sequence.InstanceIdMaker;

/**
 * Redis-id生成器
 * @author	fuhuiyuan
 */
public class RedisIdMaker implements InstanceIdMaker {
	
	private final Jedis jedis;
	/**名称*/
	private final String name;
	
	public RedisIdMaker(String name, Jedis jedis, int baseValue) throws Exception {
		this.jedis = jedis;
		this.name = name;
		if (!jedis.exists(name)) {
			jedis.set(name, baseValue + "");
		}
	}

	@Override
	public int nextInstanceId() {
		return jedis.incr(name).intValue();
	}

}
