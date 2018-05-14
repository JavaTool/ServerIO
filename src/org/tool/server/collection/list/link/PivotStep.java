package org.tool.server.collection.list.link;

public class PivotStep {
	
	private MatixNode pivot;
	
	private double alpha;
	
	private int i;
	
	private int i0;
	
	private int j;
	
	private int j0;
	
	private MatixNode p;
	
	private MatixNode p0;
	
	private MatixNode p1;
	
	private MatixNode q0;
	
	private MatixNode[] rowNodes;
	
	private MatixNode[] colNodes;
	
	private MatixNode[] ptr;
	
	public void calc(MatixNode pivot, MatixNode[] rowNodes, MatixNode[] colNodes) {
		this.pivot = pivot;
		this.rowNodes = rowNodes;
		this.colNodes = colNodes;
		ptr = new MatixNode[colNodes.length];
		init();
		s2();
	}
	
	private void init() {
		alpha = 1.0 / pivot.getVal();
		pivot.setVal(1.0);
		i0 = pivot.getRow();
		j0 = pivot.getCol();
		p0 = rowNodes[i0];
		q0 = colNodes[j0];
	}
	
	private void s2() {
//		p0 = p0.getLeft();
//		j = p0.getCol();
//		if (j < 0) {
//			s3();
//		} else {
//			ptr[j] = colNodes[j];
//			p0.setVal(alpha * p0.getVal());
//			s2();
//		}
		while ((j = (p0 = p0.getLeft()).getCol()) >= 0) {
			ptr[j] = colNodes[j];
			p0.setVal(alpha * p0.getVal());
		}
		s3();
	}
	
	private void s3() {
//		q0 = q0.getUp();
//		i = q0.getRow();
//		if (i < 0) {
//			return;
//		} else if (i == i0) {
//			s3();
//		} else {
//			p = rowNodes[i];
//			p1 = p.getLeft();
//			s4();
//		}
		while ((i = (q0 = q0.getUp()).getRow()) == i0) {}
		if (i < 0) {
			return;
		} else {
			p = rowNodes[i];
			p1 = p.getLeft();
			s4();
		}
	}
	
	private void s4() {
//		p0 = p0.getLeft();
//		j = p0.getCol();
//		if (j < 0) {
//			q0.setVal(-alpha * q0.getVal());
//			s3();
//		} else if (j == j0) {
//			s4();
//		} else {
//			s5();
//		}
		while ((j = (p0 = p0.getLeft()).getCol()) == j0) {}
		if (j < 0) {
			q0.setVal(-alpha * q0.getVal());
			s3();
		} else {
			s5();
		}
	}
	
	private void s5() {
//		if (p1.getCol() > j) {
//			p = p1;
//			p1 = p.getLeft();
//			s5();
//		} else if (p1.getCol() == j) {
//			s7();
//		} else {
//			s6();
//		}
		while (p1.getCol() > j) {
			p = p1;
			p1 = p.getLeft();
		}
		if (p1.getCol() == j) {
			s7();
		} else {
			s6();
		}
	}
	
//	private void s5e() {
//		while (p1.getCol() != j) {
//			p = p1;
//			p1 = p.getLeft();
//			if (p1.getCol() < j) {
//				while (ptr[j].getUp().getRow() > i) {
//					ptr[j] = ptr[j].getUp();
//				}
//				MatixNode x = new MatixNode(i, j, 0, p1, ptr[j].getUp());
//				p.setLeft(x);
//				ptr[j].setUp(x);
//				p1 = x;
//			}
//		}
//		s7();
//	}
	
	private void s6() {
//		if (ptr[j].getUp().getRow() > i) {
//			ptr[j] = ptr[j].getUp();
//			s6();
//		} else {
//			MatixNode x = new MatixNode(i, j, 0, p1, ptr[j].getUp());
//			p.setLeft(x);
//			ptr[j].setUp(x);
//			p1 = x;
//			s5();
//		}
		while (ptr[j].getUp().getRow() > i) {
			ptr[j] = ptr[j].getUp();
		}
		MatixNode x = new MatixNode(i, j, 0, p1, ptr[j].getUp());
		p.setLeft(x);
		ptr[j].setUp(x);
		p1 = x;
		s5();
	}
	
	private void s7() {
		p1.setVal(p1.getVal() - q0.getVal() * p0.getVal());
		if (p1.getVal() == 0) {
			s8();
		} else {
			ptr[j] = p1;
			p = p1;
			p1 = p.getLeft();
			s4();
		}
	}
	
	private void s8() {
//		if (ptr[j].getUp().getRow() > i) {
//			ptr[j] = ptr[j].getUp();
//			s8();
//		} else {
//			ptr[j].setUp(p1.getUp());
//			p.setLeft(p1.getLeft());
//			p1 = null;
//			p1 = p.getLeft();
//			s4();
//		}
		while (ptr[j].getUp().getRow() > i) {
			ptr[j] = ptr[j].getUp();
		}
		ptr[j].setUp(p1.getUp());
		p.setLeft(p1.getLeft());
		p1 = null;
		p1 = p.getLeft();
		s4();
	}

}
