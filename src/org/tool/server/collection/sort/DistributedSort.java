package org.tool.server.collection.sort;

public final class DistributedSort implements Sort {
	
	private final int max, min;
	
	public DistributedSort(int max, int min) {
		this.max = max;
		this.min = min;
	}

	@Override
	public int[] sort(int[] kn) {
		int[] count = new int[max - min];
		for (int j = 0;j < kn.length;j++) {
			count[kn[j] - min]++;
		}
		for (int i = 1;i < max - min;i++) {
			count[i] += count[i - 1];
		}
		int[] ret = new int[kn.length];
		for (int j = kn.length - 1;j >= 0;j--) {
			int i = count[kn[j] - min];
			ret[i - 1] = kn[j];
			count[kn[j] - min] = i - 1;
		}
		return ret;
	}

}
