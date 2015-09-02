package com.fanxing.server.cache;

import java.io.Serializable;

/**
 * 带前缀键的缓存器
 * @author 	fuhuiyuan
 */
public abstract class PreKeyCache extends PreKeyContainer<IScheduledCache> {
	
	/**创建-键*/
	protected final Serializable createKey;
	/**更新-键*/
	protected final Serializable updateKey;
	/**删除-键*/
	protected final Serializable deleteKey;

	public PreKeyCache(Serializable preKey, IScheduledCache cache, Serializable createKey, Serializable updateKey, Serializable deleteKey) {
		super(preKey, cache);
		this.createKey = preKey + "_" + createKey;
		this.updateKey = preKey + "_" + updateKey;
		this.deleteKey = preKey + "_" + deleteKey;
	}
	
	/**
	 * 创建
	 * @param 	name
	 * 			名称
	 * @param 	value
	 * 			值
	 */
	protected void create(Serializable name, Serializable value) {
		cache.addHScheduledUpdate(createKey, name, value, true);
	}
	
	/**
	 * 更新
	 * @param 	name
	 * 			名称
	 * @param 	value
	 * 			值
	 */
	protected void update(Serializable name, Serializable value) {
		cache.addHScheduledUpdate(updateKey, name, value, true);
	}
	
	/**
	 * 删除
	 * @param 	name
	 * 			名称
	 * @param 	value
	 * 			值
	 */
	protected void delete(Serializable name, Serializable value) {
		cache.addHScheduledDelete(deleteKey, name, value);
	}

}
