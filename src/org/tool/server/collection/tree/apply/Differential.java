package org.tool.server.collection.tree.apply;

import org.tool.server.collection.node.NodeCalculator;
import org.tool.server.collection.node.NodeCopyer;
import org.tool.server.collection.node.Traverse;
import org.tool.server.collection.tree.Expression;
import org.tool.server.collection.tree.Expression.Type;
import org.tool.server.collection.tree.RTagBinaryTreeNode;
import org.tool.server.collection.tree.copy.RTagBinaryNodeCopyer;
import org.tool.server.collection.tree.traverse.RTagBinaryBehindTraverse;

public final class Differential implements NodeCalculator<Expression, RTagBinaryTreeNode<Expression>> {
	
	private static final NodeCopyer<Expression, RTagBinaryTreeNode<Expression>> COPYER = new RTagBinaryNodeCopyer<>();
	
	private static final Traverse<Expression, RTagBinaryTreeNode<Expression>> BEHIND = new RTagBinaryBehindTraverse<>();
	
	private static void deff(RTagBinaryTreeNode<Expression> p, RTagBinaryTreeNode<Expression> p1, RTagBinaryTreeNode<Expression> p2, TempNodes tempNodes) {
		deff(p, null, p1, p2, tempNodes);
	}
	
	private static void deff(RTagBinaryTreeNode<Expression> p, Type enforceType, RTagBinaryTreeNode<Expression> p1, RTagBinaryTreeNode<Expression> p2, TempNodes tempNodes) {
		Type type = enforceType == null ? p.getContent().getType() : enforceType;
		if (type == null) {
			return;
		}
		switch (type) {
		case TYPE_CONST : 
			tempNodes.q = createTree(new Expression(Type.TYPE_CONST, 0));
			break;
		case TYPE_ADD : 
			if (isZero(tempNodes.q1)) {
				tempNodes.q1 = null;
			} else if (isZero(tempNodes.q)) {
				tempNodes.q = tempNodes.q1;
			} else {
				tempNodes.q = createTree(new Expression(Type.TYPE_ADD), tempNodes.q1, tempNodes.q);
			}
			break;
		case TYPE_DIV : 
			if (!isZero(tempNodes.q1)) {
				tempNodes.q1 = createTree(new Expression(Type.TYPE_DIV), tempNodes.q1, COPYER.copy(p2));
			}
			if (!isZero(tempNodes.q)) {
				tempNodes.q = createTree(new Expression(Type.TYPE_DIV), mult(COPYER.copy(p1), tempNodes.q), createTree(new Expression(Type.TYPE_EXP), COPYER.copy(p2), createTree(new Expression(Type.TYPE_CONST, 2))));
			}
			deff(p, Type.TYPE_SUB, p1, p2, tempNodes);
			break;
		case TYPE_EXP : 
			if (!isZero(tempNodes.q1)) {
				RTagBinaryTreeNode<Expression> r = COPYER.copy(p1);
				int p2Info = p2.getContent().getInfo();
				if (p2.getContent().getType() == Type.TYPE_CONST && p2Info != 2) {
					r = createTree(new Expression(Type.TYPE_EXP), r, createTree(new Expression(Type.TYPE_CONST, p2Info - 1)));
				} 
				if (p2.getContent().getType() != Type.TYPE_CONST) {
					r = createTree(new Expression(Type.TYPE_EXP), r, createTree(new Expression(Type.TYPE_SUB), COPYER.copy(p2), createTree(new Expression(Type.TYPE_CONST, 1))));
				}
				tempNodes.q1 = mult(tempNodes.q1, mult(COPYER.copy(p2), r));
			}
			if (!isZero(tempNodes.q)) {
				tempNodes.q = createTree(new Expression(Type.TYPE_MUL), mult(createTree(new Expression(Type.TYPE_LN), COPYER.copy(p1)), tempNodes.q), createTree(new Expression(Type.TYPE_EXP), COPYER.copy(p1), COPYER.copy(p2)));
			}
			deff(p, Type.TYPE_ADD, p1, p2, tempNodes);
			break;
		case TYPE_LN : 
			if (!isZero(tempNodes.q)) {
				tempNodes.q = createTree(new Expression(Type.TYPE_DIV), tempNodes.q, COPYER.copy(p1));
			}
			break;
		case TYPE_MUL : 
			if (!isZero(tempNodes.q1)) {
				tempNodes.q1 = mult(tempNodes.q1, COPYER.copy(p2));
			}
			if (!isZero(tempNodes.q)) {
				tempNodes.q = mult(COPYER.copy(p1), tempNodes.q);
			}
			deff(p, Type.TYPE_ADD, p1, p2, tempNodes);
			break;
		case TYPE_NEG : 
			if (!isZero(tempNodes.q)) {
				tempNodes.q = createTree(new Expression(Type.TYPE_NEG), tempNodes.q);
			}
			break;
		case TYPE_SUB : 
			if (isZero(tempNodes.q)) {
				tempNodes.q = tempNodes.q1;
			} else if (isZero(tempNodes.q1)) {
				tempNodes.q1 = null;
				tempNodes.q = createTree(new Expression(Type.TYPE_NEG), tempNodes.q);
			} else {
				tempNodes.q = createTree(new Expression(Type.TYPE_SUB), tempNodes.q1, tempNodes.q);
			}
			break;
		case TYPE_VARIABLE : 
			tempNodes.q = createTree(new Expression(Type.TYPE_CONST, p.getContent().getInfo() == 1 ? 1 : 0));
			break;
		}
	}
	
