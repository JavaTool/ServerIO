package com.fanxing.server.pubsub.impl;

import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fanxing.server.pubsub.IPubsub;
import com.fanxing.server.system.SystemUtil;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;

public class EventBusPubsub implements IPubsub<Object> {
	
	public static final String CHANNEL_ASYNC = "async";
	
	public static final String CHANNEL_ASYNC_OLNY = "async_olny";
	
	private static final Logger log = LoggerFactory.getLogger(EventBusPubsub.class);
	
	private final EventBus eventBus;
	
	private final EventBus asyncEventBus;
	
	public EventBusPubsub() {
		eventBus = new EventBus();
		asyncEventBus = new AsyncEventBus("EventBusPubsub", Executors.newFixedThreadPool(SystemUtil.CPU_AMOUNT));
	}

	@Override
	public void publish(String channel, Object message) {
		try {
			if (CHANNEL_ASYNC.equals(channel) || CHANNEL_ASYNC_OLNY.equals(channel)) {
				asyncEventBus.post(message);
			}
			if (!CHANNEL_ASYNC_OLNY.equals(channel)) {
				eventBus.post(message);
			}
		} catch (Exception e) {
			log.error("", e);
		}
	}

	@Override
	public void subscribe(Object subscribe, String... channel) {
		if(channel == null || channel.length == 0 || !CHANNEL_ASYNC_OLNY.equals(channel[0])){
			eventBus.register(subscribe);
		}
		asyncEventBus.register(subscribe);
	}

}
