package persist.impl;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.persister.entity.EntityPersister;
import org.hibernate.proxy.HibernateProxy;

import persist.DataAccessException;
import persist.EntityManager;
import persist.HibernateEntry;

public class EntityManagerImpl implements EntityManager {
	
	private Configuration conf;
	
	private SessionFactory sessionFactory;
	
	private HashMap<String, EntityPersister> entityPersisters;
	
	private HashMap<String, Lock> locks;
	
	private int batchSize;
	
	public EntityManagerImpl() {
		this(null);
	}
	
	public EntityManagerImpl(File file) {
		initHibernate(file);
		initLocks();
	}
	
	@SuppressWarnings("rawtypes")
	private void initHibernate(File file) {
		if (conf == null) {
			conf = new Configuration();
			if (file != null) {
				conf.configure(file);
			} else {
				conf.configure();
			}
		}
		sessionFactory = conf.buildSessionFactory();
		entityPersisters = new HashMap<String, EntityPersister>();
		//��ȡ����Hibernate�����е�ӳ��Ԫ��Ϣ
		Iterator ite =  sessionFactory.getAllClassMetadata().entrySet().iterator(); 
		while (ite.hasNext()) {
			Map.Entry entry = (Map.Entry) ite.next();
			String entityName = (String) entry.getKey();
			EntityPersister entityPersister = (EntityPersister) entry.getValue();
			entityPersisters.put(entityName, entityPersister);
		}
		String batch_size = conf.getProperty("hibernate.jdbc.batch_size");
		batchSize = batch_size == null ? 1 : Integer.parseInt(batch_size);
	}
	
	private void initLocks() {
		locks = new HashMap<String,Lock>();
		Iterator<String> ite = entityPersisters.keySet().iterator();
		while (ite.hasNext()) {
			String entityName = ite.next();
			locks.put(entityName, new ReentrantLock());
		}
	}
	
	private Lock getLock(String name) {
		return locks.get(name);
	}
	
	@Override
	public <T> T find(Class<T> entityClass, Serializable key) {
		String entityName = entityClass.getName();
		Lock lock = getLock(entityName);
		lock.lock();
		try {
			return findInDB(entityClass, key);
		} finally {
			lock.unlock();
		}
	}
	
	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	private <T> T findInDB(Class<T> entityClass, Serializable key) {
		Session session = getSession();
		Transaction tx = session.beginTransaction();
		try {
			@SuppressWarnings("unchecked")
			T o = (T) session.get(entityClass, key);
			session.clear();
			tx.commit();
			return o; 
		} catch(Exception e) {
			tx.rollback();
			throw new DataAccessException(e);
		}
	}	

	/**
	 * @note ͬ������һ�����󣬴˶�������Զ�����ݿ��д�������������ɹ�
	 */
	@Override
	public void createSync(Object entity) {
		checkCreateObject(entity);
		String entityName = getEntryName(entity);
		Lock lock = getLock(entityName);
		lock.lock();
		try {
			Session session = getSession();
			Transaction tx = session.beginTransaction();
			try {
				session.save(entityName, entity);
				tx.commit();
			} catch (Exception e) {
				tx.rollback();
				throw e;
			}
		} catch (Exception e) {
			throw new DataAccessException(e);
		} finally {
			lock.unlock();
		}
	}

	/**
	 * ͬ��ɾ��ָ�����󡣴˶�������Զ�����ݿ��н���ɾ�������ɾ���ɹ�����ӱ��صĻ����Լ����ص�BDB���ݿ���ɾ��
	 */
	@Override
	public void deleteSync(Object entity){
		String entityName = getEntryName(entity);
		Lock lock = getLock(entityName);
		lock.lock();
		try {
			Session session = getSession();
			Transaction tx = session.beginTransaction();
			try {
				session.delete(entityName, entity);
				tx.commit();
			} catch (Exception e) {
				tx.rollback();
				throw e;
			}
		} catch (Exception e) {
			throw new DataAccessException(e);
		} finally{
			lock.unlock();
		}
	}
	
