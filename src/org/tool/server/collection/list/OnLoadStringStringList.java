package org.tool.server.collection.list;

import static com.google.common.collect.Lists.newLinkedList;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class OnLoadStringStringList<E> implements List<E> {
	
	private final BaseStringCachedList<E> cachedList;
	
	private final List<E> list;
	
	public OnLoadStringStringList(BaseStringCachedList<E> cachedList, List<E> list) {
		this.cachedList = cachedList;
		this.list = list;
		list.addAll(cachedList.subList(0, cachedList.size()));
	}
	
	public OnLoadStringStringList(BaseStringCachedList<E> cachedList) {
		this(cachedList, newLinkedList());
	}

	@Override
	public int size() {
		return list.size();
	}

	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return list.contains(o);
	}

	@Override
	public Iterator<E> iterator() {
		return list.iterator();
	}

	@Override
	public Object[] toArray() {
		return list.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return list.toArray(a);
	}

	@Override
	public boolean add(E e) {
		cachedList.add(e);
		return list.add(e);
	}

	@Override
	public boolean remove(Object o) {
		cachedList.remove(o);
		return list.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		cachedList.containsAll(c);
		return list.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		cachedList.addAll(c);
		return list.addAll(c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		cachedList.addAll(index, c);
		return list.addAll(index, c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		cachedList.removeAll(c);
		return list.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return list.retainAll(c);
	}

	@Override
	public void clear() {
		cachedList.clear();
		list.clear();
	}

	@Override
	public E get(int index) {
		return list.get(index);
	}

	@Override
	public E set(int index, E element) {
		cachedList.set(index, element);
		return list.set(index, element);
	}

	@Override
	public void add(int index, E element) {
		cachedList.add(index, element);
		list.add(index, element);
	}

	@Override
	public E remove(int index) {
		cachedList.remove(index);
		return list.remove(index);
	}

	@Override
	public int indexOf(Object o) {
		return list.indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o) {
		return list.lastIndexOf(o);
	}

	@Override
	public ListIterator<E> listIterator() {
		return list.listIterator();
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		return list.listIterator(index);
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		return list.subList(fromIndex, toIndex);
	}

}
