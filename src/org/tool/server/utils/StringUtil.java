package org.tool.server.utils;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public final class StringUtil {
	
	private StringUtil() {}
	
	public static String firstUpper(String str) {
		return str.substring(0, 1).toUpperCase() + (str.length() > 1 ? str.substring(1) : "");
	}
	
	public static String firstLower(String str) {
		return str.substring(0, 1).toLowerCase() + (str.length() > 1 ? str.substring(1) : "");
	}
	
	public static int[] toInts(String[] strs) {
		int[] array = new int[strs.length];
		for (int i = 0;i < strs.length;i++) {
			array[i] = Integer.parseInt(strs[i]);
		}
		return array;
	}
	
	public static Map<Integer, Integer> toMap(String[] strs) {
		Map<Integer, Integer> map = Maps.newHashMap();
		for (String str : strs) {
			int key = Integer.parseInt(str);
			map.put(key, key);
		}
		return map;
	}
	
	public static String intsToString(int[] array) {
		StringBuilder builder = new StringBuilder();
		for (int value : array) {
			builder.append(value).append(",");
		}
		builder.setLength(builder.length() - 1);
		return builder.toString();
	}
	
	public static String intsToString(Collection<Integer> list){
		StringBuilder builder = new StringBuilder();
		if(list.size() > 0){
			for (int value : list) {
				builder.append(value).append(",");
			}
			builder.setLength(builder.length() - 1);
		}
		return builder.toString();
	}
	
	public static String intMapToString(Map<Integer, Integer> map, String regex){
		StringBuilder builder = new StringBuilder();
		if(map.size() > 0){
			map.forEach((key,value)->{
				builder.append(key).append(",").append(value).append(regex);
			});
			builder.setLength(builder.length() - 1);
		}
		return builder.toString();
	}
	
	public static Map<Integer, Integer> stringArrayToMap(String[] strs){
		Map<Integer, Integer> map = Maps.newHashMap();
		for(String s : strs){
			String[] arr = s.split(",");
			map.put(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]));
		}
		return map;
	}
	
	public static List<Integer> stringToList(String str){
		String[] strs = str.split(",");
		int size = strs.length;
		List<Integer> list = Lists.newArrayListWithCapacity(size);
		for(int i = 0; i < size; i++){
			list.add(Integer.parseInt(strs[i]));
		}
		return list;
	}
	
	public static String uppercaseTo_(String info) {
		StringBuilder builder = new StringBuilder(info.substring(0, 1).toUpperCase());
		for (int i = 1;i < info.length();i++) {
			if (Character.isUpperCase(info.charAt(i))) {
				builder.append("_");
			}
			builder.append(info.substring(i, i + 1).toUpperCase());
		}
		return builder.toString();
	}
	
	public static String _ToUppercase(String info) {
		StringBuilder builder = new StringBuilder();
		String[] infos = info.split("_");
		if (infos.length > 1) {
			for (String str : infos) {
				builder.append(firstUpper(str.toLowerCase()));
			}
			return builder.toString();
		} else {
			return firstUpper(info.toLowerCase());
		}
	}
	
}
