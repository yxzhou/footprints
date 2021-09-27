package basic.iterator;

import java.util.Iterator;
import java.util.List;

/**
 *
 * Leetcode #281
 *
 * Given two 1d vectors, implement an iterator to return their elements alternately.

    For example, given two 1d vectors:
    v1 = [1, 2]
    v2 = [3, 4, 5, 6]
    By calling next repeatedly until hasNext returns false, the order of elements returned by next should be: [1, 3, 2, 4, 5, 6].
    
    Follow up: What if you are given k 1d vectors? How well can your code be extended to such cases?
 *
 */

public class ZigzagIterator {

    int state;
    Iterator<Integer>[] iterators;
 
    public ZigzagIterator(List<Integer> v1, List<Integer> v2) {
        this.state = 1;
        
        this.iterators = new Iterator[]{v1.iterator(), v2.iterator()};
    }
 
    public int next() {
       for(int i = 0; i < 2; i++){
            state = (state + 1) % 2;
            if(iterators[state].hasNext()){
                return iterators[state].next();
            }
        }

       //logically it will not be here
        return -1;
    }
 
    public boolean hasNext() {

        for(int i = 0; i < iterators.length; i++){
            if(iterators[i].hasNext()){
                return true;
            }
        }

        return false;
    }
}
 
/**
 * Your ZigzagIterator object will be instantiated and called as such:
 * ZigzagIterator i = new ZigzagIterator(v1, v2);
 * while (i.hasNext()) v[f()] = i.next();
 */
