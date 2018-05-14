package org.tool.server.collection.node;

public interface NodeCopyer<T, N extends Node<T>> {
	
	N copy(N node);

}
