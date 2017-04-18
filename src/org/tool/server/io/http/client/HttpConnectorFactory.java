package org.tool.server.io.http.client;

import org.apache.http.HttpResponse;
import org.tool.server.io.http.HttpBackInfo;

import com.alibaba.fastjson.JSONObject;

public class HttpConnectorFactory {
	
	private HttpConnectorFactory() {}
	
	public static IHttpConnector<HttpResponse> createHttpResponse() {
		return new HttpConnector();
	}
	
	public static IHttpConnector<byte[]> createBytes() {
		return createBytes(createHttpResponse());
	}
	
	public static IHttpConnector<byte[]> createBytes(IHttpConnector<HttpResponse> connector) {
		return new HttpBytesConnector(connector);
	}
	
	public static IHttpConnector<JSONObject> createJSONObject() {
		return createJSONObject(createBytes());
	}
	
	public static IHttpConnector<JSONObject> createJSONObject(IHttpConnector<byte[]> connector) {
		return new HttpJsonConnector(connector);
	}
	
	public static IHttpConnector<IJsonAndBytes> createJsonAndBytes() {
		return createJsonAndBytes(createBytes());
	}
	
	public static IHttpConnector<IJsonAndBytes> createJsonAndBytes(IHttpConnector<byte[]> connector) {
		return new HttpJsonAndBytesConnector(connector);
	}
	
	public static IHttpConnector<HttpBackInfo> createHttpBackInfo() {
		return createHttpBackInfo(createHttpResponse());
	}
	
	public static IHttpConnector<HttpBackInfo> createHttpBackInfo(IHttpConnector<HttpResponse> connector) {
		return new HttpProtoConnector(connector);
	}
	
	public static IHttpConnector<JSONObject> createTrueJSONObject() {
		return createTrueJSONObject(createJSONObject());
	}
	
	public static IHttpConnector<JSONObject> createTrueJSONObject(IHttpConnector<JSONObject> connector) {
		return new HttpTrueJsonConnector(connector);
	}
	
	public static IHttpConnector<IJsonAndBytes> createTrueJsonAndBytes() {
		return createTrueJsonAndBytes(createJsonAndBytes());
	}
	
	public static IHttpConnector<IJsonAndBytes> createTrueJsonAndBytes(IHttpConnector<IJsonAndBytes> connector) {
		return new HttpTrueJsonAndBytesConnector(connector);
	}

}
