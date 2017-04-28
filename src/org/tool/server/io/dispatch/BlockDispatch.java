package org.tool.server.io.dispatch;

import java.util.Map;
import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;
import com.google.common.collect.Queues;

public class BlockDispatch implements IDispatch, Runnable {
	
	protected static final Logger log = LoggerFactory.getLogger(BlockDispatch.class);
	
	protected BlockingQueue<byte[]> queue;
	/**消息接收器*/
	protected final IContentHandler handler;
	
	protected final Map<byte[], ISender> senders;
	
	public BlockDispatch(IContentHandler handler) {
		this.handler = handler;
		queue = Queues.newLinkedBlockingQueue();
		senders = Maps.newHashMap();
	}

	@Override
	public void addDispatch(byte[] datas, ISender sender) {
		senders.put(datas, sender);
		queue.add(datas);
	}

	@Override
	public void fireDispatch(byte[] datas, ISender sender) throws Exception {
		handler.handle(datas, sender);
	}

	@Override
	public void run() {
		while (true) {
			try {
				byte[] content = queue.take();
				fireDispatch(content, senders.get(content));
			} catch (Exception e) {
				log.error("", e);
			}
		}
	}

}
