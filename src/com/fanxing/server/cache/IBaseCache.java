package com.fanxing.server.cache;

import com.fanxing.server.coder.ICoder;

public interface IBaseCache<T> extends ICache<T, T, T> {
	
	ICoder<T> getCoder();

}
