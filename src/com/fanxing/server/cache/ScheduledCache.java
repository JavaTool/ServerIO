package com.fanxing.server.cache;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.fanxing.server.persist.EntityManager;

public class ScheduledCache extends PersistenceCache {
	
	/**创建-计划任务的键队列*/
	private final Queue<Serializable> createKeys;
	/**更新-计划任务的键队列*/
	private final Queue<Serializable> updateKeys;
	/**删除-计划任务的键队列*/
	private final Queue<Serializable> deleteKeys;
	/**创建-计划任务的名称队列集合*/
	private final Map<Serializable, Queue<Serializable>> createHmap;
	/**更新-计划任务的名称队列集合*/
	private final Map<Serializable, Queue<Serializable>> updateHmap;
	/**删除-计划任务的名称队列集合*/
	private final Map<Serializable, Queue<Serializable>> deleteHmap;
	/**键的持久化后是否删除缓存的标志集合*/
	private final Map<Serializable, Boolean> keyCaches;
	/**名称的持久化后是否删除缓存的标志集合*/
	private final Map<Serializable, Boolean> nameCaches;
	
	public ScheduledCache(Cache cache, EntityManager entityManager) {
		super(cache, entityManager);
		
		createKeys = new ConcurrentLinkedQueue<Serializable>();
		updateKeys = new ConcurrentLinkedQueue<Serializable>();
		deleteKeys = new ConcurrentLinkedQueue<Serializable>();
		createHmap = new ConcurrentHashMap<Serializable, Queue<Serializable>>();
		updateHmap = new ConcurrentHashMap<Serializable, Queue<Serializable>>();
		deleteHmap = new ConcurrentHashMap<Serializable, Queue<Serializable>>();
		keyCaches = new ConcurrentHashMap<Serializable, Boolean>();
		nameCaches = new ConcurrentHashMap<Serializable, Boolean>();
	}
	
	@Override
	protected void createSync() {
		while (createKeys.size() > 0) {
			Serializable key = createKeys.poll();
			if (createHmap.containsKey(key)) { // HSet
				Queue<Serializable> queue = createHmap.get(key);
				Serializable[] names = queue.toArray(new Serializable[queue.size()]);
				List<Serializable> list = hmGet(key, names);
				entityManager.createSync(list.toArray(new Serializable[list.size()]));
				tryDeleteCache(key, queue);
			} else { // Set
				entityManager.createSync(get(key));
				tryDeleteCache(key);
			}
		}
	}
	
	/**
	 * 尝试删除缓存对象
	 * @param 	key
	 * 			键
	 * @param 	queue
	 * 			名称队列
	 */
	protected void tryDeleteCache(Serializable key, Queue<Serializable> queue) {
		int size = queue.size();
		for (int i = 0;i < size;i++) {
			Serializable name = queue.poll();
			if (!nameCaches.containsKey(name) || nameCaches.get(name)) {
				queue.add(name);
			}
		}
		Serializable[] names = queue.toArray(new Serializable[queue.size()]);
		hdel(key, names);
	}
	
	protected void tryDeleteCache(Serializable key) {
		if (keyCaches.containsKey(key) && !keyCaches.get(key)) {
			del(key);
		}
	}

	@Override
	protected void updateSync() {
		while (updateKeys.size() > 0) {
			Serializable key = updateKeys.poll();
			if (updateHmap.containsKey(key)) {
				Queue<Serializable> queue = updateHmap.get(key);
				Serializable[] names = queue.toArray(new Serializable[queue.size()]);
				List<Serializable> list = hmGet(key, names);
				entityManager.updateSync(list.toArray(new Serializable[list.size()]));
				tryDeleteCache(key, queue);
			} else {
				entityManager.updateSync(get(key));
				tryDeleteCache(key);
			}
		}
	}

	@Override
	protected void deleteSync() {
		while (deleteKeys.size() > 0) {
			Serializable key = deleteKeys.poll();
			if (deleteHmap.containsKey(key)) {
				Queue<Serializable> queue = deleteHmap.get(key);
				Serializable[] names = queue.toArray(new Serializable[queue.size()]);
				List<Serializable> list = hmGet(key, names);
				entityManager.updateSync(list.toArray(new Serializable[list.size()]));
				hmDel(key, names);
			} else {
				entityManager.updateSync(get(key));
				del(key);
			}
		}
	}

