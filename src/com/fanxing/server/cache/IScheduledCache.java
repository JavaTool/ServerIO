package com.fanxing.server.cache;

import java.io.Serializable;

public interface IScheduledCache extends Cache, Runnable {
	
	void addScheduledCreate(Serializable key, Serializable value, boolean deleteCache);
	
	void addScheduledUpdate(Serializable key, Serializable value, boolean deleteCache);
	
	void addScheduledDelete(Serializable key, Serializable value);
	
	void addHScheduledCreate(Serializable key, Serializable name, Serializable value, boolean deleteCache);
	
	void addHScheduledUpdate(Serializable key, Serializable name, Serializable value, boolean deleteCache);
	
	void addHScheduledDelete(Serializable key, Serializable name, Serializable value);

}
