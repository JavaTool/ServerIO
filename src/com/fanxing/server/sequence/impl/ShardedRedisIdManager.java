package com.fanxing.server.sequence.impl;

import java.util.HashMap;
import java.util.Map;

import redis.clients.jedis.ShardedJedis;

import com.fanxing.server.cache.redis.CacheOnShardedRedis;
import com.fanxing.server.io.ConfigurationHolder;
import com.fanxing.server.sequence.InstanceIdManager;
import com.fanxing.server.utils.SerializaUtil;

public class ShardedRedisIdManager extends CacheOnShardedRedis implements InstanceIdManager {
	
	protected Map<String, byte[]> keys;

	public ShardedRedisIdManager(ConfigurationHolder configuration) {
		super(configuration);
		keys = new HashMap<String, byte[]>();
	}

	@Override
	public void create(String name, int baseValue) {
		ShardedJedis sharded = jedis.getResource();
		try {
			byte[] key = SerializaUtil.serializable(name);
			if (!sharded.exists(key)) {
				sharded.set(key, SerializaUtil.serializable(baseValue));
				keys.put(name, key);
			}
		} catch (Exception e) {
			log.error("", e);
		} finally {
			jedis.returnResourceObject(sharded);
		}
	}

	@Override
	public int next(String name) {
		ShardedJedis sharded = jedis.getResource();
		try {
			return sharded.incr(keys.get(name)).intValue();
		} finally {
			jedis.returnResourceObject(sharded);
		}
	}

}
