package com.fanxing.server.io.http.client;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.String.valueOf;

import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fanxing.server.anthenticate.IEncrypt;
import com.fanxing.server.utils.DateUtil;

class HttpConnector implements IHttpConnector<HttpResponse> {
	
	private static final Logger log = LoggerFactory.getLogger(HttpConnector.class);
	
	private static final int TIME_OUT = 10 * DateUtil.SECOND_MILLIS;
	
	private static final IEncrypt DEFAULT_ENCRYPT = new IEncrypt() {
		
		@Override
		public byte[] encrypt(byte[] src) {
			return src;
		}
		
		@Override
		public byte[] deEncrypt(byte[] src) {
			return src;
		}
		
	};
	
	private HttpClient httpClient;
	
	private HttpHost httpHost;
	
	private String uri;
	
	private String session;
	
	private IResponseCodeHandler responseCodeHandler;
	
	private IEncrypt encrypt;
	
	private RequestConfig config;
	
	public HttpConnector() {
		httpClient = HttpClientBuilder.create().build();
		setResponseCodeHandler(code -> {if (code >= 400) throw new Exception("code is : " + code);});
		setEncrypt(DEFAULT_ENCRYPT);
		setTimeout(TIME_OUT);
	}
	
	@Override
	public void setUrl(String url) {
		this.uri = url;
		refresh();
	}

	@Override
	public String getCookie() {
		return session;
	}

	@Override
	public HttpResponse get(String param, Map<String, Object> head) throws Exception {
		return request(createGetRequest(param), head);
	}

	@Override
	public HttpResponse delete(String param, Map<String, Object> head) throws Exception {
		return request(createDeleteRequest(param), head);
	}

	@Override
	public HttpResponse post(String params, byte[] bytes, Map<String, Object> head) throws Exception {
		HttpEntityEnclosingRequestBase request = createPostRequest(params);
		makeByteEntity(request, bytes);
		return request(request, head);
	}
	
	private void makeByteEntity(HttpEntityEnclosingRequestBase request, byte[] bytes) {
		if (checkNotNull(bytes).length > 0) {
			request.setEntity(new ByteArrayEntity(encrypt.encrypt(bytes)));
		}
	}

	@Override
	public HttpResponse put(String params, byte[] bytes, Map<String, Object> head) throws Exception {
		HttpEntityEnclosingRequestBase request = createPutRequest(params);
		makeByteEntity(request, bytes);
		return request(request, head);
	}
	
	private HttpResponse request(HttpRequestBase request, Map<String, Object> head) throws Exception {
		checkNotNull(head).forEach((k, v) -> request.setHeader(k, valueOf(v)));
		request.setConfig(config);
		HttpResponse response = httpClient.execute(httpHost, request);
		readCookie(response);
		responseCodeHandler.handleResponseCode(response.getStatusLine().getStatusCode());
		return response;
	}
	
	private void readCookie(HttpResponse response) {
		for (Header header : response.getHeaders("Set-Cookie")) {
			for (String info : header.getValue().split(";")) {
				if (info.toUpperCase().startsWith("JSESSIONID=")) {
					session = info.split("=")[1];
				}
			}
		}
	}
	
	private HttpPost createPostRequest(String param) {
		return new HttpPost(uri + checkNotNull(param));
	}
	
	private HttpGet createGetRequest(String param) {
		return new HttpGet(uri + checkNotNull(param));
	}
	
	private HttpPut createPutRequest(String param) {
		return new HttpPut(uri + checkNotNull(param));
	}
	
	private HttpDelete createDeleteRequest(String param) {
		return new HttpDelete(uri + checkNotNull(param));
	}

	@Override
	public void setResponseCodeHandler(IResponseCodeHandler handler) {
		this.responseCodeHandler = checkNotNull(handler);
	}

	@Override
	public void setEncrypt(IEncrypt encrypt) {
		this.encrypt = encrypt == null ? this.encrypt : encrypt;
	}

	@Override
	public byte[] deEncrypt(byte[] src) {
		return encrypt.deEncrypt(src);
	}

	@Override
	public void refresh() {
		httpHost = HttpHost.create(checkNotNull(uri).replaceFirst("http://", "").replaceFirst("https://", "").split("/")[0]);
		log.info("Refresh url : {}", uri);
	}

	@Override
	public void setTimeout(int timeout) {
		if (timeout > 0) {
			config = RequestConfig.custom().setConnectionRequestTimeout(timeout).setConnectTimeout(timeout).setSocketTimeout(timeout).build();
		}
	}

}
