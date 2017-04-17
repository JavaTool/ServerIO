package com.fanxing.server.cache.object;

import com.fanxing.server.cache.ICacheHash;
import com.fanxing.server.cache.ICacheKey;
import com.fanxing.server.cache.ICacheList;
import com.fanxing.server.cache.ICacheValue;
import com.fanxing.server.cache.IKVCache;
import com.fanxing.server.coder.ICoder;

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
