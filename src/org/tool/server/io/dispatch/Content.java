package org.tool.server.io.dispatch;

/**
 * 默认的消息内容
 * @author 	fuhuiyuan
 */
public class Content implements IContent {
	
	/**会话id*/
	private final String sessionId;
	/**消息id*/
	private final int messageId;
	/**地址*/
	private final String ip;
	/**数据*/
	private final byte[] datas;
	/**发送器*/
	private final ISender sender;
	
	private final int serial;
	
	public Content(String sessionId, int messageId, String ip, byte[] datas, ISender sender, int serial) {
		this.sessionId = sessionId;
		this.messageId = messageId;
		this.ip = ip;
		this.datas = datas;
		this.sender = sender;
		this.serial = serial;
	}

	@Override
	public String getSessionId() {
		return sessionId;
	}

	@Override
	public int getMessageId() {
		return messageId;
	}

	@Override
	public byte[] getDatas() {
		return datas;
	}

	@Override
	public ISender getSender() {
		return sender;
	}

	@Override
	public String getIp() {
		return ip;
	}

	@Override
	public int getSerial() {
		return serial;
	}

}
