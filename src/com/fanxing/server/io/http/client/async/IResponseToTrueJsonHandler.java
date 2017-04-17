package com.fanxing.server.io.http.client.async;

import static com.fanxing.server.io.http.client.async.IResponseToJsonHandler.toJson;

import org.apache.http.HttpResponse;

import com.alibaba.fastjson.JSONObject;
import com.fanxing.server.anthenticate.IEncrypt;
import com.fanxing.server.io.IOParam;

public interface IResponseToTrueJsonHandler extends IResponseHandler<JSONObject>, IOParam {

	@Override
	default JSONObject from(IEncrypt encrypt, HttpResponse response) throws Exception {
		JSONObject json = toJson(encrypt, response);
		if (json.getInteger(RET_CODE) == CODE_OK) {
			return json;
		} else {
			throw new Exception(json.getString(RET_MSG));
		}
	}

}
