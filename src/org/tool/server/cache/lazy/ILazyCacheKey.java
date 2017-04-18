package org.tool.server.cache.lazy;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.tool.server.cache.ICacheKey;
import org.tool.server.cache.ICacheKey.ValueType;

public interface ILazyCacheKey extends IKey {
	
	void setKey(String key);
	/**
	 * 删除 key。不存在的 key 会被忽略。
	 */
	void delete();
	/**
	 * 检查给定 key 是否存在。
	 * @param 	key
	 * 			
	 * @return	给定 key 是否存在。
	 */
	boolean exists();
	/**
	 * 为给定 key 设置生存时间，当 key 过期时(生存时间为 0 )，它会被自动删除。
	 * 可以对一个已经带有生存时间的 key 执行 EXPIRE 命令，新指定的生存时间会取代旧的生存时间。
	 * @param 	seconds
	 * 			
	 * @param 	timeUnit
	 * 			
	 */
	void expire(long time, TimeUnit timeUnit);
	/**
	 * EXPIREAT 的作用和 EXPIRE 类似，都用于为 key 设置生存时间。不同在于 EXPIREAT 命令接受的时间参数是 UNIX 时间戳(unix timestamp)。
	 * @param 	timestamp
	 * 			
	 */
	void expireat(long timestamp);
	/**
	 * 查找所有符合给定模式 pattern 的 key 。
	 * @param 	pattern
	 * 			
	 * @return	符合给定模式 pattern 的 key
	 */
	Set<String> keys(String pattern);
	/**
	 * 移除给定 key 的生存时间，将这个 key 从『易失的』(带生存时间 key )转换成『持久的』(一个不带生存时间、永不过期的 key )。
	 * @param 	key
	 * 			
	 */
	void persist();
	/**
	 * 返回给定 key 的剩余生存时间(TTL, time to live)。
	 * @return	剩余生存时间（毫秒）
	 * @see		ICacheKey#TTL_NONE
	 * @see		ICacheKey#TTL_EVER
	 */
	long ttl();
	/**
	 * 将 key 改名为 newkey。
	 * @param 	newkey
	 * 			
	 */
	void rename(String newkey);
	/**
	 * 返回 key 所储存的值的类型。
	 * @return	值的类型。
	 */
	ValueType type();

}
