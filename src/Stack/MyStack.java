package Stack;

import java.util.EmptyStackException;
import java.util.NoSuchElementException;

public interface MyStack<E> {
    boolean isEmpty();
    boolean isFull();
    int size();
    void push(E element);
    int search(Object o);
    E peek() throws EmptyStackException;
    E pop() throws EmptyStackException;
    boolean contains(E o);
    E set(int index, E element) throws NoSuchElementException;
    E get(int index) throws NoSuchElementException;
}
