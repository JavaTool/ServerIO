package com.fanxing.server.sequence.impl;

import java.util.HashMap;
import java.util.Map;

import com.fanxing.server.cache.redis.CacheOnRedis;
import com.fanxing.server.io.ConfigurationHolder;
import com.fanxing.server.sequence.InstanceIdMaker;
import com.fanxing.server.sequence.InstanceIdManager;

/**
 * Redis-id管理器
 * @author	fuhuiyuan
 */
public class RedisIdManager extends CacheOnRedis implements InstanceIdManager {
	
	/**id生成器集合*/
	protected Map<String, InstanceIdMaker> idMakers;

	public RedisIdManager(ConfigurationHolder configuration) {
		super(configuration);
		idMakers = new HashMap<String, InstanceIdMaker>();
	}

	@Override
	public void create(String name, int baseValue) {
		if (!idMakers.containsKey(name)) {
			try {
				idMakers.put(name, new RedisIdMaker(name, jedis, baseValue));
			} catch (Exception e) {
				log.error("", e);
			}
		}
	}

	@Override
	public int next(String name) {
		if (idMakers.containsKey(name)) {
			return idMakers.get(name).nextInstanceId();
		} else {
			log.error("Do not have {} id maker.", name);
			throw new NullPointerException();
		}
	}

}
