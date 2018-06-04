package fgafa.basic.Iterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
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

    int count;
    List<Iterator> iter;
 
    public ZigzagIterator(List<Integer> v1, List<Integer> v2) {
        this.count = 0;
        
        this.iter = new ArrayList<Iterator>();
        if (null != v1 && !v1.isEmpty()){
            iter.add(v1.iterator());
        }

        if (null != v2 && !v2.isEmpty()){
            iter.add(v2.iterator());
        }

    }
 
    public int next() {
        int x = (Integer)iter.get(count).next();
        
        if (iter.get(count).hasNext()){
            count++;
        }else{
            iter.remove(count);
        }

        if (iter.size()!=0){
            count = count % iter.size();  //**
        }
        
        return x;
    }
 
    public boolean hasNext() {
        return iter.size()!=0;
    }
}
 
/**
 * Your ZigzagIterator object will be instantiated and called as such:
 * ZigzagIterator i = new ZigzagIterator(v1, v2);
 * while (i.hasNext()) v[f()] = i.next();
 */
