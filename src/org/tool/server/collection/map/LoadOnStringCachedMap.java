package org.tool.server.collection.map;

import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Sets.newHashSet;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public abstract class LoadOnStringCachedMap<K, V> implements Map<K, V> {
	
	private final StringCachedMap<V> stringCachedMap;
	
	private final Map<K, V> map;
	
	public LoadOnStringCachedMap(StringCachedMap<V> stringCachedMap, Map<K, V> map) {
		this.stringCachedMap = stringCachedMap;
		this.map = map;
	}
	
	public LoadOnStringCachedMap(StringCachedMap<V> stringCachedMap) {
		this(stringCachedMap, newHashMap());
	}

	@Override
	public int size() {
		return stringCachedMap.size();
	}

	@Override
	public boolean isEmpty() {
		return stringCachedMap.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return stringCachedMap.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return stringCachedMap.containsValue(value);
	}

	@SuppressWarnings("unchecked")
	@Override
	public V get(Object key) {
		return map.containsKey(key) ? map.get(key) : getFromCachedMap((K) key);
	}
	
	protected abstract String makeString(K key);
	
	protected abstract K makeK(String key);
	
	private V getFromCachedMap(K key) {
		V value = stringCachedMap.get(key);
		if (value != null) {
			map.put(key, value);
		}
		return value;
	}

	@Override
	public V put(K key, V value) {
		stringCachedMap.put(makeString(key), value);
		return map.put(key, value);
	}

	@Override
	public V remove(Object key) {
		stringCachedMap.remove(key);
		return map.remove(key);
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		Map<String, V> map = newHashMap();
		m.forEach((k, v) -> map.put(makeString(k), v));
		stringCachedMap.putAll(map);
		this.map.putAll(m);
	}

	@Override
	public void clear() {
		stringCachedMap.clear();
		map.clear();
	}

	@Override
	public Set<K> keySet() {
		Set<K> set = newHashSet();
		stringCachedMap.keySet().forEach(v -> set.add(makeK(v)));
		return set;
	}

	@Override
	public Collection<V> values() {
		return stringCachedMap.values();
	}

	@Override
	public Set<Entry<K, V>> entrySet() {
		Set<Entry<K, V>> set = newHashSet();
		stringCachedMap.entrySet().forEach(v -> set.add(new CacheEntry(v)));
		return set;
	}
	
	private class CacheEntry implements Entry<K, V> {
		
		private final Entry<String, V> entry;
		
		public CacheEntry(Entry<String, V> entry) {
			this.entry = entry;
		}

		@Override
		public K getKey() {
			return makeK(entry.getKey());
		}

		@Override
		public V getValue() {
			return entry.getValue();
		}

		@Override
		public V setValue(V value) {
			return entry.setValue(value);
		}
		
	}

}
