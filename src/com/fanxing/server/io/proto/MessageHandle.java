package com.fanxing.server.io.proto;

import javax.servlet.http.HttpSession;

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
	 * @param 	session
	 * 			会话
	 * @return	响应对象
	 * @throws 	Exception
	 */
	Response handle(byte[] receiveDatas, String ip, String receiveMessageId, HttpSession session) throws Exception;

}
