package dailyCoding.datastructure;

/**
 *
 * Implement a stack that has the following methods:
 *
 * push(val), which pushes an element onto the stack
 * pop(), which pops off and returns the topmost element of the stack. If there are no elements in the stack, then it should throw an error or return null.
 * max(), which returns the maximum value in the stack currently. If there are no elements in the stack, then it should throw an error or return null.
 * Each method should run in constant time.
 *
 * Tags: amazon
 *
 */

public class MaxStack<V extends Comparable<V>> {
    Object[] elements;
    Object[] maxElements;

    int size = 0;
    int capacity;

    MaxStack(int capacity){
        this.capacity = capacity;

        elements = new Object[capacity];
        maxElements = new Object[capacity];
    }

    public int size(){
        return this.size;
    }

    public void push( V value){

        if(size == capacity){
            resize();
        }

        elements[size] = value;
        maxElements[size] = value.compareTo((V)maxElements[size - 1]) > 0 ? value : maxElements[size - 1];

        size++;
    }

    public V pop(){
        if( 0 == size){
            return null;
        }

        size--;
        V top = (V)elements[size];

        return top;
    }

    public V max(){
        if( 0 == size ){
            return null;
        }

        size--;
        V top = (V)maxElements[size];

        return top;

    }

    private void resize(){
        int newCapacity = (capacity << 1);
        Object[] newElements = new Object[newCapacity];
        System.arraycopy(elements, 0, newElements, 0, size);

        Object[] newMaxElements = new Object[newCapacity];
        System.arraycopy(maxElements, 0, newMaxElements, 0, size);

        capacity = newCapacity;
        elements = newElements;
        maxElements = newMaxElements;
    }

}
