package com.fanxing.server.word;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 提示语管理器
 * @author	fuhuiyuan
 */
public class TipsWordManager {
	
	/**提示语集合*/
	private Map<String, Map<String, TipsWord>> tipsWords;
	
	public TipsWordManager() {
		tipsWords = new HashMap<String, Map<String,TipsWord>>();
	}
	
	/**
	 * 加载提示语
	 * @param 	path
	 * 			提示语文件路径
	 * @throws 	Exception
	 */
	public void load(String path) throws Exception {
		Workbook workbook = loadExcel(path);
		int sheetCount = workbook.getNumberOfSheets();
		for (int i = 0;i < sheetCount;i++) {
			Sheet sheet = workbook.getSheetAt(i);
			load(sheet);
		}
	}

	/**
	 * 加载Excel
	 * @param 	path
	 * 			Excel路径
	 * @return	Excel
	 * @throws 	Exception
	 */
	@SuppressWarnings("resource")
	private Workbook loadExcel(String path) throws Exception {
		InputStream stream = new FileInputStream(path);
		return path.endsWith(".xlsx") ? new XSSFWorkbook(stream) : new HSSFWorkbook(stream);
	}
	
	/**
	 * 加载提示语
	 * @param 	sheet
	 * 			表
	 */
	protected void load(Sheet sheet) {
		String type = sheet.getSheetName();
		Map<String, TipsWord> map = new HashMap<String, TipsWord>();
		int rowCount = sheet.getLastRowNum() + 1;
		Row row = sheet.getRow(0);
		String[] language = new String[row.getLastCellNum() - 1];
		for (int i = 0;i < language.length;i++) {
			language[i] = row.getCell(i + 1).getStringCellValue();
		}
		for (int i = 3;i < rowCount;i++) {
			TipsWord tipsWord = new TipsWord(sheet.getRow(i), type, language);
			map.put(tipsWord.getName(), tipsWord);
		}
		tipsWords.put(type, map);
	}
	
	/**
	 * 获取提示语
	 * @param 	type
	 * 			类型
	 * @param 	name
	 * 			名称
	 * @param 	language
	 * 			语言
	 * @return	提示语
	 */
	public String getWord(String type, String name, String language) {
		if (tipsWords.containsKey(type)) {
			Map<String, TipsWord> map = tipsWords.get(type);
			return map.containsKey(name) ? map.get(name).getWord(language) : "";
		} else {
			return null;
		}
	}

	/**
	 * 获取提示语
	 * @param 	clz
	 * 			类型
	 * @param 	name
	 * 			名称
	 * @param 	language
	 * 			语言
	 * @return	提示语
	 */
	public String getWord(@SuppressWarnings("rawtypes") Class clz, String name, String language) {
		return getWord(clz.getSimpleName().replace("Word", ""), name, language);
	}

}
