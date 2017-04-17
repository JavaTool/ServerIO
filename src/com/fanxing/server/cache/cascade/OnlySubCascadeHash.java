package com.fanxing.server.cache.cascade;

import java.util.Set;

import com.fanxing.server.cache.object.hash.IObjectHash;

public abstract class OnlySubCascadeHash<K, F, V> extends CascadeHash<K, F, V> {
	
	public OnlySubCascadeHash(IObjectHash<String, F, V> objectHash) {
		super(objectHash);
	}

	@Override
	protected void cascadeHashSet(K key, V value) {}

	@Override
	protected void cascadeHashGet(K key, V value) {}

	@Override
	protected void cascadeHashDelete(K key, F field) {}

	@Override
	protected void cascadeDelete(K key, Set<F> fields) {}

}
