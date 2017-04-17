package com.fanxing.server.cache.lazy;

public interface ILazyCache<F, V> {
	
	ILazyCacheKey key();
	
	ILazyCacheHash<F, V> hash();
	
	ILazyCacheList<V> list();
	
	ILazyCacheValue<V> value();

}
