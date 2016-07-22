package main;

import java.util.Arrays;
import java.util.List;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.ListIterator;
import java.util.Iterator;

public class LinkedList<T> implements List<T> {

    private Item<T> first = null;

    private Item<T> last = null;

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
        // BEGIN (write your solution here)
        for (Item<T> x = first; x != null; x = x.next) {
            if (x.element.equals(o))
                return true;
        }
        return false;
        // END
    }

    @Override
    public Iterator<T> iterator() {
        return new ElementsIterator();
    }

    @Override
    public Object[] toArray() {
        // BEGIN (write your solution here)
        T[] newArray = (T[])new Object[this.size()];
        int i = 0;
        for (Item<T> x = first; x != null; x = x.next) {
            newArray[i++] = x.getElement();
        }
        // END
        return newArray;
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        // BEGIN (write your solution here)
        if (a.length < this.size()) {
            a = (T1[])java.lang.reflect.Array.newInstance(
                    a.getClass().getComponentType(), size);
        }

        int i = 0;
        for (Item<T> x = first; x != null; x = x.next) {
            a[i++] = (T1)x.getElement();
        }

        if (a.length > this.size()) {
            a[this.size()] = null;
        }

        return a;
        // END
    }

    @Override
    public boolean add(final T t) {
        // BEGIN (write your solution here)
        if (isEmpty()) {
            final Item<T> newItem = new Item<>(t, null, null);
            first = newItem;
            last = newItem;
            size++;

        } else {
            final Item<T> prevItem = last;
            final Item<T> newItem = new Item<>(t, prevItem, null);
            prevItem.next = newItem;
            last = newItem;
            size++;
        }

        return true;
        // END
    }

    @Override
    public boolean remove(final Object o) {
        // BEGIN (write your solution here)

        //if list is empty
        if (isEmpty()) {
            return false;
        }

        //if there is only one element in the list
        if (this.size() == 1) {
            if (first.getElement().equals(o)){
                first = null;
                last = null;
                size--;
                return true;
            }
            return false;
        }

        //if element to remove is first
        if (first.getElement().equals(o)) {
            final Item<T> nextElement = first.next;
            first.next = null;
            nextElement.prev = null;
            first = nextElement;
            size--;
            return true;
        }

        //if element to remove is last
        if (last.getElement().equals(o)) {
            final Item<T> prevElement = last.prev;
            last.prev = null;
            prevElement.next = null;
            last = prevElement;
            size--;
            return true;
        }

        for (Item<T> x = first; x != null; x = x.next) {
            if (x.getElement().equals(o)) {
                final Item<T> elementToRemove = x;
                final Item<T> nextElement = x.next;
                final Item<T> prevElement = x.prev;

                elementToRemove.next = null;
                elementToRemove.prev = null;

                prevElement.next = nextElement;
                nextElement.prev = prevElement;

                size--;
                return true;
            }
        }

        return false;
        // END
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
            System.out.println("I'm here");
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
        // BEGIN (write your solution here)
        for (Item<T> x = first; x != null; ) {
            final Item<T> nextElement = x.next;
            x.element = null;
            x.next = null;
            x.prev = null;
            x = nextElement;
        }
        first = null;
        last = null;
        size = 0;
        // END
    }

    @Override
    public T remove(final int index) {
        // BEGIN (write your solution here)
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }

        if (index == 0) {
            final T removedItem = first.getElement();
            remove(removedItem);
            return removedItem;
        }

        if (index == this.size() - 1) {
            final T removedItem = last.getElement();
            remove(removedItem);
            return removedItem;
        }

        int i = 1;
        for (Item<T> x = first.next; x != last; x = x.next) {
            if (i == index) {
                final T removedItem = x.getElement();
                remove(removedItem);
                return removedItem;
            }
            i++;
        }

        return null;
        // END
    }

    // BEGIN (write your solution here)

    // END
    @Override
    public List<T> subList(final int start, final int end) {
        return null;
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
        throw new UnsupportedOperationException();
    }

    @Override
    public int indexOf(final Object target) {
        // BEGIN (write your solution here)
        int i = 0;
        for (Item<T> x = first; x != null; x = x.next) {
            if (x.getElement().equals(target)) {
                return i;
            }
            i++;
        }
        return -1;
        // END
    }

    @Override
    public void add(final int index, final T element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(final int index, final Collection elements) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T set(final int index, final T element) {
        // BEGIN (write your solution here)
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }

        //if element to set is first
        if (index == 0) {
            final T oldElement = first.getElement();
            first.element = element;
            return oldElement;
        }

        //if element to set is last
        if (index == this.size() - 1) {
            final T oldElement = last.getElement();
            last.element = element;
            return oldElement;
        }

        int i = 1;
        for (Item<T> x = first.next; x != last; x = x.next) {
            if (i == index) {
                final T oldElement = x.getElement();
                x.element = element;
                return oldElement;
            }
            i++;
        }

        return null;
        // END
    }

    @Override
    public T get(final int index) {
        // BEGIN (write your solution here)
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }
        int i = 0;
        for (Item<T> x = first; x != null; x = x.next) {
            if (i == index) {
                return x.getElement();
            }
            i++;
        }
        return null;
        // END
    }

    // BEGIN (write your solution here)

    // END

    private class ElementsIterator implements ListIterator<T> {

        private Item<T> current;

        private Item<T> last;

        public ElementsIterator() {
            this(0);
        }

        public ElementsIterator(final int index) {
            // BEGIN (write your solution here)
            if  (index < 0 || index > size())
                throw new IndexOutOfBoundsException();

            int i = 0;
            for (Item<T> x = first; x != null; x = x.next) {
                if (i == index) {
                    this.current = x;
                }
                i++;
            }
            // END
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            // BEGIN (write your solution here)
            System.out.println("In next() start");
            System.out.println("Index of current = " + current);
            System.out.println("Index of last = " + indexOf(last));
            if (!hasNext())
                throw new NoSuchElementException();

            last = current;
            current = current.next;
            System.out.println("In next() end");
            System.out.println("Index of current = " + indexOf(current));
            System.out.println("Index of last = " + indexOf(last));
            return last.getElement();
            // END
        }

        @Override
        public void add(final T element) {
            if (last == null)
                throw new IllegalStateException();
            LinkedList.this.add(element);
            last = null;
        }

        @Override
        public void set(final T element) {
            // BEGIN (write your solution here)
            if (last == null)
                throw new IllegalStateException();
            last.element = element;
            // END
        }

        @Override
        public int previousIndex(){
            // BEGIN (write your solution here)
            if (current == first)
                return -1;
            return LinkedList.this.indexOf(current) - 1;
            // END
        }

        @Override
        public int nextIndex() {
            // BEGIN (write your solution here)
            return LinkedList.this.indexOf(current);
            // END
        }

        @Override
        public boolean hasPrevious() {
            // BEGIN (write your solution here)
            System.out.println("Index of current = " + indexOf(current));
            System.out.println("Index of last = " + indexOf(last));
            if (indexOf(current) > 0 || indexOf(last) == 0)
                return true;
            return false;
            // END
        }

        @Override
        public T previous() {
            // BEGIN (write your solution here)
            if (!hasPrevious())
                throw new NoSuchElementException();

            last = current = current.prev;
            return last.getElement();
            // END
        }

        @Override
        public void remove() {
            // BEGIN (write your solution here)
            if (last == null)
                throw new IllegalStateException();
            LinkedList.this.remove(last);
            last = null;
            // END
        }

    }

    private static class Item<T> {

        private T element;

        private Item<T> next;

        private Item<T> prev;

        public Item(final T element, final Item<T> prev, final Item<T> next) {
            this.element = element;
            this.next = next;
            this.prev = prev;
        }

        public T getElement() {
            return element;
        }

        public Item<T> getNext() {
            return next;
        }

        public Item<T> getPrev() {
            return prev;
        }

    }

}
