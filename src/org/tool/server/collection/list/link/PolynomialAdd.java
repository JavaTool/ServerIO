package org.tool.server.collection.list.link;

public class PolynomialAdd extends Polynomial {

	@Override
	public PolynomialNode calc(PolynomialNode p, PolynomialNode q) {
		this.p = p;
		this.q = q;
		init();
		compare();
		return this.q;
	}

	@Override
	protected int getPCoef() {
		return p.getCoef();
	}

	@Override
	protected int[] getPExponents() {
		return p.getExponents();
	}

}
