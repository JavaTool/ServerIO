package org.tool.server.cache.object;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.tool.server.coder.ICoder;

abstract class CoderCache<T> {
	
	private final ICoder<T> coder;
	
	public CoderCache(ICoder<T> coder) {
		this.coder = coder;
	}
	
	protected T serializa(Object object) {
		try {
			return coder.serializa(object);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	protected T[] serializa(Object... objects) {
		try {
			return coder.serializa(objects);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	protected <K, V> Map<T, T> serializa(Map<K, V> map) {
		try {
			return coder.serializa(map);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	protected <V> V deserializa(T t, Class<V> clz) {
		try {
			return coder.deserializa(t, clz);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	protected <V> List<V> deserializa(List<T> list, Class<V> clz) {
		try {
			return coder.deserializa(list, clz);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	protected <K, V> Map<K, V> deserializa(Map<T, T> map, Class<K> kclz, Class<V> vclz) {
		try {
			return coder.deserializa(map, kclz, vclz);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	protected <V> Set<V> deserializa(Set<T> set, Class<V> clz) {
		try {
			return coder.deserializa(set, clz);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
