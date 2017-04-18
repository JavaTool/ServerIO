package org.tool.server.io.http.client.async;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.tool.server.anthenticate.IEncrypt;

public interface IResponseToBytesHandler extends IResponseHandler<byte[]> {
	
	int LENGTH = 4096;

	@Override
	default byte[] from(IEncrypt encrypt, HttpResponse response) throws Exception {
		return toBytes(encrypt, response);
	}
	
	static byte[] toBytes(IEncrypt encrypt, HttpResponse response) throws Exception {
		HttpEntity entity = response.getEntity();
		try (InputStream is = entity.getContent()) {
			byte[] b = new byte[LENGTH];
			int n;
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			while ((n = is.read(b, 0, LENGTH)) > 0) {
				baos.write(b, 0, n);
			}
			return encrypt.deEncrypt(baos.toByteArray());
		}
	}

}
