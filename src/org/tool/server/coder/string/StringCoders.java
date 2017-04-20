package org.tool.server.coder.string;

import java.util.Date;
import java.util.Map;

import com.google.common.collect.Maps;

public final class StringCoders {
	
	@SuppressWarnings("rawtypes")
	private static final Map<Class, IStringCoder> map = Maps.newConcurrentMap();
	
	private StringCoders() {}
	
	@SuppressWarnings("unchecked")
	public static IStringCoder<Date> createDateStringCoder() {
		return map.getOrDefault(Date.class, new DateToStringCoder());
	}
	
	@SuppressWarnings("unchecked")
	public static IStringCoder<Integer> createIntStringCoder() {
		return map.getOrDefault(Integer.class, new IntToStringCoder());
	}
	
	@SuppressWarnings("unchecked")
	public static IStringCoder<String> createStringStringCoder() {
		return map.getOrDefault(Date.class, new StringToStringCoder());
	}

}