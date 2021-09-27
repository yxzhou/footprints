package basic.iterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Design and implement an iterator to flatten a 2d vector. It should support the following operations: next and hasNext.


 Example:
 Vector2D iterator = new Vector2D([[1,2],[3],[4]]);

 iterator.next(); // return 1
 iterator.next(); // return 2
 iterator.next(); // return 3
 iterator.hasNext(); // return true
 iterator.hasNext(); // return true
 iterator.next(); // return 4
 iterator.hasNext(); // return false


 Notes:
 Please remember to RESET your class variables declared in Vector2D, as static/class variables are persisted across multiple test cases. Please see here for more details.
 You may assume that next() call will always be valid, that is, there will be at least a next element in the 2d vector when next() is called.


 Follow up:

 As an added challenge, try to code it using only iterators in C++ or iterators in Java.
 *
 */

public class Vector2DIII {

    Iterator<List<Integer>> outer;
    Iterator<Integer> inner = null;

    public Vector2DIII(int[][] v) {
        if(v == null){
            throw new IllegalArgumentException("It should not input a NULL. v == null");
        }

        List<List<Integer>> vector2D = new ArrayList<>(v.length);
        for(int[] arr : v){
            if(arr == null){
                continue;
            }

            List<Integer> vector1D = new ArrayList<>(arr.length);
            vector2D.add(vector1D);

            for(int n : arr){
                vector1D.add(n);
            }
        }

        outer = vector2D.iterator();
    }

    public int next() {
        if(hasNext()){
            return inner.next();
        }

        return -1; //error code
    }

    public boolean hasNext() {
        while((inner == null || !inner.hasNext()) && outer.hasNext()){
            inner = outer.next().iterator();
        }

        return inner != null && inner.hasNext();
    }
}

/**
 * Your Vector2D object will be instantiated and called as such:
 * Vector2D obj = new Vector2D(v);
 * int param_1 = obj.next();
 * boolean param_2 = obj.hasNext();
 */
