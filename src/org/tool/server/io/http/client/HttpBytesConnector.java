package org.tool.server.io.http.client;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

class HttpBytesConnector extends DecorateHttpConnector<byte[], HttpResponse> {
	
	private static final int LENGTH = 4096;
	
	public HttpBytesConnector(IHttpConnector<HttpResponse> connector) {
		super(connector);
	}
	
	@Override
	protected byte[] from(HttpResponse response) throws Exception {
		HttpEntity entity = response.getEntity();
		try (InputStream is = entity.getContent()) {
			byte[] b = new byte[LENGTH];
			int n;
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			while ((n = is.read(b, 0, LENGTH)) > 0) {
				baos.write(b, 0, n);
			}
			return deEncrypt(baos.toByteArray());
		}
	}

}
