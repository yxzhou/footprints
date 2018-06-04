package fgafa.basic.Iterator;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * 
 * Implement an iterator to flatten a 2d vector.

    For example,
    
    Given 2d vector =
    
    [
      [1,2],
      [3],
      [4,5,6]
    ]
    By calling next repeatedly until hasNext returns false, the order of elements returned by next should be: [1,2,3,4,5,6].
 *
 */

public class Vector2D {
    
    Iterator<List<Integer>> it;
    Iterator<Integer> curr;
    
    public Vector2D(List<List<Integer>> vec2d) {
        it = vec2d.iterator();
    }

    public int next() {
        if(hasNext()){
            return curr.next();
        }
        
        return -1; //error code
    }

    public boolean hasNext() {
        
        while((curr == null || !curr.hasNext()) && it.hasNext()){
            curr = it.next().iterator();
        }
        return curr != null && curr.hasNext();
    }
    
}
