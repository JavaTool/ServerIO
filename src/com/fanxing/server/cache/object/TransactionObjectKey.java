package com.fanxing.server.cache.object;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.fanxing.server.cache.ICacheKey;
import com.fanxing.server.coder.ICoder;

class TransactionObjectKey<K, S> implements ICacheKey<K> {
	
	private final ICacheKey<S> transaction;
	
	private final ICoder<S> coder;
	
	public TransactionObjectKey(ICacheKey<S> transaction, ICoder<S> coder) {
		this.transaction = transaction;
		this.coder = coder;
	}

	@Override
	public void delete(Object... keys) {
		try {
			transaction.delete(coder.serializa(keys));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean exists(K key) {
		try {
			return transaction.exists(coder.serializa(key));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void expire(K key, long time, TimeUnit timeUnit) {
		try {
			transaction.expire(coder.serializa(key), time, timeUnit);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void expireat(K key, long timestamp) {
		try {
			transaction.expireat(coder.serializa(key), timestamp);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<K> keys(K pattern) {
		try {
			return (Set<K>) coder.deserializa(transaction.keys(coder.serializa(pattern)), pattern.getClass());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void persist(K key) {
		try {
			transaction.persist(coder.serializa(key));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public long ttl(K key) {
		try {
			return transaction.ttl(coder.serializa(key));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void rename(K key, K newkey) {
		try {
			transaction.rename(coder.serializa(key), coder.serializa(newkey));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public ValueType type(K key) {
		try {
			return transaction.type(coder.serializa(key));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
