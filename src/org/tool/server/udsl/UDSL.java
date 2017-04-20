package org.tool.server.udsl;

import java.util.List;

public interface UDSL {
	
	<T> T fetch(Object... params);
	
	<T> List<T> find(Object... params);
	
	<T> List<T> orderBy(boolean dec, Object... params);
	
	<T> List<T> limit(int offset, int count, Object... params);
	
	<T> void save(T entity);
	
	<T> void delete(T entity);

}
