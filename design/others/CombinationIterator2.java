package design.others;

import org.junit.Assert;

/**
 *
 * Design an Iterator class, which has:
 *
 * A constructor that takes a string characters of sorted distinct lowercase English letters and a number combinationLength as arguments.
 * A function next() that returns the next combination of length combinationLength in lexicographical order.
 * A function hasNext() that returns True if and only if there exists a next combination.
 *
 *
 * Example:
 *
 * CombinationIterator iterator = new CombinationIterator("abc", 2); // creates the iterator.
 *
 * iterator.next(); // returns "ab"
 * iterator.hasNext(); // returns true
 * iterator.next(); // returns "ac"
 * iterator.hasNext(); // returns true
 * iterator.next(); // returns "bc"
 * iterator.hasNext(); // returns false
 *
 *
 * Constraints:
 *
 * 1 <= combinationLength <= characters.length <= 15
 * There will be at most 10^4 function calls per test.
 * It's guaranteed that all calls of the function next are valid.
 *
 *
 */

public class CombinationIterator2 {

    int n;
    char[] origin; // valid data is from 1 to n

    int k;
    int[] state; // valid data is from 1 to k
    boolean finished;

    public CombinationIterator2(String characters, int combinationLength) {
        n = characters.length();
        origin = new char[n + 1] ;
        System.arraycopy(characters.toCharArray(), 0, origin, 1, n);

        k = combinationLength;

        state = new int[k + 1]; // from 1 to k

        for(int i = 1; i <= k; i++){
            state[i] = i;
        }

        finished = false;
    }

    public String next() {

        //It's guaranteed that all calls of the function next are valid
        //if(!hasNext()){
        //    throw new IllegalAccessException();
        //}

        StringBuilder result = new StringBuilder();
        for(int i = 1; i <= k; i++){
            result.append( origin[state[i]] );
        }

        //get the next one
        finished = true;
        for(int i = k, v = n; i > 0; i--, v--){
            if(state[i] < v ){
                state[i]++;

                for(int p = i + 1; p <= k; p++){
                    state[p] = state[p - 1] + 1;
                }

                finished = false;
                break;
            }//else state[i] == v
        }

        return result.toString();
    }

    public boolean hasNext() {
        return !finished;
    }


    public static void main(String[] args){

        CombinationIterator2 sv = new CombinationIterator2("abc", 2);

        //["CombinationIterator","next","hasNext","next","hasNext","next","hasNext"]
        //[["abc",2],[],[],[],[],[],[]]

        Assert.assertEquals("ab", sv.next());

        Assert.assertTrue(sv.hasNext());

        Assert.assertEquals("ac", sv.next());

        Assert.assertTrue(sv.hasNext());

        Assert.assertEquals("bc", sv.next());

        //Assert.assertTrue(sv.hasNext());
        Assert.assertFalse(sv.hasNext());

    }

}
