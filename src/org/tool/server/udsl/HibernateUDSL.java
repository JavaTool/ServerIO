package org.tool.server.udsl;

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
import org.tool.server.persist.DataAccessException;

import com.google.common.collect.Maps;

class HibernateUDSL implements UDSL {
	
	private Configuration conf;
	
	private SessionFactory sessionFactory;
	
	private Map<String, EntityPersister> entityPersisters;
	
	private Map<String, Lock> locks;
	
	public HibernateUDSL(Configuration conf) {
		this.conf = conf;
		initHibernate();
		initLocks();
	}
	
	@SuppressWarnings("rawtypes")
	private void initHibernate() {
		if (conf == null) {
			conf = new Configuration();
			conf.configure();
		}
		sessionFactory = conf.buildSessionFactory();
		entityPersisters = Maps.newHashMap();
		Iterator ite = sessionFactory.getAllClassMetadata().entrySet().iterator(); 
		while (ite.hasNext()) {
			Map.Entry entry = (Map.Entry) ite.next();
			String entityName = (String) entry.getKey();
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
		if (o instanceof HibernateProxy) {
			throw new IllegalArgumentException();
		}
	}
	
	private String makeHql(String entityName, Object... params) {
		StringBuilder builder = new StringBuilder("from ");
		builder.append(entityName);
		if (params.length > 1) {
			builder.append(" where");
			for (int i = 1;i < params.length;i += 2) {
				builder.append(" ").append(params[i]).append("=?");
			}
		}
		return builder.toString();
	}
	
	private <T> String getEntityName(Class<T> clz) {
		return clz.toString();
	}
	
	private Query createQuery(String entityName, Object... params) {
		Query query = getSession().createQuery(makeHql(entityName, params));
		if (params.length > 1) {
			for (int i = 2, index = 0;i < params.length;i += 2, index++) {
				query.setParameter(index, params[i]);
			}
		}
		return query;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T fetch(Object... params) {
		String entityName = params[0].toString();
		Lock lock = getLock(entityName);
		lock.lock();
		try {
			Session session = getSession();
			Transaction tx = session.beginTransaction();
			try {
				Query query = createQuery(entityName, params);
				Object entity = query.uniqueResult();
				session.clear();
				tx.commit();
				return (T) entity;
			} catch (Exception e) {
				tx.rollback();
				throw new DataAccessException(e);
			}
		} catch(DataAccessException e) {
			throw e;
		} catch(Exception e) {
			throw new DataAccessException(e); 
		} finally {
			lock.unlock();
		}
	}

	@Override
	public <T> List<T> find(Object... params) {
		String entityName = params[0].toString();
		Lock lock = getLock(entityName);
		lock.lock();
		try{
			Session session = getSession();
			Transaction tx = session.beginTransaction();
			try {
				Query query = createQuery(entityName, params);
				@SuppressWarnings("unchecked")
				List<T> list = query.list();
				session.clear();
				tx.commit();
				return list;
			} catch (Exception e) {
				tx.rollback();
				throw new DataAccessException(e);
			}
		} catch(DataAccessException e) {
			throw e;
		} catch(Exception e) {
			throw new DataAccessException(e); 
		} finally {
			lock.unlock();
		}
	}

	@Override
	public <T> List<T> orderBy(boolean dec, Object... params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> List<T> limit(int offset, int count, Object... params) {
		String entityName = params[0].toString();
		Lock lock = getLock(entityName);
		lock.lock();
		try {
			Session session = getSession();
			Transaction tx = session.beginTransaction();
			try {
				Query query = createQuery(entityName, params);
		        query.setFirstResult(offset);
		        query.setMaxResults(count);
				@SuppressWarnings("unchecked")
				List<T> list = query.list();
		        session.clear();
				tx.commit();
				return list;
			} catch (Exception e) {
				tx.rollback();
				throw new DataAccessException(e);
			}
		} catch(DataAccessException e) {
			throw e;
		} catch(Exception e) {
			throw new DataAccessException(e); 
		} finally {
			lock.unlock();
		}
	}

	@Override
	public <T> void save(T entity) {
		checkCreateObject(entity);
		String entityName = getEntityName(entity.getClass());
		Lock lock = getLock(entityName);
		lock.lock();
		try {
			Session session = getSession();
			Transaction tx = session.beginTransaction();
			try {
				session.update(entity);
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

	@Override
	public <T> void delete(T entity) {
		String entityName = getEntityName(entity.getClass());
		Session session = getSession();
		Transaction tx = session.beginTransaction();
		try {
			Query query = createQuery(entityName);
			query.executeUpdate();
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			throw new DataAccessException(e);
		}
	}

}
