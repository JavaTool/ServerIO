package org.tool.server.cache.manager;

import org.tool.server.cache.manager.RedisBytesCacheManager;
import org.tool.server.cache.redis.IJedisReources;
import org.tool.server.cache.redis.SupportIndexJedisResources;
import org.tool.server.io.IConfigurationHolder;

public class SupportIndexRedisCacheManager extends RedisBytesCacheManager {
	
	public SupportIndexRedisCacheManager(IConfigurationHolder configuration) {
		super(configuration);
	}
	
	public SupportIndexRedisCacheManager(String address, int maxTotal, int maxIdle, long waitTime, String password) {
		super(address, maxTotal, maxIdle, waitTime, password);
	}
	
	@Override
	protected IJedisReources createJedisReources(String address, int maxTotal, int maxIdle, long waitTime, String password) {
		return new SupportIndexJedisResources(address, maxTotal, maxIdle,waitTime, password);
	}

	@Override
	protected IJedisReources createJedisReources(IConfigurationHolder configuration) {
		return new SupportIndexJedisResources(configuration);
	}

}
