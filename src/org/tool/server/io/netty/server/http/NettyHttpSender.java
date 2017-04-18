package org.tool.server.io.netty.server.http;

import org.tool.server.anthenticate.DefaultEncrypt;
import org.tool.server.anthenticate.IEncrypt;
import org.tool.server.io.NetType;
import org.tool.server.io.dispatch.ISender;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.rtsp.RtspHeaders.Values;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

public class NettyHttpSender implements ISender {
	
	private static final IEncrypt ENCRYPT = new DefaultEncrypt();
	
	private final String sessionId;
	
	private final boolean isKeepAlive;
	
	private final Channel channel;
	
	public NettyHttpSender(boolean isKeepAlive, String sessionId, Channel channel) {
		this.isKeepAlive = isKeepAlive;
		this.sessionId = sessionId;
		this.channel = channel;
	}

	@Override
	public void send(byte[] datas, int receiveMessageId, int messageId, long useTime) throws Exception {
		FullHttpResponse response = createResponse(ENCRYPT.encrypt(datas));
		setCookie(response);
		setContent(response, messageId, useTime);
		channel.writeAndFlush(response);
	}
	
	protected FullHttpResponse createResponse(byte[] datas) {
		return new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.wrappedBuffer(datas));
	}
	
	protected void setCookie(FullHttpResponse response) {
		HttpHeaders headers = response.headers();
		headers.add(HttpHeaders.Names.SET_COOKIE, sessionId);
	}
	
	protected void setContent(FullHttpResponse response, int messageId, long useTime) {
		response.headers().set(HttpHeaders.Names.CONTENT_TYPE, makeHead(messageId, useTime));
        response.headers().set(HttpHeaders.Names.CONTENT_LENGTH, response.content().readableBytes());
        if (isKeepAlive) {
            response.headers().set(HttpHeaders.Names.CONNECTION, Values.KEEP_ALIVE);
        }
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
	public NetType getNetType() {
		return NetType.HTTP;
	}

}
