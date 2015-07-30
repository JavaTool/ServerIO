package persist;

import java.io.Serializable;
import java.util.List;

import org.hibernate.cfg.Configuration;

public interface EntityManager {

	/**
	 * @note ��ȡָ������ָ��key�Ķ���
	 * @param entityClass ָ������
	 * @param key ָ��������
	 * @return ����ָ�����ݣ����û�ҵ�����null
	 */
	<T> T find(Class<T> entityClass, Serializable key);
	/**
	 * @note ͬ���Ĵ�������
	 * @param entity ��Ҫ�������Ķ���
	 */
	void createSync(Object entity);
	/**
	 * @note ͬ���Ĵ�������
	 * @param entity ��Ҫ�������Ķ���
	 */
	void createSync(Object[] entity);
	/**
	 * @note ͬ��ɾ������
	 * @param entity ��ɾ���Ķ���
	 */
	void deleteSync(Object entity);
	/**
	 * @note ͬ��ɾ������
	 * @param entity ��ɾ���Ķ���
	 */
	void deleteSync(Object[] entity);
	/**
	 * @note ͬ�����¶��󡣵��ô˷����Ժ󣬶����ȱ����µ�Զ�̵����ݿ⣬������³ɹ��Ժ󣬽�ɾ���������ݿ��еĴ˶���
	 * @param entity
	 */
	void updateSync(Object entity);
	/**
	 * @note ͬ�����¶��󡣵��ô˷����Ժ󣬶����ȱ����µ�Զ�̵����ݿ⣬������³ɹ��Ժ󣬽�ɾ���������ݿ��еĴ˶���
	 * @param entity
	 */
	void updateSync(Object[] entity);
	/**
	 * @note ����ָ��������ѯָ���Ķ���ע��˷���ֻ���ص������������ѯ�������ض�����󣬴˷������׳��쳣
	 * @param klass ָ��������
	 * @param hql ��ѯ��hql���
	 * @param values ��ѯ��Ҫ�Ĳ���ֵ�����û�в�������Ϊnull
	 */
	<T> T fetch(Class<T> klass, String hql, Object... values);
	/**
	 * @note ����ָ��������ѯָ���Ķ���ע��˷���ֻ���ص������������ѯ�������ض�����󣬴˷������׳��쳣
	 * @param entityName ָ��������
	 * @param hql ��ѯ��hql���
	 * @param values ��ѯ��Ҫ�Ĳ���ֵ�����û�в�������Ϊnull
	 */
	Object fetch(String entityName, String hql, Object... values);
	/**
	 * @note ����ָ��������ѯ�����б�
	 * @param klass ָ��������
	 * @param hql ��ѯ��hql���
	 * @values ��ѯ��Ҫ�Ĳ���ֵ�����û�в�������Ϊnull
	 */
	<T> List<T> query(Class<T> klass, String hql, Object... values);
	/**
	 * @note ����ָ��������ѯ�����б�
	 * @param entityName ָ��������
	 * @param hql ��ѯ��hql���
	 * @values ��ѯ��Ҫ�Ĳ���ֵ�����û�в�������Ϊnull
	 */
	@SuppressWarnings("rawtypes")
	List query(String entityName, String hql, Object... values);
	/**
	 * @note ����ָ��������ѯָ�������Ķ����б�
	 * @param klass ָ��������
	 * @param hql ��ѯ��hql���
	 * @param start ��ʼλ��
	 * @param count ����
	 * @param values ��ѯ��Ҫ�Ĳ���ֵ�����û�в�������Ϊnull
	 */
	<T> List<T> limitQuery(Class<T> klass, String hql, int start, int count, Object... values);
	/**
	 * @note ����ָ��������ѯָ�������Ķ����б�
	 * @param entityName ָ��������
	 * @param hql ��ѯ��hql���
	 * @param start ��ʼλ��
	 * @param count ����
	 * @param values ��ѯ��Ҫ�Ĳ���ֵ�����û�в�������Ϊnull
	 */
	@SuppressWarnings("rawtypes")
	List limitQuery(String entityName, String hql, int start, int count, Object... values);
	/**
	 * @note �����������������ݿ��з��������Ķ�������
	 * @param hql ��ѯ��hql��䣬����б���Ҫ ��"select count(*)"���Ƶ����
	 * @param values ��ѯ��Ҫ�Ĳ��������û�в�������Ϊnull
	 * @return
	 */
	long count(String hql, Object... values);
	/**
	 * ִ������Ĳ�ѯ����ѯ�Ľ��������ĳ��ӳ��Ķ������ӳ�����ļ��ϣ����Ƕ����ĳЩ�ֶλ���sum,avg�ȼ����ֶ�
	 * @param hql
	 * @param values
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List nativeQuery(String hql, Object... values);
	
	void shutdown() throws Exception;
	
	Configuration getConfiguration();
	
}
