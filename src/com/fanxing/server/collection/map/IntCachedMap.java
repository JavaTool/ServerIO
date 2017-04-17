package com.fanxing.server.collection.map;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public class IntCachedMap<V> implements Map<Integer, V> {
	
	protected final StringCachedMap<V> cachedMap;
	
	protected final String preKey;
	
	public IntCachedMap(StringCachedMap<V> cachedMap, String preKey) {
		this.cachedMap = cachedMap;
		this.preKey = preKey;
	}

	@Override
	public int size() {
		return cachedMap.size();
	}

	@Override
	public boolean isEmpty() {
		return cachedMap.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return cachedMap.containsKey(makeKey((Integer) key));
	}
	
	protected final String makeKey(Integer key) {
		return preKey + key;
	}

	@Override
	public boolean containsValue(Object value) {
		return cachedMap.containsValue(value);
	}

	@Override
	public V get(Object key) {
		return cachedMap.get(makeKey((Integer) key));
	}

	@Override
	public V put(Integer key, V value) {
		return cachedMap.put(makeKey(key), value);
	}

	@Override
	public V remove(Object key) {
		return cachedMap.remove(makeKey((Integer) key));
	}

	@Override
	public void putAll(Map<? extends Integer, ? extends V> m) {
		Map<String, V> map = Maps.newHashMap();
		m.forEach((k, v) -> map.put(makeKey(k), v));
		cachedMap.putAll(map);
	}

	@Override
	public void clear() {
		cachedMap.clear();
	}

	@Override
	public Set<Integer> keySet() {
		Set<Integer> keys = Sets.newHashSet();
		cachedMap.keySet().forEach(k -> keys.add(toK(k)));
		return keys;
	}

	@Override
	public Collection<V> values() {
		return cachedMap.values();
	}

	@Override
	public Set<Entry<Integer, V>> entrySet() {
		Set<Entry<Integer, V>> set = Sets.newHashSet();
		cachedMap.entrySet().forEach(v -> set.add(new CachedEntry(v.getKey())));
		return set;
	}
	
	protected final Integer toK(String key) {
		return Integer.parseInt(key.replace(preKey, ""));
	}
	
	private class CachedEntry implements Entry<Integer, V> {
		
		private String key;
		
		public CachedEntry(String key) {
			this.key = key;
		}

		@Override
		public Integer getKey() {
			return toK(key);
		}

		@Override
		public V getValue() {
			return get(key);
		}

		@Override
		public V setValue(V value) {
			return value;
		}
		
	}

}
