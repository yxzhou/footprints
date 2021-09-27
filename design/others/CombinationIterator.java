package design.others;

import org.junit.Assert;

/**
 * Leetcode #1286
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
 * iterator.next(); // returns "ba"  ( note, it's "bc" in @CombinationIterator2.class )
 * iterator.hasNext(); // returns false
 *
 *
 * Constraints:
 *
 * 1 <= combinationLength <= characters.length <= 15
 * There will be at most 10^4 function calls per test.
 * It's guaranteed that all calls of the function next are valid.
 *
 */

public class CombinationIterator {

    int n;
    char[] origin; // from 1 to n

    int k;

    int[] state;
    boolean finished;

    public CombinationIterator(String characters, int combinationLength) {
        n = characters.length();

        origin = new char[n + 1] ;
        System.arraycopy(characters.toCharArray(), 0, origin, 1, n);

        k = combinationLength;

        finished = false;
    }

    public String next() {

        //It's guaranteed that all calls of the function next are valid
        //if(!hasNext()){
        //    throw new IllegalAccessException();
        //}

        if(state == null){
            state = new int[k + 1]; // from 1 to k

            for(int i = 1; i <= k; i++){
                state[i] = i;
            }

        } else{
            boolean[] used = new boolean[n + 1];
            for(int i = k; i > 0; i--){
                used[state[i]] = true;
            }

            outer: for(int i = k; i > 0; i--){
                used[state[i]] = false;

                for(int j = state[i] + 1; j <= n; j++){
                    if(!used[j]){
                        state[i] = j;
                        used[j] = true;

                        for(int p = i + 1, v = 1; p <= k; ){
                            if(!used[v]){
                                state[p] = v;

                                p++;
                            }

                            v++;
                        }

                        break outer;
                    }
                }
            }
        }

        StringBuilder result = new StringBuilder();
        for(int i = 1; i <= k; i++){
            result.append( origin[state[i]] );
        }

        return result.toString();
    }

    public boolean hasNext() {
        if(finished){
            return false;
        }

        if(state == null){
            return true;
        }

        boolean[] used = new boolean[n + 1];
        for(int i = k; i > 0; i--){
            used[state[i]] = true;
        }

        for(int i = k; i > 0; i--){
            used[state[i]] = false;

            for(int j = state[i] + 1; j <= n; j++){
                if(!used[j]){
                    return true;
                }
            }
        }

        finished = true;
        return false;
    }


    public static void main(String[] args){

        CombinationIterator sv = new CombinationIterator("abc", 2);

        //["CombinationIterator","next","hasNext","next","hasNext","next","hasNext"]
        //[["abc",2],[],[],[],[],[],[]]

        Assert.assertEquals("ab", sv.next());

        Assert.assertTrue(sv.hasNext());

        Assert.assertEquals("ac", sv.next());

        Assert.assertTrue(sv.hasNext());

        Assert.assertEquals("ba", sv.next());

        Assert.assertTrue(sv.hasNext());

    }

}
