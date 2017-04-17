package com.fanxing.server.collection.excel;

import java.util.Calendar;
import java.util.Date;

import org.apache.poi.ss.formula.FormulaParseException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

public abstract class ImmutableCell implements Cell {
	
	private final int columnIndex;
	
	private final Row row;
	
	public ImmutableCell(Row row, int columnIndex) {
		this.row = row;
		this.columnIndex = columnIndex;
	}

	@Override
	public int getColumnIndex() {
		return columnIndex;
	}

	@Override
	public int getRowIndex() {
		return getRow().getRowNum();
	}

	@Override
	public Sheet getSheet() {
		return getRow().getSheet();
	}

	@Override
	public Row getRow() {
		return row;
	}

	@Override
	public void setCellType(int cellType) {}

	@Override
	public int getCachedFormulaResultType() {
		return 0;
	}

	@Override
	public void setCellValue(double value) {}

	@Override
	public void setCellValue(Date value) {}

	@Override
	public void setCellValue(Calendar value) {}

	@Override
	public void setCellValue(RichTextString value) {}

	@Override
	public void setCellValue(String value) {}

	@Override
	public void setCellFormula(String formula) throws FormulaParseException {}

	@Override
	public void setCellValue(boolean value) {}

	@Override
	public void setCellErrorValue(byte value) {}

	@Override
	public byte getErrorCellValue() {
		return 0;
	}

	@Override
	public void setCellStyle(CellStyle style) {}

	@Override
	public CellStyle getCellStyle() {
		return null;
	}

	@Override
	public void setAsActiveCell() {}

	@Override
	public void setCellComment(Comment comment) {}

	@Override
	public Comment getCellComment() {
		return null;
	}

	@Override
	public void removeCellComment() {}

	@Override
	public Hyperlink getHyperlink() {
		return null;
	}

	@Override
	public void setHyperlink(Hyperlink link) {}

	@Override
	public CellRangeAddress getArrayFormulaRange() {
		return null;
	}

	@Override
	public boolean isPartOfArrayFormulaGroup() {
		return false;
	}

}
