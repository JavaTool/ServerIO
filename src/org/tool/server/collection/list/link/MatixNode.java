package org.tool.server.collection.list.link;

import com.google.common.base.Strings;

public class MatixNode {
	
	private int row;
	
	private int col;
	
	private MatixNode up;
	
	private MatixNode left;
	
	private double val;
	
	public MatixNode(int row, int col) {
		this(row, col, -1);
	}
	
	public MatixNode(int row, int col, int val) {
		setCol(col);
		setRow(row);
		setVal(val);
	}
	
	public MatixNode(int row, int col, int val, MatixNode left, MatixNode up) {
		this(row, col, val);
		setLeft(left);
		setUp(up);
	}

	public double getVal() {
		return val;
	}

	public void setVal(double val) {
		this.val = val;
	}

	public MatixNode getLeft() {
		return left;
	}

	public void setLeft(MatixNode left) {
		this.left = left;
	}

	public MatixNode getUp() {
		return up;
	}

	public void setUp(MatixNode up) {
		this.up = up;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}
	
	public static void main(String[] args) {
		MatixNode[] rowNodes = createRowNodes(4);
		MatixNode[] colNodes = createColNodes(4);
		
		MatixNode node00 = new MatixNode(0, 0, 50, rowNodes[0], colNodes[0]);
		MatixNode node10 = new MatixNode(1, 0, 10, rowNodes[1], node00);
		MatixNode node12 = new MatixNode(1, 2, 20, node10, colNodes[2]);
		MatixNode node30 = new MatixNode(3, 0, -30, rowNodes[3], node10);
		MatixNode node32 = new MatixNode(3, 2, -60, node30, node12);
		MatixNode node33 = new MatixNode(3, 3, 5, node32, colNodes[3]);
		
		rowNodes[0].setLeft(node00);
		rowNodes[1].setLeft(node12);
		rowNodes[3].setLeft(node33);
		
		colNodes[0].setUp(node30);
		colNodes[2].setUp(node32);
		colNodes[3].setUp(node33);
		for (MatixNode rowNode : rowNodes) {
			System.out.println(rowNode.getInfo(colNodes.length));
		}
		
		new PivotStep().calc(node10, rowNodes, colNodes);
		System.out.println("=====PivotStep=====");
		for (MatixNode rowNode : rowNodes) {
			System.out.println(rowNode.getInfo(colNodes.length));
		}
	}
	
	public static MatixNode[] createRowNodes(int row) {
		MatixNode[] nodes = new MatixNode[row];
		for (int i = 0;i < row;i++) {
			nodes[i] = new MatixNode(i, -1);
			nodes[i].setLeft(nodes[i]);
		}
		return nodes;
	}
	
	public static MatixNode[] createColNodes(int col) {
		MatixNode[] nodes = new MatixNode[col];
		for (int i = 0;i < col;i++) {
			nodes[i] = new MatixNode(-1, i);
			nodes[i].setUp(nodes[i]);
		}
		return nodes;
	}
	
	private String getInfo(int col) {
		StringBuilder builder = new StringBuilder();
		int step = col - 1;
		MatixNode node = this;
		while ((node = node.getLeft()).getCol() >= 0) {
			for (int j = node.getCol();j < step;j++) {
				builder.insert(0, Strings.padStart("0", 6, ' '));
			}
			builder.insert(0, Strings.padStart("" + node.getVal(), 6, ' '));
			step = node.getCol() - 1;
		}
		if (builder.length() == 0) {
			for (int i = 0;i < col;i++) {
				builder.append(Strings.padStart("0", 6, ' '));
			}
		}
		return builder.toString();
	}

}
