package com.fanxing.server.word;

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;

/**
 * 提示语
 * @author	fuhuiyuan
 */
public class TipsWord {
	
	/**名称*/
	private String name;
	/**各语言内容集合*/
	private Map<String, String> words;
	/**类型*/
	private String type;
	
	public TipsWord(Row row, String type, String[] language) {
		this.type = type;
		this.name = row.getCell(0).getStringCellValue();
		words = new HashMap<String, String>();
//		for (int i = 0;i < language.length;i++) {
//			words.put(language[i], row.getCell(i + 1).getStringCellValue());
//		}
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}
	
	/**
	 * 获取提示语内容
	 * @param 	language
	 * 			语言
	 * @return	提示语内容
	 */
	public String getWord(String language) {
		return words.containsKey(language) ? words.get(language) : name;
	}

}
