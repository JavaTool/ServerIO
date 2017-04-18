package org.tool.server.collection.map;

import org.tool.server.cache.ICache;

public interface StringCachedObjectFactory {
	
	<V> V createObject(ICache<String, String, String> cache, String key, V value);
	
	<V> V createObject(ICache<String, String, String> cache, String key, Class<V> clz);
	
	<T, S> String getMultiSubKey(Class<T> clz, Class<S> sub, Object key);
	
	<T, S> String getSingleSubKey(Class<T> clz, Class<S> sub, Object key);
	
	<T, S> String appendMultiSubKey(String key);
	
	<T, S> String appendSingleSubKey(String key);
	
	<T> String makeKey(Class<T> clz, Object key);

}
