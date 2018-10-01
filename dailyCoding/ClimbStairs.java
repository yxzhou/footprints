package fgafa.dailyCoding;

/**
 *
 * There exists a staircase with N steps, and you can climb up either 1 or 2 steps at a time. Given N, write a function that returns the number of unique ways you can climb the staircase.
 * The order of the steps matters.

 For example, if N is 4, then there are 5 unique ways:

 1, 1, 1, 1
 2, 1, 1
 1, 2, 1
 1, 1, 2
 2, 2

 What if, instead of being able to climb 1 or 2 steps at a time, you could climb any number from a set of positive integers X? For example, if X = {1, 3, 5}, you could climb 1, 3, or 5 steps at a time.
 *
 * Tags, Amazon, DP
 */

public class ClimbStairs {

    /** f(n) = f(n-1) + f(n-2)*/
    public int uniqueWays_dp(int n){
        int f0 = 0;
        int f1 = 1;
        int next;
        for(int i = 2; i <= n; i++){
            next = f0 + f1;
            f0 = f1;
            f1 = next;
        }

        return f1;
    }

    /** f(n) = f(n - options[0]) + -- + f(n - options[m - 1])*/
    public int uniqueWays_dp(int n, int[] options){
        int[] results = new int[n + 1]; //default all are 0

        results[0] = 1;

        for(int i = 1; i <= n; i++){
            for(int j = 0; j < options.length; j++){
                if(i >= options[j]){
                    results[i] += results[i - options[j]];
                }
            }
        }

        return results[n];
    }


}
