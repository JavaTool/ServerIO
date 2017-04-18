package org.tool.server.cache.object;

import org.tool.server.cache.ICacheHash;
import org.tool.server.cache.ICacheKey;
import org.tool.server.cache.ICacheList;
import org.tool.server.cache.ICacheValue;
import org.tool.server.cache.IKVCache;
import org.tool.server.coder.ICoder;

class TransactionObjectCache<K, F, V, S> implements IKVCache<K, F, V> {
	
	private final ICacheKey<K> key;
	
	private final ICacheHash<K, F, V> hash;
	
	private final ICacheList<K, V> list;
	
	private final ICacheValue<K, V> value;

	public TransactionObjectCache(IKVCache<S, S, S> transaction, ICoder<S> coder, Class<F> fclz, Class<V> vclz) {
		hash = new TransactionObjectHash<>(transaction.hash(), coder, fclz, vclz);
		key = new TransactionObjectKey<>(transaction.key(), coder);
		list = new TransactionObjectList<>(transaction.list(), coder, vclz);
		value = new TransactionObjectValue<>(transaction.value(), coder, vclz);
	}

	@Override
	public ICacheKey<K> key() {
		return key;
	}

	@Override
	public ICacheHash<K, F, V> hash() {
		return hash;
	}

	@Override
	public ICacheList<K, V> list() {
		return list;
	}

	@Override
	public ICacheValue<K, V> value() {
		return value;
	}

}
