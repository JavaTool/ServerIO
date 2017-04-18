package org.tool.server.cache.object.hash;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

import org.tool.server.cache.manager.ICacheManager;

public abstract class ExpireObjectHash<K, F, V> extends ObjectHash<K, F, V> {

	public ExpireObjectHash(ICacheManager cacheManager, Class<F> fclz, Class<V> vclz, String preKey) {
		super(cacheManager, fclz, vclz, preKey);
	}
	
	@Override
	public void hashSet(K key, F field, V value) {
		String cacheKey = makeKey(key);
		boolean isCache = cache.key().exists(cacheKey);
		nativeHashSet(key, field, value);
		if (!isCache) {
			cache.key().expire(cacheKey, getExpireTime(value), MILLISECONDS);
		}
	}
	
	protected abstract long getExpireTime(V value);

}
