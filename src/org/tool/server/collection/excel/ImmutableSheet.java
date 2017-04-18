package org.tool.server.collection.excel;

import org.apache.poi.hssf.util.PaneInformation;
import org.apache.poi.ss.usermodel.AutoFilter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellRange;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Footer;
import org.apache.poi.ss.usermodel.Header;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.SheetConditionalFormatting;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

public abstract class ImmutableSheet implements Sheet {

	@Override
	public Row createRow(int rownum) {
		return null;
	}

	@Override
	public void removeRow(Row row) {}

	@Override
	public int getFirstRowNum() {
		return 0;
	}

	@Override
	public void setColumnHidden(int columnIndex, boolean hidden) {}

	@Override
	public boolean isColumnHidden(int columnIndex) {
		return false;
	}

	@Override
	public void setRightToLeft(boolean value) {}

	@Override
	public boolean isRightToLeft() {
		return false;
	}

	@Override
	public void setColumnWidth(int columnIndex, int width) {}

	@Override
	public int getColumnWidth(int columnIndex) {
		return 0;
	}

	@Override
	public void setDefaultColumnWidth(int width) {}

	@Override
	public int getDefaultColumnWidth() {
		return 0;
	}

	@Override
	public short getDefaultRowHeight() {
		return 0;
	}

	@Override
	public float getDefaultRowHeightInPoints() {
		return 0;
	}

	@Override
	public void setDefaultRowHeight(short height) {}

	@Override
	public void setDefaultRowHeightInPoints(float height) {}

	@Override
	public CellStyle getColumnStyle(int column) {
		return null;
	}

	@Override
	public int addMergedRegion(CellRangeAddress region) {
		return 0;
	}

	@Override
	public void setVerticallyCenter(boolean value) {}

	@Override
	public void setHorizontallyCenter(boolean value) {}

	@Override
	public boolean getHorizontallyCenter() {
		return false;
	}

	@Override
	public boolean getVerticallyCenter() {
		return false;
	}

	@Override
	public void removeMergedRegion(int index) {}

	@Override
	public int getNumMergedRegions() {
		return 0;
	}

	@Override
	public CellRangeAddress getMergedRegion(int index) {
		return null;
	}

	@Override
	public void setForceFormulaRecalculation(boolean value) {}

	@Override
	public boolean getForceFormulaRecalculation() {
		return false;
	}

	@Override
	public void setAutobreaks(boolean value) {}

	@Override
	public void setDisplayGuts(boolean value) {}

	@Override
	public void setDisplayZeros(boolean value) {}

	@Override
	public boolean isDisplayZeros() {
		return false;
	}

	@Override
	public void setFitToPage(boolean value) {}

	@Override
	public void setRowSumsBelow(boolean value) {}

	@Override
	public void setRowSumsRight(boolean value) {}

	@Override
	public boolean getAutobreaks() {
		return false;
	}

	@Override
	public boolean getDisplayGuts() {
		return false;
	}

	@Override
	public boolean getFitToPage() {
		return false;
	}

	@Override
	public boolean getRowSumsBelow() {
		return false;
	}

	@Override
	public boolean getRowSumsRight() {
		return false;
	}

	@Override
	public boolean isPrintGridlines() {
		return false;
	}

	@Override
	public void setPrintGridlines(boolean show) {}

	@Override
	public PrintSetup getPrintSetup() {
		return null;
	}

	@Override
	public Header getHeader() {
		return null;
	}

	@Override
	public Footer getFooter() {
		return null;
	}

	@Override
	public void setSelected(boolean value) {}

	@Override
	public double getMargin(short margin) {
		return 0;
	}

	@Override
	public void setMargin(short margin, double size) {}

	@Override
	public boolean getProtect() {
		return true;
	}

	@Override
	public void protectSheet(String password) {}

	@Override
	public boolean getScenarioProtect() {
		return false;
	}

	@Override
	public void setZoom(int numerator, int denominator) {}

	@Override
	public short getTopRow() {
		return 0;
	}

	@Override
	public short getLeftCol() {
		return 0;
	}

	@Override
	public void showInPane(short toprow, short leftcol) {}

	@Override
	public void shiftRows(int startRow, int endRow, int n) {}

	@Override
	public void shiftRows(int startRow, int endRow, int n, boolean copyRowHeight, boolean resetOriginalRowHeight) {}

	@Override
	public void createFreezePane(int colSplit, int rowSplit, int leftmostColumn, int topRow) {}

	@Override
	public void createFreezePane(int colSplit, int rowSplit) {}

	@Override
	public void createSplitPane(int xSplitPos, int ySplitPos, int leftmostColumn, int topRow, int activePane) {}

	@Override
	public PaneInformation getPaneInformation() {
		return null;
	}

	@Override
	public void setDisplayGridlines(boolean show) {}

	@Override
	public boolean isDisplayGridlines() {
		return false;
	}

	@Override
	public void setDisplayFormulas(boolean show) {}

	@Override
	public boolean isDisplayFormulas() {
		return false;
	}

	@Override
	public void setDisplayRowColHeadings(boolean show) {}

	@Override
	public boolean isDisplayRowColHeadings() {
		return false;
	}

	@Override
	public void setRowBreak(int row) {}

	@Override
	public boolean isRowBroken(int row) {
		return false;
	}

	@Override
	public void removeRowBreak(int row) {}

	@Override
	public int[] getRowBreaks() {
		return null;
	}

	@Override
	public int[] getColumnBreaks() {
		return null;
	}

	@Override
	public void setColumnBreak(int column) {}

	@Override
	public boolean isColumnBroken(int column) {
		return false;
	}

	@Override
	public void removeColumnBreak(int column) {}

	@Override
	public void setColumnGroupCollapsed(int columnNumber, boolean collapsed) {}

	@Override
	public void groupColumn(int fromColumn, int toColumn) {}

	@Override
	public void ungroupColumn(int fromColumn, int toColumn) {}

	@Override
	public void groupRow(int fromRow, int toRow) {}

	@Override
	public void ungroupRow(int fromRow, int toRow) {}

	@Override
	public void setRowGroupCollapsed(int row, boolean collapse) {}

	@Override
	public void setDefaultColumnStyle(int column, CellStyle style) {}

	@Override
	public void autoSizeColumn(int column) {}

	@Override
	public void autoSizeColumn(int column, boolean useMergedCells) {}

	@Override
	public Comment getCellComment(int row, int column) {
		return null;
	}

	@Override
	public Drawing createDrawingPatriarch() {
		return null;
	}

	@Override
	public Workbook getWorkbook() {
		return null;
	}

	@Override
	public boolean isSelected() {
		return false;
	}

	@Override
	public CellRange<? extends Cell> setArrayFormula(String formula, CellRangeAddress range) {
		return null;
	}

	@Override
	public CellRange<? extends Cell> removeArrayFormula(Cell cell) {
		return null;
	}

	@Override
	public DataValidationHelper getDataValidationHelper() {
		return null;
	}

	@Override
	public void addValidationData(DataValidation dataValidation) {}

	@Override
	public AutoFilter setAutoFilter(CellRangeAddress range) {
		return null;
	}

	@Override
	public SheetConditionalFormatting getSheetConditionalFormatting() {
		return null;
	}

	@Override
	public CellRangeAddress getRepeatingRows() {
		return null;
	}

	@Override
	public CellRangeAddress getRepeatingColumns() {
		return null;
	}

	@Override
	public void setRepeatingRows(CellRangeAddress rowRangeRef) {}

	@Override
	public void setRepeatingColumns(CellRangeAddress columnRangeRef) {}

}