	/**
	 * ͬ���ĸ���ָ�����󡣶��󽫻��ȸ��µ�Զ�̷����������³ɹ��Ժ󽫻�ӱ��ص�BDB����ɾ�������Ҹ��»���
	 */
	@Override
	public void updateSync(Object entity){
		String entityName = getEntryName(entity);
		Lock lock = getLock(entityName);
		lock.lock();
		try{
			Session session = getSession();
			Transaction tx = session.beginTransaction();
			try {
				session.update(entityName, entity);
				tx.commit();
			} catch (Exception e) {
				tx.rollback();
				throw new DataAccessException(e);
			}
		} catch(DataAccessException e) {
			throw e;
		} catch(Exception e) {
			throw new DataAccessException(e);
		} finally{
			lock.unlock();
		}
	}

	/**
	 * ������������ָ���Ķ����ڸ��ݲ�ѯ������ѯԶ�����ݿⷵ��ָ�������Ժ󽫻��ڱ��ػ���һ��DBD���в����Ƿ��д˶���ĸ��µİ汾������У���ô����Żظ��µİ汾
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T fetch(Class<T> klass, String hql, Object... values){
		return (T) fetch(klass.getName(), hql, values);
	}

	/**
	 * ���ݲ�ѯ�������ض����б�����Զ�̿��н��в�ѯ�����صĶ����б��ᱻ���� �����ұ����Ƿ������µİ汾������У����ᱻ���ض����滻
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> query(Class<T> klass, String hql, Object... values){
		return query(klass.getName(), hql, values);
	}

	/**
	 * ���ݲ�ѯ��������ָ�������Ķ����б�����Զ�̿��н��в�ѯ�����صĶ����б��ᱻ���� �����ұ����Ƿ������µİ汾������У����ᱻ���ض����滻
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> limitQuery(Class<T> klass, String hql, int start, int count, Object... values) throws DataAccessException{
		return limitQuery(klass.getName(), hql, start, count, values);
	}
	
	public long count(String hql, Object... values){
		Session session = getSession();
		Transaction tx = session.beginTransaction();
		try {
			Query query = getSession().createQuery(hql);
			if (values != null) {
				for (int i = 0; i < values.length; i++) {
					query.setParameter(i, values[i]);
				}
			}
			Number temp = (Number)query.uniqueResult();
			long ret = 0L;
			if(temp != null){
				ret = temp.longValue();
			}
			session.clear();
			tx.commit();
			return ret;
		} catch(Exception e) {
			tx.rollback();
			throw new DataAccessException(e);
		}
	}
	
	/**
	 * ִ������Ĳ�ѯ����ѯ�Ľ��������ĳ��ӳ��Ķ������ӳ�����ļ��ϣ����Ƕ����ĳЩ�ֶλ���sum,avg�ȼ����ֶ�
	 * @param hql
	 * @param values
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List nativeQuery(String hql, Object... values) {
		Session session = getSession();
		Transaction tx = session.beginTransaction();
		try {
			Query query = session.createQuery(hql);
			if (values != null) {
				for (int i = 0; i < values.length; i++) {
					query.setParameter(i, values[i]);
				}
			}
			List ret = query.list();
			tx.commit();
			return ret;
		} catch (Exception e) {
			tx.rollback();
			throw new DataAccessException(e);
		}
	}
	
	private void checkCreateObject(Object o) {
		if (o instanceof HibernateProxy) {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public Object fetch(String entityName, String hql, Object... values) {
		Lock lock = getLock(entityName);
		lock.lock();
		try{
			Session session = getSession();
			Transaction tx = session.beginTransaction();
			try {
				Query query = getSession().createQuery(hql);
				if (values != null) {
					for (int i = 0; i < values.length; i++) {
						query.setParameter(i, values[i]);
					}
				}
				Object entity = query.uniqueResult();
				session.clear();
				tx.commit();
				return entity;
			} catch (Exception e) {
				tx.rollback();
				throw new DataAccessException(e);
			}
		} catch(DataAccessException e){
			throw e;
		} catch(Exception e) {
			throw new DataAccessException(e); 
		} finally{
			lock.unlock();
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List query(String entityName, String hql, Object... values) {
		Lock lock = getLock(entityName);
		lock.lock();
		try{
			Session session = getSession();
			Transaction tx = session.beginTransaction();
			try {
				Query query = getSession().createQuery(hql);
				if (values != null) {
					for (int i = 0; i < values.length; i++) {
						query.setParameter(i, values[i]);
					}
				}
				List list = query.list();
				session.clear();
				tx.commit();
				return list;
			} catch (Exception e) {
				tx.rollback();
				throw new DataAccessException(e);
			}
		} catch(DataAccessException e){
			throw e;
		} catch(Exception e) {
			throw new DataAccessException(e); 
		} finally {
			lock.unlock();
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List limitQuery(String entityName, String hql, int start, int count, Object... values) {
		Lock lock = getLock(entityName);
		lock.lock();
		try {
			Session session = getSession();
			Transaction tx = session.beginTransaction();
			try {
				Query query = getSession().createQuery(hql);
				if (values != null) {
					for (int i = 0; i < values.length; i++) {
						query.setParameter(i, values[i]);
					}
				}
		        query.setFirstResult(start);
		        query.setMaxResults(count);
				List list = query.list();
		        session.clear();
				tx.commit();
				return list;
			} catch (Exception e) {
				tx.rollback();
				throw new DataAccessException(e);
			}
		} catch (DataAccessException e) {
			throw e;
		} catch (Exception e) {
			throw new DataAccessException(e); 
		} finally {
			lock.unlock();
		}
	}
	
	private String getEntryName(Object entity) {
		return entity instanceof HibernateEntry ? ((HibernateEntry) entity).getEntryName() : entity.getClass().getName();
	}

	@Override
	public synchronized void createSync(Object[] entity) {
		Session session = getSession();
		Transaction tx = session.beginTransaction();
		for (int i = 0;i < entity.length;i++) {
			try {
				checkCreateObject(entity[i]);
				String entityName = getEntryName(entity[i]);
				session.save(entityName, entity[i]);
			} catch (Exception e) {
				tx.rollback();
				session.clear();
				throw new DataAccessException(e);
			}
			if ((i + 1) % batchSize == 0 || i == entity.length - 1) {
				session.flush();
				session.clear();
			}
		}
		tx.commit();
	}

	@Override
	public synchronized void deleteSync(Object[] entity) {
		Session session = getSession();
		Transaction tx = session.beginTransaction();
		for (int i = 0;i < entity.length;i++) {
			try {
				checkCreateObject(entity[i]);
				String entityName = getEntryName(entity[i]);
				session.delete(entityName, entity[i]);
			} catch (Exception e) {
				tx.rollback();
				session.clear();
				throw new DataAccessException(e);
			}
			if ((i + 1) % batchSize == 0 || i == entity.length - 1) {
				session.flush();
				session.clear();
			}
		}
		tx.commit();
	}

	@Override
	public synchronized void updateSync(Object[] entity) {
		Session session = getSession();
		Transaction tx = session.beginTransaction();
		for (int i = 0;i < entity.length;i++) {
			try {
				checkCreateObject(entity[i]);
				String entityName = getEntryName(entity[i]);
				session.update(entityName, entity[i]);
			} catch (Exception e) {
				tx.rollback();
				session.clear();
				throw new DataAccessException(e);
			}
			if ((i + 1) % batchSize == 0 || i == entity.length - 1) {
				session.flush();
				session.clear();
			}
		}
		tx.commit();
	}

	@Override
	public void shutdown() throws Exception {
		sessionFactory.evictQueries();
		sessionFactory.close();
	}

	@Override
	public Configuration getConfiguration() {
		return conf;
	}
	
}
