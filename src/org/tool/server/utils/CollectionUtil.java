package org.tool.server.utils;

import static com.google.common.collect.Lists.newLinkedList;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;

public final class CollectionUtil {
	
	public static final String TABLE_JSON_ROW = "row";
	
	public static final String TABLE_JSON_COLUMN = "column";
	
	public static final String TABLE_JSON_VALUE = "value";
	
	public static <R, C, V> List<JSONObject> tableToJsonList(Table<R, C, V> table) {
		List<JSONObject> list = newLinkedList();
		table.cellSet().forEach(cell -> {
			JSONObject json = new JSONObject();
			json.put(TABLE_JSON_ROW, cell.getRowKey());
			json.put(TABLE_JSON_COLUMN, cell.getColumnKey());
			json.put(TABLE_JSON_VALUE, cell.getValue());
			list.add(json);
		});
		return list;
	}
	
	public static Table<Integer, Integer, String> jsonListToIISTable(List<JSONObject> list) {
		Table<Integer, Integer, String> table = HashBasedTable.create();
		list.forEach(v -> {
			int row = v.getIntValue(TABLE_JSON_ROW);
			int column = v.getIntValue(TABLE_JSON_COLUMN);
			String value = v.getString(TABLE_JSON_VALUE);
			table.put(row, column, value);
		});
		return table;
	}

	public static Table<String, String, String> jsonListToSSSTable(List<JSONObject> list){
		Table<String, String, String> table = HashBasedTable.create();
		list.forEach(v -> {
			String row = v.getString(TABLE_JSON_ROW);
			String column = v.getString(TABLE_JSON_COLUMN);
			String value = v.getString(TABLE_JSON_VALUE);
			table.put(row, column, value);
		});
		return table;
	}
	
	public static <K, V> Map<K, V> arrayToMap(V[] configs, Class<K> keyClass, String keyMethod) throws Exception {
		return arrayToMap(configs, keyClass, keyMethod, input -> { return input; });
	}
	
	@SuppressWarnings("unchecked")
	public static <K, V, I> Map<K, V> arrayToMap(I[] configs, Class<K> keyClass, String keyMethod, IValueCreator<I, V> valueCreator) throws Exception {
		Map<K, V> map = Maps.newHashMap();
		if (configs.length > 0) {
			Method getKeyMethod = configs[0].getClass().getMethod(keyMethod);
			for (I config : configs) {
				map.put((K) getKeyMethod.invoke(config), valueCreator.create(config));
			}
		}
		return ImmutableMap.copyOf(map);
	}
	
	public static interface IValueCreator<I, V> {
		
		V create(I input);
		
	}
	
}
