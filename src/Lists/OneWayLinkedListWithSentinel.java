package Lists;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class OneWayLinkedListWithSentinel<E> implements MyList<E> {
    private int size;

    private class Element {
        public Element(E e) {
            this.object = e;
        }

        E object;
        Element next = null;
    }

    Element sentinel;

    private class InnerIterator implements Iterator<E> {
        Element actual;

        public InnerIterator() {
            actual = sentinel.next;
        }

        @Override
        public boolean hasNext() {
            return actual != null;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more elements to show");
            }
            E object = actual.object;
            actual = actual.next;
            return object;
        }
    }

    public OneWayLinkedListWithSentinel() {
        this.size = 0;
        sentinel = new Element(null);
        sentinel.next = null;
    }

    @Override
    public Iterator<E> iterator() {
        return new InnerIterator();
    }

    @Override
    public ListIterator<E> listIterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(E e) {
        Element newElement = new Element(e);
        Element current = sentinel;
        while (current.next != null) {
            current = current.next;
        }
        current.next = newElement;
        size++;
        return true;
    }

    @Override
    public void add(int index, E element) throws NoSuchElementException {
        if (index > size || index < 0) {
            throw new NoSuchElementException("Wrong index");
        }
        Element newElement = new Element(element);
        Element current = sentinel;

        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        newElement.next = current.next;
        current.next = newElement;
        size++;
    }

    @Override
    public void clear() {
        size = 0;
        sentinel.next = null;
    }

    @Override
    public boolean contains(E element) {
        for (E e : this) {
            if (e.equals(element)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public E get(int index) throws NoSuchElementException {
        if (index >= size || index < 0) {
            throw new NoSuchElementException("Wrong index");
        }

        Element element = sentinel.next;

        for (int i = 0; i < index; i++) {
            element = element.next;
        }

        return element.object;
    }

    @Override
    public E set(int index, E element) throws NoSuchElementException {
        if (index >= size || index < 0) {
            throw new NoSuchElementException("Wrong index");
        }

        Element current = sentinel.next;

        for (int i = 0; i < index; i++) {
            current = current.next;
        }

        E oldObject = current.object;
        current.object = element;

        return oldObject;
    }

    @Override
    public int indexOf(E element) {
        int ind = 0;
        for (E e : this) {
            if (e.equals(element)) {
                return ind;
            }
            ind++;
        }
        return -1;
    }

    @Override
    public boolean isEmpty() {
        return sentinel.next == null;
    }

    @Override
    public E remove(int index) throws NoSuchElementException {
        if (index >= size || index < 0) {
            throw new NoSuchElementException("Wrong index");
        }
        Element current = sentinel;

        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        E elementToRemove = current.next.object;
        current.next = current.next.next;
        size--;
        return elementToRemove;
    }

    @Override
    public boolean remove(E e) {
        Element current = sentinel.next;
        Element previous = sentinel;
        while (current != null) {
            if (current.object.equals(e)) {
                previous.next = current.next;
                size--;
                return true;
            }
            previous = current;
            current = current.next;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }
}
