package org.tool.server.collection.sort;

import com.google.common.base.Strings;

public class SortExample implements Sort {
	
	private static final int[] kn = new int[]{3,5,2,12,0,23,10,16,1,9,};
	
	private Sort sort;

	public static void main(String[] args) {
		SortExample example = new SortExample();
		example.setSort(new NoneSort()).sort(kn);
		example.setSort(new CountSort()).sort(kn);
		example.setSort(new DistributedSort(32, 0)).sort(kn);
		example.setSort(new InsertSort()).sort(copyOf(kn));
		example.setSort(new ShellSort(new int[]{1, 3, 5})).sort(copyOf(kn));
		example.setSort(new BubbleSort()).sort(copyOf(kn));
//		example.setSort(new BarcelSort()).sort(copyOf(new int[]{503,87,512,61,908,170,987,275,653,426,154,509,612,677,765,703}));
		example.setSort(new BarcelSort()).sort(copyOf(kn));
		example.setSort(new QuickSort()).sort(copyOf(kn));
	}
	
	private static int[] copyOf(int[] kn) {
		int[] ret = new int[kn.length];
		System.arraycopy(kn, 0, ret, 0, kn.length);
		return ret;
	}
	
	private SortExample setSort(Sort sort) {
		this.sort = sort;
		return this;
	}

	@Override
	public int[] sort(int[] kn) {
		System.out.println(sort.getClass().getSimpleName() + " : ");
		for (int r : sort.sort(kn)) {
			System.out.print(Strings.padEnd(r + "", 4, ' '));
		}
		System.out.println();
		return null;
	}

}
