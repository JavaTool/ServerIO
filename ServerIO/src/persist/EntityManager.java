package persist;

import java.io.Serializable;
import java.util.List;

import org.hibernate.cfg.Configuration;

public interface EntityManager {

	/**
	 * @note 获取指定类型指定key的对象
	 * @param entityClass 指定类型
	 * @param key 指定的主键
	 * @return 返回指定数据，如果没找到返回null
	 */
	<T> T find(Class<T> entityClass, Serializable key);
	/**
	 * @note 同步的创建对象
	 * @param entity 需要被创建的对象
	 */
	void createSync(Object entity);
	/**
	 * @note 同步的创建对象
	 * @param entity 需要被创建的对象
	 */
	void createSync(Object[] entity);
	/**
	 * @note 同步删除对象
	 * @param entity 被删除的对象
	 */
	void deleteSync(Object entity);
	/**
	 * @note 同步删除对象
	 * @param entity 被删除的对象
	 */
	void deleteSync(Object[] entity);
	/**
	 * @note 同步更新对象。调用此方法以后，对象先被更新到远程的数据库，如果更新成功以后，将删除本地数据库中的此对象
	 * @param entity
	 */
	void updateSync(Object entity);
	/**
	 * @note 同步更新对象。调用此方法以后，对象先被更新到远程的数据库，如果更新成功以后，将删除本地数据库中的此对象
	 * @param entity
	 */
	void updateSync(Object[] entity);
	/**
	 * @note 根据指定条件查询指定的对象，注意此方法只返回单个对象，如果查询条件返回多个对象，此方法将抛出异常
	 * @param klass 指定的类型
	 * @param hql 查询的hql语句
	 * @param values 查询需要的参数值，如果没有参数可以为null
	 */
	<T> T fetch(Class<T> klass, String hql, Object... values);
	/**
	 * @note 根据指定条件查询指定的对象，注意此方法只返回单个对象，如果查询条件返回多个对象，此方法将抛出异常
	 * @param entityName 指定的类型
	 * @param hql 查询的hql语句
	 * @param values 查询需要的参数值，如果没有参数可以为null
	 */
	Object fetch(String entityName, String hql, Object... values);
	/**
	 * @note 根据指定条件查询对象列表
	 * @param klass 指定的类型
	 * @param hql 查询的hql语句
	 * @values 查询需要的参数值，如果没有参数可以为null
	 */
	<T> List<T> query(Class<T> klass, String hql, Object... values);
	/**
	 * @note 根据指定条件查询对象列表
	 * @param entityName 指定的类型
	 * @param hql 查询的hql语句
	 * @values 查询需要的参数值，如果没有参数可以为null
	 */
	@SuppressWarnings("rawtypes")
	List query(String entityName, String hql, Object... values);
	/**
	 * @note 根据指定条件查询指定数量的对象列表
	 * @param klass 指定的类型
	 * @param hql 查询的hql语句
	 * @param start 开始位置
	 * @param count 数量
	 * @param values 查询需要的参数值，如果没有参数可以为null
	 */
	<T> List<T> limitQuery(Class<T> klass, String hql, int start, int count, Object... values);
	/**
	 * @note 根据指定条件查询指定数量的对象列表
	 * @param entityName 指定的类型
	 * @param hql 查询的hql语句
	 * @param start 开始位置
	 * @param count 数量
	 * @param values 查询需要的参数值，如果没有参数可以为null
	 */
	@SuppressWarnings("rawtypes")
	List limitQuery(String entityName, String hql, int start, int count, Object... values);
	/**
	 * @note 根据条件，返回数据库中符合条件的对象数量
	 * @param hql 查询的hql语句，语句中必须要 有"select count(*)"类似的语句
	 * @param values 查询需要的参数，如果没有参数可以为null
	 * @return
	 */
	long count(String hql, Object... values);
	/**
	 * 执行特殊的查询，查询的结果并不是某个映射的对象或者映射对象的集合，而是对象的某些字段或者sum,avg等计算字段
	 * @param hql
	 * @param values
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List nativeQuery(String hql, Object... values);
	
	void shutdown() throws Exception;
	
	Configuration getConfiguration();
	
}
