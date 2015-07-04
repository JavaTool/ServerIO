package persist.impl;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.hibernate.EntityMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.persister.entity.EntityPersister;
import org.hibernate.proxy.HibernateProxy;

import persist.DataAccessException;
import persist.EntityManagerEx;
import persist.impl.cache.EhCacheCache;
import persist.impl.cache.SleepyCat;

import com.sleepycat.bind.EntryBinding;
import com.sleepycat.je.Cursor;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.LockMode;
import com.sleepycat.je.OperationStatus;

/**
 * 数据持久化管理器缺省的具体实现类。这个实现中借助了Hibernate，JBossCache以及BDB三个工具。用Hibernate实现对远程数据库的操作，用JBossCache做本地的数据缓存提高速度，
 * 用BDB来作为本地的存储库。此实现类使用Hibernate的配置信息，在初始化的时候将会借助Hibernate的配置信息来初始化JBossCache以及BDB。
 * @author Jeffrey
 */
public class EntityManagerExImpl implements EntityManagerEx {
	
	protected Configuration conf;
	
	protected SessionFactory sessionFactory;
	
	protected HashMap<String,EntityPersister> entityPersisters = null;
	
	protected Cache cache;
	
	protected HashMap<String,Lock> locks = null;
	
	protected HashMap<String,SleepyCatDB> dbs = null;
	
	protected SleepyCatPersister scp = null;
	
	public EntityManagerExImpl() throws Exception {
		this(null, new PropertyPersister());
	}
	
	public EntityManagerExImpl(Configuration conf,SleepyCatPersister scp) throws Exception{
		this.conf = conf;
		this.scp = scp;
		initHibernate();
		initSleepyCatDBs();
		initCache();
		initLocks();
	}
	
	@SuppressWarnings("rawtypes")
	protected void initHibernate() {
		if(this.conf == null) {
			this.conf = new Configuration();
			this.conf.configure();
		}
		this.sessionFactory = this.conf.buildSessionFactory();
		entityPersisters = new HashMap<String,EntityPersister>();
		//获取所有Hibernate配置中的映射元信息
		Iterator ite =  this.sessionFactory.getAllClassMetadata().entrySet().iterator(); 
		while(ite.hasNext()) {
			Map.Entry entry = (Map.Entry)ite.next();
			String entityName = (String)entry.getKey();
			EntityPersister entityPersister = (EntityPersister)entry.getValue();
			entityPersisters.put(entityName, entityPersister);
		}
	}
	
	protected void initSleepyCatDBs() throws Exception{
		this.dbs = new HashMap<String,SleepyCatDB>();
		//根据Hibernate配置中的元信息初始化BDB数据库，数据库的名字就是被映射类的全路径名
		Iterator<EntityPersister> ite = entityPersisters.values().iterator();
		while(ite.hasNext()) {
			EntityPersister ep = ite.next();
			Database db = SleepyCat.openDatabase(ep.getEntityName());
			dbs.put(ep.getEntityName(), new SleepyCatDB(db, null));
		}
	}
	
	protected void initCache() {
		this.cache = new EhCacheCache();
		//根据Hibernate配置中的元信息初始化JBossCache，每一个映射都有单独的Cache节点
		Iterator<String> ite = entityPersisters.keySet().iterator();
		while(ite.hasNext()) {
			String entityName = (String)ite.next();
			this.cache.createGroup(entityName);
		}
	}
	
	protected void initLocks() {
		locks = new HashMap<String,Lock>();
		Iterator<String> ite = entityPersisters.keySet().iterator();
		while(ite.hasNext()) {
			String entityName = ite.next();
			locks.put(entityName, new ReentrantLock());
		}
	}
	
	protected Lock getLock(String name) {
		return locks.get(name);
	}
	
