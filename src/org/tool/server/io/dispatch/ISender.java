package org.tool.server.io.dispatch;

import static java.text.MessageFormat.format;

import org.tool.server.io.NetType;

/**
 * 数据发送器
 * @author 	fuhuiyuan
 */
public interface ISender {
	
	String FORMAT = "text/plain; charset=UTF-8; messageid={0}";
	/**
	 * session id name.
	 */
	String SESSION_ID = "SESSION_ID";
	
	void send(byte[] datas, int serial, int messageId) throws Exception;
	
	<X, Y extends X> void setAttribute(String key, Class<X> clz, Y value);
	
	<X> X getAttribute(String key, Class<X> clz);
	
	default String makeHead(int messageId) {
		return format(FORMAT, messageId);
	}
	
	NetType getNetType();
	
	String getIp();
	
	String getSessionId();

}
