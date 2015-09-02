package com.fanxing.server.persist.impl;

import java.io.Serializable;

import com.fanxing.server.asynchronous.BlockingExecuter;
import com.fanxing.server.persist.EntityManager;

public class EntityCreater implements BlockingExecuter<Serializable> {
	
	protected EntityManager entityManager;
	
	public EntityCreater(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public void setBlockingObject(Serializable object) throws Exception {
		entityManager.createSync(object);
	}

}
