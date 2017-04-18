package org.tool.server.cache.cascade;

import org.tool.server.cache.object.hash.IObjectHash;

public abstract class OnlySubLazyCascade<V> extends OnlySubCascadeHash<Integer, Integer, V> implements ILazyCascade<V> {

	public OnlySubLazyCascade(IObjectHash<String, Integer, V> objectHash) {
		super(objectHash);
	}

}
