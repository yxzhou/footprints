package fgafa.util.random;

import org.junit.Test;

import java.util.*;
import java.util.Random;

/**
 * Leetcode #710
 *
 * Given a blacklist B containing unique integers from [0, N), write a function to return a uniform random integer from [0, N) which is NOT in B.
 *
 * Optimize it such that it minimizes the call to system’s Math.random().
 *
 * Note:
 *
 * 1 <= N <= 1000000000
 * 0 <= B.length < min(100000, N)
 * [0, N) does NOT include N. See interval notation.
 * Example 1:
 *
 * Input:
 * ["Solution","pick","pick","pick"]
 * [[1,[]],[],[],[]]
 * Output: [null,0,0,0]
 * Example 2:
 *
 * Input:
 * ["Solution","pick","pick","pick"]
 * [[2,[]],[],[],[]]
 * Output: [null,1,1,1]
 * Example 3:
 *
 * Input:
 * ["Solution","pick","pick","pick"]
 * [[3,[1]],[],[],[]]
 * Output: [null,0,0,2]
 * Example 4:
 *
 * Input:
 * ["Solution","pick","pick","pick"]
 * [[4,[2]],[],[],[]]
 *
 * Output: [null,1,3,1]
 * Explanation of Input Syntax:
 *
 * The input is two lists: the subroutines called and their arguments. Solution's constructor has two arguments, N and the blacklist B. pick has no arguments. Arguments are always wrapped with a list, even if there aren't any.
 *
 */

public class RandomPickWithBlacklist {

    @Test public void test(){


        solution(3, new int[]{0});

        for(int i = 0; i < 10; i++){
            System.out.println(pick());
        }

    }


    /***** *****
     *
     * Time O(B) construction, O(1) for pick(), Space O(B)
     *
     * Example
     *  1) {N=5, B={1, 2}}
     *   remains  N - B.length = 3,  {0, 1, 2} -> {0, 3, 4}
     *
     *  2) {N=5, B={1}}
     *   remains N - B.length = 4,   {0, 1, 2, 3} -> {0, 5, 3, 4}
     *  
     *
     */
    int n;
    int m = 0;
    Map<Integer, Integer> map = new HashMap<>();

    Random r = new Random();

    public void solution(int N, int[] blacklist) {
        this.n = N;

        Set<Integer> from = new HashSet<>();
        for(int b : blacklist){
            from.add(b);
        }

        this.m = this.n - from.size();

        Set<Integer> to = new HashSet<>();
        for(int i = m; i < N; i++){
            to.add(i);
        }

        for( int i = 0; i < blacklist.length; i++){
            if(blacklist[i] >= m){
                from.remove(blacklist[i]);
                to.remove(blacklist[i]);
            }
        }

        Iterator<Integer> iteraFrom = from.iterator();
        Iterator<Integer> iteraTo = to.iterator();
        while(iteraFrom.hasNext() && iteraTo.hasNext()){
            map.put(iteraFrom.next(), iteraTo.next());
        }

    }

    public int pick() {
        //if(m < ){
        //    return 0;
        //}
        int x = r.nextInt(m);

        return map.getOrDefault(x, x);
    }


}
