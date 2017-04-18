package org.tool.server.cache.object;

import java.util.List;

import org.tool.server.cache.IBaseCache;
import org.tool.server.cache.ICache;
import org.tool.server.cache.ICacheHash;
import org.tool.server.cache.ICacheKey;
import org.tool.server.cache.ICacheList;
import org.tool.server.cache.ICacheValue;
import org.tool.server.cache.ITransaction;

public class ObjectCache<K, F, V, S> extends CoderCache<S> implements ICache<K, F, V> {
	
	private final ICacheKey<K> cacheKey;
	
	private final ICacheHash<K, F, V> cacheHash;
	
	private final ICacheList<K, V> cacheList;
	
	private final ICacheValue<K, V> cacheValue;
	
	private final IBaseCache<S> cache;
	
	private final Class<F> fclz;
	
	private final Class<V> vclz;
	
	public ObjectCache(IBaseCache<S> cache, Class<K> kclz, Class<F> fclz, Class<V> vclz) {
		super(cache.getCoder());
		this.cache = cache;
		this.fclz = fclz;
		this.vclz = vclz;
		cacheKey = new ObjectCacheKey<>(cache.key(), cache.getCoder());
		cacheHash = new ObjectCacheHash<>(cache.hash(), cache.getCoder(), fclz, vclz);
		cacheList = new ObjectCacheList<>(cache.list(), cache.getCoder(), vclz);
		cacheValue = new ObjectCacheValue<>(cache.value(), cache.getCoder(), vclz);
	}

	@Override
	public ICacheKey<K> key() {
		return cacheKey;
	}

	@Override
	public ICacheHash<K, F, V> hash() {
		return cacheHash;
	}

	@Override
	public ICacheList<K, V> list() {
		return cacheList;
	}

	@Override
	public ICacheValue<K, V> value() {
		return cacheValue;
	}

	@Override
	public void setName(String name) {
		cache.setName(name);
	}

	@Override
	public void changeDB(int index) {
		cache.changeDB(index);
	}

	@Override
	public List<Object> transaction(ITransaction<K, F, V> transaction, @SuppressWarnings("unchecked") K... keys) {
		try {
			return cache.transaction(cacheTransaction -> {
				transaction.exec(new TransactionObjectCache<>(cacheTransaction, cache.getCoder(), fclz, vclz));
			}, cache.getCoder().serializa(keys));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
