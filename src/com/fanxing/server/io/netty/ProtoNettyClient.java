package com.fanxing.server.io.netty;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import com.fanxing.server.io.dispatch.IContentHandler;

public class ProtoNettyClient extends NettyClient {
	
	public ProtoNettyClient(IContentHandler contentHandler, int port, String host, NettyClientContentFactory contentFactory) throws Exception {
		super(contentHandler, contentFactory, port, host);
	}
	
	public void connect(String messageId, String sessionId, byte[] data) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		dos.writeUTF(messageId);
//		dos.writeUTF(sessionId);
		dos.writeInt(data.length);
		dos.write(data);
		connect(baos.toByteArray());
	}

}
