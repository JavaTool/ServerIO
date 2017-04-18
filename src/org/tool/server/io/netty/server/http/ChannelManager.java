package org.tool.server.io.netty.server.http;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tool.server.io.netty.server.http.NettyHttpHandler.NettyHttpSession;
import org.tool.server.utils.DateUtil;

import io.netty.channel.Channel;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

class ChannelManager implements Runnable {
	
	private static final Logger log = LoggerFactory.getLogger(NettyHttpHandler.class);
	
	private static final AttributeKey<NettyHttpSession> SESSION = AttributeKey.valueOf("NettyHttpSession");
	
	private static final int TIME_OUT = 5 * DateUtil.MINTUE_SECONDS * DateUtil.SECOND_MILLIS;
	
	private static final AttributeKey<Date> CHANNEL_DATE = AttributeKey.valueOf("ChannelDate");
	
	private final List<Channel> channels;
	
	public ChannelManager() {
		channels = new Vector<>();
		log.info("Create ChannelManager.");
		new Thread(this, "ChannelManager").start();
	}
	
	public void addChannel(Channel channel) {
		channels.add(channel);
	}

	@Override
	public void run() {
		while (true) {
			long time = System.currentTimeMillis();
			for (int i = channels.size() - 1;i >= 0;i--) {
				Channel channel = channels.get(i);
				Attribute<Date> channelDate = channel.attr(CHANNEL_DATE);
				if (channelDate.get() == null || time - channelDate.get().getTime() >= (TIME_OUT << 1)) {
					channels.remove(i);
					Attribute<NettyHttpSession> session = channel.attr(SESSION);
					NettyHttpSession httpSession = session.get();
					channel.close();
					log.info("Close channel {}/{}", channel.remoteAddress(), httpSession == null ? "null" : httpSession.id);
				}
			}
			
			try {
				Thread.sleep(TIME_OUT - (System.currentTimeMillis() - time));
			} catch (InterruptedException e) {
				log.error("", e);
			}
		}
	}

}
