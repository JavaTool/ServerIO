package com.fanxing.server.coder;

import static com.google.common.collect.Lists.newArrayListWithCapacity;
import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Sets.newHashSet;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public interface ICoder<T> {
	
	<V> T serializa(V v) throws Exception;
	
	<V> V deserializa(T t, Class<V> clz) throws Exception;
	
	T[] createTArray(int size);
	
	default T[] serializa(Object... objects) throws Exception {
		T[] ret = createTArray(objects.length);
		for (int i = 0;i < objects.length;i++) {
			ret[i] = serializa(objects[i]);
		}
		return ret;
	}
	
	default <K, V> Map<T, T> serializa(Map<K, V> map) throws Exception {
		Map<T, T> ret = newHashMap();
		for (Entry<K, V> entry : map.entrySet()) {
			V v = entry.getValue();
			if (v != null) {
				ret.put(serializa(entry.getKey()), serializa(v));
			}
		}
		return ret;
	}
	
	default <V> List<V> deserializa(List<T> list, Class<V> clz) throws Exception {
		List<V> ret = newArrayListWithCapacity(list.size());
		for (T t : list) {
			ret.add(deserializa(t, clz));
		}
		return ret;
	}
	
	default <K, V> Map<K, V> deserializa(Map<T, T> map, Class<K> kclz, Class<V> vclz) throws Exception {
		Map<K, V> ret = newHashMap();
		for (Entry<T, T> entry : map.entrySet()) {
			ret.put(deserializa(entry.getKey(), kclz), deserializa(entry.getValue(), vclz));
		}
		return ret;
	}
	
	default <V> Set<V> deserializa(Set<T> set, Class<V> clz) throws Exception {
		Set<V> ret = newHashSet();
		for (T t : set) {
			ret.add(deserializa(t, clz));
		}
		return ret;
	}

}
