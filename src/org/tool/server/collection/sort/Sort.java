package org.tool.server.collection.sort;

public interface Sort {
	
	int[] sort(int[] kn);
	
	static int calcLgN(int n) {
		int t = 0;
		for (int exp = 1;exp < n;exp <<= 1, t++) {}
		return t;
	}
	
	static int exp(int n) {
		int exp = 1;
		for (int i = 0;i < n;i++, exp <<= 1) {}
		return exp;
	}
	
	static <T> void swap(T[] array, int a, int b) {
		T t = array[a];
		array[a] = array[b];
		array[b] = t;
	}
	
	static void swap(int[] array, int a, int b) {
		int t = array[a];
		array[a] = array[b];
		array[b] = t;
	}

}
