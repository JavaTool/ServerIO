package org.tool.server.io.http.client;

import org.tool.server.io.IOParam;

import com.alibaba.fastjson.JSONObject;

class HttpTrueJsonConnector extends DecorateHttpConnector<JSONObject, JSONObject> implements IOParam {

	public HttpTrueJsonConnector(IHttpConnector<JSONObject> connector) {
		super(connector);
	}

	@Override
	protected JSONObject from(JSONObject s) throws Exception {
		if (s.getInteger(RET_CODE) == CODE_OK) {
			return s;
		} else {
			throw new Exception(s.getString(RET_MSG));
		}
	}

}
