package org.tool.server.io.http.client.async;

import static org.tool.server.io.http.client.async.IResponseToJsonHandler.toJson;

import org.apache.http.HttpResponse;
import org.tool.server.anthenticate.IEncrypt;
import org.tool.server.io.IOParam;

import com.alibaba.fastjson.JSONObject;

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
