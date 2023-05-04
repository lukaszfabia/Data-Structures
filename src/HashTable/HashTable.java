package HashTable;

import java.util.LinkedList;

public class HashTable<T> implements MyTable<T> {
    LinkedList<T>[] arr;
    private final static int defaultInitSize = 8;
    private final static double defaultMaxLoadFactor = 0.7;
    private int size;
    private final double maxLoadFactor;

    public HashTable() {
        this(defaultInitSize);
    }

    public HashTable(int size) {
        this(size, defaultMaxLoadFactor);
    }


    public HashTable(int initCapacity, double maxLF) {
        if (initCapacity < 2) {
            initCapacity = 2;
        }

        @SuppressWarnings("unchecked")
        LinkedList<T>[] arr = (LinkedList<T>[]) new LinkedList[initCapacity];
        this.arr = arr;
        this.size = 0;
        createLists(this.arr, this.arr.length);
        this.maxLoadFactor = maxLF;
    }


    private void createLists(LinkedList<T>[] arr, int length) {
        for (int i = 0; i < length; i++) {
            arr[i] = new LinkedList<>();
        }
    }

    private int index(T element, int size) {
        return element.hashCode() % size;
    }

    private boolean ifContains(T element) {
        int index = index(element, arr.length);
        for (T o : arr[index]) {
            if (element.equals(o)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean add(T elem) {
        if (ifContains(elem))
            return false;

        int index = index(elem, arr.length);
        arr[index].add(elem);
        size++;

        if (((double) size / arr.length) > maxLoadFactor) {
            doubleArray();
        }

        return true;
    }

    private void doubleArray() {
        int newSize = arr.length * 2;
        @SuppressWarnings("unchecked")
        LinkedList<T>[] newArr = (LinkedList<T>[]) new LinkedList[newSize];
        createLists(newArr, newSize);

        for (LinkedList<T> linkedList : arr) {
            for (T ob : linkedList) {
                int newIndex = index(ob, newSize);
                newArr[newIndex].add(ob);
            }
        }
        arr = newArr;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");

        for (int i = 0; i < arr.length; i++) {
            sb.append(i).append(": [");
            for (T elem : arr[i]) {
                sb.append(elem.toString()).append(", ");
            }
            if (!arr[i].isEmpty()) {
                sb.delete(sb.length() - 2, sb.length());
            }
            sb.append("], ");
        }

        if (size > 0) {
            sb.delete(sb.length() - 2, sb.length());
        }

        sb.append("}");
        return sb.toString();
    }

    @Override
    public T get(T toFind) {
        int index = index(toFind, arr.length);

        for (T o : arr[index]) {
            if (toFind.equals(o))
                return o;
        }
        return null;
    }

}

