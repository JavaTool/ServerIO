package com.fanxing.server.io.http.client;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

import com.fanxing.server.io.http.HttpBackInfo;

class HttpProtoConnector extends DecorateHttpConnector<HttpBackInfo, HttpResponse> {
	
	private static final String KEY = "MessageId";
	
	private static final int LENGTH = 4096;

	public HttpProtoConnector(IHttpConnector<HttpResponse> connector) {
		super(connector);
	}

	@Override
	protected HttpBackInfo from(HttpResponse response) throws Exception {
		HttpEntity entity = response.getEntity();
		
		int responseId = -1;
		for (String contentType : entity.getContentType().getValue().split(";")) {
			String[] infos = contentType.trim().split("=", -2);
			if (infos[0].toLowerCase().equals(KEY)) {
				responseId = Integer.parseInt(infos[1]);
				break;
			}
		}
		
		try (InputStream is = entity.getContent()) {
			byte[] b = new byte[LENGTH];
			int n;
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			while ((n = is.read(b, 0, LENGTH)) > 0) {
				baos.write(b, 0, n);
			}
			return new HttpBackInfo(deEncrypt(baos.toByteArray()), response.getStatusLine().getStatusCode(), responseId);
		}
	}

}