	private static boolean isZero(RTagBinaryTreeNode<Expression> node) {
		return node.getContent().getType() == Type.TYPE_CONST && node.getContent().getInfo() == 0;
	}
	
	private static RTagBinaryTreeNode<Expression> mult(RTagBinaryTreeNode<Expression> u, RTagBinaryTreeNode<Expression> v) {
		if (isMultOne(u.getContent())) {
			return v;
		}
		if (isMultOne(v.getContent())) {
			return u;
		}
		return createTree(new Expression(Type.TYPE_MUL), u, v);
	}
	
	private static boolean isMultOne(Expression content) {
		return content.getInfo() == 1 && content.getType() == Type.TYPE_CONST;
	}
	
	private static RTagBinaryTreeNode<Expression> createTree(Expression content) {
		RTagBinaryTreeNode<Expression> w = Expression.createNode(true);
		w.setContent(content);
		return w;
	}
	
	private static RTagBinaryTreeNode<Expression> createTree(Expression content, RTagBinaryTreeNode<Expression> u) {
		RTagBinaryTreeNode<Expression> w = createTree(content);
		w.setLLink(u);
		u.setRLink(w);
		u.setRTag(true);
		return w;
	}
	
	private static RTagBinaryTreeNode<Expression> createTree(Expression content, RTagBinaryTreeNode<Expression> u, RTagBinaryTreeNode<Expression> v) {
		RTagBinaryTreeNode<Expression> w = createTree(content);
		w.setLLink(u);
		u.setRLink(v);
		u.setRTag(false);
		v.setRLink(w);
		v.setRTag(true);
		return w;
	}
	
	@Override
	public RTagBinaryTreeNode<Expression> calc(RTagBinaryTreeNode<Expression> node) {
		RTagBinaryTreeNode<Expression> y = node;
		RTagBinaryTreeNode<Expression> dy = Expression.createNode(true);
		dy.setRLink(dy);
		RTagBinaryTreeNode<Expression> p = BEHIND.next(y);
		TempNodes tempNodes = new TempNodes();
		RTagBinaryTreeNode<Expression> p2 = null;
		
		do {
			// 微分
			RTagBinaryTreeNode<Expression> p1 = p.getLLink();
			if (p1 != null) {
				tempNodes.q1 = p1.getRLink();
			}
			deff(p, p1, p2, tempNodes);
			// 链复位
			Expression content = p.getContent();
			if (content != null && content.getType().getAry() == 2) {
				p1.setRLink(p2);
			}
			// 后序遍历
			p2 = p;
			p = BEHIND.next(p);
			if (!p2.getRTag()) {
				p2.setRLink(tempNodes.q);
			}
			// 判断完成
			if (p == y) {
				dy.setLLink(tempNodes.q);
				tempNodes.q.setRLink(dy);
				tempNodes.q.setRTag(true);
			}
		} while (p != y);
		
		return dy;
	}
	
	private static class TempNodes {
		
		public RTagBinaryTreeNode<Expression> q;
		
		public RTagBinaryTreeNode<Expression> q1;
		
	}

}
