package org.tool.server.collection.list.link;

import org.tool.server.collection.node.Node;

public interface LinkedNode<T> extends Node<T> {
	
	LinkedNode<T> getNext();
	
	void setNext(LinkedNode<T> next);

}
