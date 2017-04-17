package com.fanxing.server.io.http.client.async;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.String.valueOf;

import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.nio.client.HttpAsyncClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fanxing.server.anthenticate.IEncrypt;

class HttpAsynConnector<T> implements IHttpAsynConnector<T> {
	
	private static final Logger log = LoggerFactory.getLogger(HttpAsynConnector.class);
	
	private HttpAsyncClient httpClient;
	
	private HttpHost httpHost;
	
	private String uri;
	
	private String session;
	
	private IResponseCodeHandler responseCodeHandler;
	
	private IEncrypt encrypt;
	
	private RequestConfig config;
	
	public HttpAsynConnector() {
		httpClient = HttpAsyncClients.createDefault();
		setResponseCodeHandler(code -> {if (code >= 400) throw new Exception("code is : " + code);});
		setEncrypt(new IEncrypt() {
			
			@Override
			public byte[] encrypt(byte[] src) {
				return src;
			}
			
			@Override
			public byte[] deEncrypt(byte[] src) {
				return src;
			}
			
		});
	}

	@Override
	public void setUrl(String url) {
		this.uri = url;
		refresh();
	}

	@Override
	public void setTimeout(int timeout) {
		if (timeout > 0) {
			config = RequestConfig.custom().setConnectionRequestTimeout(timeout).setConnectTimeout(timeout).setSocketTimeout(timeout).build();
		}
	}

	@Override
	public void setResponseCodeHandler(IResponseCodeHandler handler) {
		this.responseCodeHandler = checkNotNull(handler);
	}

	@Override
	public String getCookie() {
		return session;
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
	
	private void makeByteEntity(HttpEntityEnclosingRequestBase request, byte[] bytes) {
		if (checkNotNull(bytes).length > 0) {
			request.setEntity(new ByteArrayEntity(encrypt.encrypt(bytes)));
		}
	}
	
	private void request(HttpRequestBase request, Map<String, Object> head, IResponseHandler<T> hanler) {
		checkNotNull(head).forEach((k, v) -> request.setHeader(k, valueOf(v)));
		request.setConfig(config);
		httpClient.execute(httpHost, request, new Callback(hanler));
	}
	
	private class Callback implements FutureCallback<HttpResponse> {
		
		private final IResponseHandler<T> hanler;
		
		public Callback(IResponseHandler<T> hanler) {
			this.hanler = hanler;
		}

		@Override
		public void completed(HttpResponse response) {
			readCookie(response);
			try {
				responseCodeHandler.handleResponseCode(response.getStatusLine().getStatusCode());
				hanler.handleResponse(hanler.from(encrypt, response));
			} catch (Exception e) {
				failed(e);
			}
		}

		@Override
		public void failed(Exception ex) {
			log.error("", ex);
		}

		@Override
		public void cancelled() {
			log.error("http cancelled.");
		}
		
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

	@Override
	public void post(String params, byte[] bytes, Map<String, Object> head, IResponseHandler<T> hanler) {
		HttpEntityEnclosingRequestBase request = createPostRequest(params);
		makeByteEntity(request, bytes);
		request(request, head, hanler);
	}

	@Override
	public void get(String params, Map<String, Object> head, IResponseHandler<T> hanler) {
		request(createGetRequest(params), head, hanler);
	}

	@Override
	public void put(String params, byte[] bytes, Map<String, Object> head, IResponseHandler<T> hanler) {
		HttpEntityEnclosingRequestBase request = createPutRequest(params);
		makeByteEntity(request, bytes);
		request(request, head, hanler);
	}

	@Override
	public void delete(String params, Map<String, Object> head, IResponseHandler<T> hanler) {
		request(createDeleteRequest(params), head, hanler);
	}

}
