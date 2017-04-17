package com.fanxing.server.cache;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 缓存的值操作
 * @author 	fuhuiyuan
 */
public interface ICacheValue<K, V> {
	
	/**
	 * 如果 key 已经存在并且是一个字符串， APPEND 命令将 value 追加到 key 原来的值的末尾。
	 * 如果 key 不存在， APPEND 就简单地将给定 key 设为 value ，就像执行 SET key value 一样。
	 * @param 	key
	 * @param 	value
	 */
	void append(K key, V value);
	/**
	 * 将 key 所储存的值减去减量 decrement 。
	 * 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 DECR 操作。
	 * @param 	key
	 * @return	
	 */
	long decr(K key, long decrement);
	/**
	 * 返回 key 所关联的字符串值。
	 * @param 	key
	 * @return	
	 */
	V get(K key);
	/**
	 * 返回所有(一个或多个)给定 key 的值。如果给定的 key 里面，有某个 key 不存在，那么这个 key 返回特殊值 nil 。
	 * @param 	keys
	 * @return	
	 */
	List<V> multiGet(Object... keys);
	/**
	 * 将 key 所储存的值加上增量 increment 。
	 * 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCR 操作。
	 * @param 	key
	 * @return	
	 */
	long incr(K key, long increment);
	/**
	 * 将字符串值 value 关联到 key 。
	 * 如果 key 已经持有其他值， SET 就覆写旧值，无视类型。
	 * 对于某个原本带有生存时间（TTL）的键来说， 当 SET 命令成功在这个键上执行时， 这个键原有的 TTL 将被清除。
	 * @param 	key
	 * @param 	value
	 */
	void set(K key, V value);
	/**
	 * @param 	key
	 * @param 	value
	 * @param 	exists
	 * 			true - 只在键已经存在时，才对键进行设置操作。
	 * 			false - 只在键不存在时，才对键进行设置操作。
	 * @param 	time
	 * @param 	timeUnit
	 * @see		ICacheValue#set(String, String)
	 */
	boolean xSet(K key, V value, boolean exists, long time, TimeUnit timeUnit);
	/**
	 * 同时设置一个或多个 key-value 对。
	 * 如果某个给定 key 已经存在，那么 MSET 会用新值覆盖原来的旧值，如果这不是你所希望的效果，请考虑使用 MSETNX 命令：它只会在所有给定 key 都不存在的情况下进行设置操作。
	 * @param 	map
	 */
	void multiSet(Map<K, V> map);

}
