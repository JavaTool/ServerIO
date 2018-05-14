package org.tool.server.collection.tree;

import org.tool.server.collection.node.DefaultNode;

public final class DefaultRTagBinaryTreeNode<T> extends DefaultNode<T> implements RTagBinaryTreeNode<T> {
	
	private RTagBinaryTreeNode<T> lLink;
	
	private RTagBinaryTreeNode<T> rLink;
	
	private boolean rTag;

	@Override
	public boolean getRTag() {
		return rTag;
	}

	@Override
	public void setRTag(boolean rTag) {
		this.rTag = rTag;
	}

	@Override
	public RTagBinaryTreeNode<T> getLLink() {
		return lLink;
	}

	@Override
	public void setLLink(RTagBinaryTreeNode<T> lLink) {
		this.lLink = lLink;
	}

	@Override
	public RTagBinaryTreeNode<T> getRLink() {
		return rLink;
	}

	@Override
	public void setRLink(RTagBinaryTreeNode<T> rLink) {
		this.rLink = rLink;
	}

	@Override
	public boolean hasLLink() {
		return lLink != null;
	}

	@Override
	public boolean hasRLink() {
		return rLink != null;
	}

}
