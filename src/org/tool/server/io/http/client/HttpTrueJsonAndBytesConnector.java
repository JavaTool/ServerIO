package org.tool.server.io.http.client;

import org.tool.server.io.IOParam;

import com.alibaba.fastjson.JSONObject;

class HttpTrueJsonAndBytesConnector extends DecorateHttpConnector<IJsonAndBytes, IJsonAndBytes> implements IOParam {

	public HttpTrueJsonAndBytesConnector(IHttpConnector<IJsonAndBytes> connector) {
		super(connector);
	}

	@Override
	protected IJsonAndBytes from(IJsonAndBytes s) throws Exception {
		JSONObject json = s.getJson();
		if (json.getInteger(RET_CODE) == CODE_OK) {
			return s;
		} else {
			throw new Exception(json.getString(RET_MSG));
		}
	}

}
