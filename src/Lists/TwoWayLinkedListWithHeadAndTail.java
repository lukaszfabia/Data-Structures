package Lists;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class TwoWayLinkedListWithHeadAndTail <E> implements MyList<E>{
    private class Element{
        public Element(E e) {
            this.object = e;
        }
        public Element(E e, Element next, Element prev) {
            this.object = e;
            this.next = next;
            this.prev = prev;
        }
        E object;
        Element next=null;
        Element prev=null;
    }

    Element head;
    Element tail;
    int size;

    private class InnerIterator implements Iterator<E> {
        Element pos;
        public InnerIterator() {
            pos = head;
        }
        @Override
        public boolean hasNext() {
            return pos!=null;
        }

        @Override
        public E next() {
            E value = pos.object;
            pos = pos.next;
            return value;
        }
    }

    private class InnerListIterator implements ListIterator<E>{
        Element p;
        boolean isTail = false;

        public InnerListIterator (){
            this.p=head;
        }

        @Override
        public void add(E e) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean hasNext() {
            return p!=null;
        }

        @Override
        public boolean hasPrevious() {
            if (p!=null && p.prev!=null) {
                return true;
            }
            return isTail;
        }

        @Override
        public E next() {
            if (p==tail || (p==head && tail==null)) {
                isTail = true;
            }
            E value = p.object;
            p=p.next;
            return value;
        }

        @Override
        public int nextIndex() {
            throw new UnsupportedOperationException();
        }

        @Override
        public E previous() {
            E value;
            if (isTail){
                if (tail!=null){
                    value= tail.object;
                    p=tail;
                } else {
                    value = head.object;
                    p=head;
                }
                isTail=false;
                return value;
            }
            p=p.prev;
            value = p.object;
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
        public void set(E e) {
            p.object = e;
        }
    }

    public TwoWayLinkedListWithHeadAndTail(){
        this.head=null;
        this.tail=null;
        this.size=0;
    }

    @Override
    public boolean add(E e) {
        if (head==null){
            head=new Element(e);
            size++;
            return true;
        }

        if (tail==null){
            tail=new Element(e, null, head);
            head.next=tail;
            size++;
            return true;
        }

        Element newElement = new Element(e, null, tail);
        tail.next=newElement;
        tail=newElement;
        size++;
        return true;
    }

    @Override
    public void add(int index, E element) throws NoSuchElementException {
        if (index < 0 || index > size) {
            throw new NoSuchElementException();
        }

        Element newEle = new Element(element);
        if (size == 0) {
            head = tail = newEle;
        } else if (index == 0) {
            newEle.next = head;
            head.prev = newEle;
            head = newEle;
        } else if (index == size) {
            tail.next = newEle;
            newEle.prev = tail;
            tail = newEle;
        } else {
            Element current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }
            newEle.next = current.next;
            current.next.prev = newEle;
            current.next = newEle;
            newEle.prev = current;
        }
        size++;
    }

    @Override
    public void clear() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    @Override
    public boolean contains(E element) {
        return indexOf(element) != -1;
    }

    @Override
    public E get(int index) throws NoSuchElementException {
        return getElement(index).object;
    }

    @Override
    public E set(int index, E element) throws NoSuchElementException {
        Element curr = getElement(index);
        E value = curr.object;
        curr.object=element;
        return value;
    }

    private Element getElement(int index) {
        if (index < 0 || index >= size) {
            throw new NoSuchElementException();
        }
        Element curr = head;
        while (index>0) {
            index--;
            curr = curr.next;
        }
        return curr;
    }

    @Override
    public int indexOf(E element) {
        if (!isEmpty()) {
            int index = 0;
            Element nextEl = head;

            for (E e : this) {
                if (e.equals(element)) {
                    return index;
                }
                index++;
                nextEl = nextEl.next;
            }
        }
        return -1;
    }

    @Override
    public boolean isEmpty() {
        return this.size==0;
    }

    @Override
    public Iterator<E> iterator() {
        return new InnerIterator();
    }

    @Override
    public ListIterator<E> listIterator() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public E remove(int index) throws NoSuchElementException {
        Element actElem = head;
        while (index>0 && actElem!=null){
            actElem = actElem.next;
            index--;
        }
        if (actElem==null || index<0) {
            throw new NoSuchElementException();
        }

        E value;
        if (actElem.next!=null){
            if (actElem==head){
                value = head.object;
                if (actElem.next==tail){
                    tail.prev=null;
                    head=tail;
                    tail=null;
                } else {
                    head = head.next;
                    head.prev=null;
                }
            } else {
                value=actElem.object;
                actElem.prev.next=actElem.next;
                actElem.next.prev=actElem.prev;
            }
        } else {
            if (actElem==head){
                value=head.object;
                head=null;
            }
            else {
                value= tail.object;
                if (actElem.prev==head){
                    tail=null;
                    head.next=null;
                } else {
                    actElem.prev.next=null;
                    tail = actElem.prev;
                }
            }
        }
        size--;
        return value;
    }

    @Override
    public boolean remove(E e) {
        Element actElem = head;
        while (actElem!=null && !e.equals(actElem.object)){
            actElem = actElem.next;
        }
        if (actElem==null)
            return false;

        if (actElem.next!=null){
            if (actElem==head){
                if (actElem.next==tail){
                    tail.prev=null;
                    head=tail;
                    tail=null;
                } else {
                    head = head.next;
                    head.prev=null;
                }
            } else {
                actElem.prev.next=actElem.next;
                actElem.next.prev=actElem.prev;
            }
        } else {
            if (actElem==head){
                head=null;
            }
            else {
                if (actElem.prev==head){
                    tail=null;
                    head.next=null;
                } else {
                    actElem.prev.next=null;
                    tail = actElem.prev;
                }
            }
        }
        size--;
        return true;
    }

    @Override
    public int size() {
        return this.size;
    }

    public void add(TwoWayLinkedListWithHeadAndTail<E> other) {
        if (other!=this && other.head!=null){
            if (head==null){
                head=other.head;
            } else {
                if (tail == null) {
                    head.next = other.head;
                } else {
                    tail.next = other.head;
                    other.head.prev = tail;
                }
            }
            this.size+=other.size;
            this.tail=other.tail;

            other.clear();
        }
    }
}
