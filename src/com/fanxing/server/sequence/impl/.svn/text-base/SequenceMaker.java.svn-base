package com.fanxing.server.sequence.impl;

import com.fanxing.server.cache.Cache;
import com.fanxing.server.sequence.InstanceIdMaker;

/**
 * 序列号生成器
 * @author	fuhuiyuan
 */
public class SequenceMaker implements InstanceIdMaker {
	
	/**缓存器*/
	protected Cache cache;
	/**名称*/
	protected String name;
	
	public SequenceMaker(Cache cache, String name) {
		this.cache = cache;
		this.name = name;
	}

	@Override
	public synchronized int nextInstanceId() {
		return ((Integer) cache.get(name)) + 1;
	}

}
