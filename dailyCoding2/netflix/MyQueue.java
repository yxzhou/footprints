package dailyCoding2.netflix;


/**
 *
 * This problem was asked by Netflix.
 *
 * Implement a queue using a set of fixed-length arrays.
 *
 * The queue should support enqueue, dequeue, and get_size operations.
 *
 */

public class MyQueue {

    int size;
    int[] arr;
    int head;
    int tail;

    public MyQueue(int capacity){
        this.arr = new int[capacity];

        head = 0;
        tail = 0;

        size = 0;
    }

    public void enqueue(int value){
        if(isFull()){
            doubleCapacity();
        }

        tail = (tail + 1) % arr.length ;
        arr[tail] = value;
        size++;
    }

    public int dequeue(){
        int result = arr[head];

        head = (head + 1) % arr.length;
        size--;

        return result;
    }

    public int getSize(){
        return size;
    }

    private boolean isEmpty(){
        return size == 0;
    }

    private boolean isFull(){
        return size == arr.length;
    }

    private void doubleCapacity(){
        assert isFull();

        int[] newArr = new int[arr.length * 2];

        if(head < tail){
            System.arraycopy(arr, head, newArr, 0,   tail - head + 1);
        }else{ //head > tail
            int end = arr.length - head;
            System.arraycopy(arr, head, newArr, 0, end);
            System.arraycopy(arr, 0, newArr, end,tail + 1);
        }

        this.arr = newArr;
        head = 0;
        tail = size;
    }
}
