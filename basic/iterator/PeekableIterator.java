package fgafa.basic.iterator;

/**
 * Given an Iterator class interface with methods: next() and hasNext(),
 * design and implement a PeekableIterator that support the peek() operation -- it essentially peek() at the element that will be returned by the next call to next().

    Here is an example. Assume that the iterator is initialized to the beginning of the list: [1, 2, 3].
    
    Call next() gets you 1, the first element in the list.
    
    Now you call peek() and it returns 2, the next element. Calling next() after that still return 2.
    
    You call next() the final time and it returns 3, the last element. Calling hasNext() after that should return false.
    

    Follow up: How would you extend your design to be generic and work with all types, not just integer?
 */

import java.util.Iterator;

public class PeekableIterator<E> implements Iterator<E> {
    Iterator<E> itr;
    E peekedValue = null;
    boolean hasPeekedValue = false;

    public PeekableIterator(Iterator<E> iterator) {
        this.itr = iterator;
    }

    // Returns the next element in the iteration without advancing the iterator.
    public E peek() {
        if(!hasPeekedValue){
            if(!itr.hasNext()){
                return null;
            }

            peekedValue = itr.next();
            hasPeekedValue = true;
        }
   
        return peekedValue;    
    }

    @Override
    public E next() {
        if(hasPeekedValue){
            E result = peekedValue;
            peekedValue= null;
            hasPeekedValue = false;
            return result;  
        }else{
            if(!itr.hasNext()){
                return null;
            }

            return itr.next();
        }
    }

    @Override
    public boolean hasNext() {
        return hasPeekedValue || itr.hasNext() ;
    }
    
}
