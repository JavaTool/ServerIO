package org.tool.server.collection.list.link;

public class PolynomialNode {
	
	private int coef;
	
	private int[] exponents;
	
	private PolynomialNode link = this;
	
	private boolean isTail;

	public int getCoef() {
		return coef;
	}

	public void setCoef(int coef) {
		this.coef = coef;
	}

	public int[] getExponents() {
		return isTail ? null : exponents;
	}

	public void setExponents(int[] exponents) {
		this.exponents = exponents;
	}
	
//	public int getExponent() {
//		int total = 0;
//		for (int i = 0;i < exponents.length;i++) {
//			int a = 1, num = i;
//			while (++num < exponents.length) {
//				a *= 10;
//			}
//			total += a * exponents[i];
//		}
//		return isTail ? -total : total;
//	}

	public PolynomialNode getLink() {
		return link;
	}

	public void setLink(PolynomialNode link) {
		this.link = link;
	}

	public boolean isTail() {
		return isTail;
	}

	public void setTail(boolean isTail) {
		this.isTail = isTail;
	}
	
	public static void main(String[] args) {
		PolynomialNode p = new PolynomialNode(), p1;
		p1 = p.initValue(0, new int[]{0, 0, 1}, true, new PolynomialNode());
		p1 = p1.initValue(1, new int[]{1, 0, 0}, false, new PolynomialNode());
		p1 = p1.initValue(1, new int[]{0, 1, 0}, false, new PolynomialNode());
		p1 = p1.initValue(1, new int[]{0, 0, 1}, false, p);
		System.out.println(p.getInfo());
		
		PolynomialNode q = new PolynomialNode(), q1;
		q1 = q.initValue(0, new int[]{0, 0, 1}, true, new PolynomialNode());
		q1 = q1.initValue(1, new int[]{2, 0, 0}, false, new PolynomialNode());
		q1 = q1.initValue(-2, new int[]{0, 1, 0}, false, new PolynomialNode());
		q1 = q1.initValue(-1, new int[]{0, 0, 1}, false, q);
		System.out.println(q.getInfo());
		
		long time1 = System.currentTimeMillis();
		q = new PolynomialAdd().calc(p, q);
		long time2 = System.currentTimeMillis();
		System.out.println(q.getInfo() + " : " + (time2 - time1));
		
		System.out.println("------------------------------------------------");
		
		p = new PolynomialNode();
		p1 = p.initValue(0, new int[]{0, 1}, true, new PolynomialNode());
		p1 = p1.initValue(1, new int[]{4, 0}, false, new PolynomialNode());
		p1 = p1.initValue(2, new int[]{3, 1}, false, new PolynomialNode());
		p1 = p1.initValue(3, new int[]{2, 2}, false, new PolynomialNode());
		p1 = p1.initValue(4, new int[]{1, 3}, false, new PolynomialNode());
		p1 = p1.initValue(5, new int[]{0, 4}, false, p);
		System.out.println(p.getInfo());
		
		q = new PolynomialNode();
		q1 = q.initValue(0, new int[]{0, 1}, true, new PolynomialNode());
		q1 = q1.initValue(1, new int[]{2, 0}, false, new PolynomialNode());
		q1 = q1.initValue(-2, new int[]{1, 1}, false, new PolynomialNode());
		q1 = q1.initValue(1, new int[]{0, 2}, false, q);
		System.out.println(q.getInfo());

		long time3 = System.currentTimeMillis();
		q = new PolynomialMul().calc(p, q);
		long time4 = System.currentTimeMillis();
		System.out.println(q.getInfo() + " : " + (time4 - time3));
	}
	
	public PolynomialNode initValue(int coef, int[] exponents, boolean isTail, PolynomialNode next) {
		this.coef = coef;
		this.exponents = exponents;
		this.isTail = isTail;
		setLink(next);
		return next;
	}
	
	public String getInfo() {
		PolynomialNode p = this;
		StringBuilder builder = new StringBuilder();
		do {
			if (!p.isTail) {
				boolean hasCoef = Math.abs(p.getCoef()) != 1;
				builder.append(builder.length() > 0 && p.getCoef() > 0 ? "+" : "").append(p.getCoef() == 1 ? "" : p.getCoef() == -1 ? "-" : p.getCoef());
				for (int i = 0;i < p.exponents.length;i++) {
					if (p.exponents[i] > 0) {
						builder.append(hasCoef ? "*" : "").append(VARS[i]).append(p.exponents[i] > 1 ? p.exponents[i] : "");
					}
				}
			}
			p = p.getLink();
		} while (p != this);
		return builder.toString();
	}
	
	private static final char[] VARS = {'x', 'y', 'z'};

}
