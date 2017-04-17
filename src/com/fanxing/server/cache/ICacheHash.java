package com.fanxing.server.cache;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ICacheHash<K, F, V> {
	
	/**
	 * 删除哈希表 key 中的一个或多个指定域，不存在的域将被忽略。
	 * @param 	key
	 * @param 	fields
	 */
	void remove(K key, Object... fields);
	/**
	 * 查看哈希表 key 中，给定域 field 是否存在。
	 * @param 	key
	 * @param 	field
	 * @return	
	 */
	boolean contains(K key, F field);
	/**
	 * 返回哈希表 key 中给定域 field 的值。
	 * @param 	key
	 * @param 	field
	 * @return	给定域的值。当给定域不存在或是给定 key 不存在时，返回 null。
	 */
	V get(K key, F field);
	/**
	 * 返回哈希表 key 中，一个或多个给定域的值。如果给定的域不存在于哈希表，那么返回一个 null 值。
	 * @param 	key
	 * @param 	fields
	 * @return	一个包含多个给定域的关联值的表，表值的排列顺序和给定域参数的请求顺序一样。
	 */
	List<V> multiGet(K key, Object... fields);
	/**
	 * 返回哈希表 key 中，所有的域和值。
	 * @param 	key
	 * @return	
	 */
	Map<F, V> getAll(K key);
	/**
	 * 返回哈希表 key 中的所有域。
	 * @param 	key
	 * @return	一个包含哈希表中所有域的表。
	 */
	Set<F> fields(K key);
	/**
	 * 返回哈希表 key 中域的数量。
	 * @param 	key
	 * @return	哈希表中域的数量。当 key 不存在时，返回 0 。
	 */
	long size(K key);
	/**
	 * 同时将多个 field-value (域-值)对设置到哈希表 key 中。
	 * 此命令会覆盖哈希表中已存在的域。
	 * 如果 key 不存在，一个空哈希表被创建并执行 HMSEK 操作。
	 * @param 	key
	 * @param 	map
	 */
	void multiSet(K key, Map<F, V> map);
	/**
	 * 将哈希表 key 中的域 field 的值设为 value 。
	 * 如果 key 不存在，一个新的哈希表被创建并进行 HSEK 操作。
	 * 如果域 field 已经存在于哈希表中，旧值将被覆盖。
	 * @param 	key
	 * @param 	field
	 * @param 	value
	 */
	void set(K key, F field, V value);
	/**
	 * 返回哈希表 key 中所有域的值。
	 * @param 	key
	 * @return	一个包含哈希表中所有值的表。当 key 不存在时，返回一个空表。
	 */
	List<V> values(K key);
	/**
	 * 为哈希表 key 中的域 field 的值加上增量 increment 。
	 * 增量也可以为负数，相当于对给定域进行减法操作。
	 * 如果 key 不存在，一个新的哈希表被创建并执行 HINCRBY 命令。
	 * 如果域 field 不存在，那么在执行命令前，域的值被初始化为 0 。
	 * 对一个储存字符串值的域 field 执行 HINCRBY 命令将造成一个错误。
	 * 本操作的值被限制在 64 位(bit)有符号数字表示之内。
	 * @param 	key
	 * @param 	field
	 * @param 	value
	 * @return	
	 */
	long incrBy(K key, F field, long value);

}
