package com.fanxing.server.cache.redis;

import java.util.Map;

import com.fanxing.server.io.IConfigurationHolder;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

public class JedisPoolResources extends JedisReources {
	
	private final JedisPool pool;
	
	public JedisPoolResources(IConfigurationHolder configuration) {
		this(configuration.getConfigurationValue("cache_redisHosts"), 
				Integer.parseInt(configuration.getConfigurationValue("cache_redisMaxConnections")), 
				Integer.parseInt(configuration.getConfigurationValue("max_idle")), 
				Integer.parseInt(configuration.getConfigurationValue("wait_time")), 
				configuration.getConfigurationValue("password"));
	}
	
	public JedisPoolResources(Map<String, String> configuration) {
		this(configuration.get("cache_redisHosts"), 
				Integer.parseInt(configuration.get("cache_redisMaxConnections")), 
				Integer.parseInt(configuration.get("max_idle")), 
				Integer.parseInt(configuration.get("wait_time")), 
				configuration.get("password"));
	}
	
	public JedisPoolResources(String address, int maxTotal, int maxIdle, long waitTime, String password) {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(maxTotal);
		config.setMaxIdle(maxIdle);
		config.setMaxWaitMillis(waitTime);
		String[] infos = address.split(":");
		if (password == null || password.length() == 0) {
			pool = new JedisPool(config, infos[0], Integer.parseInt(infos[1]));
		} else {
			pool = new JedisPool(config, infos[0], Integer.parseInt(infos[1]), Protocol.DEFAULT_TIMEOUT, password);
		}
	}

	@Override
	protected Jedis getJedis() {
		return pool.getResource();
	}

	@Override
	protected void useFinish(Jedis jedis) {
		try {
			jedis.close();
		} catch (Exception e) {
			log.error("", e);
		}
	}

}
