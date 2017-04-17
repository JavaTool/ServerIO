package com.fanxing.server.cache.manager;

import com.fanxing.server.cache.manager.RedisBytesCacheManager;
import com.fanxing.server.cache.redis.IJedisReources;
import com.fanxing.server.cache.redis.SupportIndexJedisResources;
import com.fanxing.server.io.IConfigurationHolder;

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
