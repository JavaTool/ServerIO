package com.fanxing.server.persist.impl;

import java.io.Serializable;

import com.fanxing.server.asynchronous.BlockingExecuter;
import com.fanxing.server.persist.EntityManager;

/**
 * 实体删除器
 * @author 	fuhuiyuan
 */
public class EntityDeleter implements BlockingExecuter<Serializable> {
	
	protected EntityManager entityManager;
	
	public EntityDeleter(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public void setBlockingObject(Serializable object) throws Exception {
		entityManager.deleteSync(object);
	}

}
