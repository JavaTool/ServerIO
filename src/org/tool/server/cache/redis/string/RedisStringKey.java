package org.tool.server.cache.redis.string;

import static org.tool.server.cache.ICacheKey.ValueType.None;
import static org.tool.server.cache.ICacheKey.ValueType.valueof;
import static org.tool.server.utils.DateUtil.toMilliTime;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.tool.server.cache.ICacheKey;
import org.tool.server.cache.redis.ExistsJedisReources;
import org.tool.server.cache.redis.IJedisReources;

import com.google.common.collect.ImmutableSet;

public class RedisStringKey extends ExistsJedisReources implements ICacheKey<String> {
	
	private static final Set<String> EMPTY_SET = ImmutableSet.of();
	
	public RedisStringKey(IJedisReources resources) {
		setResouces(resources);
	}

	@Override
	public void delete(Object... keys) {
		if (keys instanceof Object[]) {
			if (keys.length > 0) {
				String[] newKeys = new String[keys.length];
				for (int i = 0;i < keys.length;i++) {
					newKeys[i] = (String) keys[i];
				}
				exec((jedis) -> jedis.del(newKeys));
			}
		} else {
			exec((jedis) -> jedis.del((String[]) keys));
		}
	}

	@Override
	public boolean exists(String key) {
		return exec((jedis) -> {
			return jedis.exists(key);
		}, false);
	}

	@Override
	public void expire(String key, long time, TimeUnit timeUnit) {
		exec((jedis) -> jedis.pexpire(key, toMilliTime(time, timeUnit)));
	}

	@Override
	public void expireat(String key, long timestamp) {
		exec((jedis) -> jedis.pexpireAt(key, timestamp));
	}

	@Override
	public Set<String> keys(String pattern) {
		return exec((jedis) -> {
			return jedis.keys(pattern);
		}, EMPTY_SET);
	}

	@Override
	public void persist(String key) {
		exec((jedis) -> jedis.persist(key));
	}

	@Override
	public long ttl(String key) {
		return exec((jedis) -> {
			return jedis.pttl(key);
		}, 0L);
	}

	@Override
	public void rename(String key, String newkey) {
		exec((jedis) -> jedis.rename(key, newkey));
	}

	@Override
	public ValueType type(String key) {
		return exec((jedis) -> {
			return valueof(jedis.type(key));
		}, None);
	}

}
