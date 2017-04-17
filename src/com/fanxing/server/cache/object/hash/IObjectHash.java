package com.fanxing.server.cache.object.hash;

import java.util.Map;
import java.util.Set;

public interface IObjectHash<K, F, V> {
	
	void hashSet(K key, F field, V value);
	
	V hashGet(K key, F field);
	
	void hashDelete(K key, F field);
	
	void hashDelete(K key, F[] fields);
	
	void delete(K key);
	
	Map<F, V> getAll(K key);
	
	int hashSize(K key);
	
	Class<V> getValueClass();
	
	void hashSet(K key, Map<F, V> values);
	
	Set<F> hashFields(K key);
	
	boolean hashContains(K key, F field);

}
