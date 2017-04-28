package org.tool.server.io.dispatch;

import org.tool.server.io.proto.IErrorHandler;

/**
 * 消息接收器
 * @author 	fuhuiyuan
 */
public interface IContentHandler {
	
	/**
	 * 接收消息
	 * @throws 	Exception
	 */
	void handle(byte[] datas, ISender sender) throws Exception;
	/**
	 * 设置错误处理器
	 * @param 	errorHandler
	 * 			错误处理器
	 */
	void setErrorHandler(IErrorHandler errorHandler);

}
