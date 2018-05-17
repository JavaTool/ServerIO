package org.tool.server.collection.sort;

public class BarcelSort implements Sort {

	@Override
	public int[] sort(int[] kn) {
		int n = kn.length;
		int t = Sort.calcLgN(n);
		System.out.println("Lg(" + n + ") = " + t);
		int p = Sort.exp(t - 1);
		do {
			int q = Sort.exp(t - 1);
			int r = 0;
			int d = p;
			boolean loop;
			do {
				for (int i = 0;i < n - d;i++) {
					if ((i & p) == r) {
						if (kn[i] > kn[i + d]) {
							int k = kn[i];
							kn[i] = kn[i + d];
							kn[i + d] = k;
						}
					}
				}
				loop = q != p;
				if (loop) {
					d = q - p;
					q >>= 1;
					r = p;
				}
			} while (loop);
			p >>= 1;
		} while (p > 0);
		return kn;
	}

}