	@Override
	public synchronized void addScheduledCreate(Serializable key, Serializable value, boolean deleteCache) {
		if (deleteKeys.contains(key)) {
			deleteKeys.remove(key);
			queueAdd(updateKeys, key);
		} else {
			queueAdd(createKeys, key);
		}
		
		saveDeleteKeyCache(key, deleteCache);
		scheduledSet(key, value);
	}

	@Override
	public synchronized void addScheduledUpdate(Serializable key, Serializable value, boolean deleteCache) {
		if (createKeys.contains(key)) {
			saveDeleteKeyCache(key, deleteCache);
		} else if (!deleteKeys.contains(key)) {
			queueAdd(updateKeys, key);
			saveDeleteKeyCache(key, deleteCache);
		}
		scheduledSet(key, value);
	}

	@Override
	public synchronized void addScheduledDelete(Serializable key, Serializable value) {
		createKeys.remove(key);
		updateKeys.remove(key);
		queueAdd(deleteKeys, key);
		scheduledSet(key, value);
	}

	@Override
	public synchronized void addHScheduledCreate(Serializable key, Serializable name, Serializable value, boolean deleteCache) {
		if (getHNameList(key, deleteHmap).contains(name)) {
			addHScheduled(key, name, updateKeys, updateHmap);
		} else {
			addHScheduled(key, name, createKeys, createHmap);
		}

		saveDeleteNameCache(key, deleteCache);
		scheduledHSet(key, name, value);
	}

	@Override
	public synchronized void addHScheduledUpdate(Serializable key, Serializable name, Serializable value, boolean deleteCache) {
		if (!getHNameList(key, createHmap).contains(name) && !getHNameList(key, deleteHmap).contains(name)) {
			addHScheduled(key, name, updateKeys, updateHmap);
			saveDeleteNameCache(key, deleteCache);
		}
		scheduledHSet(key, name, value);
	}

	@Override
	public synchronized void addHScheduledDelete(Serializable key, Serializable name, Serializable value) {
		getHNameList(key, createHmap).remove(name);
		getHNameList(key, updateHmap).remove(name);
		addHScheduled(key, name, deleteKeys, deleteHmap);
		scheduledHSet(key, name, value);
	}
	
	protected Queue<Serializable> getHNameList(Serializable key, Map<Serializable, Queue<Serializable>> hmap) {
		if (hmap.containsKey(key)) {
			return hmap.get(key);
		} else {
			Queue<Serializable> queue = new ConcurrentLinkedQueue<Serializable>();
			hmap.put(key, queue);
			return queue;
		}
	}
	
	protected void queueAdd(Queue<Serializable> queue, Serializable key) {
		if (!queue.contains(key)) {
			queue.add(key);
		}
	}
	
	protected void addHScheduled(Serializable key, Serializable name, Queue<Serializable> keys, Map<Serializable, Queue<Serializable>> hmap) {
		queueAdd(keys, key);
		queueAdd(getHNameList(key, hmap), name);
	}
	
	protected void saveDeleteCache(Serializable key, boolean deleteCache, Map<Serializable, Boolean> caches) {
		if (caches.containsKey(key) && !caches.get(key)) {
			deleteCache = false; // 之前的任务需要保留缓存对象，则这次任务也标记为保留
		}
		caches.put(key, deleteCache);
	}
	
	protected void saveDeleteKeyCache(Serializable key, boolean deleteCache) {
		saveDeleteCache(key, deleteCache, keyCaches);
	}
	
	protected void saveDeleteNameCache(Serializable key, boolean deleteCache) {
		saveDeleteCache(key, deleteCache, nameCaches);
	}
	
	protected void scheduledSet(Serializable key, Serializable value) {
		if (value != null) {
			set(key, value);
		}
	}
	
	protected void scheduledHSet(Serializable key, Serializable name, Serializable value) {
		if (value != null) {
			hset(key, name, value);
		}
	}

}
