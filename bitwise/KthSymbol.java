/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bitwise;

import java.util.BitSet;
import org.junit.Assert;

/**
 * _https://www.lintcode.com/problem/809
 *
 * On the first row, we write a 0. Now in every subsequent row, we look at the previous row and replace each occurrence
 * of 0 with 01, and each occurrence of 1 with 10.
 *
 * Given row N and index K, return the K-th indexed symbol in row N. (The values of K are 1-indexed.) (1 indexed).
 *
 * Constraints:
 * N will be an integer in the range [1, 30]. 
 * K will be an integer in the range [1, 2^(N-1)]. 
 * 
 * Example 1:
 * Input： N = 1, K = 1 
 * Output：0 
 * Explanation： 
 * row 1: 0 
 * 
 * Example 2:
 * Input：N = 2, K = 1 
 * Output：0 
 * Explanation： 
 * row 1: 0 
 * row 2: 01 
 * 
 * Example 3:
 * Input：N = 2, K = 2 Output：1 
 * Explanation： 
 * row 1: 0 
 * row 2: 01 
 * 
 * Example 4:
 * Input：N = 4, K = 5 Output：1 
 * Explanation： 
 * row 1: 0 
 * row 2: 01 
 * row 3: 0110 
 * row 4: 01101001
 *
 * Thoughts:
 * --row #  --------------- 0b (from right to left) --------------- 0x (from right to left) ------------ 
 * row 1:                                       0                         
 * row 2:                                      10  
 * row 3:                                    0110                                         6
 * row 4:                               1001 0110                                        96
 * row 5:                     0110 1001 1001 0110                                      6996
 * row 6: 1001 0110 0110 1001 0110 1001 1001 0110                                 9669 6996                                     
 * 
 * so found: 
 *   1)if K < N, f(N, K) == f(K, K), example: f(32, 3) = f(3,3) = 1  ( the third bit of 0b0110 )
 *   2)if k > N, example f(6, 2^5), it's the first bit in row 6, 
 *     in row 6, the first half "1001 0110 0110 1001" and the second half "0110 1001 1001 0110" is symmetrical on the 
 *     half axis 2^(N-2)=2^4
 *     in row 5, the first half "0110 1001" and the second half "1001 0110" is symmetrical on the half axis 2^(N-2)=2^3
 * 
 *     define half = (1 << (N - 2)), 
 *     when K >= half, f(N, K) == !f(N - 1, K - (1 << (N - 2)) )
 * 
 */
public class KthSymbol {
    
    /**
     * simulate with BitSet
     * 
     * @param N: the row
     * @param K: the index
     * @return the K-th indexed symbol in row N
     */
    public int kthGrammar(int N, int K) {
        // N in [1, 30],  K in [1, 2 ^ (N - 1)]

        BitSet bits = new BitSet(K + 3);
        
        int half = K / 2;
        int j;
        int d;
        for(int r = 1; r < N; r++){
            
            j = (1 << (r - 1));
            if(j > half){
                break;
            }

            for( ; j > 0; j--){
                d = (j << 1);
                if(bits.get(j)){
                    bits.set(d - 1, true);  //  1 -> 10
                    bits.set(d, false); 
                    
                }else{
                    bits.set(d - 1, false);  //  0 -> 01
                    bits.set(d, true); 
                }                
            }

        }

        return bits.get(K)? 1 : 0;
    }
    
    public int kthGrammar_n(int N, int K) {
        // N in [1, 30],  K in [1, 2 ^ (N - 1)]
        
        int row = 3;
        int base = 0b0110;
        
        boolean revert = false;
        //N = Math.min(N, K);

        int half ; //axis 
        while(N > row ){
            half = (1 << (N - 2));
            
            if(K >= half ){
                K -= half;
                revert = !revert;
            }
            
            N--;
        }
        
        int result = (base >> (K - 1)) & 1;

        return revert? result ^ 1 : result;
    }
    
    
    public static void main(String[] args){
        
        /** test */
        int x = -2;
        System.out.println( (x >> 31) & 1 );
        
        System.out.println( Integer.toBinaryString(1 << 31) );
        
        
        int[][] inputs = {
            {1, 1, 0},
            {2, 1, 0},
            {2, 2, 1},
            {4, 5, 1},
            {6, 31, 0},
            {6, 32, 1},
            {30, 434991989, 0}
        };
        
        KthSymbol sv = new KthSymbol();
        
        for(int[] input : inputs){
            System.out.println(String.format("\nN = %d, K = %d, expect: %d", input[0], input[1], input[2] ));
            
            Assert.assertEquals(input[2], sv.kthGrammar(input[0], input[1]));
            
            Assert.assertEquals(input[2], sv.kthGrammar_n(input[0], input[1]));
        }
    }
    
}
