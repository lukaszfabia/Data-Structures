package Lists;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class TwoWayCycledOrderedListWithSentinel<E extends Comparable<E>> implements MyList<E> {

    private class Element {
        public Element(E e) {
            this.object = e;
        }

        public Element(E e, Element next, Element prev) {
            this.object = e;
            this.next = next;
            this.prev = prev;
        }

        // add element e after this
        public void addAfter(Element elem) {
            elem.next = this.next;
            elem.prev = this;
            this.next.prev = elem;
            this.next = elem;
        }

        public void remove() {
            assert this != sentinel;
            this.prev.next = this.next;
            this.next.prev = this.prev;
        }

        E object;
        Element next = null;
        Element prev = null;
    }


    Element sentinel;
    private int size;

    private class InnerIterator implements Iterator<E> {
        Element pos;

        public InnerIterator() {
            pos = sentinel.next;
        }

        @Override
        public boolean hasNext() {
            return pos != sentinel;
        }

        @Override
        public E next() {
            E value = pos.object;
            pos = pos.next;
            return value;
        }
    }

    private class InnerListIterator implements ListIterator<E> {
        Element pos;

        public InnerListIterator() {
            this.pos = sentinel;
        }

        @Override
        public boolean hasNext() {
            return pos.next != sentinel;
        }

        @Override
        public E next() {
            pos = pos.next;
            return pos.object;
        }

        @Override
        public void add(E arg0) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean hasPrevious() {
            return pos != sentinel;
        }

        @Override
        public int nextIndex() {
            throw new UnsupportedOperationException();
        }

        @Override
        public E previous() {
            E value = pos.object;
            pos = pos.prev;
            return value;
        }

        @Override
        public int previousIndex() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void set(E arg0) {
            throw new UnsupportedOperationException();
        }
    }


    public TwoWayCycledOrderedListWithSentinel() {
        this.sentinel = new Element(null);
        clear();
    }

    @Override
    public boolean add(E e) {
        Element currElement = sentinel.next;
        while (currElement != sentinel && e.compareTo(currElement.object) >= 0) {
            currElement = currElement.next;
        }

        Element newElement = new Element(e, currElement, currElement.prev);
        currElement.prev.next = newElement;
        currElement.prev = newElement;
        size++;
        return true;
    }


    private Element getElement(int index) throws NoSuchElementException {
        if (size == 0 || index < 0) {
            throw new NoSuchElementException();
        }

        Element actElem = sentinel.next;
        while (actElem != sentinel && index > 0) {
            actElem = actElem.next;
            index--;
        }
        if (actElem == sentinel) {
            throw new NoSuchElementException();
        }

        return actElem;
    }


    private Element getElement(E obj) throws NoSuchElementException {
        Element actElem = sentinel.next;
        while (actElem != sentinel && !obj.equals(actElem.object)) {
            actElem = actElem.next;
        }
        if (actElem == sentinel) {
            throw new NoSuchElementException();
        }
        return actElem;
    }


    @Override
    public void add(int index, E element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        this.sentinel.next = sentinel;
        this.sentinel.prev = sentinel;
        this.size = 0;
    }

    @Override
    public boolean contains(E element) {
        return indexOf(element) != -1;
    }

    @Override
    public E get(int index) {
        return getElement(index).object;
    }

    @Override
    public E set(int index, E element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int indexOf(E element) {
        int index = 0;
        Element actElem = sentinel.next;

        while (actElem != sentinel && !actElem.object.equals(element)) {
            actElem = actElem.next;
            index++;
        }

        if (actElem == sentinel) {
            return -1;
        }

        return index;
    }


    @Override
    public boolean isEmpty() {
        return sentinel.next == sentinel;
    }

    @Override
    public Iterator<E> iterator() {
        return new InnerIterator();
    }

    @Override
    public ListIterator<E> listIterator() {
        return new InnerListIterator();
    }

    @Override
    public E remove(int index) throws NoSuchElementException {
        if (size == 0 || index < 0) {
            throw new NoSuchElementException();
        }
        Element actElem = sentinel.next;
        while (actElem != sentinel && index > 0) {
            actElem = actElem.next;
            index--;
        }
        if (actElem == sentinel) {
            throw new NoSuchElementException();
        }

        E value = actElem.object;

        actElem.remove();

        size--;
        return value;
    }

    @Override
    public boolean remove(E e) {
        if (size == 0) {
            return false;
        }

        Element actElem = sentinel.next;
        while (actElem != sentinel && !e.equals(actElem.object)) {
            actElem = actElem.next;
        }

        if (actElem == sentinel) {
            return false;
        }

        actElem.remove();
        size--;
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    public void add(TwoWayCycledOrderedListWithSentinel<E> other) {
        int secondListSize = other.size;
        if (secondListSize != 0 && other != this) {
            size = size + secondListSize;
            Element actElemFirstList = this.sentinel.next;
            while (secondListSize > 0) {
                if (actElemFirstList == this.sentinel) {
                    actElemFirstList.prev.next = other.sentinel.next;
                    other.sentinel.next.prev = actElemFirstList.prev;
                    other.sentinel.prev.next = this.sentinel;
                    this.sentinel.prev = other.sentinel.prev;
                    secondListSize = 0;
                } else {
                    Comparable<E> link = other.sentinel.next.object;
                    if (link.compareTo(actElemFirstList.object) < 0) {
                        actElemFirstList.prev.addAfter(new Element(other.sentinel.next.object, other.sentinel.next.next, other.sentinel));
                        other.sentinel.next.remove();
                        secondListSize--;
                    } else {
                        actElemFirstList = actElemFirstList.next;
                    }
                }
            }
            other.clear();
        }

    }

    public void removeAll(E element) {
        Element actElem = sentinel.next;
        while (actElem!=sentinel && element.compareTo(actElem.object)>=0){
            actElem = actElem.next;
            if (element.compareTo(actElem.prev.object)==0){
                actElem.prev.remove();
                size--;
            }
        }
    }

}
