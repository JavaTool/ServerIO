package org.tool.server.io.http.client.async;

import org.apache.http.HttpResponse;
import org.tool.server.anthenticate.IEncrypt;

public interface IResponseHandler<T> {
	
	void handleResponse(T t);
	
	T from(IEncrypt encrypt, HttpResponse response) throws Exception;

}