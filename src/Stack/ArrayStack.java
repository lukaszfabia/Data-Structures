package Stack;

public class ArrayStack<E> implements MyStack<E> {
    private final static int CAPACITY = 16;
    private final E[] array;
    private int pos;

    public ArrayStack(int size) {
        @SuppressWarnings("unchecked")
        E []array = (E[]) new Object[size];
        this.array= array;
        this.pos = -1;
    }

    public ArrayStack() {
        this(CAPACITY);
        this.pos = -1;
    }


    @Override
    public boolean isEmpty() {
        return pos == -1;
    }

    @Override
    public boolean isFull() {
        return pos == array.length;
    }

    @Override
    public int size() {
        return pos + 1;
    }

    @Override
    public void push(E element) {
        array[++pos] = element;
    }

    @Override
    public int search(Object o) {
        int i = 0;
        for (E e : array) {
            if (e.equals(o)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    @Override
    public E peek() {
        return array[pos];
    }

    @Override
    public E pop() {
        E removed = array[pos];
        array[pos] = null;
        pos--;
        return removed;
    }

    @Override
    public boolean contains(Object o) {
        return search(o) != -1;
    }

    @Override
    public E set(int index, E element) {
        E old = array[index];
        array[index] = element;
        return old;
    }

    @Override
    public E get(int index) {
        return array[index];
    }

}
