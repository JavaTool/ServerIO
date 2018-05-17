package org.tool.server.collection.sort;

public class BubbleSort implements Sort {

	@Override
	public int[] sort(int[] kn) {
		int bound = kn.length - 1;
		int t;
		do {
			t = 0;
			for (int j = 0;j < bound;j++) {
				if (kn[j] > kn[j + 1]) {
					t = kn[j];
					kn[j] = kn[j + 1];
					kn[j + 1] = t;
					t = j;
				}
			}
			if (t > 0) {
				bound = t;
			}
		} while (t > 0);
		return kn;
	}

}
