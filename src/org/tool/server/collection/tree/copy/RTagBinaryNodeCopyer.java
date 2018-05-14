package org.tool.server.collection.tree.copy;

import org.tool.server.collection.node.NodeCopyer;
import org.tool.server.collection.tree.DefaultRTagBinaryTreeNode;
import org.tool.server.collection.tree.RTagBinaryTreeNode;

public final class RTagBinaryNodeCopyer<T> implements NodeCopyer<T, RTagBinaryTreeNode<T>> {

	@Override
	public RTagBinaryTreeNode<T> copy(RTagBinaryTreeNode<T> node) {
		RTagBinaryTreeNode<T> root = copyNode(node);
		if (node.isRoot()) {
			root.setRLink(root);
		}

		RTagBinaryTreeNode<T> p = node;
		RTagBinaryTreeNode<T> p1 = p;
		RTagBinaryTreeNode<T> q = root;
		RTagBinaryTreeNode<T> q1 = q;
		
		do {
			// 先完成左子树
			while (p.hasLLink()) {
				q1 = q;
				p = p.getLLink();
				
				RTagBinaryTreeNode<T> r = copyNode(p);
				
				q.setLLink(r);
				q = r;
				if (p.getRTag()) { // 设置线索
					r.setRLink(q1);
				} else { // 先临时补上右孩子，待左子树完成后再完善右子树
					p1 = p;
					p = p.getRLink();
					r = copyNode(p);
					
					q.setRLink(r);
					r.setRLink(q1);
					if (p1.hasLLink()) {
						p = p1;
					} else {
						q = r;
					}
				}
			}
			// 右子树
			if (p.getRTag()) { // 回到最近的临时右孩子
				while (p != null && p != node && (!p.hasLLink() || (p.hasLLink() && q.hasLLink()))) {
					p = p.getRLink();
					q = q.getRLink();
				}
			} else { // 补全右子树
				p = p.getRLink();
				RTagBinaryTreeNode<T> r = copyNode(p);
				
				q.setRLink(r);
				r.setRLink(q1);
			}
		} while (p != null && p != node);
		
		return root;
	}
	
	private RTagBinaryTreeNode<T> copyNode(RTagBinaryTreeNode<T> node) {
		RTagBinaryTreeNode<T> r = new DefaultRTagBinaryTreeNode<>();
		r.setContent(node.getContent());
		r.setRTag(node.getRTag());
		return r;
	}

}
