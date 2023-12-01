/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package companyTag.airbnb;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;


/** 
 * blocking Queue with Semaphore 
 *
 * @param <V> 
 */
public class QueueWithFixedSizeOfArray3<V> {
    Object[] datas; 
    int head; // the index of head
    int tail; // the index of tail's next
    
    private static final Semaphore sync = new Semaphore(0);
    private static final Semaphore putting = new Semaphore(1);
    private static final Semaphore getting = new Semaphore(0);
    
    public QueueWithFixedSizeOfArray3(int fixedSize){
        datas = new Object[fixedSize];
        
        head = 0;
        tail = 0;
    }


    public void add(V element) throws InterruptedException {
        if(element == null) {
            return;
        }
                
        if(getting.tryAcquire(1, TimeUnit.SECONDS)) {
        
            datas[tail] = element;
            tail = (tail + 1) % datas.length;
            
            getting.release();
            sync.acquire();
        }

    }

    //@Override
    public V poll() throws InterruptedException {


        if(putting.tryAcquire(1, TimeUnit.SECONDS)) {
        
            Object element = datas[head];
            head = (head + 1) % datas.length;
        
            sync.release();
            putting.release();
            
            return (V)element;
        }

        return null;
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
