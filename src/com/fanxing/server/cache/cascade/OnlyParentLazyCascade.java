package com.fanxing.server.cache.cascade;

import com.fanxing.server.cache.object.hash.IObjectHash;

public abstract class OnlyParentLazyCascade<V> extends OnlyParentCascadeHash<Integer, Integer, V> implements ILazyCascade<V> {

	public OnlyParentLazyCascade(IObjectHash<String, Integer, V> objectHash) {
		super(objectHash);
	}

}
