package com.fanxing.server.cache.manager;

public interface ICacheManagers<M extends ICacheManager> {
	
	void addCacheManager(String name, M cacheManager);
	
	M getCacheManager(String name);
	
	void removeCacheManager(String name);

}
