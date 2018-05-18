package org.tool.server.collection.sort;

public class InsertSort implements Sort {
	
	@Override
	public int[] sort(int[] kn) {
		for (int j = 1;j < kn.length;j++) {
			int i = j - 1;
			int k = kn[j];
			while (i >= 0 && k < kn[i]) {
				kn[i + 1] = kn[i];
				i = i - 1;
			}
			kn[i + 1] = k;
		}
		return kn;
	}

}
