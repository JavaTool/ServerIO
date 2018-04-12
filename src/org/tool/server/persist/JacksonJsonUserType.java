package org.tool.server.persist;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class JacksonJsonUserType<T> extends JsonUserType<T> {
	
	private static ObjectMapper MAPPER = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

	@SuppressWarnings("unchecked")
	@Override
	protected T makeObject(String str) throws Exception {
		return (T) MAPPER.readValue(str, returnedClass());
	}

	@Override
	protected String makeString(Object object) throws Exception {
		return MAPPER.writeValueAsString(object);
	}

}
