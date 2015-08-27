package com.fanxing.server.io.dispatch;

public interface IDispatch {
	
	void addDispatch(IContent content);
	
	void fireDispatch(IContent content);

}
