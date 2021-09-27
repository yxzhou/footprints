package basic.iterator;

import org.junit.Assert;

import java.util.Arrays;
import java.util.Iterator;

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

public class Vector2DIII2 {
    Iterator<Integer> iter;

    public Vector2DIII2(int[][] v) {

        iter = Arrays.asList(v)
                .stream()
                .flatMapToInt(i -> Arrays.stream(i))
                .iterator();
    }

    public int next() {
        return iter.next();
    }

    public boolean hasNext() {
        return iter.hasNext();
    }


    public static void main(String[] args){
        Vector2DIII2 iterator = new Vector2DIII2(new int[][]{{1, 2}, {3}, {4}});

        Assert.assertEquals(1, iterator.next()); // return 1
        Assert.assertEquals(2, iterator.next()); // return 2
        Assert.assertEquals(3, iterator.next()); // return 3
        Assert.assertEquals(true, iterator.hasNext()); // return true
        Assert.assertEquals(true, iterator.hasNext()); // return true
        Assert.assertEquals(4, iterator.next()); // return 4
        Assert.assertEquals(false, iterator.hasNext()); // return false
    }
}

/**
 * Your Vector2D object will be instantiated and called as such:
 * Vector2D obj = new Vector2D(v);
 * int param_1 = obj.next();
 * boolean param_2 = obj.hasNext();
 */
