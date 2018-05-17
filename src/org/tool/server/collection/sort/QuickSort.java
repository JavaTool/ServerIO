package org.tool.server.collection.sort;

public class QuickSort implements Sort {
	
	private final int m;
	
	public QuickSort(int m) {
		this.m = m;
	}
	
	public QuickSort() {
		this(0);
	}

	@Override
	public int[] sort(int[] kn) {
		int n = kn.length;
		if (n < m) {
			for (int j = 1;j < n;j++) {
				if (kn[j - 1] > kn[j]) {
					int k = kn[j];
					int i = j - 1;
					while (kn[i] > k) {
						kn[i + 1] = kn[i];
						i = i - 1;
					}
					kn[i + 1] = k;
				}
			}
		} else {
			int[] stack = new int[Sort.calcLgN(n)];
			int top = 0;
			int l = 0;
			int r = n - 1;
			do {
				int i = l;
				int j = r + 1;
				int k = kn[l];
				
				do {
					while (kn[++i] < k) {}
					while (k < kn[--j]) {}
					if (j <= i) {
						int t = kn[l];
						kn[l] = kn[j];
						kn[j] = t;
						if (r - j >= j - l && j - l > m) {
							for (t = j + 1;t <= r;t++) {
								stack[top++] = t;
							}
							r = j - 1;
						} else if (j - l > r - j && j - l > m) {
							for (t = l;t < j;t++) {
								stack[top++] = t;
							}
							l = j + 1;
						} else if (r - j >= m && m >= j - l) {
							l = j + 1;
						} else if (j - l > m && m >= r - j) {
							r = j - 1;
						} else {
							r = stack[top];
							do {
								l = stack[top--];
							} while (top > 0);
						}
					} else {
						int t = kn[i];
						kn[i] = kn[j];
						kn[j] = t;
					}
				} while (j <= i);
			} while (top > 0);
		}
		return kn;
	}

}
