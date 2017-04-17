package com.fanxing.server.collection.excel;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;

import com.google.common.collect.ImmutableList;

public class RemoteRow extends ImmutableRow {
	
	private List<Cell> cellList;

	public RemoteRow(Sheet sheet, int rowNum) {
		super(sheet, rowNum);
	}
	
	void setCellList(Collection<Cell> cellList) {
		this.cellList = ImmutableList.copyOf(cellList);
	}

	@Override
	public Cell getCell(int cellnum) {
		return cellList.get(cellnum);
	}

	@Override
	public short getLastCellNum() {
		return (short) (cellList.size() - 1);
	}

	@Override
	public int getPhysicalNumberOfCells() {
		return cellList.size();
	}

	@Override
	public Iterator<Cell> cellIterator() {
		return ImmutableList.copyOf(cellList).iterator();
	}

	@Override
	public Iterator<Cell> iterator() {
		return cellIterator();
	}

}
