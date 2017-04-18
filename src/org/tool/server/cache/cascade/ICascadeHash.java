package org.tool.server.cache.cascade;

import java.util.Map;
import java.util.Set;

import org.tool.server.cache.manager.ICachePlatform;
import org.tool.server.cache.object.hash.IObjectHash;

public interface ICascadeHash<K, F, V> {
	
	// both parent and sub
	
	void hashSet(V value, boolean cascade);
	
	void hashSet(Iterable<V> values, boolean cascade);
	
	void hashSet(Map<F, V> values, boolean cascade);
	
	<T> V hashGet(Class<T> clz, K key, F field, boolean cascade);
	
	<T> Map<F, V> getAll(Class<T> clz, K key, boolean cascade);
	
	<T> void hashDelete(Class<T> clz, K key, F field, boolean cascade);
	
	void hashDelete(V value, boolean cascade);
	
	<T> void delete(Class<T> clz, K key, boolean cascade);
	
	<T> int hashSize(Class<T> clz, K key);
	
	<T> Set<F> hashFields(Class<T> clz, K key);
	
	<T> boolean hashContains(Class<T> clz, K key, F field);
	
	// sub
	
	void hashSet(V value);
	
	void hashSet(Iterable<V> values);
	
	void hashSet(Map<F, V> values);
	
	<T> V hashGet(Class<T> clz, K key, F field);
	
	<T> Map<F, V> getAll(Class<T> clz, K key);
	
	<T> void hashDelete(Class<T> clz, K key, F field);
	
	void hashDelete(V value);
	
	<T> void delete(Class<T> clz, K key);
	
	// parent
	
	V hashGet(K key, F field, boolean cascade);
	
	Map<F, V> getAll(K key, boolean cascade);
	
	void hashDelete(K key, F field, boolean cascade);
	
	void delete(K key, boolean cascade);
	
	int hashSize(K key);
	
	boolean hashContains(K key, F field);
	
	// etc
	
	Class<V> getValueClass();
	
	static <K, F, V> IObjectHash<K, F, V> createCache(ICachePlatform cachePlatform, Class<K> kclz, Class<F> fclz, Class<V> vclz, String preKey) {
		return cachePlatform.addObjectHashOnCreate(kclz, fclz, vclz, preKey);
	}

}
