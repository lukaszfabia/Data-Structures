package Lists;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class OneWayLinkedListWithHead<E> implements MyList<E> {
    private class Element {
        Element next = null;
        E object;

        public Element(E e) {
            this.object = e;
        }
    }
    private class InnerIterator implements Iterator<E> {

        private Element curr;

        public InnerIterator() {
            this.curr = head;
        }
        @Override
        public boolean hasNext() {
            return curr!=null;
        }

        @Override
        public E next() {
            if (!hasNext()){
                throw new NoSuchElementException();
            }
            E element = curr.object;
            curr=curr.next;
            return element;
        }
    }

    private class InnerListIterator implements ListIterator<E> {
        private Element curr;

        public InnerListIterator() {
            this.curr = head;
        }

        @Override
        public boolean hasNext() {
            return curr != null;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            E next = curr.object;
            curr = curr.next;
            return next;
        }

        @Override
        public boolean hasPrevious() {
            throw new UnsupportedOperationException();
        }

        @Override
        public E previous() {
            throw new UnsupportedOperationException();
        }

        @Override
        public int nextIndex() {
            throw new UnsupportedOperationException();
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
        public void set(E e) {
            curr.object=e;
        }

        @Override
        public void add(E e) {
            throw new UnsupportedOperationException();
        }
    }

    private Element head;
    private int size;

    public OneWayLinkedListWithHead() {
        this.head = null;
        this.size = 0;
    }

    public ListIterator<E> listIterator() {
        return new InnerListIterator();
    }

    public Iterator<E> iterator(){
        return new InnerIterator();
    }

    @Override
    public boolean add(E e) {
        Element element = new Element(e);
        Element curr = head;
        if (head == null) {
            head = element;
            size++;
            return true;
        }

        while (curr.next != null) {
            curr = curr.next;
        }

        curr.next = element;
        size++;
        return true;
    }

    @Override
    public void add(int index, E element) {
        Element newElement = new Element(element);
        if (index == 0) {
            //newElement.next = head;
            head = newElement;
            size++;
            return;
        }

        Element curr = head;
        for (int i = 0; i < index - 1; i++) {
            curr=curr.next;
        }
        newElement.next = curr.next;
        curr.next = newElement;
        size++;
    }

    @Override
    public E set(int index, E element) {
        InnerListIterator iterator = new InnerListIterator();

        for (int i = 0; i < index; i++) {
            iterator.next();
        }
        E changed = iterator.curr.object;
        iterator.set(element);
        return changed;
    }


    @Override
    public E get(int index) {
        ListIterator<E> iterator = listIterator();
        int i=0;

        while(iterator.hasNext() && i<index){
            iterator.next();
            i++;
        }
        return iterator.next();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public E remove(int index) {
        Element curr = head;
        E removed;
        if (index == 0) {
            removed = head.object;
            head = head.next;
            size--;
            return removed;
        }
        for (int i = 0; i < index - 1; i++) {
            curr = curr.next;
        }
        removed = curr.next.object;
        curr.next = curr.next.next;
        size--;
        return removed;
    }

    @Override
    public boolean remove(E element) {
        if (head == null) {
            return false;
        }

        if (head.object.equals(element)) {
            head = head.next;
            size--;
            return true;
        }

        Element curr = head;
        while (curr.next != null && !curr.next.object.equals(element)) {
            curr = curr.next;
        }

        assert curr.next != null;
        curr.next = curr.next.next;
        size--;
        return true;
    }

    @Override
    public boolean contains(E element) {
        return indexOf(element) != -1;
    }

    @Override
    public int indexOf(E element) {
        ListIterator<E> iterator = listIterator();
        int i=0;
        while(iterator.hasNext()){
            if (iterator.next().equals(element)){
                return i;
            }
            i++;
        }
        return -1;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    @Override
    public void clear() {
        this.head = null;
    }

    public void removeAll(E element) {
        if (head == null) {
            return;
        }

        Element curr = head;
        while (curr!=null){
            if (curr.object.equals(element)){
                remove(curr.object);
            }
            curr=curr.next;
        }
    }
}
