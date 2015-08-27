package com.fanxing.server.sequence.impl;

import java.util.concurrent.atomic.AtomicInteger;

import com.fanxing.server.sequence.InstanceIdMaker;

/**
 * Java自动id生成器
 * @author	fuhuiyuan
 */
public class JavaAutoIdMaker implements InstanceIdMaker {
	
	protected AtomicInteger id_gen = new AtomicInteger();

	@Override
	public int nextInstanceId() {
		return id_gen.incrementAndGet();
	}

}
