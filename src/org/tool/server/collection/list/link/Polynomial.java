package org.tool.server.collection.list.link;

public abstract class Polynomial {
	
	protected PolynomialNode p, q, q1, q2;
	
	public abstract PolynomialNode calc(PolynomialNode m, PolynomialNode p);
	
	protected void init() {
		p = p.getLink();
		q1 = q;
		q = q.getLink();
	}
	
	protected void compare() {
		do {
			int[] pv = getPExponents();
			int[] qv = q.getExponents();
			int result = compare(pv, qv);
			if (result == 0) {
				if (pv == null) {
					return;
				} else {
					q.setCoef(q.getCoef() + getPCoef());
				}
				
				if (q.getCoef() == 0) {
					remove();
				} else {
					p = p.getLink();
					q1 = q;
					q = q.getLink();
				}
			} else if (result > 0) {
				insert();
			} else {
				q1 = q;
				q = q.getLink();
			}
		} while (true);
	}
	
	private static int compare(int[] exponents1, int[] exponents2) {
		if (exponents1 == null && exponents2 == null) {
			return 0;
		} else if (exponents1 == null) {
			return -1;
		} else if (exponents2 == null) {
			return 1;
		} else {
			for (int i = 0;i < exponents1.length;i++) {
				if (exponents1[i] > exponents2[i]) {
					return 1;
				} else if (exponents1[i] < exponents2[i]) {
					return -1;
				}
			}
			return 0;
		}
	}
	
	protected abstract int getPCoef();
	
	protected void remove() {
		q2 = q;
		q = q.getLink();
		q1.setLink(q);
		q2.setLink(null);
		p = p.getLink();
	}
	
	protected void insert() {
		q2 = new PolynomialNode();
		q2.setCoef(getPCoef());
		q2.setExponents(getPExponents());
		q2.setLink(q);                 
		q1.setLink(q2);
		q1 = q2;
		p = p.getLink();
	}
	
	protected abstract int[] getPExponents();

}
