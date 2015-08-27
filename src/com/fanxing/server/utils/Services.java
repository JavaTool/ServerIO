package com.fanxing.server.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 服务组
 * @author	fuhuiyuan
 */
public class Services {
	
	/**服务集合*/
	@SuppressWarnings("rawtypes")
	private Map<Class, Object> services;
	
	@SuppressWarnings("rawtypes")
	public Services() {
		services = new HashMap<Class, Object>();
	}
	
	/**
	 * 添加服务
	 * @param 	clz
	 * 			服务类型
	 * @param 	service
	 * 			服务
	 */
	public <X, Y extends X> void addServie(Class<X> clz, Y service) {
		services.put(clz, service);
	}
	
	/**
	 * 获取服务
	 * @param 	clz
	 * 			服务类型
	 * @return	服务
	 */
	@SuppressWarnings("unchecked")
	public <X, Y extends X> Y getService(Class<X> clz) {
		return (Y) services.get(clz);
	}

}
