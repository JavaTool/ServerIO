package org.tool.server.cache;

public interface ITransaction<K, F, V> {
	
	void exec(IKVCache<K, F, V> cacheTransaction);

}