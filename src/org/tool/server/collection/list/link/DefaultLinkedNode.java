package org.tool.server.collection.list.link;

import org.tool.server.collection.node.DefaultNode;

public class DefaultLinkedNode<T> extends DefaultNode<T> implements LinkedNode<T> {
	
	private LinkedNode<T> next;

	@Override
	public LinkedNode<T> getNext() {
		return next;
	}

	public void setNext(LinkedNode<T> next) {
		this.next = next;
	}

}
