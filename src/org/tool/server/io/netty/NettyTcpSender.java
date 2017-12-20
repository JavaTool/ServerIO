package org.tool.server.io.netty;

import static io.netty.buffer.Unpooled.buffer;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import org.tool.server.anthenticate.IEncrypt;
import org.tool.server.io.NetType;
import org.tool.server.io.dispatch.ISender;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

public final class NettyTcpSender implements ISender {
	
	private final IEncrypt encrypt;
	
	private final Channel channel;
	
	public NettyTcpSender(Channel channel, IEncrypt encrypt) {
		this.channel = channel;
		this.encrypt = encrypt;
	}

	@Override
	public <X, Y extends X> void setAttribute(String key, Class<X> clz, Y value) {
		AttributeKey<X> attributeKey = AttributeKey.valueOf(key);
		channel.attr(attributeKey).set(value);
	}

	@Override
	public <X> X getAttribute(String key, Class<X> clz) {
		AttributeKey<X> attributeKey = AttributeKey.valueOf(key);
		Attribute<X> attribute = channel.attr(attributeKey);
		return attribute == null ? null : attribute.get();
	}

	@Override
	public void send(byte[] datas, int serial, int messageId) throws Exception {
		channel.writeAndFlush(createByteBuf(packageDatas(datas, serial, messageId)));
	}
	
	private byte[] packageDatas(byte[] datas, int serial, int messageId) throws Exception {
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bout);
		dos.writeInt(messageId);
		dos.writeInt(serial); // 客户端的协议序列号，如果是需要返回消息的协议，则该值原样返回
		dos.write(datas);
		return encrypt.encrypt(bout.toByteArray());
	}
	
	private ByteBuf createByteBuf(byte[] bytes) {
		int length = bytes.length;
		ByteBuf result = buffer(length);
		result.writeInt(length);
		result.writeBytes(bytes);
		return result;
	}

	@Override
	public NetType getNetType() {
		return NetType.TCP;
	}

	@Override
	public String getIp() {
		return channel.remoteAddress().toString();
	}

	@Override
	public String getSessionId() {
		return getAttribute(SESSION_ID, String.class);
	}

}
