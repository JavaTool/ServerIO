package org.tool.server.collection.sort;

public class QuickSort implements Sort {
	
	private final int m;
	
	public QuickSort(int m) {
		this.m = m;
	}
	
	public QuickSort() {
		this(1);
	}

	@Override
	public int[] sort(int[] kn) {
		int n = kn.length;
		if (n < m) {
			return insertSort(kn);
		} else {
			StackNode stack = null;
			// Q1
			int l = 0;
			int r = n - 1;
			do {
				// Q2
				int i = l;
				int j = r + 1;
				int k = kn[l];
				do {
					// Q3
					while (kn[++i] < k) {}
					// Q4
					while (k < kn[--j]) {}
					// Q5
					if (j <= i) {
						Sort.swap(kn, l, j);
						// Q7
						if (r - j >= j - l && j - l > m) {
							stack = new StackNode(j + 1, r, stack);
							r = j - 1;
						} else if (j - l > r - j && r - j > m) {
							stack = new StackNode(l, j - 1, stack);
							l = j + 1;
						} else if (r - j > m && m >= j - l) {
							l = j + 1;
						} else if (j - l > m && m >= r - j) {
							r = j - 1;
						} else if (stack == null) {
							return m == 1 ? kn : insertSort(kn);
						} else {
							// Q8
							l = stack.l;
							r = stack.r;
							stack = stack.previous;
						}
					} else {
						// Q6
						Sort.swap(kn, i, j);
					}
				} while (i < j);
			} while (true);
		}
	}
	
	private static int[] insertSort(int[] kn) {
		for (int j = 1;j < kn.length;j++) {
			if (kn[j - 1] > kn[j]) {
				int k = kn[j];
				int i = j - 1;
				do {
					kn[i + 1] = kn[i];
					i = i - 1;
				} while (i >= 0 && kn[i] > k);
				kn[i + 1] = k;
			}
		}
		return kn;
	}
	
	private static class StackNode {
		
		private final int l;
		
		private final int r;
		
		private final StackNode previous;
		
		public StackNode(int l, int  r, StackNode previous) {
			this.l = l;
			this.r = r;
			this.previous = previous;
		}
		
	}

}
