package org.tool.server.collection.list;

public interface IRangeList<V> {
	
	void add(int bounde, V value);
	
	V get(int key);
	
	int getMin();
	
	int getMax();
	
	void clear();

}
