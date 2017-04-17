package com.fanxing.server.persist.impl;

import static java.lang.String.valueOf;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.persister.entity.EntityPersister;
import org.hibernate.proxy.HibernateProxy;

import com.fanxing.server.persist.DataAccessException;
import com.fanxing.server.persist.IEntityManager;
import com.google.common.collect.Maps;

public class EntityManagerImpl implements IEntityManager {
	
	private Configuration conf;
	
	private SessionFactory sessionFactory;
	
	private Map<String, EntityPersister> entityPersisters;
	
	private Map<String, Lock> locks;
	
	public EntityManagerImpl(Configuration conf) {
		this.conf = conf;
		initHibernate();
		initLocks();
	}
	
	private void initHibernate() {
		if (conf == null) {
			conf = new Configuration();
			conf.configure();
		}
		sessionFactory = conf.buildSessionFactory();
		entityPersisters = new HashMap<String,EntityPersister>();
		Iterator<Entry<String, ClassMetadata>> ite = sessionFactory.getAllClassMetadata().entrySet().iterator(); 
		while (ite.hasNext()) {
			Map.Entry<String, ClassMetadata> entry = ite.next();
			String entityName = entry.getKey();
			EntityPersister entityPersister = (EntityPersister) entry.getValue();
			entityPersisters.put(entityName, entityPersister);
		}
	}
	
	private void initLocks() {
		locks = Maps.newHashMap();
		Iterator<String> ite = entityPersisters.keySet().iterator();
		while (ite.hasNext()) {
			String entityName = ite.next();
			locks.put(entityName, new ReentrantLock());
		}
	}
	
