package org.tool.server.cache.manager;

import java.util.Map;

import com.google.common.collect.Maps;

public class CacheManagers<M extends ICacheManager> implements ICacheManagers<M> {
	
	protected final Map<String, M> managers;
	
	public CacheManagers() {
		this.managers = Maps.newHashMap();
	}

	@Override
	public void addCacheManager(String name, M cacheManager) {
		managers.put(name, cacheManager);
	}

	@Override
	public M getCacheManager(String name) {
		return managers.get(name);
	}

	@Override
	public void removeCacheManager(String name) {
		managers.remove(name);
	}

}
