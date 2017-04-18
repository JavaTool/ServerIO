package org.tool.server.cache.redis.bytes;

import static org.tool.server.cache.ICacheKey.ValueType.None;
import static org.tool.server.cache.ICacheKey.ValueType.valueof;
import static org.tool.server.utils.DateUtil.toMilliTime;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.tool.server.cache.ICacheKey;
import org.tool.server.cache.redis.ExistsJedisReources;
import org.tool.server.cache.redis.IJedisReources;

import com.google.common.collect.ImmutableSet;

public class RedisBytesKey extends ExistsJedisReources implements ICacheKey<byte[]> {
	
	private static final Set<byte[]> EMPTY_SET = ImmutableSet.of();
	
	public RedisBytesKey(IJedisReources jedisReources) {
		setResouces(jedisReources);
	}

	@Override
	public void delete(Object... keys) {
		if (keys instanceof Object[]) {
			if (keys.length > 0) {
				byte[][] newKeys = new byte[keys.length][];
				for (int i = 0;i < keys.length;i++) {
					newKeys[i] = (byte[]) keys[i];
				}
				exec((jedis) -> jedis.del(newKeys));
			}
		} else {
			exec((jedis) -> jedis.del((byte[][]) keys));
		}
	}

	@Override
	public boolean exists(byte[] key) {
		return exec((jedis) -> {
			return jedis.exists(key);
		}, false);
	}

	@Override
	public void expire(byte[] key, long time, TimeUnit timeUnit) {
		exec((jedis) -> jedis.pexpire(key, toMilliTime(time, timeUnit)));
	}

	@Override
	public void expireat(byte[] key, long timestamp) {
		exec((jedis) -> jedis.pexpireAt(key, timestamp));
	}

	@Override
	public Set<byte[]> keys(byte[] pattern) {
		return exec((jedis) -> {
			return jedis.keys(pattern);
		}, EMPTY_SET);
	}

	@Override
	public void persist(byte[] key) {
		exec((jedis) -> jedis.persist(key));
	}

	@Override
	public long ttl(byte[] key) {
		return exec((jedis) -> {
			return jedis.pttl(key);
		}, 0L);
	}

	@Override
	public void rename(byte[] key, byte[] newkey) {
		exec((jedis) -> jedis.rename(key, newkey));
	}

	@Override
	public ValueType type(byte[] key) {
		return exec((jedis) -> {
			return valueof(jedis.type(key));
		}, None);
	}

}
