package com.fanxing.server.cache.manager;

import java.util.Map;

import com.fanxing.server.cache.cascade.ICascadeHash;
import com.fanxing.server.cache.cascade.ILazyCascade;
import com.fanxing.server.cache.lazy.ILazyCache;
import com.fanxing.server.cache.lazy.LazyCache;
import com.fanxing.server.cache.lazy.hash.DayRefreshLazyHash;
import com.fanxing.server.cache.lazy.hash.ILazyHash;
import com.fanxing.server.cache.lazy.hash.LazyHash;
import com.fanxing.server.cache.object.hash.DayRefreshObjectHash;
import com.fanxing.server.cache.object.hash.IObjectHash;
import com.fanxing.server.cache.object.hash.ObjectHash;
import com.fanxing.server.io.IConfigurationHolder;
import com.google.common.collect.Maps;

public class RedisCachePlatform extends RedisBytesCacheManager implements ICachePlatform {
	
	@SuppressWarnings("rawtypes")
	protected final Map<String, ILazyCache> lazyCaches;
	
	@SuppressWarnings("rawtypes")
	protected final Map<Class, ILazyHash> lazyHashs;
	
	@SuppressWarnings("rawtypes")
	protected final Map<Class, IObjectHash> objectHashs;
	
	@SuppressWarnings("rawtypes")
	protected final Map<Class, ICascadeHash> cascadeHashs;

	public RedisCachePlatform(IConfigurationHolder configuration) {
		super(configuration);
		lazyCaches = Maps.newHashMap();
		lazyHashs = Maps.newHashMap();
		objectHashs = Maps.newHashMap();
		cascadeHashs = Maps.newHashMap();
	}
	
	public RedisCachePlatform(String address, int maxTotal, int maxIdle, long waitTime, String password) {
		super(address, maxTotal, maxIdle, waitTime, password);
		lazyCaches = Maps.newHashMap();
		lazyHashs = Maps.newHashMap();
		objectHashs = Maps.newHashMap();
		cascadeHashs = Maps.newHashMap();
	}
	
	@Override
	public <F, V> ILazyCache<F, V> createLazyCache(Class<F> fclz, Class<V> vclz, String preKey) {
		return new LazyCache<>(getCache(String.class, fclz, vclz), preKey);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <F, V> ILazyHash<F, V> getLazyHash(Class<V> clz) {
		return lazyHashs.get(clz);
	}

	@Override
	public <F, V> ILazyHash<F, V> addLazyHashOnCreate(Class<F> fclz, Class<V> vclz, String preKey) {
		ILazyHash<F, V> cache = new LazyHash<>(this, fclz, vclz, preKey);
		addLazyHash(cache);
		return cache;
	}

	@Override
	public <F, V> ILazyHash<F, V> addDayRefreshLazyHashOnCreate(Class<F> fclz, Class<V> vclz, String preKey) {
		ILazyHash<F, V> cache = new DayRefreshLazyHash<>(this, fclz, vclz, preKey);
		addLazyHash(cache);
		return cache;
	}

	@Override
	public <F, V> void addLazyHash(ILazyHash<F, V> lazyHash) {
		lazyHashs.put(lazyHash.getValueClass(), lazyHash);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <K, F, V> IObjectHash<K, F, V> getObjectHash(Class<V> clz) {
		return objectHashs.get(clz);
	}

	@Override
	public <K, F, V> IObjectHash<K, F, V> addObjectHashOnCreate(Class<K> kclz, Class<F> fclz, Class<V> vclz, String preKey) {
		IObjectHash<K, F, V> cache = getObjectHash(vclz);
		if (cache == null) {
			cache = new ObjectHash<>(this, fclz, vclz, preKey);
			addObjectHash(cache);
		}
		return cache;
	}

	@Override
	public <K, F, V> void addObjectHash(IObjectHash<K, F, V> objectHash) {
		objectHashs.put(objectHash.getValueClass(), objectHash);
	}

	@Override
	public <K, F, V> IObjectHash<K, F, V> addDayRefreshObjectHashOnCreate(Class<K> kclz, Class<F> fclz, Class<V> vclz, String preKey) {
		IObjectHash<K, F, V> cache = new DayRefreshObjectHash<>(this, fclz, vclz, preKey);
		addObjectHash(cache);
		return cache;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <K, F, V> ICascadeHash<K, F, V> getCascadeHash(Class<V> clz) {
		return cascadeHashs.get(clz);
	}

	@Override
	public <K, F, V> void addCascadeHash(ICascadeHash<K, F, V> cascadeHash) {
		cascadeHashs.put(cascadeHash.getValueClass(), cascadeHash);
	}

	@Override
	public <V> ILazyCascade<V> getLazyCascade(Class<V> clz) {
		ICascadeHash<Integer, Integer, V> cascadeHash = getCascadeHash(clz);
		return (ILazyCascade<V>) cascadeHash;
	}

	@Override
	public <V> void addLazyCascade(ILazyCascade<V> lazyCascade) {
		addCascadeHash(lazyCascade);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <F, V> ILazyCache<F, V> getLazyCache(Class<F> fclz, Class<V> vclz) {
		return lazyCaches.get(makeClassString(String.class, fclz, vclz));
	}

	@Override
	public <F, V> void addLazyCache(Class<F> fclz, Class<V> vclz, ILazyCache<F, V> lazyCache) {
		lazyCaches.put(makeClassString(String.class, fclz, vclz), lazyCache);
	}
	
//	private <K, F, V> String makeClassString(Class<K> kclz, Class<F> fclz, Class<V> vclz) {
//		StringBuffer keyBulider = new StringBuffer();
//		keyBulider.append(kclz.getName()).append(fclz.getName()).append(vclz.getName());
//		return keyBulider.toString();
//	}

}
