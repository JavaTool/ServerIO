package org.tool.server.collection.list;

import static com.google.common.collect.Lists.newArrayListWithCapacity;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import org.tool.server.cache.ICache;
import org.tool.server.cache.ICacheKey;
import org.tool.server.collection.map.StringCachedObjectFactory;

public class StringCachedList<E> implements List<E> {
	
	private final StringCachedObjectFactory stringCachedObjectFactory;
	
	private final ICacheKey<String> cacheKey;
	
	private final ICache<String, String, String> cache;
	
	private final String key;
	
	private final Class<E> clz;
	
	public StringCachedList(ICache<String, String, String> cache, String key, StringCachedObjectFactory stringCachedObjectFactory, Class<E> clz) {
		this.cache = cache;
		cacheKey = cache.key();
		this.key = key;
		this.stringCachedObjectFactory = stringCachedObjectFactory;
		this.clz = clz;
	}

    private void rangeCheckForAdd(int index) {
        if (index < 0 || index > size())
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

    private String outOfBoundsMsg(int index) {
        return "Index: "+index+", Size: "+size();
    }

	@Override
	public int size() {
		return keys().size();
	}
	
	protected final Set<String> keys() {
		return cacheKey.keys(key);
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public boolean contains(Object o) {
		throw new UnsupportedOperationException("StringCachedList.contains on " + key);
	}

	@Override
	public Iterator<E> iterator() {
		return new CachedIterator();
	}

	@Override
	public Object[] toArray() {
		int size = size();
		Object[] ret = new Object[size];
		for (int i = 0;i < size;i++) {
			ret[i] = get(i);
		}
		return ret;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T[] toArray(T[] a) {
		for (int i = 0;i < a.length;i++) {
			a[i] = (T) get(i);
		}
		return a;
	}

	@Override
	public boolean add(E e) {
		return stringCachedObjectFactory.createObject(cache, makeKey(size() + 1), e) != null;
	}

	@Override
	public boolean remove(Object o) {
		throw new UnsupportedOperationException("StringCachedList.remove on " + key);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		throw new UnsupportedOperationException("StringCachedList.containsAll on " + key);
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		c.forEach(v -> add(v));
		return true;
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		rangeCheckForAdd(index);
		move(index, c.size());
		int i = 0;
		for (E e : c) {
			put(index + (i++), e);
		}
		return true;
	}
	
	private void move(int start, int length) {
		int size = size();
		for (int i = size - 1;i >= start;i--) {
			cacheKey.rename(makeKey(i), makeKey(i + length));
		}
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		throw new UnsupportedOperationException("StringCachedList.removeAll on " + key);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException("StringCachedList.retainAll on " + key);
	}

	@Override
	public void clear() {
		keys().forEach(key -> cacheKey.delete(key));
	}

	@Override
	public E get(int index) {
		rangeCheckForAdd(index);
		return get(makeKey(index));
	}
	
	private E get(String key) {
		return stringCachedObjectFactory.createObject(cache, key, clz);
	}
	
	protected String makeKey(int index) {
		return key + index;
	}

	@Override
	public E set(int index, E element) {
		rangeCheckForAdd(index);
		E e = get(index);
		put(index, element);
		return e;
	}
	
	private void put(int index, E element) {
		stringCachedObjectFactory.createObject(cache, makeKey(index), element);
	}

	@Override
	public void add(int index, E element) {
		rangeCheckForAdd(index);
		move(index, 1);
		put(index, element);
	}

	@Override
	public E remove(int index) {
		rangeCheckForAdd(index);
		E e = get(index);
		move(index, -1);
		return e;
	}

	@Override
	public int indexOf(Object o) {
		throw new UnsupportedOperationException("StringCachedList.indexOf on " + key);
	}

	@Override
	public int lastIndexOf(Object o) {
		throw new UnsupportedOperationException("StringCachedList.lastIndexOf on " + key);
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
		rangeCheckForAdd(fromIndex);
		rangeCheckForAdd(toIndex);
		List<E> sub = newArrayListWithCapacity(toIndex - fromIndex);
		for (int i = fromIndex;i < toIndex;i++) {
			sub.add(get(i));
		}
		return sub;
	}
	
	private class CachedIterator implements Iterator<E> {
		
		protected int index = -1;

		@Override
		public boolean hasNext() {
			return has(1);
		}
		
		protected boolean has(int v) {
			return cacheKey.exists(makeKey(index + v));
		}

		@Override
		public E next() {
			return get(++index);
		}
		
	}
	
	private class CachedListIterator extends CachedIterator implements ListIterator<E> {
		
		public CachedListIterator(int index) {
			this.index = index - 1;
		}
		
		public CachedListIterator() {
			this(-1);
		}

		@Override
		public boolean hasPrevious() {
			return index == 0 ? false : has(-1);
		}

		@Override
		public E previous() {
			return index == 0 ? null : get(--index);
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
			StringCachedList.this.remove(index);
		}

		@Override
		public void set(E e) {
			StringCachedList.this.set(index, e);
		}

		@Override
		public void add(E e) {
			StringCachedList.this.add(index, e);
		}
		
	}

}
