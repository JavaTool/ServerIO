package org.tool.server.cache.redis;

import java.util.Map;

import org.tool.server.io.IConfigurationHolder;

import redis.clients.jedis.Jedis;

public class SupportIndexJedisResources extends JedisPoolResources {
	
	private int index;
	
	public SupportIndexJedisResources(IConfigurationHolder configuration) {
		super(configuration);
	}
	
	public SupportIndexJedisResources(Map<String, String> configuration) {
		super(configuration);
	}
	
	public SupportIndexJedisResources(String address, int maxTotal, int maxIdle, long waitTime, String password) {
		super(address, maxTotal, maxIdle, waitTime, password);
	}

	@Override
	protected Jedis getJedis() {
		Jedis jedis = super.getJedis();
		jedis.select(index);
		return jedis;
	}

	@Override
	public void changeDB(int index) {
		this.index = index;
	}

}
