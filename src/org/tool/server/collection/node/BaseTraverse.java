package org.tool.server.collection.node;

public abstract class BaseTraverse<T, B extends Node<T>> implements Traverse<T, B> {

	@Override
	public void traverse(B root, NodeVisitor<T> visitor) {
		B node = root;
		for (B next = next(node);next != null && !next.isRoot();node = next, visitor.visit(node.getContent()), next = next(node)) {}
	}

}
