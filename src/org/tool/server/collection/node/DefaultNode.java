package org.tool.server.collection.node;

public class DefaultNode<T> implements Node<T> {
	
	private T content;

	@Override
	public final T getContent() {
		return content;
	}

	@Override
	public final void setContent(T content) {
		this.content = content;
	}

	@Override
	public boolean isRoot() {
		return content == null;
	}

}
