package com.fanxing.server.cache.object;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fanxing.server.cache.ICacheHash;
import com.fanxing.server.coder.ICoder;

public class ObjectCacheHash<T, F, V, S> extends CoderCache<S> implements ICacheHash<T, F, V> {
	
	private final ICacheHash<S, S, S> cacheHash;
	
	private final Class<F> fclz;
	
	private final Class<V> vclz;

	public ObjectCacheHash(ICacheHash<S, S, S> cacheHash, ICoder<S> coder) {
		this(cacheHash, coder, null, null);
	}

	public ObjectCacheHash(ICacheHash<S, S, S> cacheHash, ICoder<S> coder, Class<F> fclz, Class<V> vclz) {
		super(coder);
		this.cacheHash = cacheHash;
		this.fclz = fclz;
		this.vclz = vclz;
	}

	@Override
	public void remove(T key, Object... fields) {
		cacheHash.remove(serializa(key), serializa(fields));
	}

	@Override
	public boolean contains(T key, F field) {
		return cacheHash.contains(serializa(key), serializa(field));
	}

	@Override
	public V get(T key, F field) {
		return deserializa(cacheHash.get(serializa(key), serializa(field)), vclz);
	}

	@Override
	public List<V> multiGet(T key, Object... fields) {
		return deserializa(cacheHash.multiGet(serializa(key), serializa(fields)), vclz);
	}

	@Override
	public Map<F, V> getAll(T key) {
		return deserializa(cacheHash.getAll(serializa(key)), fclz, vclz);
	}

	@Override
	public Set<F> fields(T key) {
		return deserializa(cacheHash.fields(serializa(key)), fclz);
	}

	@Override
	public long size(T key) {
		return cacheHash.size(serializa(key));
	}

	@Override
	public void multiSet(T key, Map<F, V> map) {
		cacheHash.multiSet(serializa(key), serializa(map));
	}

	@Override
	public void set(T key, F field, V value) {
		cacheHash.set(serializa(key), serializa(field), serializa(value));
	}

	@Override
	public List<V> values(T key) {
		return deserializa(cacheHash.values(serializa(key)), vclz);
	}

	@Override
	public long incrBy(T key, F field, long value) {
		return cacheHash.incrBy(serializa(key), serializa(field), value);
	}

}
