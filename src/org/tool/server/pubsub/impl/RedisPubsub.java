package org.tool.server.pubsub.impl;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tool.server.cache.redis.IJedisReources;
import org.tool.server.coder.stream.IStreamCoder;
import org.tool.server.coder.stream.StreamCoders;
import org.tool.server.pubsub.IPubsub;
import org.tool.server.pubsub.ISubscribe;

import redis.clients.jedis.BinaryJedisPubSub;

public class RedisPubsub implements IPubsub<ISubscribe> {
	
	private static final Logger log = LoggerFactory.getLogger(RedisPubsub.class);
	
	private static final IStreamCoder DEFAULT_CODER = StreamCoders.newProtoStuffCoder();
	
	private static final int THREAD_COUNT = 5;
	
	private final IJedisReources cache;
	
	private final ExecutorService executorService;
	
	private final IStreamCoder coder;
	
	public RedisPubsub(IJedisReources cache, IStreamCoder coder) {
		this.cache = cache;
		this.coder = coder;
		executorService = Executors.newFixedThreadPool(THREAD_COUNT);
	}
	
	public RedisPubsub(IJedisReources cache) {
		this(cache, DEFAULT_CODER);
	}

	@Override
	public void publish(String channel, Object message) {
		checkNotNull(channel, "null channel name.");
		checkNotNull(message, "null message");
		checkArgument(channel.length() > 0, "zero length channel name.");
		
		cache.exec((jedis) -> jedis.publish(coder.write(channel), coder.write(message)));
	}

	@Override
	public void subscribe(ISubscribe subscribe, String... channel) {
		try {
			byte[][] channels = new byte[channel.length][];
			for (int i = 0;i < channel.length;i++) {
				channels[i] = coder.write(channel[i]);
			}
			
			executorService.execute(new SubscribeThread(subscribe, channels));
			log.info("subscribe : " + subscribe);
		} catch (Exception e) {
			log.error("", e);
		}
	}
	
	protected class SubscribeThread extends BinaryJedisPubSub implements Runnable {
		
		private final ISubscribe subscribe;
		
		private final byte[][] channels;
		
		public SubscribeThread(ISubscribe subscribe, byte[][] channels) {
			this.subscribe = subscribe;
			this.channels = channels;
		}

		@Override
		public void run() {
			cache.exec((jedis) -> jedis.subscribe(this, channels));
		}

		@Override
		public void onMessage(byte[] channel, byte[] message) {
			try {
				String channelName = coder.read(channel);
				Object object = coder.read(message);
				log.info("onMessage : {}, {}", channelName, object);
				subscribe.onMessage(channelName, object);
			} catch (Exception e) {
				log.error("", e);
			}
		}
		
	}

	@Override
	public void unsubscribe(ISubscribe subscribe, String... channel) {
		throw new UnsupportedOperationException();
	}

}
