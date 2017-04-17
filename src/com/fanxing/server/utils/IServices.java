package com.fanxing.server.utils;

public interface IServices {
	
	/**
	 * 获取服务
	 * @param 	clz
	 * 			服务类型
	 * @return	服务
	 */
	<X, Y extends X> Y getService(Class<X> clz);

}
