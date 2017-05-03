package org.tool.server.cache.redis.string;

import static redis.clients.jedis.BinaryClient.LIST_POSITION.BEFORE;

import java.util.List;

import org.tool.server.cache.ICacheList;
import org.tool.server.cache.redis.ExistsJedisReources;
import org.tool.server.cache.redis.IJedisReources;

import com.google.common.collect.ImmutableList;

public class RedisStringList extends ExistsJedisReources implements ICacheList<String, String> {
	
	private static final List<String> EMPTY_LIST = ImmutableList.of();
	
	public RedisStringList(IJedisReources resources) {
		setResouces(resources);
	}

	@Override
	public String headPop(String key) {
		return exec((jedis) -> {
			return jedis.lpop(key);
		}, null);
	}

	@Override
	public void tailPush(String key, Object... objects) {
		String[] array = new String[objects.length];
		for (int i = 0;i < objects.length;i++) {
			array[i] = objects[i].toString();
		}
		exec((jedis) -> jedis.lpush(key, array));
	}

	@Override
	public String get(String key, long index) {
		return exec((jedis) -> {
			return jedis.lindex(key, index);
		}, null);
	}

	@Override
	public long size(String key) {
		return exec((jedis) -> {
			return jedis.llen(key);
		}, 0L);
	}

	@Override
	public void trim(String key, long start, long end) {
		exec((jedis) -> jedis.ltrim(key, start, end));
	}

	@Override
	public List<String> range(String key, long start, long end) {
		return exec((jedis) -> {
			return jedis.lrange(key, start, end);
		}, EMPTY_LIST);
	}

	@Override
	public void lrem(String key, String value) {
		exec((jedis) -> jedis.lrem(key, 1, value));
	}

	@Override
	public void insert(String key, long index, String value) {
		exec((jedis) -> jedis.linsert(key, BEFORE, jedis.lindex(key, index), value));
	}

	@Override
	public void set(String key, long index, String value) {
		exec((jedis) -> jedis.lset(key, index, value));
	}

}
