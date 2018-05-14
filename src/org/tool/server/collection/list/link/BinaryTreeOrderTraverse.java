package org.tool.server.collection.list.link;

import java.util.List;

import com.google.common.collect.Lists;

public class BinaryTreeOrderTraverse {
	
	private BinaryTreeNode t;
	
	private BinaryTreeNode p;
	
	private List<BinaryTreeNode> a = Lists.newLinkedList();
	
	public void traverse(BinaryTreeNode t) {
		this.t = t;
		t1();
		t2();
	}
	
	public BinaryTreeNode threadedFind(BinaryTreeNode p) {
		BinaryTreeNode q = p.getRight();
		if (p.isrTag()) {
			return q;
		} else {
			while (!q.islTag()) {
				q = q.getLeft();
			}
			return q;
		}
	}
	
	private void t1() {
		a.clear();
		p = t;
	}
	
	private void t2() {
		if (p == null) {
			t4();
		} else {
			t3();
		}
	}
	
	private void t3() {
		a.add(p);
		p = p.getLeft();
		t2();
	}
	
	private void t4() {
		if (a.size() == 0) {
			return;
		}
		p = a.remove(a.size() - 1);
		t5();
	}
	
	private void t5() {
		System.out.print(p.getValue() + " ");
		p = p.getRight();
		t2();
	}

}
