package com.fanxing.server.collection.excel;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Table;

public class RemoteSheet extends ImmutableSheet {
	
	private final String name;
	
	private List<Row> rowList;
	
	public RemoteSheet(String name) {
		this.name = name;
	}
	
	public void readFrom(Table<Integer, Integer, String> table) {
		List<Row> rowList = Lists.newLinkedList();
		table.rowMap().forEach((k, v) -> rowList.add(readRow(k, v)));
		this.rowList = rowList;
	}
	
	private Row readRow(int rowNum, Map<Integer, String> map) {
		RemoteRow row = new RemoteRow(this, rowNum);
		List<Cell> cellList = Lists.newArrayListWithCapacity(map.size());
		map.forEach((k, v) -> cellList.add(createCell(row, k, v)));
		row.setCellList(cellList);
		return row;
	}
	
	private Cell createCell(Row row, int columnIndex, String value) {
		return new RemoteCell(row, columnIndex, value);
	}

	@Override
	public Row getRow(int rownum) {
		return rowList.get(rownum);
	}

	@Override
	public int getPhysicalNumberOfRows() {
		return rowList.size();
	}

	@Override
	public int getLastRowNum() {
		return rowList.size() - 1;
	}

	@Override
	public Iterator<Row> rowIterator() {
		return ImmutableList.copyOf(rowList).iterator();
	}

	@Override
	public String getSheetName() {
		return name;
	}

	@Override
	public Iterator<Row> iterator() {
		return rowIterator();
	}
	
}
