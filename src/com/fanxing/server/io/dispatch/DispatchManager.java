package com.fanxing.server.io.dispatch;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class DispatchManager implements IDispatchManager {
	
	protected final Map<String, IDispatch> dispatchs;
	
	protected final IContentHandler handler;
	
	protected final ScheduledExecutorService executorService;
	
	protected final int sleepTime;
	
	public DispatchManager(IContentHandler handler, int sleepTime, int corePoolSize) {
		this.handler = handler;
		this.sleepTime = sleepTime;
		Dispatch.setSLEEP_TIME(sleepTime);
		dispatchs = new ConcurrentHashMap<String, IDispatch>();
		executorService = new ScheduledThreadPoolExecutor(corePoolSize);
	}

	@Override
	public void addDispatch(IContent content) {
		IDispatch dispatch = fetch(content);
		dispatch.addDispatch(content);
	}

	@Override
	public void fireDispatch(IContent content) {
		IDispatch dispatch = fetch(content);
		dispatch.fireDispatch(content);
	}
	
	protected synchronized IDispatch fetch(IContent content) {
		String key = content.getSessionId();
		IDispatch dispatch = dispatchs.get(key);
		if (dispatch == null) {
			Dispatch dis = new Dispatch(handler);
			executorService.scheduleWithFixedDelay(dis, sleepTime, sleepTime, TimeUnit.MILLISECONDS);
			dispatchs.put(key, dis);
			return dis;
		} else {
			return dispatch;
		}
	}

	@Override
	public void disconnect(IContent content) {
		String key = content.getSessionId();
		dispatchs.remove(key);
	}

}
