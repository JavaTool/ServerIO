package org.tool.server.collection.node;

public interface NodeCalculator<T, B extends Node<T>> {
	
	B calc(B node);

}
