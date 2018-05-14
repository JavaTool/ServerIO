package org.tool.server.collection.tree;

import org.tool.server.collection.node.Node;

public interface RTagBinaryTreeNode<T> extends Node<T>, RTag {
	
	RTagBinaryTreeNode<T> getLLink();
	
	void setLLink(RTagBinaryTreeNode<T> lLink);
	
	RTagBinaryTreeNode<T> getRLink();
	
	void setRLink(RTagBinaryTreeNode<T> rLink);
	
	boolean hasLLink();
	
	boolean hasRLink();

}
