package org.tool.server.io.netty;

import static io.netty.buffer.Unpooled.buffer;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import org.tool.server.anthenticate.DefaultEncrypt;
import org.tool.server.anthenticate.IDataAnthenticate;
import org.tool.server.anthenticate.IEncrypt;
import org.tool.server.io.NetType;
import org.tool.server.io.dispatch.ISender;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

public class NettyTcpSender implements ISender {
	
	protected static final IEncrypt encrypt = new DefaultEncrypt();
	
	protected final Channel channel;
	
	protected final IDataAnthenticate<byte[], DataOutputStream> dataAnthenticate;
	
	public NettyTcpSender(Channel channel, IDataAnthenticate<byte[], DataOutputStream> dataAnthenticate) {
		this.channel = channel;
		this.dataAnthenticate = dataAnthenticate;
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
	public void send(byte[] datas, int serial, int messageId, long useTime) throws Exception {
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bout);
		if (dataAnthenticate != null) {
			dataAnthenticate.write(dos);
		}
		dos.writeInt(serial); // 客户端的协议序列号，如果是需要返回消息的协议，则该值原样返回
		dos.writeShort(messageId);
		dos.write(encrypt.encrypt(datas));
		byte[] bytes = bout.toByteArray();
		int length = bytes.length;
		ByteBuf result = buffer(length);
		result.writeShort(length);
		result.writeBytes(bytes);
		channel.writeAndFlush(result);
	}

	@Override
	public NetType getNetType() {
		return NetType.TCP;
	}

}
