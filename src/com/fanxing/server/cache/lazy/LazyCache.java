package com.fanxing.server.cache.lazy;

import com.fanxing.server.cache.IKVCache;

public class LazyCache<F, V> implements ILazyCache<F, V> {
	
	private final ILazyCacheKey lazyCacheKey;
	
	private final ILazyCacheHash<F, V> lazyCacheHash;
	
	private final ILazyCacheList<V> lazyCacheList;
	
	private final ILazyCacheValue<V> lazyCacheValue;
	
	public LazyCache(IKVCache<String, F, V> cache, String preKey) {
		lazyCacheKey = new LazyCacheKey(cache.key(), preKey);
		lazyCacheHash = new LazyCacheHash<>(cache.hash(), lazyCacheKey);
		lazyCacheList = new LazyCacheList<>(cache.list(), lazyCacheKey);
		lazyCacheValue = new LazyCacheValue<>(cache.value(), lazyCacheKey);
	}

	@Override
	public ILazyCacheKey key() {
		return lazyCacheKey;
	}

	@Override
	public ILazyCacheHash<F, V> hash() {
		return lazyCacheHash;
	}

	@Override
	public ILazyCacheList<V> list() {
		return lazyCacheList;
	}

	@Override
	public ILazyCacheValue<V> value() {
		return lazyCacheValue;
	}

}
