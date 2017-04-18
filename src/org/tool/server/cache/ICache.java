package org.tool.server.cache;

import java.util.List;

public interface ICache<K, F, V> extends IKVCache<K, F, V> {
	
	void setName(String name);
	
	void changeDB(int index);
	
	List<Object> transaction(ITransaction<K, F, V> transaction, @SuppressWarnings("unchecked") K... keys);

}
