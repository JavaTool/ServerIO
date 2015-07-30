package com.fanxing.server.persist;

import java.io.Serializable;
import java.util.List;

import org.hibernate.cfg.Configuration;

import com.fanxing.server.cache.Cache;
import com.fanxing.server.sequence.InstanceIdManager;

public interface EntityManager {

	/**
	 * @note ͬ���Ĵ�������
	 * @param entity ��Ҫ�������Ķ���
	 */
	void createSync(Serializable entity);
	/**
	 * @note ͬ���Ĵ�������
	 * @param entity ��Ҫ�������Ķ���
	 */
	void createSync(Serializable[] entity);
	/**
	 * @note ͬ��ɾ�����
	 * @param entity ��ɾ��Ķ���
	 */
	void deleteSync(Serializable entity);
	/**
	 * @note ͬ��ɾ�����
	 * @param entity ��ɾ��Ķ���
	 */
	void deleteSync(Serializable[] entity);
	/**
	 * @note ͬ�����¶��󡣵��ô˷����Ժ󣬶����ȱ����µ�Զ�̵���ݿ⣬�����³ɹ��Ժ󣬽�ɾ�����ݿ��еĴ˶���
	 * @param entity
	 */
	void updateSync(Serializable entity);
	/**
	 * @note ͬ�����¶��󡣵��ô˷����Ժ󣬶����ȱ����µ�Զ�̵���ݿ⣬�����³ɹ��Ժ󣬽�ɾ�����ݿ��еĴ˶���
	 * @param entity
	 */
	void updateSync(Serializable[] entity);
	/**
	 * @note ���ָ��������ѯָ���Ķ���ע��˷���ֻ���ص�����������ѯ�������ض�����󣬴˷������׳��쳣
	 * @param klass ָ��������
	 * @param hql ��ѯ��hql���
	 * @param values ��ѯ��Ҫ�Ĳ���ֵ�����û�в������Ϊnull
	 */
	<T extends Serializable> T fetch(Class<T> klass, String hql, Object... values);
	/**
	 * @note ���ָ��������ѯָ���Ķ���ע��˷���ֻ���ص�����������ѯ�������ض�����󣬴˷������׳��쳣
	 * @param entityName ָ��������
	 * @param hql ��ѯ��hql���
	 * @param values ��ѯ��Ҫ�Ĳ���ֵ�����û�в������Ϊnull
	 */
	Object fetch(String entityName, String hql, Object... values);
	/**
	 * @note ���ָ��������ѯ�����б�
	 * @param klass ָ��������
	 * @param hql ��ѯ��hql���
	 * @values ��ѯ��Ҫ�Ĳ���ֵ�����û�в������Ϊnull
	 */
	<T extends Serializable> List<T> query(Class<T> klass, String hql, Object... values);
	/**
	 * @note ���ָ��������ѯ�����б�
	 * @param entityName ָ��������
	 * @param hql ��ѯ��hql���
	 * @values ��ѯ��Ҫ�Ĳ���ֵ�����û�в������Ϊnull
	 */
	@SuppressWarnings("rawtypes")
	List query(String entityName, String hql, Object... values);
	/**
	 * @note ���ָ��������ѯָ�������Ķ����б�
	 * @param klass ָ��������
	 * @param hql ��ѯ��hql���
	 * @param start ��ʼλ��
	 * @param count ����
	 * @param values ��ѯ��Ҫ�Ĳ���ֵ�����û�в������Ϊnull
	 */
	<T extends Serializable> List<T> limitQuery(Class<T> klass, String hql, int start, int count, Object... values);
	/**
	 * @note ���ָ��������ѯָ�������Ķ����б�
	 * @param entityName ָ��������
	 * @param hql ��ѯ��hql���
	 * @param start ��ʼλ��
	 * @param count ����
	 * @param values ��ѯ��Ҫ�Ĳ���ֵ�����û�в������Ϊnull
	 */
	@SuppressWarnings("rawtypes")
	List limitQuery(String entityName, String hql, int start, int count, Object... values);
	/**
	 * @note ���������������ݿ��з�������Ķ�������
	 * @param hql ��ѯ��hql��䣬����б���Ҫ ��"select count(*)"���Ƶ����
	 * @param values ��ѯ��Ҫ�Ĳ������û�в������Ϊnull
	 * @return
	 */
	long count(String hql, Object... values);
	/**
	 * ִ������Ĳ�ѯ����ѯ�Ľ����ĳ��ӳ��Ķ������ӳ�����ļ��ϣ����Ƕ����ĳЩ�ֶλ���sum,avg�ȼ����ֶ�
	 * @param hql
	 * @param values
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List nativeQuery(String hql, Object... values);
	
	void shutdown() throws Exception;
	
	Configuration getConfiguration();
	
	InstanceIdManager getInstanceIdManager();
	
	Cache getCache();
	
	@SuppressWarnings("rawtypes")
	void clearLocal(Class klass);
	
}