	private Lock getLock(String name) {
		return locks.get(name);
	}
	
	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	private void checkCreateObject(Object o) {
		if(o instanceof HibernateProxy) {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public void createSync(Object entity) {
		checkCreateObject(entity);
		String entityName = entity.getClass().getName();
		Lock lock = getLock(entityName);
		lock.lock();
		try {
			Session session = getSession();
			Transaction tx = session.beginTransaction();
			try {
				session.save(entity);
				tx.commit();
			} catch (Exception e) {
				tx.rollback();
				throw e;
			}
		} catch (Exception ex) {
			throw new DataAccessException(ex);
		} finally{
			lock.unlock();
		}
	}

	@Override
	public void createSync(Object[] entity) {
		for (Object en : entity) {
			if (en != null) {
				createSync(en);
			}
		}
	}

	@Override
	public void deleteSync(Object entity) {
		String entityName = entity.getClass().getName();
		Lock lock = getLock(entityName);
		lock.lock();
		try {
			Session session = getSession();
			Transaction tx = session.beginTransaction();
			try {
				session.delete(entity);
				tx.commit();
			} catch (Exception e) {
				tx.rollback();
				throw e;
			}
		} catch (Exception ex) {
			throw new DataAccessException(ex);
		} finally{
			lock.unlock();
		}
	}

	@Override
	public void deleteSync(Object[] entity) {
		for (Object en : entity) {
			if (en != null) {
				deleteSync(en);
			}
		}
	}

	@Override
	public void updateSync(Object entity) {
		String entityName = entity.getClass().getName();
		Lock lock = getLock(entityName);
		lock.lock();
		try {
			Session session = getSession();
			Transaction tx = session.beginTransaction();
			try {
				session.saveOrUpdate(entity);
				tx.commit();
			} catch (Exception e) {
				tx.rollback();
				throw new DataAccessException(e);
			}
		} catch (DataAccessException e) {
			throw e;
		} catch (Exception e) {
			throw new DataAccessException(e);
		} finally{
			lock.unlock();
		}
	}

	@Override
	public void updateSync(Object[] entity) {
		for (Object en : entity) {
			if (en != null) {
				updateSync(en);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T fetch(Class<T> klass, String hql, Object... values) {
		return (T) fetch(klass.getName(), hql, values);
	}

	@Override
	public Object fetch(String entityName, String hql, Object... values) {
		Lock lock = getLock(entityName);
		lock.lock();
		try {
			Session session = getSession();
			Transaction tx = session.beginTransaction();
			try {
				Query query = getSession().createQuery(hql);
				if (values != null) {
					for (int i = 0; i < values.length; i++) {
						query.setParameter(valueOf(i), values[i]);
					}
				}
				Object entity = query.uniqueResult();
				session.clear();
				tx.commit();
				if (entity == null) {
					return null;
				}
				return entity;
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

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> query(Class<T> klass, String hql, Object... values) {
		return query(klass.getName(), hql, values);
	}

	@SuppressWarnings({ "rawtypes" })
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
						query.setParameter(valueOf(i), values[i]);
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
		} catch (DataAccessException e) {
			throw e;
		} catch (Exception e) {
			throw new DataAccessException(e); 
		} finally {
			lock.unlock();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> limitQuery(Class<T> klass, String hql, int start, int count, Object... values) {
		return limitQuery(klass.getName(), hql, start, count, values);
	}

	@SuppressWarnings({ "rawtypes" })
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
						query.setParameter(valueOf(i), values[i]);
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

	@Override
	public long count(String hql, Object... values) {
		Session session = getSession();
		Transaction tx = session.beginTransaction();
		try {
			Query query = session.createQuery(hql);
			if (values != null) {
				for (int i = 0; i < values.length; i++) {
					query.setParameter(valueOf(i), values[i]);
				}
			}
			Number temp = (Number) query.uniqueResult();
			long ret = 0L;
			if (temp != null) {
				ret = temp.longValue();
			}
			session.clear();
			tx.commit();
			return ret;
		} catch (Exception e) {
			tx.rollback();
			throw new DataAccessException(e);
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List nativeQuery(String hql, Object... values) {
		Session session = getSession();
		Transaction tx = session.beginTransaction();
		try {
			Query query = session.createQuery(hql);
			if (values != null) {
				for (int i = 0; i < values.length; i++) {
					query.setParameter(valueOf(i), values[i]);
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

	@Override
	public int nativeSQLUpate(String sql, Object... values) {
		Session session = getSession();
		Transaction tx = session.beginTransaction();
		try {
			Query query = session.createSQLQuery(sql);
			if (values != null) {
				for (int i = 0; i < values.length; i++) {
					query.setParameter(valueOf(i), values[i]);
				}
			}
			int ret = query.executeUpdate();
			tx.commit();
			return ret;
		} catch (Exception e) {
			tx.rollback();
			throw new DataAccessException(e);
		}
	}

	@Override
	public Object nativeSQLQuery(String hql) {
		Session session = getSession();
		Transaction tx = session.beginTransaction();
		try {
			Object ret = session.createSQLQuery(hql).uniqueResult();
			tx.commit();
			return ret;
		} catch (Exception e) {
			tx.rollback();
			throw new DataAccessException(e);
		}
	}

	@Override
	public void shutdown() throws Exception {
		sessionFactory.close();
	}

	@Override
	public Configuration getConfiguration() {
		return conf;
	}

	@Override
	public int deleteSync(String hql, Object... values) {
		Session session = getSession();
		Transaction tx = session.beginTransaction();
		try {
			Query query = session.createQuery(hql);
			if (values != null) {
				for (int i = 0; i < values.length; i++) {
					query.setParameter(valueOf(i), values[i]);
				}
			}
			int ret = query.executeUpdate();
			tx.commit();
			return ret;
		} catch (Exception e) {
			tx.rollback();
			throw new DataAccessException(e);
		}
	}

	@Override
	public <T> List<T> nativeSQLQueryList(String hql) {
		Session session = getSession();
		Transaction tx = session.beginTransaction();
		try {
			@SuppressWarnings("unchecked")
			List<T> ret = session.createSQLQuery(hql).list();
			tx.commit();
			return ret;
		} catch (Exception e) {
			tx.rollback();
			throw new DataAccessException(e);
		}
	}

}
