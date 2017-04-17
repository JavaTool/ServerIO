package com.fanxing.server.io.http.client.async;

import static com.fanxing.server.io.http.client.async.IResponseToJsonAndBytesHandler.toJsonAndBytes;

import org.apache.http.HttpResponse;

import com.alibaba.fastjson.JSONObject;
import com.fanxing.server.anthenticate.IEncrypt;
import com.fanxing.server.io.IOParam;
import com.fanxing.server.io.http.client.IJsonAndBytes;

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