	/**
	 * @note 获取指定类型指定主键的对象。具体的实现思路是先根据类型和key在缓存中进行查找，如果没有找到就到BDB中查找，如果再没有找到就去远程数据库查找
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T find(Class<T> entityClass, Serializable key) {
		String entityName = entityClass.getName();
		Lock lock = getLock(entityName);
		lock.lock();
		try {
			T result = (T) cache.get(entityName, key);
			if(result == null) {
				result = (T) getFromSleepyCat(entityName, key);
				if (result == null) {
					result = findInDB(entityClass, key);
				}
				if (result != null) {
					cache.put(entityName, key, result);
				}
			}
			return result;
		} finally {
			lock.unlock();
		}
	}
	
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	protected <T> T findInDB(Class<T> entityClass, Serializable key) {
		Session session = getSession();
		Transaction tx = session.beginTransaction();
		try{
			@SuppressWarnings("unchecked")
			T o = (T) session.get(entityClass, key);
			session.clear();
			tx.commit();
			return o; 
		} catch(Exception ex) {
			tx.rollback();
			throw new DataAccessException(ex);
		}
	}	

	/**
	 * @note 同步创建一个对象，此对象将现在远程数据库中创建，如果创建成功，那么将会放到本地的缓存中
	 */
	@Override
	public void createSync(Object entity) {
		checkCreateObject(entity);
		String entityName = entity.getClass().getName();
		Lock lock = getLock(entityName);
		lock.lock();
		try{
			Session session = getSession();
			Transaction tx = session.beginTransaction();
			try {
				session.save(entity);
				tx.commit();
				putToCache(entityName, entity, EntityMode.POJO);
			} catch (Exception e) {
				tx.rollback();
				throw e;
			}
		} catch(Exception ex) {
			throw new DataAccessException(ex);
		} finally{
			lock.unlock();
		}
	}
	
	protected void putToCache(String entityName, Object entity, EntityMode entityMode) {
		EntityPersister eper = entityPersisters.get(entityName);
		Serializable id = eper.getIdentifier(entity, entityMode);
		cache.put(entityName, id, entity);
	}
	
