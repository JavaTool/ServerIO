package com.fanxing.server.cache;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 缓存清理器
 * @author 	fuhuiyuan
 */
public class CacheCleaner {
	
	protected static final Logger log = LoggerFactory.getLogger(CacheCleaner.class);
	/**缓存键容器组*/
	protected final List<CacheKeyContainer> containers;
	/**缓存器*/
	protected final Cache cache;
	
	public CacheCleaner(Cache cache) {
		this.cache = cache;
		containers = createContainers();
	}
	
	/**
	 * 创建缓存键容器组
	 * @return	缓存键容器组
	 */
	protected List<CacheKeyContainer> createContainers() {
		return new LinkedList<CacheKeyContainer>();
	}
	
	/**
	 * 添加缓存键容器
	 * @param 	container
	 * 			缓存键容器
	 */
	public void addContainer(CacheKeyContainer container) {
		containers.add(container);
	}
	
	/**
	 * 清空缓存
	 */
	public void clear() {
		for (CacheKeyContainer cacheKeyContainer : containers) {
			Serializable[] keys = cacheKeyContainer.getKeys();
			if (keys != null) {
				for (Serializable key : keys) {
					cache.del(key);
					log.info("Cache delete key : {}.", key);
				}
			}
		}
	}

}
