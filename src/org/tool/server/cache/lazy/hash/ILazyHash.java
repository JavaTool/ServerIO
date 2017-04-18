package org.tool.server.cache.lazy.hash;

import java.util.Map;
import java.util.Set;

public interface ILazyHash<F, V> {
	
	void hashSet(F field, V value);
	
	void hashSet(Map<F, V> values);
	
	V hashGet(F field);
	
	void hashDelete(F field);
	
	Map<F, V> getAll();
	
	int hashSize();
	
	Class<V> getValueClass();
	
	void clear();
	
	Set<F> fields();
	
	boolean contains(F field);
	
	boolean exists();

}
