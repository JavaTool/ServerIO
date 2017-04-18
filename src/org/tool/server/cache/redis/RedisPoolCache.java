package org.tool.server.cache.redis;

import java.util.List;

import org.tool.server.cache.IBaseCache;
import org.tool.server.cache.ICacheHash;
import org.tool.server.cache.ICacheKey;
import org.tool.server.cache.ICacheList;
import org.tool.server.cache.ICacheValue;
import org.tool.server.cache.IKVCache;
import org.tool.server.cache.ITransaction;

import com.google.common.collect.ImmutableList;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

public abstract class RedisPoolCache<T> implements IBaseCache<T> {
	
	private static final List<Object> EMPTY_LIST = ImmutableList.of();
	
	private final ICacheKey<T> cacheKey;
	
	private final ICacheHash<T, T, T> cacheHash;
	
	private final ICacheList<T, T> cacheList;
	
	private final ICacheValue<T, T> cacheValue;
	
	private final IJedisReources jedisReources;
	
	public RedisPoolCache(IJedisReources jedisReources) {
		cacheKey = createCacheKey(jedisReources);
		cacheHash = createCacheHash(jedisReources);
		cacheList = createCacheList(jedisReources);
		cacheValue = createCacheValue(jedisReources);
		this.jedisReources = jedisReources;
	}
	
	protected abstract ICacheKey<T> createCacheKey(IJedisReources jedisReources);
	
	protected abstract ICacheHash<T, T, T> createCacheHash(IJedisReources jedisReources);
	
	protected abstract ICacheList<T, T> createCacheList(IJedisReources jedisReources);
	
	protected abstract ICacheValue<T, T> createCacheValue(IJedisReources jedisReources);

	@Override
	public ICacheKey<T> key() {
		return cacheKey;
	}

	@Override
	public ICacheHash<T, T, T> hash() {
		return cacheHash;
	}

	@Override
	public ICacheList<T, T> list() {
		return cacheList;
	}

	@Override
	public ICacheValue<T, T> value() {
		return cacheValue;
	}

	@Override
	public void setName(String name) {
//		jedisReources.exec(jedis -> jedis.clientSetname(name));
	}

	@Override
	public void changeDB(int index) {
		jedisReources.changeDB(index);
	}

	@Override
	public List<Object> transaction(ITransaction<T, T, T> transaction, @SuppressWarnings("unchecked") T... keys) {
		return jedisReources.exec(jedis -> {
			watch(jedis, keys);
			Transaction trans = jedis.multi();
			transaction.exec(createKVTransaction(trans));
			return trans.exec();
		}, EMPTY_LIST);
	}
	
	protected abstract IKVCache<T, T, T> createKVTransaction(Transaction transaction);
	
	protected abstract void watch(Jedis jedis, @SuppressWarnings("unchecked") T... keys);

}
