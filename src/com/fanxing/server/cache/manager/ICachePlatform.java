package com.fanxing.server.cache.manager;

import com.fanxing.server.cache.cascade.ICascadeHash;
import com.fanxing.server.cache.cascade.ILazyCascade;
import com.fanxing.server.cache.lazy.ILazyCache;
import com.fanxing.server.cache.lazy.hash.ILazyHash;
import com.fanxing.server.cache.object.hash.IObjectHash;

public interface ICachePlatform extends ICacheManager {
	
	// lazy
	
	<F, V> ILazyCache<F, V> createLazyCache(Class<F> fclz, Class<V> vclz, String preKey);
	
	<F, V> ILazyCache<F, V> getLazyCache(Class<F> fclz, Class<V> vclz);
	
	<F, V> void addLazyCache(Class<F> fclz, Class<V> vclz, ILazyCache<F, V> lazyCache);
	
	// lazy hash
	
	<F, V> ILazyHash<F, V> getLazyHash(Class<V> clz);
	
	<F, V> ILazyHash<F, V> addLazyHashOnCreate(Class<F> fclz, Class<V> vclz, String preKey);
	
	<F, V> ILazyHash<F, V> addDayRefreshLazyHashOnCreate(Class<F> fclz, Class<V> vclz, String preKey);
	
	<F, V> void addLazyHash(ILazyHash<F, V> lazyHash);
	
	// object hash
	
	<K, F, V> IObjectHash<K, F, V> getObjectHash(Class<V> clz);
	
	<K, F, V> IObjectHash<K, F, V> addObjectHashOnCreate(Class<K> kclz, Class<F> fclz, Class<V> vclz, String preKey);
	
	<K, F, V> IObjectHash<K, F, V> addDayRefreshObjectHashOnCreate(Class<K> kclz, Class<F> fclz, Class<V> vclz, String preKey);
	
	<K, F, V> void addObjectHash(IObjectHash<K, F, V> objectHash);
	
	// cascade hash
	
	<K, F, V> ICascadeHash<K, F, V> getCascadeHash(Class<V> clz);
	
	<K, F, V> void addCascadeHash(ICascadeHash<K, F, V> cascadeHash);
	
	// lazy cascade
	
	<V> ILazyCascade<V> getLazyCascade(Class<V> clz);
	
	<V> void addLazyCascade(ILazyCascade<V> lazyCascade);

}
