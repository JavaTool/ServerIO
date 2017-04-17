package com.fanxing.server.debug;

import java.util.List;
import java.util.Map;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class TimeOutputer {
	
	private final Map<Integer, Long> times;
	
	private final BiMap<String, Integer> keyMap;
	
	private final List<String> keys;
	
	private String title;
	
	public TimeOutputer() {
		times = Maps.newTreeMap();
		keyMap = HashBiMap.create();
		keys = Lists.newLinkedList();
		setTitle("");
	}
	
	public void timing(String key) {
		int index = times.size();
		times.put(index, System.currentTimeMillis());
		keyMap.put(key, index);
		keys.add(key);
	}
	
	public void timing() {
		timing(times.size() + "");
	}
	
	public void output(String key1, String key2) {
		System.out.println(title + " " + key1 + " to " + key2 + " use time " + (times.get(keyMap.get(key2)) - times.get(keyMap.get(key1))) + " ms.");
	}
	
	public void outputLast() {
		int size = keys.size();
		if (size > 1) {
			output(keys.get(size - 2), keys.get(size - 1));
		} else {
			System.out.println(title + " do not use time.");
		}
	}
	
	public void outputAll() {
		int size = keys.size();
		if (size > 1) {
			for (int i = 1;i < size;i++) {
				output(keys.get(i - 1), keys.get(i));
			}
		} else {
			System.out.println(title + " do not use times.");
		}
	}
	
	public void clear() {
		times.clear();
		keyMap.clear();
		keys.clear();
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public static void main(String[] args) {
		TimeOutputer timeOutputer = new TimeOutputer();
		timeOutputer.setTitle("Test");
		// 0
		timeOutputer.outputLast();
		timeOutputer.outputAll();
		// 1
		timeOutputer.timing("A");
		timeOutputer.outputLast();
		timeOutputer.outputAll();
		// 2...
		for (int i = 0;i < 10;i++) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
				System.exit(1);
			}
			timeOutputer.timing(i + "");
		}
		timeOutputer.outputLast();
		timeOutputer.outputAll();
		// again
		timeOutputer.clear();
		timeOutputer.setTitle("Again");
		// 0
		timeOutputer.outputLast();
		timeOutputer.outputAll();
		// 1
		timeOutputer.timing("A");
		timeOutputer.outputLast();
		timeOutputer.outputAll();
		// 2...
		for (int i = 0;i < 10;i++) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
				System.exit(1);
			}
			timeOutputer.timing();
		}
		timeOutputer.outputLast();
		timeOutputer.outputAll();
	}

}
