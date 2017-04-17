package com.fanxing.server.cache.redis.bytes;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fanxing.server.cache.ICacheHash;
import com.fanxing.server.cache.redis.ExistsJedisReources;
import com.fanxing.server.cache.redis.IJedisReources;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

public class RedisBytesHash extends ExistsJedisReources implements ICacheHash<byte[], byte[], byte[]> {
	
	private static final Set<byte[]> EMPTY_SET = ImmutableSet.of();
	
	private static final List<byte[]> EMPTY_LIST = ImmutableList.of();
	
	private static final Map<byte[], byte[]> EMPTY_MAP = ImmutableMap.of();
	
	public RedisBytesHash(IJedisReources jedisReources) {
		setResouces(jedisReources);
	}

	@Override
	public void remove(byte[] key, Object... fields) {
		if (fields != null && fields.length > 0) {
			exec((jedis) -> jedis.hdel(key, (byte[][]) fields));
		}
	}

	@Override
	public boolean contains(byte[] key, byte[] field) {
		return exec((jedis) -> {
			return jedis.hexists(key, field);
		}, false);
	}

	@Override
	public byte[] get(byte[] key, byte[] field) {
		return exec((jedis) -> {
			return jedis.hget(key, field);
		}, null);
	}

	@Override
	public List<byte[]> multiGet(byte[] key, Object... fields) {
		return exec((jedis) -> {
			return jedis.hmget(key, (byte[][]) fields);
		}, EMPTY_LIST);
	}

	@Override
	public Map<byte[], byte[]> getAll(byte[] key) {
		return exec((jedis) -> {
			return jedis.hgetAll(key);
		}, EMPTY_MAP);
	}

	@Override
	public Set<byte[]> fields(byte[] key) {
		return exec((jedis) -> {
			return jedis.hkeys(key);
		}, EMPTY_SET);
	}

	@Override
	public long size(byte[] key) {
		return exec((jedis) -> {
			return jedis.hlen(key);
		}, 0L);
	}

	@Override
	public void multiSet(byte[] key, Map<byte[], byte[]> map) {
		if (map != null && map.size() > 0) {
			exec((jedis) -> jedis.hmset(key, map));
		}
	}

	@Override
	public void set(byte[] key, byte[] field, byte[] value) {
		exec((jedis) -> jedis.hset(key, field, value));
	}

	@Override
	public List<byte[]> values(byte[] key) {
		return exec((jedis) -> {
			return jedis.hvals(key);
		}, EMPTY_LIST);
	}

	@Override
	public long incrBy(byte[] key, byte[] field, long value) {
		return exec((jedis) -> {
			return jedis.hincrBy(key, field, value);
		}, 0L);
	}

}
