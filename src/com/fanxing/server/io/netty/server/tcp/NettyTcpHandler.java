package com.fanxing.server.io.netty.server.tcp;

import java.io.DataOutputStream;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fanxing.server.anthenticate.IDataAnthenticate;
import com.fanxing.server.io.dispatch.Content;
import com.fanxing.server.io.dispatch.IContent;
import com.fanxing.server.io.dispatch.IContentFactory;
import com.fanxing.server.io.dispatch.IDispatchManager;
import com.fanxing.server.io.dispatch.ISender;
import com.fanxing.server.io.netty.NettyTcpSender;
import com.fanxing.server.io.netty.server.http.NettyHttpSender;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

/**
 * TCP处理器
 * @author 	fuhuiyuan
 */
public class NettyTcpHandler extends SimpleChannelInboundHandler<ByteBuf> {
	
	protected static final AttributeKey<ISender> SENDER_KEY = AttributeKey.valueOf("SENDER_KEY");
	
	protected static final AttributeKey<String> SESSSION_ID_KEY = AttributeKey.valueOf(IContentFactory.SESSION_ID);
	
	protected static final Logger log = LoggerFactory.getLogger(NettyTcpHandler.class);
	/**消息处理器*/
	protected final IDispatchManager dispatchManager;
	/**消息工厂*/
	protected final IContentFactory contentFactory;
	
	protected final int anthencateLength;
	
	public NettyTcpHandler(IDispatchManager dispatchManager, IContentFactory contentFactory) {
		this.dispatchManager = dispatchManager;
		this.contentFactory = contentFactory;
		IDataAnthenticate<byte[], DataOutputStream> dataAnthenticate = contentFactory.getDataAnthenticate();
		anthencateLength =  dataAnthenticate == null ? 0 : dataAnthenticate.getAnthenticateLength();
	}

	@Override
	public void channelActive(final ChannelHandlerContext ctx) throws Exception {
//		log.info("[Coming Active]IP:{}", ctx.channel().remoteAddress());
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		Channel channel = ctx.channel();
		String address = channel.remoteAddress().toString();
		String sessionId = channel.attr(SESSSION_ID_KEY).get();
		log.info("[Coming Out]IP:{}", address);
		channel.close();
		ctx.close();
		if (sessionId != null) {
			dispatchManager.disconnect(new Content(sessionId, 0, "", null, new NettyHttpSender(true, sessionId, channel), 0));
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		log.info("[Coming Out Error]IP:{} ; ERROR:{}", ctx.channel().remoteAddress(), cause.getMessage());
		ctx.channel().close();
		ctx.close();
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
		if (check(msg)) {
			Channel channel = ctx.channel();
			Attribute<ISender> attribute = channel.attr(SENDER_KEY);
			ISender sender = attribute.get();
			if (sender == null) {
				sender = new NettyTcpSender(channel, contentFactory.getDataAnthenticate());
				attribute.set(sender);
				Attribute<String> session = channel.attr(SESSSION_ID_KEY);
				session.set(UUID.randomUUID().toString());
			}
	
		    byte[] data = new byte[msg.readableBytes()];
		    msg.readBytes(data);
		    String ip = channel.remoteAddress().toString();
			IContent content = contentFactory.createContent(ip, data, sender);
			log.info("Netty tcp content : [MessageId : {}] [Cookie : {}] [Host : {}]", content.getMessageId(), content.getSessionId(), content.getIp());
			if (content != null) {
				dispatchManager.addDispatch(content);
			}
		}
	}
	
	private boolean check(ByteBuf msg) {
		if (anthencateLength > 0) {
			byte[] data = new byte[anthencateLength];
			msg.readBytes(data);
			return contentFactory.getDataAnthenticate().read(data);
		} else {
			return true;
		}
	}
	
}
