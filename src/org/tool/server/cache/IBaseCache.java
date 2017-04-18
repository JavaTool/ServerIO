package org.tool.server.cache;

import org.tool.server.coder.ICoder;

public interface IBaseCache<T> extends ICache<T, T, T> {
	
	ICoder<T> getCoder();

}
