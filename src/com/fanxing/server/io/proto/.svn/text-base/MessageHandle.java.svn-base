package com.fanxing.server.io.proto;

import com.fanxing.server.io.dispatch.ISender;

/**
 * 消息接收器
 * @author 	fuhuiyuan
 */
public interface MessageHandle {
	
	/**
	 * 接收消息
	 * @param 	receiveDatas
	 * 			接收的数据
	 * @param 	ip
	 * 			客户端地址
	 * @param 	receiveMessageId
	 * 			接收的消息id
	 * @param 	sessionId
	 * 			会话id
	 * @param 	sender
	 * 			数据发送器
	 * @throws 	Exception
	 */
	void handle(byte[] receiveDatas, String ip, String receiveMessageId, String sessionId, ISender sender) throws Exception;

}
