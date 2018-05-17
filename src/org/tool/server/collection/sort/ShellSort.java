package org.tool.server.collection.sort;

public class ShellSort implements Sort {
	
	private final int[] h;
	
	public ShellSort(int[] h) {
		this.h = h;
	}

	@Override
	public int[] sort(int[] kn) {
		int t = h.length;
		for (int s = t - 1;s >= 0;s--) {
			int h = this.h[s];
			for (int j = h;j < kn.length;j++) {
				int i = j - h;
				int k = kn[j];
				while (k < kn[i]) {
					kn[i + h] = kn[i];
					i = i - h;
					if (i < 0) {
						break;
					}
				}
				kn[i + h] = k;
			}
		}
		return kn;
	}

}
