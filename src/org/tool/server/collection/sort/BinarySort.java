package org.tool.server.collection.sort;

public class BinarySort implements Sort {
	
	private int m;
	
	public BinarySort() {
		setM(31);
	}

	@Override
	public int[] sort(int[] kn) {
		StackNode stack = null;
		int l = 0;
		int r = kn.length - 1;
		int b = m;
		int i;
		int j;
		// R2
		do {
			if (l == r) {
				
			} else {
				i = l;
				j = r;
				// R3 R4
				for (;(1 << b & kn[i]) > 0 && ++i <= j;) {}
				// R8
				if (--b < 0) {
					// R10
					if (stack == null) {
						return kn;
					} else {
						l = r + 1;
						r = stack.r;
						b = stack.b;
						stack = stack.previous;
						continue;
					}
				} else if (j < l || j == r) {
					continue;
				} else if (j == l) {
					l++;
					continue;
				} else {
					// R9
					stack = new StackNode(b, r, stack);
					r = j;
				}
				// R5
				for (;(1 << b & kn[i]) == 0 && i <= --j;) {}
			}
		} while (true);
	}

	public void setM(int m) {
		this.m = m;
	}

	private static class StackNode {
		
		private final int b;
		
		private final int r;
		
		private final StackNode previous;
		
		public StackNode(int b, int  r, StackNode previous) {
			this.b = b;
			this.r = r;
			this.previous = previous;
		}
		
	}

}
