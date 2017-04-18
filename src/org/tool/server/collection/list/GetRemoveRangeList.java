package org.tool.server.collection.list;

class GetRemoveRangeList<V> implements IRangeList<V> {
	
	private final int base;
	
	private Node<V> head;
	
	private Node<V> tail;
	
	public GetRemoveRangeList(int base) {
		this.base = base;
	}

	@Override
	public void add(int bound, V value) {
		Node<V> node = new Node<V>();
		node.value = value;
		node.bound = bound;
		
		if (isEmpty()) {
			head = node;
			head.base = base;
			head.max = bound;
		} else {
			tail.next = node;
			node.base = tail.max;
		}
		node.max = node.base + bound;
		tail = node;
	}

	@Override
	public V get(int key) {
		if (isEmpty()) {
			return null;
		} else {
			Node<V> preNode = null;
			Node<V> node = head;
			while (node != null) {
				if (key < node.max) {
					if (preNode == null) {
						setNewHead(node.next);
					} else {
						preNode.setNext(node.next);
						if (node.next == null) {
							tail = preNode;
						}
					}
					return node.value;
				} else {
					preNode = node;
					node = node.next;
				}
			}
			return null;
		}
	}
	
	private void setNewHead(Node<V> head) {
		if (head != null) {
			int base = getMin();
			Node<V> node = head;
			while (node != null) {
				node.base = base;
				node.max = node.base + node.bound;
				base = node.max;
				if (node.next != null) {
					node = node.next;
					tail = node;
				} else {
					tail = node;
					node = node.next;
				}
			}
		} else {
			tail = null;
		}
		this.head = head;
	}

	@Override
	public int getMin() {
		return base;
	}

	@Override
	public int getMax() {
		return isEmpty() ? getMin() : tail.max;
	}

	@Override
	public void clear() {
		head = null;
		tail = null;
	}
	
	private boolean isEmpty() {
		return head == null;
	}
	
	private static class Node<V> {
		
		private int base;
		
		private int max;
		
		private int bound;
		
		private V value;
		
		private Node<V> next;
		
		void setNext(Node<V> next) {
			if (next != null) {
				int base = max;
				Node<V> node = next;
				while (node != null) {
					node.base = base;
					node.max = node.base + node.bound;
					base = node.max;
					node = node.next;
				}
			}
			this.next = next;
		}
		
	}

}
