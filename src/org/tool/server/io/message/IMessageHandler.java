package org.tool.server.io.message;

import org.tool.server.io.dispatch.ISender;
import org.tool.server.io.proto.IErrorHandler;

/**
 * 消息接收器
 * @author 	fuhuiyuan
 */
public interface IMessageHandler {
	
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
