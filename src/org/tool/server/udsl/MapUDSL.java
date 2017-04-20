package org.tool.server.udsl;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

class MapUDSL implements UDSL {
	
	private final Map<String, Object> map;
	
	private final IMapObjects mapObjects;
	
	public MapUDSL(Map<String, Object> map, IMapObjects mapObjects) {
		this.map = map;
		this.mapObjects = mapObjects;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T fetch(Object... params) {
		return (T) map.get(params[0]);
	}

	@Override
	public <T> List<T> find(Object... params) {
		List<T> list = Lists.newArrayListWithCapacity(params.length);
		for (Object param : params) {
			list.add(fetch(param));
		}
		return list;
	}

	@Deprecated
	@Override
	public <T> List<T> orderBy(boolean dec, Object... params) {
		return null;
	}

	@Deprecated
	@Override
	public <T> List<T> limit(int offset, int count, Object... params) {
		return null;
	}

	@Override
	public <T> void save(T entity) {
		map.put(mapObjects.getKey(entity), mapObjects.getValue(entity));
	}

	@Override
	public <T> void delete(T entity) {
		map.remove(mapObjects.getKey(entity));
	}

}
