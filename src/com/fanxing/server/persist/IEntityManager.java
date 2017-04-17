package com.fanxing.server.persist;

import java.util.List;

import org.hibernate.cfg.Configuration;

public interface IEntityManager {

	void createSync(Object entity);
	
	void createSync(Object[] entity);
	
	int deleteSync(String hql, Object... values);
	
	void deleteSync(Object entity);
	
	void deleteSync(Object[] entity);
	
	void updateSync(Object entity);
	
	void updateSync(Object[] entity);
	
	<T> T fetch(Class<T> klass, String hql, Object... values);
	
	Object fetch(String entityName, String hql, Object... values);
	
	<T> List<T> query(Class<T> klass, String hql, Object... values);
	
	@SuppressWarnings("rawtypes")
	List query(String entityName, String hql, Object... values);
	
	<T> List<T> limitQuery(Class<T> klass, String hql, int start, int count, Object... values);
	
	@SuppressWarnings("rawtypes")
	List limitQuery(String entityName, String hql, int start, int count, Object... values);
	
	long count(String hql, Object... values);
	
	@SuppressWarnings("rawtypes")
	List nativeQuery(String hql, Object... values);
	
	Object nativeSQLQuery(String hql);
	
	int nativeSQLUpate(String sql, Object... values);
	
	<T> List<T> nativeSQLQueryList(String hql);
	
	void shutdown() throws Exception;
	
	Configuration getConfiguration();
	
}
