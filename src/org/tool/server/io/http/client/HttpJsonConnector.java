package org.tool.server.io.http.client;

import static com.alibaba.fastjson.JSON.parseObject;

import java.io.UnsupportedEncodingException;

import com.alibaba.fastjson.JSONObject;

class HttpJsonConnector extends DecorateHttpConnector<JSONObject, byte[]> {
	
	public HttpJsonConnector(IHttpConnector<byte[]> connector) {
		super(connector);
	}
	
	@Override
	protected JSONObject from(byte[] bytes) {
		try {
			return bytesToJson(bytes);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
	
	static JSONObject bytesToJson(byte[] bytes) throws UnsupportedEncodingException {
		return parseObject(new String(bytes, "utf-8"));
	}

}
