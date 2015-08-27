package com.fanxing.server.io.netty;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import com.fanxing.server.io.proto.MessageHandle;

public class ProtoNettyClient extends NettyClient {
	
	public ProtoNettyClient(final MessageHandle messageHandle, int port, String host) throws Exception {
		super(new NettyClientCallback() {
			
			@Override
			public void callback(byte[] data) throws Exception {
				DataInputStream dis = new DataInputStream(new ByteArrayInputStream(data));
				String messageId = dis.readUTF();
				int messageLength = dis.readInt();
				byte[] value = new byte[messageLength];
				dis.read(value);
				messageHandle.handle(data, null, messageId, null, null);
			}
			
		}, port, host);
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
