package org.tool.server.cache.cascade;

import org.tool.server.cache.object.hash.IObjectHash;

public abstract class OnlyParentLazyCascade<V> extends OnlyParentCascadeHash<Integer, Integer, V> implements ILazyCascade<V> {

	public OnlyParentLazyCascade(IObjectHash<String, Integer, V> objectHash) {
		super(objectHash);
	}

}
