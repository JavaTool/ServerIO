package org.tool.server.cache.object;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.tool.server.cache.ICacheKey;
import org.tool.server.coder.ICoder;

public class ObjectCacheKey<T, S> extends CoderCache<S> implements ICacheKey<T> {
	
	private final ICacheKey<S> cacheKey;
	
	public ObjectCacheKey(ICacheKey<S> cacheKey, ICoder<S> coder) {
		super(coder);
		this.cacheKey = cacheKey;
	}

	@Override
	public void delete(Object... keys) {
		cacheKey.delete(serializa(keys));
	}

	@Override
	public boolean exists(T key) {
		return cacheKey.exists(serializa(key));
	}

	@Override
	public void expire(T key, long time, TimeUnit timeUnit) {
		cacheKey.expire(serializa(key), time, timeUnit);
	}

	@Override
	public void expireat(T key, long timestamp) {
		cacheKey.expireat(serializa(key), timestamp);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<T> keys(T pattern) {
		return (Set<T>) deserializa(cacheKey.keys(serializa(pattern)), pattern.getClass());
	}

	@Override
	public void persist(T key) {
		cacheKey.persist(serializa(key));
	}

	@Override
	public long ttl(T key) {
		return cacheKey.ttl(serializa(key));
	}

	@Override
	public void rename(T key, T newkey) {
		cacheKey.rename(serializa(key), serializa(newkey));
	}

	@Override
	public ValueType type(T key) {
		return cacheKey.type(serializa(key));
	}

}
