package com.fanxing.server.collection.excel;

import static com.fanxing.server.utils.DateUtil.parseDate;
import static java.lang.Double.parseDouble;

import java.util.Date;

import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;

public class RemoteCell extends ImmutableCell {
	
	private final String value;

	public RemoteCell(Row row, int columnIndex, String value) {
		super(row, columnIndex);
		this.value = value;
	}

	@Override
	public int getCellType() {
		return CELL_TYPE_STRING;
	}

	@Override
	public String getCellFormula() {
		return value;
	}

	@Override
	public double getNumericCellValue() {
		return parseDouble(value);
	}

	@Override
	public Date getDateCellValue() {
		return parseDate(value);
	}

	@Override
	public RichTextString getRichStringCellValue() {
		return null;
	}

	@Override
	public String getStringCellValue() {
		return value;
	}

	@Override
	public boolean getBooleanCellValue() {
		return value.equals("1");
	}

}
