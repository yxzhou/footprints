/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bitwise;

import java.util.Stack;
import junit.framework.Assert;

/**
 *
 * A number is Sparse if there are no two adjacent 1s in its binary representation. 
 * Example 5 (binary representation: 101) is sparse, but 6 (binary representation: 110) is not sparse.
 * Given a number n, find the smallest Sparse number which greater than or equal to n. 
 *
 * Example 1: Input：6 Output：8 Explanation： 6 = 110(2) and 8 = 1000(2) 
 * Example 2: Input：13 Output：16 Explanation： 13 = 1101(2) and 16 =10000(2)
 */
public class NextSparseNumber {
    /**
     * @param x: a number
     * @return the next sparse number behind x
     */
    public int nextSparseNum(int x) {

        //boolean sign = (x > 0);
        //only avaible for positive x 
        assert x >= 0;
        Stack<Integer> ones = new Stack<>();

        for(int i = 30, j = 0; i >= 0; i--){
            if( ((x >> i) & 1) == 1 ){
                
                if(!ones.isEmpty() && ones.peek() == i + 1){
                    j = i;
                    while(!ones.isEmpty() && ones.peek() == j + 1 ){
                        j = ones.pop() + 1;
                    }
                    ones.add(j);
                    break;
                }

                ones.add(i);
            }
        }
        
        int result = 0;
        while(!ones.isEmpty()){
            result |= (1 << ones.pop());
        }

        return result;
    }

    // 44 =  101100 
    //      1000000  
    
    public static void main(String[]  args){
        NextSparseNumber sv = new NextSparseNumber();
        
        int[][] inputs = {
            {0, 0},
            {1, 1},
            {6, 8},   //   110 ->    1000
            {11, 16}, //  1011 ->   10000
            {13, 16}, //  1101 ->   10000
            {19, 20}, // 10011 ->   10100
            {44, 64}  //101100 -> 1000000 
        };
        
        System.out.println(System.currentTimeMillis() + " " + inputs.length);
        
        for(int[] input : inputs){
            Assert.assertEquals(input[1], sv.nextSparseNum(input[0]));
        }
        
    }
    
}
