package com.fanxing.server.cache;

import java.io.Serializable;

/**
 * 带有前缀的缓存键容器
 * @author 	fuhuiyuan
 */
public abstract class PreKeyContainer implements CacheKeyContainer {
	
	/**键前缀*/
	protected final Serializable preKey;
	/**缓存器*/
	protected final Cache cache;
	
	public PreKeyContainer(Serializable preKey, Cache cache) {
		this.preKey = preKey;
		this.cache = cache;
	}

}
