package com.fanxing.server.cache;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fanxing.server.persist.EntityManager;

public abstract class PersistenceCache implements IScheduledCache {
	
	protected final Cache cache;
	
	protected final EntityManager entityManager;
	
	public PersistenceCache(Cache cache, EntityManager entityManager) {
		this.cache = cache;
		this.entityManager = entityManager;
	}

	@Override
	public boolean exists(Serializable key) {
		return cache.exists(key);
	}

	@Override
	public boolean hexists(Serializable key, Serializable name) {
		return cache.hexists(key, name);
	}
	
	@Override
	public void set(Serializable key, Serializable object) {
		cache.set(key, object);
	}

	@Override
	public void set(Serializable key, Serializable object, int timeout) {
		cache.set(key, object, timeout);
	}

	@Override
	public void hset(Serializable key, Serializable name, Serializable object) {
		cache.hset(key, name, object);
	}

	@Override
	public void mSet(Map<Serializable, Serializable> map) {
		cache.mSet(map);
	}

	@Override
	public void hmSet(Serializable key, Map<Serializable, Serializable> map) {
		cache.hmSet(key, map);
	}

	@Override
	public void del(Serializable key) {
		cache.del(key);
	}

	@Override
	public void hdel(Serializable key, Serializable name) {
		cache.hdel(key, name);
	}

	@Override
	public void mDel(Serializable... keys) {
		cache.mDel(keys);
	}

	@Override
	public void hmDel(Serializable key, Serializable... names) {
		cache.hmDel(key, names);
	}

	@Override
	public Serializable get(Serializable key) {
		return cache.get(key);
	}

	@Override
	public Serializable hget(Serializable key, Serializable name) {
		return cache.hget(key, name);
	}

	@Override
	public List<Serializable> mGet(Serializable... keys) {
		return cache.mGet(keys);
	}

	@Override
	public List<Serializable> hmGet(Serializable key, Serializable... names) {
		return cache.hmGet(key, names);
	}

	@Override
	public Map<Serializable, Serializable> hGetAll(Serializable key) {
		return cache.hGetAll(key);
	}

	@Override
	public Set<Serializable> hKeys(Serializable key) {
		return cache.hKeys(key);
	}

	@Override
	public void clear() {
		cache.clear();
	}

	@Override
	public long hlen(Serializable key) {
		return cache.hlen(key);
	}

	@Override
	public void run() {
		createSync();
		updateSync();
		deleteSync();
	}
	
	protected abstract void createSync();
	
	protected abstract void updateSync();
	
	protected abstract void deleteSync();

}
