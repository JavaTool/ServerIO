package org.tool.server.collection.tree;

import org.tool.server.collection.node.NodeCopyer;
import org.tool.server.collection.node.Traverse;
import org.tool.server.collection.tree.apply.Differential;
import org.tool.server.collection.tree.copy.RTagBinaryNodeCopyer;
import org.tool.server.collection.tree.traverse.RTagBinaryBehindTraverse;
import org.tool.server.collection.tree.traverse.RTagBinaryFrontTraverse;

public class Expression {
	
	public static enum Type {
		
		TYPE_CONST {
			
			@Override
			public int getAry() {
				return 0;
			}

			@Override
			public String getValue(Expression node) {
				return "" + node.getInfo();
			}
			
		},
		
		TYPE_VARIABLE {
			
			@Override
			public int getAry() {
				return 0;
			}

			@Override
			public String getValue(Expression node) {
				switch (node.getInfo()) {
				case 2 : 
					return "a";
				default : 
					return "x";
				}
			}
			
		},
		
		TYPE_LN {
			
			@Override
			public int getAry() {
				return 1;
			}

			@Override
			public String getValue(Expression node) {
				return "ln";
			}
			
		},
		
		TYPE_NEG {
			
			@Override
			public int getAry() {
				return 1;
			}

			@Override
			public String getValue(Expression node) {
				return "-";
			}
			
		},
		
		TYPE_ADD {
			
			@Override
			public int getAry() {
				return 2;
			}

			@Override
			public String getValue(Expression node) {
				return "+";
			}
			
		},
		
		TYPE_SUB {
			
			@Override
			public int getAry() {
				return 2;
			}

			@Override
			public String getValue(Expression node) {
				return "-";
			}
			
		},
		
		TYPE_MUL {
			
			@Override
			public int getAry() {
				return 2;
			}

			@Override
			public String getValue(Expression node) {
				return "*";
			}
			
		},
		
		TYPE_DIV {
			
			@Override
			public int getAry() {
				return 2;
			}

			@Override
			public String getValue(Expression node) {
				return "/";
			}
			
		},
		
		TYPE_EXP {
			
			@Override
			public int getAry() {
				return 2;
			}

			@Override
			public String getValue(Expression node) {
				return "^";
			}
			
		},
		;
		
		public abstract int getAry();
		
		public abstract String getValue(Expression node);
		
	}
	
	public static void main(String[] args) {
		RTagBinaryTreeNode<Expression> root = createExample();
		
		NodeCopyer<Expression, RTagBinaryTreeNode<Expression>> copyer = new RTagBinaryNodeCopyer<>();
		
		Traverse<Expression, RTagBinaryTreeNode<Expression>> front = new RTagBinaryFrontTraverse<>();
		printInfo(front, root);
		printInfo(front, copyer.copy(root));
		
		Traverse<Expression, RTagBinaryTreeNode<Expression>> behind = new RTagBinaryBehindTraverse<>();
		printInfo(behind, root);
		printInfo(behind, copyer.copy(root));
		
		printInfo(front, new Differential().calc(root));
	}
	
	private static RTagBinaryTreeNode<Expression> createExample() {
		RTagBinaryTreeNode<Expression> root = createNode(true);
		root.setRLink(root);
		root.setLLink(createNode(root, true, Type.TYPE_SUB));
		
		RTagBinaryTreeNode<Expression> node = root.getLLink();
		node.setLLink(createNode(node, false, Type.TYPE_MUL));

		addRight(node.getLLink(), node);
		node = node.getLLink();
		addLeft(node);
		
		return root;
	}
	
	public static RTagBinaryTreeNode<Expression> createNode(boolean rTag) {
		RTagBinaryTreeNode<Expression> node = new DefaultRTagBinaryTreeNode<>();
		node.setRTag(rTag);
		return node;
	}
	
	public static RTagBinaryTreeNode<Expression> createNode(RTagBinaryTreeNode<Expression> rLink, boolean rTag, Type type) {
		RTagBinaryTreeNode<Expression> node = createNode(rTag);
		node.setRLink(rLink);
		node.setContent(new Expression(type));
		return node;
	}
	
	public static RTagBinaryTreeNode<Expression> createNode(RTagBinaryTreeNode<Expression> rLink, boolean rTag, Type type, int info) {
		RTagBinaryTreeNode<Expression> node = createNode(rTag);
		node.setRLink(rLink);
		node.setContent(new Expression(type, info));
		return node;
	}
	
	private static void addLeft(RTagBinaryTreeNode<Expression> mul) {
		mul.setLLink(createNode(createNode(mul, true, Type.TYPE_LN), false, Type.TYPE_CONST, 3));
		RTagBinaryTreeNode<Expression> node = mul.getLLink(); // 3
		node = node.getRLink(); // ln
		node.setLLink(createNode(node, true, Type.TYPE_ADD));
		node = node.getLLink(); // add
		node.setLLink(createNode(createNode(node, true, Type.TYPE_CONST, 1), false, Type.TYPE_VARIABLE, 1));
	}
	
	private static void addRight(RTagBinaryTreeNode<Expression> mul, RTagBinaryTreeNode<Expression> sub) {
		mul.setRLink(createNode(sub, true, Type.TYPE_DIV));
		RTagBinaryTreeNode<Expression> node = mul.getRLink(); // sub
		node.setLLink(createNode(createNode(node, true, Type.TYPE_EXP), false, Type.TYPE_VARIABLE, 2));
		node = node.getLLink(); // a
		node = node.getRLink(); // exp
		node.setLLink(createNode(createNode(node, true, Type.TYPE_CONST, 2), false, Type.TYPE_VARIABLE, 1));
	}
	
	private static void printInfo(Traverse<Expression, RTagBinaryTreeNode<Expression>> traverse, RTagBinaryTreeNode<Expression> node) {
		traverse.traverse(node, content -> System.out.print(content.getValue() + " "));
		System.out.println();
	}
	
	private Type type;
	
	private int info;
	
	public Expression(Type type, int info) {
		this.type = type;
		this.info = info;
	}
	
	public Expression(Type type) {
		this.type = type;
	}
	
	public Expression() {}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public int getInfo() {
		return info;
	}

	public void setInfo(int info) {
		this.info = info;
	}
	
	public String getValue() {
		return type == null ? "" : type.getValue(this);
	}

}
