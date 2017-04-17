package com.fanxing.server.collection.list;

public final class RangeLists {
	
	private RangeLists() {}
	
	public static <V> IRangeList<V> createRangeList(int base) {
		return new GetRemoveRangeList<V>(base);
	}
	
	public static <V> IRangeList<V> createRangeList() {
		return createRangeList(0);
	}

}
