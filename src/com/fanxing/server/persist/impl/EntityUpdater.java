package com.fanxing.server.persist.impl;

import java.io.Serializable;

import com.fanxing.server.asynchronous.BlockingExecuter;
import com.fanxing.server.persist.EntityManager;

/**
 * 实体更新器
 * @author 	fuhuiyuan
 */
public class EntityUpdater implements BlockingExecuter<Serializable> {
	
	protected EntityManager entityManager;
	
	public EntityUpdater(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public void setBlockingObject(Serializable object) throws Exception {
		entityManager.updateSync(object);
	}

}
