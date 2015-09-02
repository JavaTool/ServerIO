package com.fanxing.server.cache;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fanxing.server.persist.EntityManager;

/**
 * 会持久化的任务缓存
 * @author 	fuhuiyuan
 */
public abstract class PersistenceCache implements IScheduledCache {
	
	protected static final Logger log = LoggerFactory.getLogger(PersistenceCache.class);
	/**缓存*/
	protected final Cache cache;
	/**持久化管理器*/
	protected final EntityManager entityManager;
	/**任务*/
	protected ScheduledFuture<?> scheduledFuture;
	
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
		long time = System.currentTimeMillis();
		createSync();
		updateSync();
		deleteSync();
		log.info("Sync finish use {} ms.", System.currentTimeMillis() - time);
	}
	
	/**
	 * 同步创建
	 */
	protected abstract void createSync();
	/**
	 * 同步更新
	 */
	protected abstract void updateSync();
	/**
	 * 同步删除
	 */
	protected abstract void deleteSync();

	@Override
	public void scheduled(ScheduledExecutorService executorService) {
		scheduledFuture = executorService.scheduleWithFixedDelay(this, 0, 1, TimeUnit.MINUTES);
	}

	@Override
	public void shutdown() {
		if (scheduledFuture != null) {
			scheduledFuture.cancel(false);
		}
		run();
		log.info("Shutdown finish.");
	}

}
