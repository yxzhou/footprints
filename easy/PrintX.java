/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package easy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import util.Misc;

/**
 * Enter a positive integer 'N'. You need to return a list of strings as shown in the Example.
 * 
 * note:
 *   1 <= n <= 15
 * 
 * Example 1:
 * Input: n = 1
 * Output: ["X"]
 * 
 * Example 2:
 * Input: n = 2
 * Output: ["XX", "XX"]
 * Explanation: The answer list can be seen as the following shape:
 * XX
 * XX
 * 
 * Example 3:
 * Input: n = 3
 * Output: ["X X", " X ", "X X"]
 * Explanation: The answer list can be seen as the following shape:
 * X X
 *  X 
 * X X
 *
 * Example 4:
 * Input: n = 4
 * Output: ["X  X", " XX ", " XX ", "X  X"]
 * Explanation: The answer list can be seen as the following shape:
 * X  X 
 *  XX  
 *  XX 
 * X  X
 * 
 * Thoughts:
 * 
 * example n = 3
 *      lineId   X position 
 * X X      0       0 and 2 (  0 and n - 1 )     
 *  X       1       1 and 1     
 * X X      2       2 and 0
 * 
 * example n = 4
 *      lineId   X position 
 * X  X      0       0 and 3 (  0 and n - 1 )     
 *  XX       1       1 and 2     
 *  XX       2       2 and 1 
 * X  X      3       3 and 0
 * 
 * And when n = 2, line 0th and line 2th are symmetry; when n = 4, (line 0th, 1th) and (line 2th, 3th) are symmetry
 * 
 * @author yuanxi
 */
public class PrintX {
    
    /**
     * 
     * 
     * @param n: An integer.
     * @return A string list.
     */
    public List<String> printX(int n) {
        if(n < 1){
            return Collections.EMPTY_LIST;
        }

        List<String> result = new ArrayList<>();

        char[] chars = new char[n];
        Arrays.fill(chars, ' ');

        for(int rowId = 0, mirror = n - 1; rowId < n; rowId++, mirror--){
            chars[rowId] = 'X';
            chars[mirror] = 'X';

            result.add(String.valueOf(chars) );

            chars[rowId] = ' ';
            chars[mirror] = ' ';
        }

        return result;
    }
    
    /**
     * 
     * 
     * @param n: An integer.
     * @return A string list.
     */
    public List<String> printX_1(int n) {
        if(n < 1){
            return Collections.EMPTY_LIST;
        }

        List<String> result = new ArrayList<>();

        char[] chars = new char[n];
        Arrays.fill(chars, ' ');

         int rowId = 0;
         for(int mid = n/2; rowId < mid; rowId++ ){
             chars[rowId] = 'X';
             chars[n - 1 - rowId] = 'X';

             result.add(String.valueOf(chars) );

             chars[rowId] = ' ';
             chars[n - 1 - rowId] = ' ';
         }

         if((n & 1) == 1){
             chars[rowId] = 'X';
             result.add(String.valueOf(chars) );
             chars[rowId] = ' ';
         }

         for( rowId--; rowId >= 0; rowId--){
             result.add(result.get(rowId));
         }

        return result;
    }
    
    
    public static void main(String[] args){
        PrintX sv = new PrintX();
        
        for(int n = 0; n < 6; n++){
            System.out.println(String.format("Input: n = %d", n));
            
            System.out.println("call printX:");
            Misc.printArrayList(sv.printX(n), false, "\n");
            
            System.out.println("call printX_1:");
            Misc.printArrayList(sv.printX_1(n), false, "\n");
        }
        
    }
    
}
