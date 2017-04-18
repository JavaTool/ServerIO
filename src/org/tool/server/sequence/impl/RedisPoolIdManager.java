package org.tool.server.sequence.impl;

import java.util.Map;

import org.tool.server.cache.ICache;
import org.tool.server.sequence.IInstanceIdMaker;
import org.tool.server.sequence.IInstanceIdManager;

import com.google.common.collect.Maps;

public class RedisPoolIdManager implements IInstanceIdManager {
	
	/**id生成器集合*/
	protected Map<String, IInstanceIdMaker> idMakers;
	
	protected ICache<String, String, Integer> cache;
	
	public RedisPoolIdManager(ICache<String, String, Integer> cache) {
		this.cache = cache;
		idMakers = Maps.newHashMap();
	}

	@Override
	public void create(String name, int baseValue) {
		if (!idMakers.containsKey(name)) {
			idMakers.put(name, new RedisIdMaker(name, cache, baseValue));
		}
	}

	@Override
	public int next(String name) {
		if (idMakers.containsKey(name)) {
			return idMakers.get(name).nextInstanceId();
		} else {
			throw new NullPointerException("Do not have " + name + " id maker.");
		}
	}

}
