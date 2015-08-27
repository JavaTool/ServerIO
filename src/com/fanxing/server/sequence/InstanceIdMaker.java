package com.fanxing.server.sequence;

/**
 * id生成器
 * @author	fuhuiyuan
 */
public interface InstanceIdMaker {
	
	/**
	 * 生成新的id
	 * @return	id
	 */
	int nextInstanceId();

}
