package com.fanxing.server.cache.lazy;

public interface ILazyCacheList<V> {
	
	/**
	 * 弹出列表头元素
	 * @param 	key
	 * 			列表名称
	 * @return	头元素
	 */
	V headPop();
	/**
	 * 向列表加入尾元素
	 * @param 	key
	 * 			列表名称
	 * @param 	objects
	 * 			尾元素
	 */
	void tailPush(Object... objects);
	/**
	 * 获取列表指定索引的元素
	 * @param 	key
	 * 			列表名称
	 * @param 	index
	 * 			索引
	 * @return	元素
	 */
	V get(long index);
	/**
	 * 获取一个列表的长度
	 * @param 	key
	 * 			键
	 * @return	列表的长度
	 */
	long size();
	/**
	 * 截取列表
	 * @param 	key
	 * 			列表名称
	 * @param 	start
	 * 			截取起始索引
	 * @param 	end
	 * 			截取结束索引
	 */
	void trim(long start, long end);

}
