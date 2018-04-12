package org.tool.server.persist;

import com.alibaba.fastjson.JSON;

public abstract class FastJsonUserType<T> extends JsonUserType<T> {

	@Override
	protected T makeObject(String str) throws Exception {
		return JSON.parseObject(str, returnedClass());
	}

	@Override
	protected String makeString(Object object) throws Exception {
		return JSON.toJSONString(object);
	}

}
