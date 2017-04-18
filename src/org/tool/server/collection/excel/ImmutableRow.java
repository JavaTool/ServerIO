package org.tool.server.collection.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public abstract class ImmutableRow implements Row {
	
	private final Sheet sheet;
	
	private final int rowNum;
	
	public ImmutableRow(Sheet sheet, int rowNum) {
		this.sheet = sheet;
		this.rowNum = rowNum;
	}

	@Override
	public Cell createCell(int column) {
		return null;
	}

	@Override
	public Cell createCell(int column, int type) {
		return null;
	}

	@Override
	public void removeCell(Cell cell) {}

	@Override
	public void setRowNum(int rowNum) {}

	@Override
	public int getRowNum() {
		return rowNum;
	}

	@Override
	public Cell getCell(int cellnum, MissingCellPolicy policy) {
		return null;
	}

	@Override
	public short getFirstCellNum() {
		return 0;
	}

	@Override
	public void setHeight(short height) {}

	@Override
	public void setZeroHeight(boolean zHeight) {}

	@Override
	public boolean getZeroHeight() {
		return false;
	}

	@Override
	public void setHeightInPoints(float height) {}

	@Override
	public short getHeight() {
		return 0;
	}

	@Override
	public float getHeightInPoints() {
		return 0;
	}

	@Override
	public boolean isFormatted() {
		return false;
	}

	@Override
	public CellStyle getRowStyle() {
		return null;
	}

	@Override
	public void setRowStyle(CellStyle style) {}

	@Override
	public Sheet getSheet() {
		return sheet;
	}

}
