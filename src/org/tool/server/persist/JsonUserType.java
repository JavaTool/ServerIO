package org.tool.server.persist;

public abstract class JsonUserType<T> extends AbstractStringUserType<T> {
	
	private static final String EMPTY = "{}";

	@Override
	protected T makeObject(String str, Object owner) throws Exception {
		return makeObject(str);
	}

	@Override
	protected T makeObject(Object owner) throws Exception {
		return makeObject(EMPTY);
	}

	@Override
	protected T makeObject() throws Exception {
		return makeObject(EMPTY);
	}

	@Override
	protected String makeString() throws Exception {
		return EMPTY;
	}

}
