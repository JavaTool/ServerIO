package com.fanxing.server.sequence;

/**
 * 计数器
 * @author 	fuhuiyuan
 */
public interface Counter {
	
	/**
	 * 获得一个当前计数
	 * @param 	key
	 * 			计数名称
	 * @return	当前计数
	 */
	long get(String key);
	/**
	 * 增加计数
	 * @param 	key
	 * 			计数名称
	 * @param 	value
	 * 			增量
	 * @return	当前计数
	 */
	long incr(String key, long value);
	/**
	 * 减少计数
	 * @param 	key
	 * 			计数名称
	 * @param 	value
	 * 			减量
	 * @return	当前计数
	 */
	long decr(String key, long value);
	/**
	 * 删除一个计数
	 * @param 	key
	 * 			计数名称
	 */
	void delete(String key);

}
