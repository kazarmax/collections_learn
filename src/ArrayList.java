
import java.util.Arrays;
import java.util.List;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.ListIterator;
import java.util.Iterator;

public class ArrayList<T> implements List<T> {

    private T[] m = (T[])new Object[1];

    private int size;

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public boolean contains(final Object o) {
        for (int i = 0; i < size; i++) {
            if (m[i].equals(o)) return true;
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new ElementsIterator();
    }

    @Override
    public Object[] toArray() {
        final T[] newM = (T[])new Object[this.size()];
        System.arraycopy(m, 0, newM, 0, this.size());
        return newM;
    }

    @Override
    public <T1> T1[] toArray(final T1[] a) {
        if (a.length < size)
            return (T1[]) Arrays.copyOf(m, size, a.getClass());

        System.arraycopy(m, 0, a, 0, size);

        if (a.length > size)
            a[size] = null;

        return a;
    }

    @Override
    public boolean add(final T t) {
        if (m.length == size) {
            final T[] oldM = m;
            m = (T[]) new Object[this.size() * 2];
            System.arraycopy(oldM, 0, m, 0, oldM.length);
        }
        m[size++] = t;
        return true;
    }

    @Override
    public boolean remove(final Object o) {
        for (int i = 0; i < size(); i++) {
            if (m[i].equals(o)) {
                this.remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(final Collection<?> c) {
        for (final Object item : c) {
            if (!this.contains(item)) return false;
        }
        return true;
    }

    @Override
    public boolean addAll(final Collection<? extends T> c) {
        for (final T item : c) {
            add(item);
        }
        return true;
    }

    @Override
    public boolean removeAll(final Collection<?> c) {
        for (final Object item : c) {
            remove(item);
        }
        return true;
    }

    @Override
    public boolean retainAll(final Collection<?> c) {
        for (final Object item : this) {
            if (!c.contains(item)) this.remove(item);
        }
        return true;
    }

    @Override
    public void clear() {
        m = (T[])new Object[1];
        size = 0;
    }

    @Override
    public T remove(final int index) {
        final T element = m[index];
        if (index != this.size() - 1)
            System.arraycopy(m, index + 1, m, index, this.size() - index - 1);
        size--;
        return element;
    }

    @Override
    public List<T> subList(final int start, final int end) {
        if (start < 0 || end > size || start > end)
            throw new IndexOutOfBoundsException();
        List<T> list = new ArrayList<>();
        for (int i = start; i < end; i++) {
            list.add(m[i]);
        }
        return list;
    }

    @Override
    public ListIterator listIterator() {
        return new ElementsIterator(0);
    }

    @Override
    public ListIterator listIterator(final int index) {
        return new ElementsIterator(index);
    }

    @Override
    public int lastIndexOf(final Object target) {
        for (int i = size() - 1; i >= 0; i--) {
            if (m[i].equals(target))
                return i;
        }
        return -1;
    }

    @Override
    public int indexOf(final Object target) {
        for (int i = 0; i < size(); i++) {
            if (m[i].equals(target))
                return i;
        }
        return -1;
    }

    @Override
    public void add(final int index, final T element) {
        if (index < 0 || index > size())
            throw new IndexOutOfBoundsException();
        if (m.length == size) {
            final T[] oldM = m;
            m = (T[]) new Object[this.size() * 2];
            System.arraycopy(oldM, 0, m, 0, oldM.length);
        }
        if (index == size()) {
            m[size++] = element;
        } else {
            System.arraycopy(m, index, m, index + 1, size() - index);
            m[index] = element;
            size++;
        }
    }

    @Override
    public boolean addAll(final int index, final Collection elements) {
        int prevSize = size();
        if (index < 0 || index > size())
            throw new IndexOutOfBoundsException();
        Iterator elementsIterator = elements.iterator();
        for (int i = index; i < index + elements.size(); i++) {
            add(i, (T)elementsIterator.next());
        }
        return size() > prevSize;
    }

    @Override
    public T set(final int index, final T element) {
        m[index] = element;
        return element;
    }

    @Override
    public T get(final int index) {
        return m[index];
    }

    private class ElementsIterator implements ListIterator<T> {

        private int index;

        private int last = -1;

        public ElementsIterator() {
            this(0);
        }

        public ElementsIterator(final int index) {
            // BEGIN (write your solution here)
            this.index = index;
            // END
        }

        @Override
        public boolean hasNext() {
            return index < ArrayList.this.size();
        }

        @Override
        public T next() {
            if (!hasNext())
                throw new NoSuchElementException();
            last = index;
            return ArrayList.this.m[index++];
        }

        @Override
        public void add(final T element) {
            if (last == -1)
                throw new IllegalStateException();
            ArrayList.this.add(index, element);
            last = -1;
        }

        @Override
        public void set(final T element) {
            // BEGIN (write your solution here)
            if (last == -1)
                throw new IllegalStateException();
            ArrayList.this.set(last, element);
            // END
        }

        @Override
        public int previousIndex(){
            // BEGIN (write your solution here)
            if (index == 0)
                return -1;
            return index - 1;
            // END
        }

        @Override
        public int nextIndex() {
            // BEGIN (write your solution here)
            return index;
            // END
        }

        @Override
        public boolean hasPrevious() {
            // BEGIN (write your solution here)
            if (index > 0) return true;
            return false;
            // END
        }

        @Override
        public T previous() {
            // BEGIN (write your solution here)
            if (!hasPrevious())
                throw new NoSuchElementException();
            final int prevIndex = previousIndex();
            last = prevIndex;
            index--;
            return ArrayList.this.m[prevIndex];
            // END
        }

        @Override
        public void remove() {
            if (last == -1)
                throw new IllegalStateException();
            ArrayList.this.remove(last);
            index--;
            last = -1;
        }

    }


}
