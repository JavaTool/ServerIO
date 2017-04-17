package com.fanxing.server.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fanxing.server.ioc.IOC;
import com.fanxing.server.reload.IStartable;
import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.MutableClassToInstanceMap;

/**
 * 服务组
 * @author	fuhuiyuan
 */
public class Services implements IStartable, IServices {
	
	private static final Logger log = LoggerFactory.getLogger(Services.class);
	/**服务集合*/
	private ClassToInstanceMap<Object> services;
	
	private IOC ioc;
	
	public Services() {
		services = MutableClassToInstanceMap.create();
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

	@Override
	@SuppressWarnings("unchecked")
	public <X, Y extends X> Y getService(Class<X> clz) {
		return services.containsKey(clz) ? (Y) services.get(clz) : ioc.getBean(clz);
	}

	@Override
	public void startup() throws Exception {
		services.values().forEach((service) -> {
			if (service instanceof IStartable) {
				try {
					((IStartable) service).startup();
				} catch (Exception e) {
					log.error("", e);
				}
			}
		});
		ioc.getAll().values().forEach((service) -> {
			if (service instanceof IStartable) {
				try {
					((IStartable) service).startup();
				} catch (Exception e) {
					log.error("", e);
				}
			}
		});
	}

	public void setIoc(IOC ioc) {
		this.ioc = ioc;
	}

}
