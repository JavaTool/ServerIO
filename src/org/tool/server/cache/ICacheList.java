package org.tool.server.cache;

import java.util.List;

public interface ICacheList<K, V> {
	
	/**
	 * 弹出列表头元素
	 * @param 	key
	 * 			列表名称
	 * @return	头元素
	 */
	V headPop(K key);
	/**
	 * 向列表加入尾元素
	 * @param 	key
	 * 			列表名称
	 * @param 	objects
	 * 			尾元素
	 */
	void tailPush(K key, Object... objects);
	/**
	 * 获取列表指定索引的元素
	 * @param 	key
	 * 			列表名称
	 * @param 	index
	 * 			索引
	 * @return	元素
	 */
	V get(K key, long index);
	/**
	 * 获取一个列表的长度
	 * @param 	key
	 * 			键
	 * @return	列表的长度
	 */
	long size(K key);
	/**
	 * 截取列表
	 * @param 	key
	 * 			列表名称
	 * @param 	start
	 * 			截取起始索引
	 * @param 	end
	 * 			截取结束索引
	 */
	void trim(K key, long start, long end);
	/**
	 * 返回列表 key 中指定区间内的元素
	 * @param 	key
	 * 			列表名称
	 * @param 	start
	 * 			截取起始索引
	 * @param 	end
	 * 			截取结束索引
	 */
	List<V> range(K key, long start, long end);
	
	void lrem(K key, V value);
	
	void insert(K key, long index, V value);
	
	void set(K key, long index, V value);

}
