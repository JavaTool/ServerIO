package org.tool.server.cache.manager;

import org.tool.server.cache.ICache;

public interface ICacheManager {
	
	<K, F, V> ICache<K, F, V> createCache(Class<K> kclz, Class<F> fclz, Class<V> vclz);
	
	<K, F, V> ICache<K, F, V> getCache(Class<K> kclz, Class<F> fclz, Class<V> vclz);
	
	void setName(String name);
	
	void changeDB(int index);

}
