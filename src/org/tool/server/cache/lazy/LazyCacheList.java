package org.tool.server.cache.lazy;

import org.tool.server.cache.ICacheList;

public class LazyCacheList<V> implements ILazyCacheList<V> {
	
	private final ICacheList<String, V> cacheList;
	
	private final IKey key;
	
	public LazyCacheList(ICacheList<String, V> cacheList, IKey key) {
		this.cacheList = cacheList;
		this.key = key;
	}

	@Override
	public V headPop() {
		return cacheList.headPop(key.getKey());
	}

	@Override
	public void tailPush(Object... objects) {
		cacheList.tailPush(key.getKey(), objects);
	}

	@Override
	public V get(long index) {
		return cacheList.get(key.getKey(), index);
	}

	@Override
	public long size() {
		return cacheList.size(key.getKey());
	}

	@Override
	public void trim(long start, long end) {
		cacheList.trim(key.getKey(), start, end);
	}

}
