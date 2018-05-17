package org.tool.server.collection.sort;

import com.google.common.base.Strings;

public final class CountSort implements Sort {
	
	@Override
	public int[] sort(int[] kn) {
		int[] count = new int[kn.length];
		for (int i = kn.length - 1;i > 0;i--) {
			for (int j = i - 1;j >= 0;j--) {
				count[kn[i] < kn[j] ? j : i]++;
			}
		}
		int[] ret = new int[kn.length];
		for (int i = 0;i < kn.length;i++) {
			System.out.print(Strings.padEnd(count[i] + "", 3, ' '));
			ret[count[i]] = kn[i];
		}
		System.out.println();
		return ret;
	}

}
