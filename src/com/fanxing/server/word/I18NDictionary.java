package com.fanxing.server.word;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

public abstract class I18NDictionary {
	
	private static final Logger log = LoggerFactory.getLogger(I18NDictionary.class);
	
	private ImmutableMap<String, String> dictionary;
	
	protected final String path;
	
	public I18NDictionary(String path) {
		this.path = path;
	}
	
	public void load() {
		Map<String, String> dictionary = Maps.newHashMap();
		try {
			List<String> list = read();//FileUtil.readAllLine(new File(path));
			for (String line : list) {
				if (line.trim().startsWith("//")) {
					continue;
				} else if (line.trim().length() == 0) {
					continue;
				} else {
					String[] infos = line.split("\\|");
					if (infos.length == 2) {
						dictionary.put(infos[0], infos[1]);
					}
				}
			}
		} catch (Exception e) {
			log.error("", e);
		}
		this.dictionary = ImmutableMap.copyOf(dictionary);
	}
	
	protected abstract List<String> read() throws Exception;
	
	public String get(String key) {
		return dictionary.get(key);
	}

}
