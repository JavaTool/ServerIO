package org.tool.server.cache.object;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.tool.server.cache.ICacheHash;
import org.tool.server.coder.ICoder;

class TransactionObjectHash<K, F, V, S> implements ICacheHash<K, F, V> {
	
	private final ICacheHash<S, S, S> transaction;
	
	private final ICoder<S> coder;
	
	private final Class<F> fclz;
	
	private final Class<V> vclz;
	
	public TransactionObjectHash(ICacheHash<S, S, S> transaction, ICoder<S> coder, Class<F> fclz, Class<V> vclz) {
		this.transaction = transaction;
		this.coder = coder;
		this.fclz = fclz;
		this.vclz = vclz;
	}

	@Override
	public void remove(K key, Object... fields) {
		if (fields != null && fields.length > 0) {
			try {
				transaction.remove(coder.serializa(key), coder.serializa(fields));
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	@Override
	public boolean contains(K key, F field) {
		try {
			return transaction.contains(coder.serializa(key), coder.serializa(field));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public V get(K key, F field) {
		try {
			return coder.deserializa(transaction.get(coder.serializa(key), coder.serializa(field)), vclz);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<V> multiGet(K key, Object... fields) {
		try {
			return coder.deserializa(transaction.multiGet(coder.serializa(key), coder.serializa(fields)), vclz);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Map<F, V> getAll(K key) {
		try {
			return coder.deserializa(transaction.getAll(coder.serializa(key)), fclz, vclz);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Set<F> fields(K key) {
		try {
			return coder.deserializa(transaction.fields(coder.serializa(key)), fclz);
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
	public void multiSet(K key, Map<F, V> map) {
		if (map != null && map.size() > 0) {
			try {
				transaction.multiSet(coder.serializa(key), coder.serializa(map));
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	@Override
	public void set(K key, F field, V value) {
		try {
			transaction.set(coder.serializa(key), coder.serializa(field), coder.serializa(value));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<V> values(K key) {
		try {
			return coder.deserializa(transaction.values(coder.serializa(key)), vclz);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public long incrBy(K key, F field, long value) {
		try {
			return transaction.incrBy(coder.serializa(key), coder.serializa(field), value);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
