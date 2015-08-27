package com.fanxing.server.cache;

import java.io.Serializable;

/**
 * 拥有持久化计划任务的缓存器
 * <p>
 * 插入时如果存在同键的删除任务，则改为更新，deleteCache如果存在。<br>
 * 更新时如果存在同键的删除/插入任务，则不记录该任务。<br>
 * 删除时移除存在同键的更新/插入任务。<br>
 * 除删除任务之外，如果之前的任务需要保留缓存对象，则这次任务也标记为保留。<br>
 * 所有操作需要保证线程安全。
 * </p>
 * @author 	fuhuiyuan
 */
public interface IScheduledCache extends Cache, Runnable {
	
	/**
	 * 插入计划任务
	 * @param 	key
	 * 			缓存键
	 * @param 	value
	 * 			缓存对象，为空时表示已经缓存过
	 * @param 	deleteCache
	 * 			是否在持久化后删除缓存对象
	 */
	void addScheduledCreate(Serializable key, Serializable value, boolean deleteCache);
	/**
	 * 更新计划任务
	 * @param 	key
	 * 			缓存键
	 * @param 	value
	 * 			缓存对象，为空时表示已经缓存过
	 * @param 	deleteCache
	 * 			是否在持久化后删除缓存对象
	 */
	void addScheduledUpdate(Serializable key, Serializable value, boolean deleteCache);
	/**
	 * 删除计划任务
	 * @param 	key
	 * 			缓存键
	 * @param 	value
	 * 			缓存对象，为空时表示已经缓存过
	 */
	void addScheduledDelete(Serializable key, Serializable value);
	/**
	 * 插入计划任务
	 * @param 	key
	 * 			缓存键
	 * @param 	name
	 * 			哈希名称
	 * @param 	value
	 * 			缓存对象，为空时表示已经缓存过
	 * @param 	deleteCache
	 * 			是否在持久化后删除缓存对象
	 */
	void addHScheduledCreate(Serializable key, Serializable name, Serializable value, boolean deleteCache);
	/**
	 * 更新计划任务
	 * @param 	key
	 * 			缓存键
	 * @param 	name
	 * 			哈希名称
	 * @param 	value
	 * 			缓存对象，为空时表示已经缓存过
	 * @param 	deleteCache
	 * 			是否在持久化后删除缓存对象
	 */
	void addHScheduledUpdate(Serializable key, Serializable name, Serializable value, boolean deleteCache);
	/**
	 * 删除计划任务
	 * @param 	key
	 * 			缓存键
	 * @param 	name
	 * 			哈希名称
	 * @param 	value
	 * 			缓存对象，为空时表示已经缓存过
	 */
	void addHScheduledDelete(Serializable key, Serializable name, Serializable value);

}
