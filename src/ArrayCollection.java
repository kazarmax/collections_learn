import java.util.*;

public class ArrayCollection<T> implements Collection<T> {

    private T[] myElementsArray = (T[])new Object[1];
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
    public boolean contains(Object o) {

        for (int i = 0; i < this.size(); i++) {
            if (myElementsArray[i].equals(o)) return true;
        }

        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayIterator();
    }

    @Override
    public Object[] toArray() {
        final Object[] colArray = new Object[this.size()];
        System.arraycopy(myElementsArray, 0, colArray, 0, this.size());
        return colArray;
    }

    @Override
    public <T> T[] toArray(T[] a) {

        if (a.length < size)
            return (T[]) Arrays.copyOf(myElementsArray, size, a.getClass());

        System.arraycopy(myElementsArray, 0, a, 0, size);

        if (a.length > size)
            a[size] = null;

        return a;
    }

    @Override
    public boolean add(T t) {

        if (myElementsArray.length == this.size()) {
            final Object[] oldArray = myElementsArray;
            myElementsArray = (T[])new Object[this.size() * 2];
            System.arraycopy(oldArray, 0, myElementsArray, 0, oldArray.length);
        }
        myElementsArray[size++] = t;

        return true;
    }

    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < this.size(); i++) {
             if (myElementsArray[i].equals(o)) {
                if (i == this.size() - 1) {
                    myElementsArray[i] = null;
                    size--;
                    return true;
                } else {
                    System.arraycopy(myElementsArray, i + 1, myElementsArray, i, this.size() - i - 1);
                    size--;
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {

        for (final Object item : c) {
            if (!this.contains(item))
                return false;
        }

        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        for (Object item : c) {
            add((T)item);
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        for (final Object item : c) {
            remove(item);
        }
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        for (final Object item : this) {
            if (!c.contains(item)) {
                this.remove(item);
            }
        }
        return true;
    }

    @Override
    public void clear() {
        myElementsArray = (T[])new Object[1];
        this.size = 0;
    }

    private class ArrayIterator implements Iterator {

        private int current = 0;
        private int last = -1;

        @Override
        public boolean hasNext() {
            return ArrayCollection.this.size() > current;
        }

        @Override
        public Object next() {
            if (!hasNext()) {throw new NoSuchElementException();}
            last = current;
            return ArrayCollection.this.myElementsArray[current++];
        }

        @Override
        public void remove() {

            if (last == -1)
                throw new IllegalStateException();

            ArrayCollection.this.remove(last);
            size--;
            last = -1;

        }


    }

}



