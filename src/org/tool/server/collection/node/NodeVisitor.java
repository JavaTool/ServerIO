package org.tool.server.collection.node;

public interface NodeVisitor<T> {
	
	void visit(T content);

}
