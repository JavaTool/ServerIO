package com.fanxing.server.cache.cascade;

import com.fanxing.server.cache.object.hash.IObjectHash;

public abstract class LazyCascade<V> extends CascadeHash<Integer, Integer, V> implements ILazyCascade<V> {

	public LazyCascade(IObjectHash<String, Integer, V> objectHash) {
		super(objectHash);
	}

}
