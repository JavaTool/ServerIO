package org.tool.server.collection.list;

import static com.google.common.collect.Lists.newArrayListWithCapacity;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.tool.server.cache.ICache;
import org.tool.server.cache.ICacheList;

public abstract class BaseStringCachedList<E> implements List<E> {
	
	private final ICache<String, String, String> cache;
	
	private final ICacheList<String, String> cacheList;
	
	private final String key;
	
	public BaseStringCachedList(ICache<String, String, String> cache, String key) {
		this.cache = cache;
		cacheList = cache.list();
		this.key = key;
	}

	@Override
	public int size() {
		return (int) cacheList.size(key);
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}
	
	protected abstract E toE(String e);
	
	protected abstract String eToString(E e);

	@SuppressWarnings("unchecked")
	@Override
	public boolean contains(Object o) {
		return getAll().contains(eToString((E) o));
	}
	
	protected final List<String> getAll() {
		return cacheList.range(key, 0, size());
	}

	@Override
	public Iterator<E> iterator() {
		return new CachedIterator();
	}

	@Override
	public Object[] toArray() {
		return getAll().toArray();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T[] toArray(T[] a) {
		List<String> all = getAll();
		for (int i = 0, loop = a.length;i < loop;i++) {
			a[i] = (T) toE(all.get(i));
		}
		return a;
	}

	@Override
	public boolean add(E e) {
		cacheList.tailPush(key, eToString(e));
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean remove(Object o) {
		cacheList.lrem(key, eToString((E) o));
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean containsAll(Collection<?> c) {
		List<String> all = getAll();
		for (Object o : c) {
			if (!all.contains(eToString((E) o))) {
				return false;
			}
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean addAll(Collection<? extends E> c) {
		for (Object o : c) {
			add((E) o);
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		for (Object o : c) {
			add(index++, (E) o);
		}
		return true;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		for (Object o : c) {
			remove(o);
		}
		return true;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException("StringStringCachedList.retainAll on " + key);
	}

	@Override
	public void clear() {
		cache.key().delete(key);
	}

	@Override
	public E get(int index) {
		return toE(cacheList.get(key, index));
	}

	@Override
	public E set(int index, E element) {
		cacheList.insert(key, index, eToString(element));
		return element;
	}

	@Override
	public void add(int index, E element) {
		cacheList.insert(key, index, eToString(element));
	}

	@Override
	public E remove(int index) {
		E e = get(index);
		remove(e);
		return e;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int indexOf(Object o) {
		List<String> all = getAll();
		for (int i = 0;i < all.size();i++) {
			if (eToString((E) o).equals(all.get(i))) {
				return i;
			}
		}
		return -1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int lastIndexOf(Object o) {
		List<String> all = getAll();
		for (int i = all.size() - 1;i >= 0;i++) {
			if (eToString((E) o).equals(all.get(i))) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public ListIterator<E> listIterator() {
		return new CachedListIterator();
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		return new CachedListIterator(index);
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		List<String> list = cacheList.range(key, fromIndex, toIndex);
		List<E> ret = newArrayListWithCapacity(list.size());
		list.forEach(v -> ret.add(toE(v)));
		return ret;
	}
	
	private class CachedIterator implements Iterator<E> {
		
		protected int index;

		@Override
		public boolean hasNext() {
			return get(index + 1) != null;
		}

		@Override
		public E next() {
			return get(++index);
		}
		
	}
	
	private class CachedListIterator extends CachedIterator implements ListIterator<E> {
		
		public CachedListIterator(int index) {
			this.index = index;
		}
		
		public CachedListIterator() {
			this(0);
		}

		@Override
		public boolean hasPrevious() {
			return index > 0 ? get(previousIndex()) != null : null;
		}

		@Override
		public E previous() {
			return index > 0 ? get(--index) : null;
		}

		@Override
		public int nextIndex() {
			return index + 1;
		}

		@Override
		public int previousIndex() {
			return index - 1;
		}

		@Override
		public void remove() {
			BaseStringCachedList.this.remove(index);
		}

		@Override
		public void set(E e) {
			BaseStringCachedList.this.set(index, e);
		}

		@Override
		public void add(E e) {
			BaseStringCachedList.this.add(index, e);
		}
		
	}

}
