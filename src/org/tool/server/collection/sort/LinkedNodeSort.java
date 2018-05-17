package org.tool.server.collection.sort;

import java.util.Comparator;

import org.tool.server.collection.list.link.LinkedNode;

public interface LinkedNodeSort {
	
	<T> LinkedNode<T> sort(LinkedNode<T> node, Comparator<T> comparator);

}
