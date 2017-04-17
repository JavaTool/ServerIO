package com.fanxing.server.cache.cascade;

import java.util.Map;
import java.util.Set;

import com.fanxing.server.cache.object.hash.IObjectHash;

public abstract class OnlyParentCascadeHash<K, F, V> extends CascadeHash<K, F, V> {
	
	private static final Class<?> CLASS = OnlyParentCascadeHash.class;
	
	public OnlyParentCascadeHash(IObjectHash<String, F, V> objectHash) {
		super(objectHash);
	}

	@Override
	protected Class<?> getClassFromValue(V value) {
		return getParentClass();
	}

	@Override
	protected Class<?> getParentClass() {
		return CLASS;
	}

	@Override
	public <T> V hashGet(Class<T> clz, K key, F field, boolean cascade) {
		return super.hashGet(CLASS, key, field, cascade);
	}

	@Override
	public <T> void hashDelete(Class<T> clz, K key, F field) {
		super.hashDelete(CLASS, key, field);
	}

	@Override
	public <T> Map<F, V> getAll(Class<T> clz, K key, boolean cascade) {
		return super.getAll(CLASS, key, cascade);
	}

	@Override
	public <T> int hashSize(Class<T> clz, K key) {
		return super.hashSize(CLASS, key);
	}

	@Override
	public <T> V hashGet(Class<T> clz, K key, F field) {
		return super.hashGet(CLASS, key, field);
	}

	@Override
	public <T> Map<F, V> getAll(Class<T> clz, K key) {
		return super.getAll(CLASS, key);
	}

	@Override
	public <T> void delete(Class<T> clz, K key) {
		super.delete(CLASS, key);
	}

	@Override
	protected K getKey(V value) {
		return null;
	}

	@Override
	public <T> Set<F> hashFields(Class<T> clz, K key) {
		return super.hashFields(getParentClass(), key);
	}

}
