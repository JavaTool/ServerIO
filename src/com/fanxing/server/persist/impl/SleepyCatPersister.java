package com.fanxing.server.persist.impl;

import org.hibernate.persister.entity.EntityPersister;

public interface SleepyCatPersister {
	
	byte[] getData(Object value, EntityPersister ep);
	
	Object getObject(byte[] bytes, EntityPersister ep) throws Exception;
	
}
