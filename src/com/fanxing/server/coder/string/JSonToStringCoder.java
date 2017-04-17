package com.fanxing.server.coder.string;

import static com.alibaba.fastjson.JSONObject.parseObject;
import static com.alibaba.fastjson.JSONObject.toJSONString;

import java.util.Map;

import com.google.common.collect.Maps;

public class JSonToStringCoder<T> implements IStringCoder<T> {
	
	private static final char SPLIT = ':';
	
	@SuppressWarnings("rawtypes")
	private final Map<String, Class> classes = Maps.newHashMap();

	@Override
	public String code(T s) {
		String name = s.getClass().getName();
		if (!classes.containsKey(name)) {
			classes.put(name, s.getClass());
		}
		return new StringBuilder(name).append(SPLIT).append(toJSONString(s)).toString();
	}

	@SuppressWarnings("unchecked")
	@Override
	public T decode(String t) {
		if (t == null || t.length() == 0) {
			return null;
		} else {
			int index = t.indexOf(SPLIT);
			String name = t.substring(0, index);
			return (T) parseObject(t.substring(index + 1), classes.containsKey(name) ? classes.get(name) : forName(name));
		}
	}
	
	@SuppressWarnings("unchecked")
	private Class<T> forName(String name) {
		try {
			Class<T> clz = (Class<T>) Class.forName(name);
			classes.put(name, clz);
			return clz;
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

}
