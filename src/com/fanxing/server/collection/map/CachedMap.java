package com.fanxing.server.collection.map;

import static com.google.common.collect.Lists.newLinkedList;
import static java.lang.String.valueOf;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import com.fanxing.server.cache.ICache;
import com.fanxing.server.cache.ICacheKey;
import com.google.common.collect.Sets;

public abstract class CachedMap<K, V> implements Map<K, V> {
	
	protected static final String ALL = "*";
	
	protected final ICache<String, String, String> cache;
	
	protected final ICacheKey<String> cacheKey;
	
	protected final String preKey;
	
	public CachedMap(ICache<String, String, String> cache, String preKey) {
		this.cache = cache;
		cacheKey = cache.key();
		this.preKey = preKey;
	}
	
	protected String makeKey(String key) {
		return preKey + key;
	}

	@Override
	public int size() {
		return keySet().size();
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public boolean containsKey(Object key) {
		return exists(makeKey(valueOf(key)));
	}

	private boolean exists(String key) {
		return cacheKey.exists(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return values().contains(value);
	}

	@Override
	public V get(Object key) {
		String k = makeKey(valueOf(key));
		return exists(k) ? makeObject(k) : null;
	}
	
	protected abstract V makeObject(String key);

	@Override
	public V remove(Object key) {
		cacheKey.delete(makeKey(valueOf(key)));
		return null;
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		m.forEach((k, v) -> put(k, v));
	}

	@Override
	public void clear() {
		cacheKey.delete(keySet().toArray());
	}

	@Override
	public Set<K> keySet() {
		return keySet(keys());
	}
	
	protected abstract Set<K> keySet(Set<String> keys);
	
	protected abstract Set<String> keys();

	@Override
	public Collection<V> values() {
		Collection<V> ret = newLinkedList();
		keys().forEach(key -> ret.add(makeObject(key)));
		return ret;
	}

	@Override
	public Set<Entry<K, V>> entrySet() {
		Set<Entry<K, V>> set = Sets.newHashSet();
		keys().forEach(k -> set.add(new CachedEntry(k)));
		return set;
	}
	
	protected abstract K toK(String key);
	
	private class CachedEntry implements Entry<K, V> {
		
		private String key;
		
		public CachedEntry(String key) {
			this.key = key;
		}

		@Override
		public K getKey() {
			return toK(key);
		}

		@Override
		public V getValue() {
			return makeObject(key);
		}

		@Override
		public V setValue(V value) {
			return value;
		}
		
	}

}
