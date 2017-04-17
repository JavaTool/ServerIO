package com.fanxing.server.cache.lazy;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ILazyCacheHash<F, V> {
	
	/**
	 * 删除哈希表中的一个或多个指定域，不存在的域将被忽略。
	 * @param 	fields
	 */
	void remove(Object... fields);
	/**
	 * 查看哈希表中，给定域 field 是否存在。
	 * @param 	field
	 * @return	
	 */
	boolean contains(F field);
	/**
	 * 返回哈希表中给定域 field 的值。
	 * @param 	field
	 * @return	给定域的值。当给定域不存在或是给定不存在时，返回 null。
	 */
	V get(F field);
	/**
	 * 返回哈希表中，一个或多个给定域的值。如果给定的域不存在于哈希表，那么返回一个 null 值。
	 * @param 	fields
	 * @return	一个包含多个给定域的关联值的表，表值的排列顺序和给定域参数的请求顺序一样。
	 */
	List<V> get(Object... fields);
	/**
	 * 返回哈希表中，所有的域和值。
	 * @return	
	 */
	Map<F, V> getAll();
	/**
	 * 返回哈希表中的所有域。
	 * @return	一个包含哈希表中所有域的表。
	 */
	Set<F> fields();
	/**
	 * 返回哈希表中域的数量。
	 * @return	哈希表中域的数量。当不存在时，返回 0 。
	 */
	long size();
	/**
	 * 同时将多个 field-value (域-值)对设置到哈希表中。
	 * 此命令会覆盖哈希表中已存在的域。
	 * 如果不存在，一个空哈希表被创建并执行 HMSET 操作。
	 * @param 	map
	 */
	void set(Map<F, V> map);
	/**
	 * 将哈希表中的域 field 的值设为 value 。
	 * 如果不存在，一个新的哈希表被创建并进行 HSET 操作。
	 * 如果域 field 已经存在于哈希表中，旧值将被覆盖。
	 * @param 	field
	 * @param 	value
	 */
	void set(F field, V value);
	/**
	 * 返回哈希表中所有域的值。
	 * @return	一个包含哈希表中所有值的表。当不存在时，返回一个空表。
	 */
	List<V> values();

}
