package com.fanxing.server.io.configuration;

import static com.google.common.collect.Lists.newArrayListWithCapacity;
import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fanxing.server.cache.ICache;
import com.fanxing.server.cache.redis.IJedisReources;
import com.fanxing.server.cache.redis.SupportIndexJedisResources;
import com.fanxing.server.cache.redis.string.RedisStringPoolCache;

public abstract class ExcelDataCache {
	
	private ICache<String, String, String> cache;
	
	private IJedisReources jedisReources;
	
	public ExcelDataCache(String redisHosts, int maxTotal, int maxIdle, int waitTime, String password){
		jedisReources = new SupportIndexJedisResources(redisHosts, maxTotal, maxIdle, waitTime, password);
		cache = new RedisStringPoolCache(jedisReources);
	}
	
	public String getString(String key, String field){
		return cache.hash().get(key, field);
	}
	
	public String getString(String key, String field, Map<String, String> map){
		String value = map.get(field);
		if(value == null){
			value = getString(key, field);
			map.put(field, value);
			return value;
		}
		return value;
	}
	
	public int getInt(String key, String field){
		return parseInt(getString(key, field));
	}
	
	public int getInt(String key, String field, Map<String, Integer> map){
		Integer value = map.get(field);
		if(value == null){
			value = getInt(key, field);
			map.put(field, value);
		}
		return value;
		
	}
	
	public float getFloat(String key, String field){
		return parseFloat(getString(key, field));
	}
	
	public float getFloat(String key, String field, Map<String, Float> map){
		Float value = map.get(field);
		if(value == null){
			value = getFloat(key, field);
			map.put(field, value);
		}
		return value;
	}
	
	public int getKeyCount(String prekey){
		Set<String> keys = cache.key().keys(prekey + "*");
		return keys.size();
	}
	
	public IJedisReources getJedisReources(){
		return jedisReources;
	}
	
	protected <T> List<T> loadConf(String preKey, Class<T> clz) throws Exception {
		int size = getKeyCount(preKey);
		List<T> list = newArrayListWithCapacity(size);
		for (int i = 0, j = 4; i < size; i++, j++) {
			list.add(clz.getConstructor(ExcelDataCache.class, String.class, int.class).newInstance(this, preKey, j));
		}
		return list;
	}
}
