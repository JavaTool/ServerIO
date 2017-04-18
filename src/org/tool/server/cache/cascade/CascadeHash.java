package org.tool.server.cache.cascade;

import java.util.Map;
import java.util.Set;

import org.tool.server.cache.object.hash.IObjectHash;

import com.google.common.collect.Maps;

public abstract class CascadeHash<K, F, V> implements ICascadeHash<K, F, V> {
	
	protected final IObjectHash<String, F, V> objectHash;
	
	public CascadeHash(IObjectHash<String, F, V> objectHash) {
		this.objectHash = objectHash;
	}

	@Override
	public void hashSet(V value, boolean cascade) {
		K key = getKey(value);
		objectHash.hashSet(makeKey(getClassFromValue(value), key), getField(value), value);
		if (cascade) {
			cascadeHashSet(key, value);
		}
	}
	
	protected abstract Class<?> getClassFromValue(V value);
	
	protected abstract K getKey(V value);
	
	protected abstract F getField(V value);
	
	protected String makeKey(Class<?> clz, K key) {
		return new StringBuilder().append("_").append(clz.getName()).append("_").append(key).toString();
	}
	
	protected abstract void cascadeHashSet(K key, V value);

	@Override
	public <T> V hashGet(Class<T> clz, K key, F field, boolean cascade) {
		V value = objectHash.hashGet(makeKey(clz, key), field);
		if (value != null && cascade) {
			cascadeHashGet(key, value);
		}
		return value;
	}

	@Override
	public <T> void hashDelete(Class<T> clz, K key, F field) {
		hashDelete(clz, key, field, false);
	}

	@Override
	public <T> Map<F, V> getAll(Class<T> clz, K key, boolean cascade) {
		Map<F, V> map = objectHash.getAll(makeKey(clz, key));
		if (cascade) {
			map.forEach((k, v) -> cascadeHashGet(key, v));
		}
		return map;
	}
	
	protected abstract void cascadeHashGet(K key, V value);

	@Override
	public <T> int hashSize(Class<T> clz, K key) {
		return objectHash.hashSize(makeKey(clz, key));
	}

	@Override
	public Class<V> getValueClass() {
		return objectHash.getValueClass();
	}

	@Override
	public void hashSet(Iterable<V> values, boolean cascade) {
		K key = null;
		Class<?> clz = null;
		Map<F, V> map = Maps.newHashMap();
		for (V value : values) {
			if (key == null && value != null) {
				key = getKey(value);
				clz = getClassFromValue(value);
			}
			map.put(getField(value), value);
		}
		if (key != null) {
			hashSet(clz, key, map, cascade);
		}
	}
	
	private <T> void hashSet(Class<T> clz, K key, Map<F, V> values, boolean cascade) {
		objectHash.hashSet(makeKey(clz, key), values);
		if (cascade) {
			values.forEach((k, v) -> {if (v != null) cascadeHashSet(key, v);});
		}
	}

	@Override
	public void hashSet(Map<F, V> values, boolean cascade) {
		K key = null;
		Class<?> clz = null;
		for (V value : values.values()) {
			if (key == null && value != null) {
				key = getKey(value);
				clz = getClassFromValue(value);
			}
		}
		if (key != null) {
			hashSet(clz, key, values, cascade);
		}
	}

	@Override
	public void hashSet(V value) {
		hashSet(value, false);
	}

	@Override
	public void hashSet(Iterable<V> values) {
		hashSet(values, false);
	}

	@Override
	public <T> V hashGet(Class<T> clz, K key, F field) {
		return hashGet(clz, key, field, false);
	}

	@Override
	public <T> Map<F, V> getAll(Class<T> clz, K key) {
		return getAll(clz, key, false);
	}

	@Override
	public V hashGet(K key, F field, boolean cascade) {
		return hashGet(getParentClass(), key, field, cascade);
	}

	@Override
	public void hashDelete(K key, F field, boolean cascade) {
		hashDelete(getParentClass(), key, field, cascade);
	}

	@Override
	public Map<F, V> getAll(K key, boolean cascade) {
		return getAll(getParentClass(), key, cascade);
	}

	@Override
	public int hashSize(K key) {
		return hashSize(getParentClass(), key);
	}

	@Override
	public <T> void delete(Class<T> clz, K key) {
		delete(clz, key, false);
	}

	@Override
	public void delete(K key, boolean cascade) {
		delete(getParentClass(), key, cascade);
	}

	@Override
	public void hashSet(Map<F, V> values) {
		hashSet(values, false);
	}

	@Override
	public <T> Set<F> hashFields(Class<T> clz, K key) {
		return objectHash.hashFields(makeKey(clz, key));
	}

	@Override
	public <T> boolean hashContains(Class<T> clz, K key, F field) {
		return objectHash.hashContains(makeKey(clz, key), field);
	}

	@Override
	public boolean hashContains(K key, F field) {
		return hashContains(getParentClass(), key, field);
	}
	
	protected abstract Class<?> getParentClass();

	@Override
	public <T> void hashDelete(Class<T> clz, K key, F field, boolean cascade) {
		objectHash.hashDelete(makeKey(clz, key), field);
		if (cascade) {
			cascadeHashDelete(key, field);
		}
	}
	
	protected abstract void cascadeHashDelete(K key, F field);

	@Override
	public <T> void delete(Class<T> clz, K key, boolean cascade) {
		if (cascade) {
			cascadeDelete(key, hashFields(clz, key));
		}
		objectHash.delete(makeKey(clz, key));
	}
	
	protected void cascadeDelete(K key, Set<F> fields) {
		fields.forEach((field) -> cascadeHashDelete(key, field));
	}

	@Override
	public void hashDelete(V value, boolean cascade) {
		hashDelete(getClassFromValue(value), getKey(value), getField(value), cascade);
	}

	@Override
	public void hashDelete(V value) {
		hashDelete(value, false);
	}

}
