package org.tool.server.persist;

import com.alibaba.fastjson.JSON;

public abstract class JsonUserType extends AbstractStringUserType {
	
	private static final String EMPTY = "{}";

	@Override
	protected Object makeObject(String str, Object owner) throws Exception {
		return makeObject(str);
	}

	@Override
	protected Object makeObject(Object owner) throws Exception {
		return makeObject(EMPTY);
	}

	@Override
	protected Object makeObject() throws Exception {
		return makeObject(EMPTY);
	}

	@Override
	protected String makeString() throws Exception {
		return EMPTY;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Object makeObject(String str) throws Exception {
		return JSON.parseObject(str, returnedClass());
	}

	@Override
	protected String makeString(Object object) throws Exception {
		return JSON.toJSONString(object);
	}

}
