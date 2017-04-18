package org.tool.server.cache.cascade;

import org.tool.server.cache.object.hash.IObjectHash;

public abstract class LazyCascade<V> extends CascadeHash<Integer, Integer, V> implements ILazyCascade<V> {

	public LazyCascade(IObjectHash<String, Integer, V> objectHash) {
		super(objectHash);
	}

}
