package com.fanxing.server.io.dispatch;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fanxing.server.anthenticate.DefaultEncrypt;
import com.fanxing.server.anthenticate.IDataAnthenticate;
import com.fanxing.server.anthenticate.IEncrypt;

public class SimpleContentFactory implements IContentFactory {
	
	protected static final Logger log = LoggerFactory.getLogger(SimpleContentFactory.class);
	
	protected final IDataAnthenticate<byte[], DataOutputStream> dataAnthenticate;
	
	protected final IEncrypt encrypt;
	
	public SimpleContentFactory(IDataAnthenticate<byte[], DataOutputStream> dataAnthenticate) {
		this.dataAnthenticate = dataAnthenticate;
		encrypt = new DefaultEncrypt();
	}

	@Override
	public IContent createContent(String ip, byte[] datas, String sessionId, int messageId, ISender sender, int serial) {
		return new Content(sessionId, messageId, ip, encrypt.deEncrypt(datas), sender, serial);
	}

	@Override
	public IContent createContent(String ip, byte[] data, ISender sender) {
		DataInputStream dis = new DataInputStream(new ByteArrayInputStream(data));
		try {
			return createContent(ip, dis, sender);
		} catch (IOException e) {
			log.error("", e);
			return null;
		}
	}
	
	protected IContent createContent(String ip, DataInputStream dis, ISender sender) throws IOException {
		int serial = dis.readInt(); // 客户端的协议序列号，如果是需要返回消息的协议，则该值原样返回
		int messageId = dis.readShort();
		byte[] datas = new byte[dis.available()];
		dis.read(datas);
		String sessionId = sender.getAttribute(SESSION_ID, String.class);
		sessionId = sessionId == null ? "" : sessionId;
		return createContent(ip, datas, sessionId, messageId, sender, serial);
	}

	@Override
	public IDataAnthenticate<byte[], DataOutputStream> getDataAnthenticate() {
		return dataAnthenticate;
	}

}
