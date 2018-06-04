package fgafa.basic.Iterator;

/**
    
    Follow up: How would you extend your design to be generic and work with all types, not just integer?
 */

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class PeekingIteratorII<E> implements Iterator<E> {
    Iterator<E> itr;
    Queue<E> queue;

    public PeekingIteratorII(Iterator<E> iterator) {
        this.itr = iterator;
        this.queue = new LinkedList<>();
    }

    // Returns the next element in the iteration without advancing the iterator.
    public E peek() {
        if(!hasNext()){
            return null;
        }
        
        if(queue.isEmpty()){
            queue.add(itr.next());
        }
   
        return queue.peek();    
    }

    @Override
    public E next() {
        if(!hasNext()){
            return null;
        }
        
        if(queue.isEmpty()){
            return itr.next();
        }else{
            return queue.poll();    
        }
    }

    @Override
    public boolean hasNext() {
        return !queue.isEmpty() || itr.hasNext() ;
    }
    
}
