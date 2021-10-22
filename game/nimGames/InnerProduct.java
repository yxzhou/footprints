/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.nimGames;

import junit.framework.Assert;

/**
 * _https://www.lintcode.com/problem/26
 * 
 *  Given an array A with length N and an array B with length K.
 *  You can choose K numbers from array A with the following rules:
 *      select from two side, (leftmost or rightmost)
 *      each number can select once
 * 
 *  Define C with K elements as you selected, what's the max inner product, 
 *    B * C = (B[0]*C[0] + B[1]*C[1] + ... + B[K - 1]*C[K - 1]) 
 * 
 *  Notes:
 *    1 <= K <= N <= 2000
 *    1 <= A[i], B[i] <= 100000
 * 
 *  Example 1:
 * 
 *  Input: 
 *    A = [2,3,5,1]
 *    B = [2,1]
 *  Output: 7
 *  Explanation: 
 *    step 1, select A0, C=[2], The reason why A2 can't be taken out directly is that A0, A1 and A3 are not taken out.
 *    step 2, select A1, C=[2,3]
 *  B · C = 2 * 2 + 1 * 1 = 7
 * 
 */
public class InnerProduct {
    /**
     * DFS + memorization search
     * 
     * @param A
     * @param B
     * @return 
     */
    public long getMaxInnerProduct(int[] A, int[] B) {

        long[][] cache = new long[B.length][B.length];
        boolean[][] visited = new boolean[B.length][B.length];

        helper(A, 0, B, 0, cache, visited);

        return cache[0][0];
    }

    //n - 1 - ar = bi - al
    private long helper(int[] A, int ai, int[] B, int bi, long[][] cache, boolean[][] visited) {
        if (bi == B.length) {
            return 0;
        }

        if (visited[ai][bi]) {
            return cache[ai][bi];
        }

        visited[ai][bi] = true;

        cache[ai][bi] = Math.max(
                (long)B[bi] * A[ai] + helper(A, ai + 1, B, bi + 1, cache, visited),  //note the (long) 
                (long)B[bi] * A[A.length - 1 - bi + ai] + helper(A, ai, B, bi + 1, cache, visited)
        );

        return cache[ai][bi];
    }
    
    /**
     * Continue on the dfs + memorization search, 
     * 
     * f[0][0] = max( B[0]*A[0] + f[1][1], B[0]*A[n - 1] + f[0][1] )
     * 
     * f[i][j] = max( B[j]*A[i] + f[i + 1][j + 1], B[j]*A[n - 1 - j + i] + f[i][j + 1] )
     * 
     * f[i][k - 1] = max( B[k - 1]*A[i] + f[i + 1][k], B[k - 1]*A[n - 1 - j + i] + f[i][k] )
     * init f[i][k] = 0, i is [0, k]
     * 
     * @param A
     * @param B
     * @return 
     */
    public long getMaxInnerProduct_dp(int[] A, int[] B) {

        long[][] f = new long[B.length + 1][B.length + 1];

        int n = A.length;
        int k = B.length;
        for(int j = k - 1; j >= 0; j-- ){
            for(int i = j; i >= 0; i--){
                f[i][j] = Math.max((long)A[i]* B[j] + f[i + 1][j + 1], (long)A[n - 1 - j + i] * B[j] + f[i][j + 1] );
            }
        }
        
        return f[0][0];
    }
    
    /**
     * continue on the getMaxInnerProduct_dp
     * 
     *   init:  f[k][k]  f[k-1][k]  f[k-2][k] ... f[1][k]  f[0][k]
     *                 \      |   \      |               \    |
     *                 f[k-1][k-1] f[k-2][k-1]  f[1][k-1] f[0][k-1]
     * 
     * @param A
     * @param B
     * @return 
     */
    public long getMaxInnerProduct_dp_n(int[] A, int[] B) {
        long[] f = new long[B.length + 1];

        int n = A.length;
        int k = B.length;
        for(int j = k - 1; j >= 0; j-- ){
            for(int i = 0; i <= j; i++){
                f[i] = Math.max((long)A[i]* B[j] + f[i + 1], (long)A[n - 1 - j + i] * B[j] + f[i] );
            }
        }
        
        return f[0];
    }
    
    
    public static void main(String[] args){
        InnerProduct sv = new InnerProduct();
        
        Assert.assertEquals(7, sv.getMaxInnerProduct(new int[]{2, 3, 5, 1}, new int[]{2, 1}));
        Assert.assertEquals(38, sv.getMaxInnerProduct(new int[]{1, 4, 3, 5, 2}, new int[]{1, 2, 3, 4}));

        Assert.assertEquals(7, sv.getMaxInnerProduct_dp(new int[]{2, 3, 5, 1}, new int[]{2, 1}));
        Assert.assertEquals(38, sv.getMaxInnerProduct_dp(new int[]{1, 4, 3, 5, 2}, new int[]{1, 2, 3, 4}));
        
        Assert.assertEquals(7, sv.getMaxInnerProduct_dp_n(new int[]{2, 3, 5, 1}, new int[]{2, 1}));
        Assert.assertEquals(38, sv.getMaxInnerProduct_dp_n(new int[]{1, 4, 3, 5, 2}, new int[]{1, 2, 3, 4}));
    }
}
