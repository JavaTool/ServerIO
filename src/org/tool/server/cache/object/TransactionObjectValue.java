package org.tool.server.cache.object;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.tool.server.cache.ICacheValue;
import org.tool.server.coder.ICoder;

import com.google.common.collect.Maps;

class TransactionObjectValue<K, V, S> implements ICacheValue<K, V> {
	
	private final ICacheValue<S, S> transaction;
	
	private final ICoder<S> coder;
	
	private final Class<V> vclz;
	
	public TransactionObjectValue(ICacheValue<S, S> transaction, ICoder<S> coder, Class<V> vclz) {
		this.transaction = transaction;
		this.coder = coder;
		this.vclz = vclz;
	}

	@Override
	public void append(K key, V value) {
		try {
			transaction.append(coder.serializa(key), coder.serializa(value));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public long decr(K key, long decrement) {
		try {
			return transaction.decr(coder.serializa(key), decrement);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public V get(K key) {
		try {
			return coder.deserializa(transaction.get(coder.serializa(key)), vclz);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<V> multiGet(Object... keys) {
		try {
			return coder.deserializa(transaction.multiGet(coder.serializa(keys)), vclz);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public long incr(K key, long increment) {
		try {
			return transaction.incr(coder.serializa(key), increment);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void set(K key, V value) {
		try {
			transaction.set(coder.serializa(key), coder.serializa(value));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean xSet(K key, V value, boolean exists, long time, TimeUnit timeUnit) {
		try {
			return transaction.xSet(coder.serializa(key), coder.serializa(value), exists, time, timeUnit);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void multiSet(Map<K, V> map) {
		Map<S, S> keyValues = Maps.newHashMap();
		try {
			for (K key : map.keySet()) {
					keyValues.put(coder.serializa(key), coder.serializa(map.get(key)));
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		transaction.multiSet(keyValues);
	}

}
