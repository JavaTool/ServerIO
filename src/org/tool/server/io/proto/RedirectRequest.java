package org.tool.server.io.proto;

import org.tool.server.io.dispatch.IContent;
import org.tool.server.io.dispatch.ISender;

/**
 * 重定向请求
 * @author 	fuhuiyuan
 */
public class RedirectRequest extends Request {
	
	/**消息数据*/
	private final byte[] data;

	public RedirectRequest(String ip, int receiveMessageId, String sessionId, byte[] data, ISender sender) {
		super(ip, receiveMessageId, sessionId, sender);
		this.data = data;
	}
	
	public RedirectRequest(IContent content) {
		super(content);
		this.data = content.getDatas();
	}

	@Override
	public byte[] getByteArray() {
		return data;
	}

}
