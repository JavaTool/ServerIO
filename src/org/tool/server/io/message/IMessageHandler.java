package org.tool.server.io.message;

import org.tool.server.io.dispatch.ISender;

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

}
