package org.tool.server.cache.redis.bytes;

import static redis.clients.jedis.BinaryClient.LIST_POSITION.BEFORE;

import java.util.List;

import org.tool.server.cache.ICacheList;
import org.tool.server.cache.redis.ExistsJedisReources;
import org.tool.server.cache.redis.IJedisReources;

import com.google.common.collect.ImmutableList;

public class RedisBytesList extends ExistsJedisReources implements ICacheList<byte[], byte[]> {
	
	private static final List<byte[]> EMPTY_LIST = ImmutableList.of();
	
	public RedisBytesList(IJedisReources jedisReources) {
		setResouces(jedisReources);
	}

	@Override
	public byte[] headPop(byte[] key) {
		return exec((jedis) -> {
			return jedis.lpop(key);
		}, null);
	}

	@Override
	public void tailPush(byte[] key, Object... objects) {
		exec((jedis) -> jedis.lpush(key, (byte[][]) objects));
	}

	@Override
	public byte[] get(byte[] key, long index) {
		return exec((jedis) -> {
			return jedis.lindex(key, index);
		}, null);
	}

	@Override
	public long size(byte[] key) {
		return exec((jedis) -> {
			return jedis.llen(key);
		}, 0L);
	}

	@Override
	public void trim(byte[] key, long start, long end) {
		exec((jedis) -> jedis.ltrim(key, start, end));
	}

	@Override
	public List<byte[]> range(byte[] key, long start, long end) {
		return exec((jedis) -> {
			return jedis.lrange(key, start, end);
		}, EMPTY_LIST);
	}

	@Override
	public void lrem(byte[] key, byte[] value) {
		exec((jedis) -> jedis.lrem(key, 1, value));
	}

	@Override
	public void insert(byte[] key, long index, byte[] value) {
		exec((jedis) -> jedis.linsert(key, BEFORE, jedis.lindex(key, index), value));
	}

	@Override
	public void set(byte[] key, long index, byte[] value) {
		exec((jedis) -> jedis.lset(key, index, value));
	}

}
