package org.tool.server.collection.tree.traverse;

import org.tool.server.collection.node.BaseTraverse;
import org.tool.server.collection.tree.RTagBinaryTreeNode;

public final class RTagBinaryFrontTraverse<T> extends BaseTraverse<T, RTagBinaryTreeNode<T>> {

	@Override
	public RTagBinaryTreeNode<T> next(RTagBinaryTreeNode<T> node) {
		if (node.hasLLink()) {
			return node.getLLink();
		} else { // 左孩子
			RTagBinaryTreeNode<T> right = node.getRLink();
			if (node.getRTag()) { // 右线索寻找兄弟
				for (;!right.isRoot() && right.getRTag();right = right.getRLink()) {}
				return right.getRLink();
			} else { // 右孩子
				return right;
			}
		}
	}

}
