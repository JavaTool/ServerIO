package org.tool.server.cache.lazy.hash;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

import org.tool.server.cache.manager.ICachePlatform;

public abstract class ExpireLazyHash<F, V> extends LazyHash<F, V> {

	public ExpireLazyHash(ICachePlatform cachePlatform, Class<F> fclz, Class<V> vclz, String preKey) {
		super(cachePlatform, fclz, vclz, preKey);
	}
	
	@Override
	public void hashSet(F field, V value) {
		boolean isCache = cache.key().exists();
		nativeHashSet(field, value);
		if (!isCache) {
			cache.key().expire(getExpireTime(value), MILLISECONDS);
		}
	}
	
	protected abstract long getExpireTime(V value);

}
