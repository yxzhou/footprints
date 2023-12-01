/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package companyTag.airbnb;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/** 
 * blocking Queue with Lock 
 *
 * @param <V> 
 */
public class QueueWithFixedSizeOfArray2<V> {
    Object[] datas; 
    int head; // the index of head
    int tail; // the index of tail's next
    
    static final Lock LOCK = new ReentrantLock();
    static final Condition CONDITIONPUT = LOCK.newCondition();
    static final Condition CONDITIONPOLL = LOCK.newCondition();
    
    public QueueWithFixedSizeOfArray2(int fixedSize){
        datas = new Object[fixedSize];
        
        head = 0;
        tail = 0;
    }


    public void add(V element) {
        LOCK.lock();
        try{
            while(isFull()){
                CONDITIONPUT.awaitUninterruptibly();
            }
        
            datas[tail] = element;
            tail = (tail + 1) % datas.length;
            
            //CONDITIONPOLL.signal();
            CONDITIONPOLL.signalAll();

        }finally {
            LOCK.unlock();
        }
    }

    //@Override
    public V poll() {
        LOCK.lock();
        try{
            while( isEmpty() ){
                CONDITIONPOLL.awaitUninterruptibly();
            }
        
            Object element = datas[head];
            head = (head + 1) % datas.length;
        
            //CONDITIONPUT.signal();
            CONDITIONPUT.signalAll();

            return (V)element;
        }finally {
            LOCK.unlock();
        }

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
