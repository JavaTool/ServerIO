package org.tool.server.io.dispatch;

import static java.text.MessageFormat.format;

import org.tool.server.io.NetType;

/**
 * 数据发送器
 * @author 	fuhuiyuan
 */
public interface ISender {
	
	String FORMAT = "text/plain; charset=UTF-8; time={0}; messageid=";
	/**
	 * session id name.
	 */
	String SESSION_ID = "SESSION_ID";
	
	void send(byte[] datas, int serial, int messageId, long useTime) throws Exception;
	
	<X, Y extends X> void setAttribute(String key, Class<X> clz, Y value);
	
	<X> X getAttribute(String key, Class<X> clz);
	
	default String makeHead(int messageId, long useTime) {
		return format(FORMAT, useTime) + messageId;
	}
	
	NetType getNetType();
	
	String getIp();
	
	String getSessionId();

}
