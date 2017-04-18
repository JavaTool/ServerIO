package org.tool.server.io.http;

import org.tool.server.io.ConnectBackInfo;

/**
 * HTTP反馈信息
 * @author	fuhuiyuan
 */
public class HttpBackInfo extends ConnectBackInfo {
	
	/**服务器状态*/
	private final int status;
	
	public HttpBackInfo(byte[] data, int status, int messageId) {
		super(data, messageId);
		this.status = status;
	}

	/**
	 * 获取服务器状态
	 * @return	服务器状态
	 */
	public int getStatus() {
		return status;
	}

}
