package com.fanxing.server.cache.lazy;

import java.util.concurrent.TimeUnit;

import com.fanxing.server.cache.ICacheValue;

public interface ILazyCacheValue<V> {
	
	/**
	 * 如果 key 已经存在并且是一个字符串， APPEND 命令将 value 追加到 key 原来的值的末尾。
	 * 如果 key 不存在， APPEND 就简单地将给定 key 设为 value ，就像执行 SET key value 一样。
	 * @param 	value
	 */
	void append(V value);
	/**
	 * 将 key 所储存的值减去减量 decrement 。
	 * 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 DECR 操作。
	 * @return	
	 */
	long decr(long decrement);
	/**
	 * 返回 key 所关联的字符串值。
	 * @return	
	 */
	V get();
	/**
	 * 将 key 所储存的值加上增量 increment 。
	 * 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCR 操作。
	 * @param 	key
	 * @return	
	 */
	long incr(long increment);
	/**
	 * 将字符串值 value 关联到 key 。
	 * 如果 key 已经持有其他值， SET 就覆写旧值，无视类型。
	 * 对于某个原本带有生存时间（TTL）的键来说， 当 SET 命令成功在这个键上执行时， 这个键原有的 TTL 将被清除。
	 * @param 	value
	 */
	void set(V value);
	/**
	 * @param 	value
	 * @param 	exists
	 * 			true - 只在键已经存在时，才对键进行设置操作。
	 * 			false - 只在键不存在时，才对键进行设置操作。
	 * @param 	time
	 * @param 	timeUnit
	 * @see		ICacheValue#set(String, String)
	 */
	void set(V value, boolean exists, long time, TimeUnit timeUnit);

}
