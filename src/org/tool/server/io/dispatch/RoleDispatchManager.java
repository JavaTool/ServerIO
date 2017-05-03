package org.tool.server.io.dispatch;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tool.server.io.dispatch.BlockDispatch;
import org.tool.server.io.dispatch.IDispatch;
import org.tool.server.io.dispatch.IDispatchManager;
import org.tool.server.io.dispatch.ISender;
import org.tool.server.io.message.IMessageHandler;
import org.tool.server.sequence.IInstanceIdMaker;
import org.tool.server.sequence.impl.JavaAutoIdMaker;
import org.tool.server.system.SystemUtil;

import com.google.common.collect.Maps;

public class RoleDispatchManager implements IDispatchManager, SenderThreadPool {
	
	private static final Logger log = LoggerFactory.getLogger(RoleDispatchManager.class);
	
	private final IMessageHandler handler;
	
	private final Map<Integer, RoleDispatch> dispatchs;
	
	private final Map<String, Integer> roleThreads;
	
	private final Map<Integer, String> aloneTags;
	
	private final Map<String, BlockDispatch> tagDispatchs;
	
	private final Map<Integer, Integer> fires;
	
	@SuppressWarnings("unused")
	private final IDispatch[] singleDispatches;
	
	private final ExecutorService executorService;
	
	@SuppressWarnings("unused")
	private final IInstanceIdMaker idMaker;

	public RoleDispatchManager(IMessageHandler handler, int corePoolSize) {
		this.handler = handler;
		
		executorService = Executors.newFixedThreadPool(corePoolSize);
		dispatchs = Maps.newConcurrentMap();
		roleThreads = Maps.newConcurrentMap();
		aloneTags = Maps.newConcurrentMap();
		tagDispatchs = Maps.newConcurrentMap();
		fires = Maps.newConcurrentMap();
		singleDispatches = new IDispatch[]{createSingleDispatch(), createSingleDispatch()};
		idMaker = new JavaAutoIdMaker();

		log.info("CPU amount is {}.", SystemUtil.CPU_AMOUNT);
		for (int i = 0;i < SystemUtil.CPU_AMOUNT;i++) {
			RoleDispatch dispatch = new RoleDispatch(handler, i);
			addThread(dispatch, i);
			dispatchs.put(i, dispatch);
		}
	}
	
	private BlockDispatch createSingleDispatch() {
		BlockDispatch singleDispatch = new BlockDispatch(handler);
		executorService.execute(singleDispatch);
		return singleDispatch;
	}
	
	private void addThread(Runnable runnable, int index) {
		new Thread(runnable, "Thread_" + index).start();
	}

	private synchronized IDispatch fetch(ISender sender) {
		String session = sender.getSessionId();
		session = createRequestKey(session);
//		int messageId = content.getMessageId();
//		if (aloneTags.containsKey(messageId)) {
//			return tagDispatchs.get(aloneTags.get(messageId));
//		} else if (!roleThreads.containsKey(session)) {
//			return singleDispatches[idMaker.nextInstanceId() % 2];
//		} else {
//			return dispatchs.get(roleThreads.get(session));
//		}
		return dispatchs.get(roleThreads.get(session));
	}

	@Override
	public void addDispatch(byte[] datas, ISender sender) {
//		if (fires.containsKey(content.getMessageId())) {
//			try {
//				fireDispatch(datas, sender);
//			} catch (Exception e) {
//				log.error("", e);
//			}
//		} else {
			fetch(sender).addDispatch(datas, sender);
//		}
	}

	@Override
	public void fireDispatch(byte[] datas, ISender sender) throws Exception {
		fetch(sender).fireDispatch(datas, sender);
	}

	@Override
	public String allocation(ISender sender) {
		String session = sender.getSessionId();
		session = createRequestKey(session);
		int min = -1;
		if (roleThreads.containsKey(session)) {
			dispatchs.get(roleThreads.get(session)).changeCount(false);
		}
		int key = 0;
		for (Integer threadId : dispatchs.keySet()) {
			int count = dispatchs.get(threadId).getCount();
			if (min > count || min == -1) {
				min = count;
				key = threadId;
			}
		}
		log.info("select threadId : {}/{}", key, session);
		RoleDispatch dispatch = dispatchs.get(key);
		dispatch.changeCount(true);
		roleThreads.put(session, key);
		return session;
	}

	@Override
	public void disconnect(String session) {
		session = createRequestKey(session);
		if (roleThreads.containsKey(session)) {
			RoleDispatch dispatch = dispatchs.get(roleThreads.get(session));
			dispatch.changeCount(false);
			log.info("Dispatch {} remove {} count is {}", dispatch.getKey(), session, dispatch.getCount());
		}
		roleThreads.remove(session);
	}

	@Override
	public void addAlone(String tag, int... messageIds) {
		StringBuilder builder = new StringBuilder();
		for (int messageId : messageIds) {
			aloneTags.put(messageId, tag);
			builder.append(messageId).append(", ");
		}
		builder.setLength(builder.length() - 1);
		log.info("Add alone messageIds {} to tag {}.", builder.toString(), tag);
		if (!tagDispatchs.containsKey(tag)) {
			BlockDispatch tagDispatch = new BlockDispatch(handler);
			new Thread(tagDispatch, "Thread_" + tag).start();
			tagDispatchs.put(tag, tagDispatch);
			log.info("Add new tag {}.", tag);
		}
	}
	
	@Override
	public void addFire(List<Integer> messageIds) {
		messageIds.forEach(id -> fires.put(id, id));
	}

	@Override
	public ISender getSender(String session) {
		return null;
	}

	@Override
	public Collection<ISender> getAllSender() {
		return null;
	}
	
	public static String createRequestKey(ISender sender) {
		return createRequestKey(sender.getSessionId());
	}
	
	public static String createRequestKey(String session) {
		String sessionId = "";
		for (String info : session.split(";")) {
			if (info.startsWith("jsessionid")) {
				sessionId = info.split("=")[1];
			}
		}
		return sessionId.length() == 0 ? session : sessionId;
	}

	@Override
	public void disconnect(ISender sender) {
		String session = createRequestKey(sender.getSessionId());
		disconnect(session);
	}

}
