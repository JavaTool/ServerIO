package org.tool.server.io.dispatch;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import com.google.common.collect.Maps;
import com.google.common.util.concurrent.Service;

/**
 * 默认的分配管理器
 * @author 	fuhuiyuan
 */
@Deprecated
public class DispatchManager implements IDispatchManager {
	
	/**分配器集合*/
	protected final Map<String, IDispatch> dispatchs;
	/**消息接收器*/
	protected final IContentHandler handler;
	/**任务执行服务*/
	protected final ScheduledExecutorService executorService;
	
	protected final List<Service> serviceList;
	
	private final Map<Integer, String> aloneTags;
	
	private final Map<String, BlockDispatch> tagDispatchs;
	
	public DispatchManager(IContentHandler handler, int sleepTime, int corePoolSize, List<Service> serviceList) {
		this.handler = handler;
		this.serviceList = serviceList;
		Dispatch.setSLEEP_TIME(sleepTime);
		dispatchs = new ConcurrentHashMap<String, IDispatch>();
		executorService = new ScheduledThreadPoolExecutor(corePoolSize);
		aloneTags = Maps.newConcurrentMap();
		tagDispatchs = Maps.newConcurrentMap();
	}

	@Override
	public void addDispatch(byte[] datas, ISender sender) {
		IDispatch dispatch = fetch(datas, sender);
		dispatch.addDispatch(datas, sender);
	}

	@Override
	public void fireDispatch(byte[] datas, ISender sender) throws Exception {
		IDispatch dispatch = fetch(datas, sender);
		dispatch.fireDispatch(datas, sender);
	}
	
	protected synchronized IDispatch fetch(byte[] datas, ISender sender) {
		String key = sender.getSessionId();
		IDispatch dispatch = dispatchs.get(key);
		if (dispatch == null) {
			Dispatch dis = new Dispatch(handler);
			serviceList.add(dis);
			dispatchs.put(key, dis);
			dis.startAsync();
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

	@Override
	public void addAlone(String tag, int... messageIds) {
		for (int messageId : messageIds) {
			aloneTags.put(messageId, tag);
		}
		if (!tagDispatchs.containsKey(tag)) {
			BlockDispatch tagDispatch = new BlockDispatch(handler);
			new Thread(tagDispatch, "Thread_" + tag).start();
			tagDispatchs.put(tag, tagDispatch);
		}
	}

	@Override
	public void addFire(List<Integer> messageIds) {
		// TODO Auto-generated method stub
		
	}

}
