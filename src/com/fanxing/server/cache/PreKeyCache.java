package com.fanxing.server.cache;

import java.io.Serializable;

public abstract class PreKeyCache extends PreKeyContainer<IScheduledCache> {
	
	protected final Serializable createKey;
	
	protected final Serializable updateKey;
	
	protected final Serializable deleteKey;

	public PreKeyCache(Serializable preKey, IScheduledCache cache, Serializable createKey, Serializable updateKey, Serializable deleteKey) {
		super(preKey, cache);
		this.createKey = preKey + "_" + createKey;
		this.updateKey = preKey + "_" + updateKey;
		this.deleteKey = preKey + "_" + deleteKey;
	}
	
	protected void create(Serializable name, Serializable value) {
		cache.addHScheduledUpdate(createKey, name, value, true);
	}
	
	protected void update(Serializable name, Serializable value) {
		cache.addHScheduledUpdate(updateKey, name, value, true);
	}
	
	protected void delete(Serializable name, Serializable value) {
		cache.addHScheduledDelete(deleteKey, name, value);
	}

}
