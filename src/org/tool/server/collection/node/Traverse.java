package org.tool.server.collection.node;

public interface Traverse<T, B extends Node<T>> {
	
	void traverse(B root, NodeVisitor<T> visitor);
	
	B next(B node);

}
