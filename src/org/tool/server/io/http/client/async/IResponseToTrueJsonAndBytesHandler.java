package org.tool.server.io.http.client.async;

import static org.tool.server.io.http.client.async.IResponseToJsonAndBytesHandler.toJsonAndBytes;

import org.apache.http.HttpResponse;
import org.tool.server.anthenticate.IEncrypt;
import org.tool.server.io.IOParam;
import org.tool.server.io.http.client.IJsonAndBytes;

import com.alibaba.fastjson.JSONObject;

public interface IResponseToTrueJsonAndBytesHandler extends IResponseHandler<IJsonAndBytes>, IOParam {

	@Override
	default IJsonAndBytes from(IEncrypt encrypt, HttpResponse response) throws Exception {
		IJsonAndBytes s = toJsonAndBytes(encrypt, response);
		JSONObject json = s.getJson();
		if (json.getInteger(RET_CODE) == CODE_OK) {
			return s;
		} else {
			throw new Exception(json.getString(RET_MSG));
		}
	}

}
