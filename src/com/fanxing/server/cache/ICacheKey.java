package com.fanxing.server.cache;

import java.util.Set;
import java.util.concurrent.TimeUnit;

public interface ICacheKey<K> {
	
	/**
	 * key 不存在
	 */
	long TTL_NONE = -2;
	/**
	 *  key 存在但没有设置剩余生存时间
	 */
	long TTL_EVER = -1;
	
	/**
	 * 值的类型
	 * @author 	fuhuiyuan
	 */
	enum ValueType {
		
		/** key不存在 */
		None, 
		/** 字符串 */
		String, 
		/** 列表 */
		List, 
		/** 哈希表 */
		Hash;
		
		public static ValueType valueof(String value) {
			if ("none".equals(value)) {
				return None;
			} else if ("string".equals(value)) {
				return String;
			} else if ("list".equals(value)) {
				return List;
			} else if ("hash".equals(value)) {
				return Hash;
			} else {
				throw new RuntimeException("Unknow type : " + value);
			}
		}
		
	}
	
	/**
	 * 删除给定的一个或多个 key 。不存在的 key 会被忽略。
	 * @param 	keys
	 * 			
	 */
	void delete(Object... keys);
	/**
	 * 检查给定 key 是否存在。
	 * @param 	key
	 * 			
	 * @return	给定 key 是否存在。
	 */
	boolean exists(K key);
	/**
	 * 为给定 key 设置生存时间，当 key 过期时(生存时间为 0 )，它会被自动删除。
	 * 可以对一个已经带有生存时间的 key 执行 EXPIRE 命令，新指定的生存时间会取代旧的生存时间。
	 * @param 	key
	 * 			
	 * @param 	seconds
	 * 			
	 * @param 	timeUnit
	 * 			
	 */
	void expire(K key, long time, TimeUnit timeUnit);
	/**
	 * EXPIREAT 的作用和 EXPIRE 类似，都用于为 key 设置生存时间。不同在于 EXPIREAT 命令接受的时间参数是 UNIX 时间戳(unix timestamp)。
	 * @param 	key
	 * 			
	 * @param 	timestamp
	 * 			
	 */
	void expireat(K key, long timestamp);
	/**
	 * 查找所有符合给定模式 pattern 的 key 。
	 * @param 	pattern
	 * 			
	 * @return	符合给定模式 pattern 的 key
	 */
	Set<K> keys(K pattern);
	/**
	 * 移除给定 key 的生存时间，将这个 key 从『易失的』(带生存时间 key )转换成『持久的』(一个不带生存时间、永不过期的 key )。
	 * @param 	key
	 * 			
	 */
	void persist(K key);
	/**
	 * 返回给定 key 的剩余生存时间(TTL, time to live)。
	 * @return	剩余生存时间（毫秒）
	 * @see		#TTL_NONE
	 * @see		#TTL_EVER
	 */
	long ttl(K key);
	/**
	 * 将 key 改名为 newkey。
	 * @param 	key
	 * 			
	 * @param 	newkey
	 * 			
	 */
	void rename(K key, K newkey);
	/**
	 * 返回 key 所储存的值的类型。
	 * @param 	key
	 * 			
	 * @return	值的类型。
	 */
	ValueType type(K key);

}
