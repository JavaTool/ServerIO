package com.fanxing.server.cache.manager;

import java.util.Map;

import com.fanxing.server.cache.IBaseCache;
import com.fanxing.server.cache.ICache;
import com.fanxing.server.cache.object.ObjectCache;
import com.fanxing.server.cache.redis.string.RedisStringPoolCache;
import com.fanxing.server.coder.string.IStringCoder;
import com.fanxing.server.coder.string.IntToStringCoder;
import com.fanxing.server.coder.string.StringToStringCoder;
import com.fanxing.server.io.IConfigurationHolder;
import com.google.common.collect.Maps;

public class RedisStringCacheManager extends RedisCacheManager {
	
	private final IBaseCache<String> cache;
	
	public RedisStringCacheManager(IConfigurationHolder configuration) {
		super(configuration);
		cache = new RedisStringPoolCache(getJedisReources());
	}
	
	public RedisStringCacheManager(String address, int maxTotal, int maxIdle, long waitTime, String password) {
		super(address, maxTotal, maxIdle, waitTime, password);
		cache = new RedisStringPoolCache(getJedisReources());
	}
	
	@SuppressWarnings("rawtypes")
	protected Map<Class, IStringCoder> createStringCoders() {
		Map<Class, IStringCoder> stringCoders = Maps.newHashMap();
		stringCoders.put(Integer.class, new IntToStringCoder());
		stringCoders.put(String.class, new StringToStringCoder());
		return stringCoders;
	}

	@SuppressWarnings({ "unchecked"})
	@Override
	public <K, F, V> ICache<K, F, V> createCache(Class<K> kclz, Class<F> fclz, Class<V> vclz) {
		if (kclz.equals(String.class) && fclz.equals(String.class) && vclz.equals(String.class)) {
			return (ICache<K, F, V>) cache;
		} else {
//			IStringCoder keyCoder = checkNotNull(stringCoders.get(kclz));
//			IStringCoder fieldCoder = checkNotNull(stringCoders.get(fclz));
//			IStringCoder valueCoder = stringCoders.containsKey(vclz) ? stringCoders.get(kclz) : objectCoder;
//			return new StringObjectCache<>(cache, keyCoder, fieldCoder, valueCoder);
			return new ObjectCache<>(cache, kclz, fclz, vclz);
		}
	}

	@Override
	public <K, F, V> ICache<K, F, V> getCache(Class<K> kclz, Class<F> fclz, Class<V> vclz) {
		return createCache(kclz, fclz, vclz);
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
