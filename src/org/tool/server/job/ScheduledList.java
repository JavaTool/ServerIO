package org.tool.server.job;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

public class ScheduledList<T extends ScheduledObject> implements Runnable {
	
	private static final Logger log = LoggerFactory.getLogger(ScheduledList.class);
	
	private final List<T> list;
	
	private final ScheduledListRemoveListener<T> removeListener;
	
	private long expire;
	
	public ScheduledList(ScheduledListRemoveListener<T> removeListener) {
		List<T> list = Lists.newLinkedList();
		this.list = Collections.synchronizedList(list);
		this.removeListener = removeListener;
	}

	@Override
	public void run() {
		long time = System.currentTimeMillis();
		try {
			while (list.size() > 0) {
				if (time - list.get(0).getStartTime() < expire) {
					break;
				} else {
					removeListener.onRemove(list.remove(0));
				}
			}
		} catch (Exception e) {
			log.error("", e);
		}
	}

	public void setExpire(long expire) {
		this.expire = expire;
	}
	
	public void add(T element) {
		list.add(element);
	}
	
	public static void main(String[] args) {
		final ScheduledList<Element> list = new ScheduledList<>(new ScheduledListRemoveListener<Element>() {

			@Override
			public void onRemove(Element element) {
				System.out.println(element.getIndex());
			}
			
		});
		list.setExpire(10000);
		Executors.newScheduledThreadPool(1).scheduleAtFixedRate(list, 0, 1, TimeUnit.SECONDS);
		Executors.newScheduledThreadPool(1).scheduleAtFixedRate(new Runnable() {
			
			@Override
			public void run() {
				list.add(new Element());
			}
			
		}, 0, 100, TimeUnit.MILLISECONDS);
	}
	
	private static class Element implements ScheduledObject {
		
		private static int INDEX = 0;
		
		private final long start = System.currentTimeMillis();
		
		private final int index;
		
		public Element() {
			this.index = INDEX++;
		}

		@Override
		public long getStartTime() {
			return start;
		}

		public int getIndex() {
			return index;
		}
		
	}

}