	@SuppressWarnings("rawtypes")
	public void clearLocal(Class klass) {
		String entityName = klass.getName();
		Lock lock = getLock(entityName);
		lock.lock();
		try{
			SleepyCatDB db = getDatabase(entityName);
			Cursor cursor = db.database.openCursor(null, null);
			DatabaseEntry key = new DatabaseEntry();
			DatabaseEntry data = new DatabaseEntry();
//			EntityPersister ep = entityPersisters.get(db.database.getDatabaseName());
			while (cursor.getPrev(key, data, LockMode.DEFAULT) == OperationStatus.SUCCESS) {
				cursor.delete();
			}
			cursor.close();
		} catch (DatabaseException ex) {
			ex.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

	/**
	 * 同步删除指定对象。此对象将先在远程数据库中进行删除，如果删除成功将会从本地的缓存以及本地的BDB数据库中删除
	 */
	@Override
	public void deleteSync(Object entity){
		checkRemoveObject(entity);
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
			Serializable id = getEntityIdentifier(entityName, entity,
					EntityMode.POJO);
			removeFromCache(entityName, id);
			deleteFromSleepyCat(entityName, id);
		} catch (Exception ex) {
			throw new DataAccessException(ex);
		} finally{
			lock.unlock();
		}
	}
	
	protected DatabaseEntry getKey(Serializable id) {
		DatabaseEntry key = new DatabaseEntry();
		if (id instanceof Integer) {
			byte[] data = new byte[4];
			int value = ((Integer)id).intValue();
			data[3] = (byte)((value >> 24)&0xFF);
			data[2] = (byte)((value >> 16)&0xFF);
			data[1] = (byte)((value >> 8)&0xFF);
			data[0] = (byte)(value&0xFF);
			key.setData(data);
		} else if(id instanceof String) {
			try {
				key.setData(((String)id).getBytes("utf-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return key;
	}
	
	protected Serializable getEntityIdentifier(String entityName, Object entity, EntityMode entityMode) {
		EntityPersister eper = entityPersisters.get(entityName);
		return eper.getIdentifier(entity, entityMode);
	}
	
	protected SleepyCatDB getDatabase(String entityName) {
		return dbs.get(entityName);
	}
	
	protected void deleteFromSleepyCat(String entityName, Serializable id) {
		try {
			Database db = getDatabase(entityName).database;
			db.delete(null, getKey(id));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	protected void putToSleepyCat(String entityName, Object entity, Serializable id) {
		try{
			SleepyCatDB db = getDatabase(entityName);
			EntityPersister eper =  entityPersisters.get(entityName);
			DatabaseEntry data = new DatabaseEntry();
			DatabaseEntry key = getKey(id);
			data.setData(scp.getData(entity, eper));
			db.database.put(null, key, data);
			System.out.printf("PutToSleepyCat:%s-%d",entityName,id);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	protected Object getFromSleepyCat(String entityName, Serializable id) {
		try {
			SleepyCatDB db = getDatabase(entityName);
			DatabaseEntry key = getKey(id);
			DatabaseEntry data = new DatabaseEntry();
			EntityPersister eper =  entityPersisters.get(entityName);
			if(db.database.get(null, key, data, LockMode.DEFAULT)==OperationStatus.SUCCESS){
				return scp.getObject(data.getData(), eper);
			}
			return null;
		} catch(Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	
	protected void removeFromCache(String entityName, Serializable id) {
		cache.remove(entityName, id);
	}
	
	protected void removeFromCache(String entityName, Object entity, EntityMode entityMode) {
		Serializable id = getEntityIdentifier(entityName, entity, entityMode);
		cache.remove(entityName, id);
	}

	/**
	 * 异步的更新一个对象，对象将会更新到本地的缓存以及本地的BDB库中，只有当调用了sync方法以后才会从本地更新到远程数据库
	 */
	@Override
	public void update(Object entity) {
		checkUpdateObject(entity);
		String entityName = entity.getClass().getName();
		Lock lock = getLock(entityName);
		lock.lock();
		try {
			Serializable id = getEntityIdentifier(entityName, entity, EntityMode.POJO);
//			if(getObjectFromDeletedCache(entityName, id) != null) 
//				throw new IllegalArgumentException();
			cache.put(entityName, id, entity);
			putToSleepyCat(entityName, entity, id);
		} catch(Exception ex) {
			throw new DataAccessException(ex);
		} finally{
			lock.unlock();
		}
	}
	
	/**
	 * 同步的更新指定对象。对象将会先更新到远程服务器，更新成功以后将会从本地的BDB库中删除，并且更新缓存
	 */
	@Override
	public void updateSync(Object entity){
		checkUpdateObject(entity);
		String entityName = entity.getClass().getName();
		Lock lock = getLock(entityName);
		lock.lock();
		try{
			Serializable id = getEntityIdentifier(entityName, entity, EntityMode.POJO);
			Session session = getSession();
			Transaction tx = session.beginTransaction();
			try{
				session.update(entity);
				tx.commit();
				deleteFromSleepyCat(entityName, id);
				cache.put(entityName, id, entity);
			} catch (Exception e) {
				tx.rollback();
				throw new DataAccessException(e);
			}
		} catch(DataAccessException ex) {
			throw ex;
		} catch(Exception ex1) {
			throw new DataAccessException(ex1);
		} finally{
			lock.unlock();
		}
	}

	/**
	 * 根据条件返回指定的对象，在根据查询条件查询远程数据库返回指定对象以后将会在本地缓存一个DBD库中查找是否有此对象的更新的版本，如果有，那么将会放回更新的版本
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T fetch(Class<T> klass, String hql, Object... values){
		return (T) fetch(klass.getName(), hql, values);
	}
	
	protected Object getLocalEntity(String entityName, Object entity, Serializable id) {
//		if(getObjectFromDeletedCache(entityName, id) != null)
//			return null;
		Object o = entity;
		o = cache.get(entityName, id);
		if(o == null) {
			o = getFromSleepyCat(entityName, id);
		}
		if(o == null)
			return entity;
		return o;
	}

	
	/**
	 * 根据查询条件返回对象列表。先在远程库中进行查询，返回的对象列表将会被遍历 ，查找本地是否有最新的版本，如果有，将会被本地对象替换
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> query(Class<T> klass, String hql, Object... values){
		return query(klass.getName(), hql, values);
	}

	/**
	 * 根据查询条件返回指定个数的对象列表。先在远程库中进行查询，返回的对象列表将会被遍历 ，查找本地是否有最新的版本，如果有，将会被本地对象替换
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> limitQuery(Class<T> klass, String hql, int start, int count, Object... values) throws DataAccessException {
		return limitQuery(klass.getName(), hql, start, count, values);
	}
	
	public long count(String hql, Object... values){
		Session session = getSession();
		Transaction tx = session.beginTransaction();
		try{
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
	 * 执行特殊的查询，查询的结果并不是某个映射的对象或者映射对象的集合，而是对象的某些字段或者sum,avg等计算字段
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
	
	protected void checkUpdateObject(Object o) {}
	
	protected void checkRemoveObject(Object o) {}
	
	@SuppressWarnings("rawtypes")
	protected Class getEntityClass(Object o) {
		if(o instanceof HibernateProxy) {
			return o.getClass().getSuperclass();
		}
		return o.getClass();
	}
	
	protected void checkCreateObject(Object o) {
		if(o instanceof HibernateProxy) {
			throw new IllegalArgumentException();
		}
	}
	
	public void syncSleepyCat() {
		SleepyCat.sync();
		SleepyCat.close();
	}
	
	/**
	 * 进行本地库跟远程库的同步，本地库中的对象将会被同步到远程库中，并且清空本地库
	 */
	@Override
	public void sync() {
		for(SleepyCatDB db : dbs.values()) {
			try {
				Cursor cursor = db.database.openCursor(null, null);
				DatabaseEntry key = new DatabaseEntry();
				DatabaseEntry data = new DatabaseEntry();
				EntityPersister ep = entityPersisters.get(db.database.getDatabaseName());
				while(cursor.getPrev(key, data, LockMode.DEFAULT)==OperationStatus.SUCCESS){
					try {
						Object o = scp.getObject(data.getData(), ep);
						Session session = getSession();
						Transaction tx = session.beginTransaction();
						try{
							session.update(o);
							tx.commit();
							cursor.delete();
						} catch(Exception ex) {
							tx.rollback();
							ex.printStackTrace();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				cursor.close();
			} catch (DatabaseException e) {
				e.printStackTrace();
			}
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
				if (entity == null) {
					return null;
				}
				Serializable id = getEntityIdentifier(entityName, entity, EntityMode.POJO);
				return getLocalEntity(entityName, entity, id);
			} catch (Exception e) {
				tx.rollback();
				throw new DataAccessException(e);
			}
		} catch(DataAccessException ex){
			throw ex;
		} catch(Exception ex1) {
			throw new DataAccessException(ex1); 
		} finally{
			lock.unlock();
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
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
				for(int i = list.size() - 1;i >= 0;i--) {
					Object entity = list.get(i);
					Serializable id = getEntityIdentifier(entityName, entity, EntityMode.POJO);
					Object newEntity = getLocalEntity(entityName, entity, id);
					if (newEntity == entity) {
						continue;
					}
					list.set(i, newEntity);
				}
				return list;
			} catch (Exception e) {
				tx.rollback();
				throw new DataAccessException(e);
			}
		} catch(DataAccessException ex){
			throw ex;
		} catch(Exception ex1) {
			throw new DataAccessException(ex1); 
		} finally{
			lock.unlock();
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List limitQuery(String entityName, String hql, int start, int count, Object... values) {
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
		        query.setFirstResult(start);
		        query.setMaxResults(count);
				List list = query.list();
		        session.clear();
				tx.commit();
				for(int i = list.size() - 1;i >= 0;i--) {
					Object entity = list.get(i);
					Serializable id = getEntityIdentifier(entityName, entity, EntityMode.POJO);
					Object newEntity = getLocalEntity(entityName, entity, id);
					if (newEntity == entity) {
						continue;
					} else if (newEntity == null) {
						list.remove(i);
					} else{
						list.set(i, newEntity);
					}
				}
				return list;
			} catch (Exception e) {
				tx.rollback();
				throw new DataAccessException(e);
			}
		} catch(DataAccessException ex){
			throw ex;
		} catch(Exception ex1) {
			throw new DataAccessException(ex1); 
		} finally{
			lock.unlock();
		}
	}

	@Override
	public void createSync(Object[] entity) {
		for (Object en : entity) {
			createSync(en);
		}
	}

	@Override
	public void deleteSync(Object[] entity) {
		for (Object en : entity) {
			deleteSync(en);
		}
	}

	@Override
	public void updateSync(Object[] entity) {
		for (Object en : entity) {
			updateSync(en);
		}
	}

	@Override
	public void shutdown() {
		sessionFactory.close();
	}

	@Override
	public Configuration getConfiguration() {
		return conf;
	}
	
}

class SleepyCatDB{
	
	public Database database;
	
	@SuppressWarnings("rawtypes")
	public SleepyCatDB(Database database, EntryBinding binding) {
		this.database = database;
	}
	
}
