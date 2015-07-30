package com.fanxing.server.cache;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 缓存器
 * @author 	fuhuiyuan
 */
public interface Cache {
	
	/**
	 * 存储一个对象
	 * @param 	key
	 * 			键
	 * @param 	object
	 * 			对象
	 */
	void set(Serializable key, Serializable object);
	/**
	 * 以哈希的方式存储一个对象
	 * @param 	key
	 * 			键
	 * @param 	name
	 * 			哈希名称
	 * @param 	object
	 * 			对象
	 */
	void hset(Serializable key, Serializable name, Serializable object);
	/**
	 * 存储多个对象
	 * @param 	map
	 * 			键值集合
	 */
	void mSet(Map<Serializable, Serializable> map);
	/**
	 * 以哈希的方式存储多个对象
	 * @param 	key
	 * 			键
	 * @param 	map
	 * 			键值集合
	 */
	void hmSet(Serializable key, Map<Serializable, Serializable> map);
	/**
	 * 删除一个键所对应的内容
	 * @param 	key
	 * 			键
	 */
	void del(Serializable key);
	/**
	 * 删除一个键所对应的哈希名称的内容
	 * @param 	key
	 * 			键
	 * @param 	name
	 * 			哈希名称
	 */
	void hdel(Serializable key, Serializable name);
	/**
	 * 删除多个键所对应的内容
	 * @param 	keys
	 * 			键集合
	 */
	void mDel(Serializable... keys);
	/**
	 * 删除一个键所对应的哈希名称的内容
	 * @param 	key
	 * 			键
	 * @param 	names
	 * 			哈希名称集合
	 */
	void hmDel(Serializable key, Serializable... names);
	/**
	 * 获取一个存储内容
	 * @param 	key
	 * 			键
	 * @return	存储内容
	 */
	Object get(Serializable key);
	/**
	 * 以哈希的方式获取一个存储内容
	 * @param 	key
	 * 			键
	 * @param 	name
	 * 			哈希名称
	 * @return	存储内容
	 */
	Object hget(Serializable key, Serializable name);
	/**
	 * 获取多个存储内容
	 * @param 	keys
	 * 			键集合
	 * @return	存储内容列表
	 */
	List<Object> mGet(Serializable... keys);
	/**
	 * 以哈希的方式获取多个存储内容
	 * @param 	key
	 * 			键
	 * @param 	names
	 * 			哈希名称集合
	 * @return	存储内容列表
	 */
	List<Object> hmGet(Serializable key, Serializable... names);
	/**
	 * 清空缓存
	 */
	void clear();
	/**
	 * 获取一个哈希的域长度
	 * @param 	key
	 * 			键
	 * @return	哈希的域长度
	 */
	long hlen(Serializable key);

}
