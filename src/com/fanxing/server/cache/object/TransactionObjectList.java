package com.fanxing.server.cache.object;

import java.util.List;

import com.fanxing.server.cache.ICacheList;
import com.fanxing.server.coder.ICoder;

class TransactionObjectList<K, V, S> implements ICacheList<K, V> {
	
	private final ICacheList<S, S> transaction;
	
	private final ICoder<S> coder;
	
	private final Class<V> vclz;
	
	public TransactionObjectList(ICacheList<S, S> transaction, ICoder<S> coder, Class<V> vclz) {
		this.transaction = transaction;
		this.coder = coder;
		this.vclz = vclz;
	}

	@Override
	public V headPop(K key) {
		try {
			return coder.deserializa(transaction.headPop(coder.serializa(key)), vclz);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void tailPush(K key, Object... objects) {
		try {
			transaction.tailPush(coder.serializa(key), coder.serializa(objects));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public V get(K key, long index) {
		try {
			return coder.deserializa(transaction.get(coder.serializa(key), index), vclz);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public long size(K key) {
		try {
			return transaction.size(coder.serializa(key));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void trim(K key, long start, long end) {
		try {
			transaction.trim(coder.serializa(key), start, end);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<V> range(K key, long start, long end) {
		try {
			return coder.deserializa(transaction.range(coder.serializa(key), start, end), vclz);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void lrem(K key, V value) {
		try {
			transaction.lrem(coder.serializa(key), coder.serializa(value));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void insert(K key, long index, V value) {
		try {
			transaction.insert(coder.serializa(key), index, coder.serializa(value));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void set(K key, long index, V value) {
		try {
			transaction.set(coder.serializa(key), index, coder.serializa(value));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
