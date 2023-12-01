/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package companyTag.airbnb;

/** 
 * Implement a queue data structure queue with a fixed size array of length k.similar with CircularQueue;
 * 
 * Followup: 
 *   thread safe, 
 *
 * @param <V> 
 */
public class QueueWithFixedSizeOfArray<V> {
    Object[] datas; 
    int head; // the index of head
    int tail; // the index of tail's next
    
    
    public QueueWithFixedSizeOfArray(int fixedSize){
        datas = new Object[fixedSize];
        
        head = 0;
        tail = 0;
    }


    public boolean add(V element) {
        if(isFull()){
            return false;
        }
        
        datas[tail] = element;
        tail = (tail + 1) % datas.length;
        return true;
    }

    //@Override
    public V poll() {
        if(isEmpty()){
            return null;
        }
        
        Object element = datas[head];
        head = (head + 1) % datas.length;
        return (V)element;
    }

    //@Override
    public V peek() {
        if(isEmpty()){
            return null;
        }
        
        return (V)datas[head];
    }
    
    public boolean isEmpty(){
        return head == tail;
    } 
    
    public boolean isFull(){
        return head == (tail + 1) % datas.length;
    }
    
    public int size(){
        return (tail - head + datas.length) % datas.length;
    }
}
