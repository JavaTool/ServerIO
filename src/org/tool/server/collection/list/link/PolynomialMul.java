package org.tool.server.collection.list.link;

public class PolynomialMul extends Polynomial {
	
	private PolynomialNode m;

	@Override
	protected int getPCoef() {
		return p.getCoef() * m.getCoef();
	}

	@Override
	public PolynomialNode calc(PolynomialNode p, PolynomialNode q) {
		this.q = new PolynomialNode();
		this.q.initValue(0, new int[]{0, 1}, true, this.q);
		this.m = q;
		this.p = p;
		
		while (!(this.m = this.m.getLink()).isTail()) {
			mul();
		}
		
		return this.q;
	}
	
	private void mul() {
		init();
		compare();
	}

	@Override
	protected int[] getPExponents() {
		if (p.isTail()) {
			return p.getExponents();
		}
		int[] exponents = new int[p.getExponents().length];
		for (int i = 0;i < exponents.length;i++) {
			exponents[i] = p.getExponents()[i] + m.getExponents()[i];
		}
		return exponents;
	}

}
