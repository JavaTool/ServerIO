package persist.impl;

public interface Cache {
	
	void createGroup(String group);
	
	void put(String group, Object key, Object entity);
	
	Object get(String group, Object key);
	
	boolean remove(String group, Object key);
	
}
