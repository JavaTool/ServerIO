package com.fanxing.server.utils;

import org.apache.poi.ss.usermodel.Cell;

public class ExcelUtil {
	
	public static String readCellAsString(Cell cell) {
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_BLANK : 
			return "";
		case Cell.CELL_TYPE_BOOLEAN : 
			return cell.getBooleanCellValue() ? "true" : "false";
		case Cell.CELL_TYPE_ERROR : 
			return cell.getErrorCellValue() + "";
		case Cell.CELL_TYPE_FORMULA : 
			return cell.getCellFormula();
		case Cell.CELL_TYPE_NUMERIC : 
			return cell.getNumericCellValue() + "";
		case Cell.CELL_TYPE_STRING : 
			return cell.getStringCellValue();
		default : 
			throw new IllegalArgumentException("Unknow cell type : " + cell.getCellType() + ".");
		}
	}

}
