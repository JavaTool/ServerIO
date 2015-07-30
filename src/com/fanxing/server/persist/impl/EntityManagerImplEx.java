package com.fanxing.server.persist.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.cfg.Configuration;

import com.fanxing.server.asynchronous.Asynchronous;
import com.fanxing.server.cache.Cache;
import com.fanxing.server.io.ConfigurationHolder;
import com.fanxing.server.persist.EntityManager;
import com.fanxing.server.sequence.InstanceIdManager;

/**
 * 扩展的实体管理器
 * @author 	fuhuiyuan
 */
public class EntityManagerImplEx implements EntityManager {
	
	protected EntityManager entityManager;
	/**异步更新器*/
	protected Asynchronous<Serializable> updater;
	/**异步删除器*/
	protected Asynchronous<Serializable> deleter;
	/**异步保存器*/
	protected Asynchronous<Serializable> creater;

	public EntityManagerImplEx(Configuration conf, ConfigurationHolder configuration) {
		entityManager = new EntityManagerImpl(conf, configuration);
		
		updater = new Asynchronous<Serializable>(new EntityUpdater(entityManager));
		new Thread(updater, "EntityManager-updater").start();
		deleter = new Asynchronous<Serializable>(new EntityDeleter(entityManager));
		new Thread(deleter, "EntityManager-deleter").start();
		creater = new Asynchronous<Serializable>(new EntityCreater(entityManager));
		new Thread(creater, "EntityManager-creater").start();
	}

	@Override
	public void deleteSync(Serializable entity) {
		deleter.add(entity);
	}

	@Override
	public void updateSync(Serializable entity) {
		updater.add(entity);
	}

	@Override
	public void createSync(Serializable entity) {
		creater.add(entity);
	}

	@Override
	public void createSync(Serializable[] entity) {
		for (Serializable en : entity) {
			createSync(en);
		}
	}

	@Override
	public void deleteSync(Serializable[] entity) {
		for (Serializable en : entity) {
			deleteSync(en);
		}
	}

	@Override
	public void updateSync(Serializable[] entity) {
		for (Serializable en : entity) {
			updateSync(en);
		}
	}

	@Override
	public <T extends Serializable> T fetch(Class<T> klass, String hql, Object... values) {
		return entityManager.fetch(klass, hql, values);
	}

	@Override
	public Object fetch(String entityName, String hql, Object... values) {
		return entityManager.fetch(entityName, hql, values);
	}

	@Override
	public <T extends Serializable> List<T> query(Class<T> klass, String hql, Object... values) {
		return entityManager.query(klass, hql, values);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List query(String entityName, String hql, Object... values) {
		return entityManager.query(entityName, hql, values);
	}

	@Override
	public <T extends Serializable> List<T> limitQuery(Class<T> klass, String hql, int start, int count, Object... values) {
		return entityManager.limitQuery(klass, hql, start, count, values);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List limitQuery(String entityName, String hql, int start, int count, Object... values) {
		return entityManager.limitQuery(entityName, hql, start, count, values);
	}

	@Override
	public long count(String hql, Object... values) {
		return entityManager.count(hql, values);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List nativeQuery(String hql, Object... values) {
		return entityManager.nativeQuery(hql, values);
	}

	@Override
	public void shutdown() throws Exception {
		entityManager.shutdown();
	}

	@Override
	public Configuration getConfiguration() {
		return entityManager.getConfiguration();
	}

	@Override
	public InstanceIdManager getInstanceIdManager() {
		return entityManager.getInstanceIdManager();
	}

	@Override
	public Cache getCache() {
		return entityManager.getCache();
	}

	@Override
	public void clearLocal(@SuppressWarnings("rawtypes") Class klass) {
		entityManager.clearLocal(klass);
	}

}
