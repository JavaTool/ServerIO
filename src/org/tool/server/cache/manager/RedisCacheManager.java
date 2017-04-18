package org.tool.server.cache.manager;

import org.tool.server.cache.redis.IJedisReources;
import org.tool.server.cache.redis.JedisPoolResources;
import org.tool.server.io.IConfigurationHolder;

public abstract class RedisCacheManager implements ICacheManager {
	
	private final IJedisReources jedisReources;
	
	public RedisCacheManager(IConfigurationHolder configuration) {
		jedisReources = createJedisReources(configuration);
	}
	
	public RedisCacheManager(String address, int maxTotal, int maxIdle, long waitTime, String password) {
		jedisReources = createJedisReources(address, maxTotal, maxIdle, waitTime, password);
	}
	
	protected IJedisReources createJedisReources(String address, int maxTotal, int maxIdle, long waitTime, String password) {
		return new JedisPoolResources(address, maxTotal, maxIdle,waitTime, password);
	}
	
	protected IJedisReources createJedisReources(IConfigurationHolder configuration) {
		return new JedisPoolResources(configuration);
	}
	
	public IJedisReources getJedisReources() {
		return jedisReources;
	}

}
