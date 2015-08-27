package com.fanxing.server.io.proto;

public class RedirectResponse extends Response {
	
	private String url;

	public RedirectResponse(Request request) {
		super(request);
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
