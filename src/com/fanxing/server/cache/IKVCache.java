package com.fanxing.server.cache;

public interface IKVCache<K, F, V> {

	ICacheKey<K> key();
	
	ICacheHash<K, F, V> hash();
	
	ICacheList<K, V> list();
	
	ICacheValue<K, V> value();

}
