package com.fanxing.server.io.dispatch;

/**
 * 消息接收器
 * @author 	fuhuiyuan
 */
public interface IContentHandler {
	
	/**
	 * 接收消息
	 * @param 	content
	 * 			消息
	 * @throws 	Exception
	 */
	void handle(IContent content) throws Exception;

}
