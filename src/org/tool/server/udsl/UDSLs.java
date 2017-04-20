package org.tool.server.udsl;

import java.util.Map;

import org.hibernate.cfg.Configuration;

import com.google.common.collect.Maps;

public final class UDSLs {
	
	private UDSLs() {}
	
	public static UDSL createHibernateUDSL(Configuration conf) {
		return new HibernateUDSL(conf);
	}
	
	public static UDSL createMapUDSL(Map<String, Object> map, IMapObjects mapObjects) {
		return new MapUDSL(map, mapObjects);
	}
	
	public static UDSL createMapUDSL(IMapObjects mapObjects) {
		return createMapUDSL(Maps.newConcurrentMap(), mapObjects);
	}

}
