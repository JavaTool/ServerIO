package org.tool.server.sequence.impl;

import org.tool.server.cache.ICache;
import org.tool.server.sequence.IInstanceIdMaker;

/**
 * Redis-id生成器
 * @author	fuhuiyuan
 */
public class RedisIdMaker implements IInstanceIdMaker {
	
	private final ICache<String, String, Integer> cache;
	/**名称*/
	private final String name;
	
	public RedisIdMaker(String name, ICache<String, String, Integer> cache, int baseValue) {
		this.cache = cache;
		this.name = name;
		
		int currentValue = cache.key().exists(name) ? nextInstanceId() : 0;
		for (int i = currentValue;i < baseValue;i++) {
			nextInstanceId();
		}
	}

	@Override
	public int nextInstanceId() {
		return (int) cache.value().incr(name, 1);
	}

	@Override
	public int currentInstanceId() {
		return cache.value().get(name);
	}

}
