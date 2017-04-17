package com.fanxing.server.cache.manager;

import java.util.Map;

import com.fanxing.server.cache.IBaseCache;
import com.fanxing.server.cache.ICache;
import com.fanxing.server.cache.object.ObjectCache;
import com.fanxing.server.cache.redis.bytes.RedisBytesPoolCache;
import com.fanxing.server.io.IConfigurationHolder;
import com.google.common.collect.Maps;

public class RedisBytesCacheManager extends RedisCacheManager {
	
	private final IBaseCache<byte[]> cache;
	
	@SuppressWarnings("rawtypes")
	private final Map<String, ICache> caches;
	
	public RedisBytesCacheManager(IConfigurationHolder configuration) {
		super(configuration);
		cache = new RedisBytesPoolCache(getJedisReources());
		caches = Maps.newHashMap();
	}
	
	public RedisBytesCacheManager(String address, int maxTotal, int maxIdle, long waitTime, String password) {
		super(address, maxTotal, maxIdle, waitTime, password);
		cache = new RedisBytesPoolCache(getJedisReources());
		caches = Maps.newHashMap();
	}

	@Override
	public <K, F, V> ICache<K, F, V> getCache(Class<K> kclz, Class<F> fclz, Class<V> vclz) {
		return getCacheOnCreate(kclz, fclz, vclz);
	}
	
	@SuppressWarnings("unchecked")
	protected <K, F, V> ICache<K, F, V> getCacheOnCreate(Class<K> kclz, Class<F> fclz, Class<V> vclz) {
		if (kclz.equals(byte[].class) && fclz.equals(byte[].class) && vclz.equals(byte[].class)) {
			return (ICache<K, F, V>) cache;
		} else {
			String key = makeClassString(kclz, fclz, vclz);
			ICache<K, F, V> cache = caches.get(key);
			if (cache == null) {
				cache = createCache(kclz, fclz, vclz);
				caches.put(key, cache);
			}
			return cache;
		}
	}
	
	protected <K, F, V> String makeClassString(Class<K> kclz, Class<F> fclz, Class<V> vclz) {
		StringBuffer keyBulider = new StringBuffer();
		keyBulider.append(kclz.getName()).append(fclz.getName()).append(vclz.getName());
		return keyBulider.toString();
	}

	@Override
	public <K, F, V> ICache<K, F, V> createCache(Class<K> kclz, Class<F> fclz, Class<V> vclz) {
		return new ObjectCache<>(cache, kclz, fclz, vclz);
	}

	@Override
	public void setName(String name) {
		cache.setName(name);
	}

	@Override
	public void changeDB(int index) {
		cache.changeDB(index);
	}

}
