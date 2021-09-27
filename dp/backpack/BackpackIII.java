/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dp.backpack;

/**
 * _https://www.lintcode.com/problem/440/
 * 
 * Given n kinds of items, and each kind of item has an infinite number available. The i_th item has size A[i] and value V[i], 
 * Also give a backpack with size m. What's the maximum value can you put into the backpack?
 *
 * Note
 * You cannot divide item into small pieces.
 * The total size of items you choose should smaller or equal to m.
 *
 * Example 1
 * Input: A = [2, 3, 5, 7] and V = [1, 5, 2, 4], m = 10.
 * Output: 15
 * Explanation: Put three item 1 (A[1] = 3, V[1] = 5 ) into backpack.
 * 
 * Example 2
 * Input: A = [1, 2, 3] and V = [1, 2, 3], m = 5.
 * Output: 5
 * Explanation: Strategy is not unique. E.g. put five item 0 (A[0] = 1, V[0] = 1 ) into backpack.
 * 
 * Challenge
 * O(n x m) memory is acceptable, can you do it in O(m) memory?
 *
 */
public class BackpackIII {
    public int backPackIII(int[] A, int[] V, int m) {
        if(m < 1 || A == null || A.length == 0 || V == null || V.length == 0 ){
            return 0;
        }

        int[] max = new int[m + 1]; // max[i] is the max value with size i exactly
        max[0] = 1;

        for(int size = 1; size <= m; size++){
            for(int i = 0; i < A.length; i++){
                if(size >= A[i] && max[size - A[i]] > 0){
                    max[size] = Math.max(max[size], max[size - A[i]] + V[i] );
                }
            }
        }

        int result = 0;
        for( ; m > 0; m--){
            result = Math.max(result, max[m]);
        }

        return result == 0 ? 0 : result - 1;
    }
    
    /* fastest */
    public int backPackIII_n(int[] A, int[] V, int m) {
        if(m < 1 || A == null || A.length == 0 || V == null || V.length == 0 ){
            return 0;
        }

        int[] max = new int[m + 1]; // max[i] is the max value with size i exactly
        max[0] = 1;

        int next;
        for(int i = 0; i < A.length; i++){
           for(int size = 0, end = m - A[i]; size <= end; size++){
                if(max[size] > 0){
                    next = size + A[i];
                    max[next] = Math.max(max[next], max[size] + V[i] );
                }
            }
        }

        int result = 0;
        for( ; m > 0; m--){
            result = Math.max(result, max[m]);
        }

        return result == 0 ? 0 : result - 1;
    }
    
}
