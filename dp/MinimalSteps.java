package fgafa.dp;

/**
 *
 * This problem was asked by PagerDuty.
 *
 * Given a positive integer N, find the smallest number of steps it will take to reach 1.
 *
 * There are two kinds of permitted steps:
 *
 * You may decrement N to N - 1.
 * If a * b = N, you may decrement N to the larger of a and b.
 * For example, given 100, you can reach 1 in five steps with the following route: 100 -> 10 -> 9 -> 3 -> 2 -> 1.
 *
 * Thoughts:
 *   Define S(100) as the minimal steps from 100 t0 1.
 *   S(100) = 1 + Math.min(S99), S(10))   // 100 = 5 * 5 * 2 * 2
 *   S(10) = 1 + Math.min(S(9), S(5))
 *
 */

public class MinimalSteps {

    //memoization algorithm
    public int minSteps(int n){
        if(n < 2){
            return 0;
        }

        //todo
        return -1;

    }

}
