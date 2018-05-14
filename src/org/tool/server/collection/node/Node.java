package org.tool.server.collection.node;

public interface Node<T> {
	
	T getContent();
	
	void setContent(T content);
	
	boolean isRoot();

}
