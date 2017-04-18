package org.tool.server.sequence;

/**
 * 计数器
 * @author 	fuhuiyuan
 */
public interface ICounter {
	
	long NO_TIME = -1;
	
	/**
	 * 获得一个当前计数
	 * @param 	key
	 * 			计数名称
	 * @return	当前计数
	 */
	long getCount(String key);
	/**
	 * 增加计数
	 * @param 	key
	 * 			计数名称
	 * @param 	value
	 * 			增量
	 * @return	当前计数
	 */
	long incr(String key, long value, long time);
	/**
	 * 减少计数
	 * @param 	key
	 * 			计数名称
	 * @param 	value
	 * 			减量
	 * @return	当前计数
	 */
	long decr(String key, long value, long time);
	/**
	 * 删除一个计数
	 * @param 	key
	 * 			计数名称
	 */
	void deleteCount(String key);
	/**
	 * 获得一个当前计数
	 * @param 	key
	 * 			计数名称
	 * @return	当前计数
	 */
	long getCount(String key, String name);
	/**
	 * 增加计数
	 * @param 	key
	 * 			计数名称
	 * @param 	value
	 * 			增量
	 * @return	当前计数
	 */
	long incr(String key, String name, long value, long time);
	/**
	 * 减少计数
	 * @param 	key
	 * 			计数名称
	 * @param 	value
	 * 			减量
	 * @return	当前计数
	 */
	long decr(String key, String name, long value, long time);
	/**
	 * 删除一个计数
	 * @param 	key
	 * 			计数名称
	 */
	void deleteCount(String key, String name);

}
