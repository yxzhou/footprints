/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

import junit.framework.Assert;

/**
 * _https://www.lintcode.com/problem/867
 *
 * Imagine you have a special keyboard with the following keys:
 *
 * Key 1: (A): Print one 'A' on screen. 
 * Key 2: (Ctrl-A): Select the whole screen. 
 * Key 3: (Ctrl-C): Copy selection to buffer. 
 * Key 4: (Ctrl-V): Print buffer on screen appending it after what has already been printed. 
 * 
 * Now, you can only press the keyboard for N times (with the above four keys), find out the maximum numbers of 'A' you 
 * can print on screen.
 *
 * Notes:
 * 1 <= N <= 50 
 * Answers will be in the range of 32-bit signed integer.
 *
 * Example 1: 
 * Input: 3 
 * Output: 3 
 * Explanation: A, A, A
 *
 * Example 2: 
 * Input: 7 
 * Output: 9 
 * Explanation: A, A, A, Ctrl A, Ctrl C, Ctrl V, Ctrl V
 * 
 * Thoughts:
    N.  maxA    Explanation
    1,   1
    2.   2 
    3    3 
    4.   4      f[3] + 1
    5,   5      f[4] + 1
    6,   6      max( f[3] * 2, f[2] * 3, )
    7,   9      max( f[4] * 2, f[3] * 3, f[4] * 4 ) 
    8,  12      max( f[5] * 2, f[4] * 3, f[3] * 4 )
    9,  16      max( f[6] * 2, f[5] * 3, f[4] * 4, f[3] * 5)
   10,  20      max( f[7] * 2, f[6] * 3, f[5] * 4, f[4] * 5)
   11.  27      max( f[8] * 2, f[7] * 3, f[6] * 4, f[5] * 5)
   12.  36      max( f[9] * 2, f[8] * 3, f[7] * 4, f[6] * 5)   

   so f(n) =  max( f(n - 3) * 3 ,  f(n - 4) * 3, - - - )
 *
 */
public class FourKeysKeyboard {
    
    /**
     * 
     * @param N: press the keyboard for N times
     * @return the maximum number
     */
    public int maxA(int N) {
        int[] f = new int[N + 1];

        for(int i = 1; i <= Math.min(6, N); i++){
            f[i] = i;
        }

        int local;
        for(int i = 7; i <= N; i++){
            for(int j = i - 3; j > 2; j-- ){
                local = f[j] * (i - j - 1);

                 if(local >= f[i]){
                    f[i] = local;
                 } else {
                     break;
                 }
            }
        }

        return f[N];
    }
    
    public int maxA_n(int N) {
        int[] f = new int[N + 1];

        for(int i = 1, end = Math.min(6, N); i <= end; i++){
            f[i] = i;
        }

        for(int i = 7; i <= N; i++){
            for(int k = 2; k < 5; k++ ){
                f[i] = Math.max(f[i], f[i - k - 1] * k);
            }
        }

        return f[N];
    }
    
    
    public static void main(String[] args){
        int[][] inputs = {
            //{n, expect}
            {5, 5},
            {6, 6},
            {7, 9},
            {8, 12},
            {9, 16},
            {10, 20},
            {11, 27},
            {12, 36},
            {13, 48},
            {14, 64},
            {15, 81},
            {16, 108},
            {17, 144},
            {18, 192},
            {19, 256},
            {20, 324},
            {21, 432},
            {22, 576},
            {23, 768},
            {24, 1024},
            {25, 1296},
            {26, 1728},
            {27, 2304},
            {28, 3072},
            {29, 4096},
            {30, 5184},
            {31, 6912},
            {32, 9216},
            {33, 12288},
            {34, 16384},
            {35, 20736},
            {36, 27648},
            {37, 36864},
            {38, 49152},
            {39, 65536},
            {40, 82944},
            {41, 110592},
            {42, 147456},
            {43, 196608},
            {44, 262144},
            {45, 331776},
            {46, 442368},
            {47, 589824},
            {48, 786432},
            {49, 1048576},
            {50, 1327104},
        };
        
        FourKeysKeyboard sv = new FourKeysKeyboard();
        
        for(int[] input : inputs){
            System.out.println(String.format("n = %d, expect: \n-- %d \n-- %d \n-- %d", input[0], input[1], sv.maxA(input[0]), sv.maxA_n(input[0]) ));
            
            Assert.assertEquals(input[1], sv.maxA(input[0]));
            Assert.assertEquals(input[1], sv.maxA_n(input[0]));
        }
    }
}
