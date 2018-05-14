package org.tool.server.collection.list.link;

public class BinaryTreeNode {
	
	private String value;
	
	private BinaryTreeNode left;
	
	private BinaryTreeNode right;
	
	private boolean lTag;
	
	private boolean rTag;
	
	public BinaryTreeNode(String value) {
		setValue(value);
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public BinaryTreeNode getLeft() {
		return left;
	}

	public void setLeft(BinaryTreeNode left) {
		this.left = left;
	}

	public BinaryTreeNode getRight() {
		return right;
	}

	public void setRight(BinaryTreeNode right) {
		this.right = right;
	}
	
	public static void main(String[] args) {
		BinaryTreeNode t = new BinaryTreeNode("A");
		t.setLeft(new BinaryTreeNode("B"));
		t.setRight(new BinaryTreeNode("C"));
		t.getLeft().setLeft(new BinaryTreeNode("D"));
		t.getRight().setLeft(new BinaryTreeNode("E"));
		t.getRight().setRight(new BinaryTreeNode("F"));
		t.getRight().getLeft().setRight(new BinaryTreeNode("G"));
		t.getRight().getRight().setLeft(new BinaryTreeNode("H"));
		t.getRight().getRight().setRight(new BinaryTreeNode("J"));
		
		t.getLeft().setrTag(true);
		t.getLeft().getLeft().setrTag(true);
		t.getRight().getLeft().setlTag(true);
		t.getRight().getLeft().getRight().setlTag(true);
		t.getRight().getLeft().getRight().setrTag(true);
		t.getRight().getRight().getLeft().setlTag(true);
		t.getRight().getRight().getLeft().setrTag(true);
		t.getRight().getRight().getRight().setlTag(true);
		
		BinaryTreeOrderTraverse orderTraverse = new BinaryTreeOrderTraverse();
		orderTraverse.traverse(t);
		System.out.println();
		BinaryTreeNode q = orderTraverse.threadedFind(t);
		System.out.println(q == null ? "null" : q.getValue());
	}

	public boolean islTag() {
		return lTag;
	}

	public void setlTag(boolean lTag) {
		this.lTag = lTag;
	}

	public boolean isrTag() {
		return rTag;
	}

	public void setrTag(boolean rTag) {
		this.rTag = rTag;
	}

}
