package org.tool.server.collection.sort;

public class InsertSort implements Sort {
	
	@Override
	public int[] sort(int[] kn) {
		int[] ret = kn;
		for (int j = 1;j < ret.length;j++) {
			int i = j - 1;
			int k = ret[j];
			while (k < ret[i]) {
				ret[i + 1] = ret[i];
				i = i - 1;
				if (i < 0) {
					break;
				}
			}
			ret[i + 1] = k;
		}
		return ret;
	}

}
