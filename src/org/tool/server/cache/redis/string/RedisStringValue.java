package org.tool.server.cache.redis.string;

import static org.tool.server.utils.DateUtil.toMilliTime;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.tool.server.cache.ICacheValue;
import org.tool.server.cache.redis.ExistsJedisReources;
import org.tool.server.cache.redis.IJedisReources;

import com.google.common.collect.ImmutableList;

public class RedisStringValue extends ExistsJedisReources implements ICacheValue<String, String> {
	
	private static final List<String> EMPTY_LIST = ImmutableList.of();
	
	public RedisStringValue(IJedisReources resources) {
		setResouces(resources);
	}

	@Override
	public void append(String key, String value) {
		exec((jedis) -> jedis.append(key, value));
	}

	@Override
	public long decr(String key, long decrement) {
		return exec((jedis) -> {
			return jedis.decrBy(key, decrement);
		}, 0L);
	}

	@Override
	public String get(String key) {
		return exec((jedis) -> {
			return jedis.get(key);
		}, null);
	}

	@Override
	public List<String> multiGet(Object... keys) {
		return keys.length > 0 ? exec((jedis) -> {
			String[] newKeys = new String[keys.length];
			for (int i = 0;i < keys.length;i++) {
				newKeys[i] = (String) keys[i];
			}
			return jedis.mget(newKeys);
		}, EMPTY_LIST) : EMPTY_LIST;
	}

	@Override
	public long incr(String key, long increment) {
		return exec((jedis) -> {
			return jedis.incrBy(key, increment);
		}, 0L);
	}

	@Override
	public void set(String key, String value) {
		exec((jedis) -> jedis.set(key, value));
	}

	@Override
	public boolean xSet(String key, String value, boolean exists, long time, TimeUnit timeUnit) {
		return exec(jedis -> {
			return jedis.set(key, value, (exists ? "XX" : "NX"), "PX", toMilliTime(time, timeUnit)).equals("OK");
		}, false);
	}

	@Override
	public void multiSet(Map<String, String> map) {
		String[] keyValues = new String[map.size() << 1];
		int index = 0;
		for (String key : map.keySet()) {
			keyValues[index << 1] = key;
			keyValues[(index << 1) + 1] = map.get(key);
			++index;
		}

		exec((jedis) -> jedis.mset(keyValues));
	}

}
