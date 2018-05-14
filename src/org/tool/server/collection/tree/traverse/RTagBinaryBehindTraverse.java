package org.tool.server.collection.tree.traverse;

import org.tool.server.collection.node.BaseTraverse;
import org.tool.server.collection.tree.RTagBinaryTreeNode;

public final class RTagBinaryBehindTraverse<T> extends BaseTraverse<T, RTagBinaryTreeNode<T>> {

	@Override
	public RTagBinaryTreeNode<T> next(RTagBinaryTreeNode<T> node) {
		// 根节点
		RTagBinaryTreeNode<T> next = node;
		if (node.isRoot()) {
			return maxLeft(next);
		}
		// 非根节点
		next = node.getRLink();
		return node.getRTag() ? next : maxLeft(next);
	}
	
	private static <T> RTagBinaryTreeNode<T> maxLeft(RTagBinaryTreeNode<T> node) {
		for (;node.hasLLink();node = node.getLLink()) {}
		return node;
	}

}
