package org.tool.server.udsl;

public interface ReadOnlyUDSL extends UDSL {
	
	@Deprecated
	@Override
	default <T> void save(T entity) {}
	
	@Deprecated
	@Override
	default <T> void delete(T entity) {}

}
