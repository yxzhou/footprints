package datastructure.heap;

public class MyMaxHeap< E extends Comparable<? super E>> implements java.io.Serializable{

    private static final long serialVersionUID = 745471965446253342L;
    
    private Object[] heap;
    private int capacity;
    private int size;
    
    public MyMaxHeap(int capacity){
        this.capacity = capacity;
        this.heap = new Object[capacity];
    }
    
    private int leftChild(int position){
        return (position << 1) + 1;
    }
    
    private int rightChild(int position){
        return (position << 1) + 2;
    }
    
    private int parent(int position){
        return (position - 1) >> 1;
    }
    
    /*Time O(logn)*/
    public void insert(E value){
        //check if it's full 
        if(size == capacity){
            throw new IllegalArgumentException("The heap is full. ");
            //or double the size
        }
        
        int curr = size;
        size++;
        
        heap[curr] = value;
        shiftUp(curr);
    }
    
    /*Time O(logn)*/
    public E pop(){
        //check if it's empty
        if(size == 0){
            throw new IllegalArgumentException("The heap is empty.");
        }
        
        size--;
        E result = (E)heap[0];
        heap[0] = heap[size];
        shiftDown(0);
        
        return result;
    }
    
    private void shiftUp(int position){
        
        int curr = position;
        int parent;
        while(curr > 0){
            parent = parent(curr);
            if(((E)heap[curr]).compareTo((E)heap[parent]) > 0){
                swap(heap, curr, parent);
                curr = parent;
            }else{
                break;
            }
        }
        
    }
    
    private void shiftDown(int position){
        
        int curr = position;
        int leftChild = leftChild(curr);
        int rightChild;
        int max;

        while(leftChild < size){
            rightChild = leftChild + 1;
            
            max = leftChild;
            if(rightChild < size && ((E)heap[rightChild]).compareTo((E)heap[leftChild]) > 0){
                max = rightChild;
            }
            
            if(((E)heap[curr]).compareTo((E)heap[max]) >= 0){
                break;
            }
            
            swap(heap, curr, max);
            curr = max;
            leftChild = leftChild(curr);
        }
    }
    
    private void swap(Object[] heap, int i, int j){
        Object tmp = heap[i];
        heap[i] = heap[j];
        heap[j] = tmp;
    }
}
