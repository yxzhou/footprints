/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.palindrome;

import java.util.ArrayList;
import java.util.List;
import junit.framework.Assert;

/**
 *_https://www.lintcode.com/problem/745
 * 
 * A positive integer is a palindrome if its decimal representation (without leading zeros) is a palindromic string (a
 * string that reads the same forwards and backwards). For example, the numbers 5, 77, 363, 4884, 11111, 12121 and
 * 349943 are palindromes.
 *
 * A range of integers is interesting if it contains an even number of palindromes. The range [L, R], with L <= R, is
 * defined as the sequence of integers from L to R (inclusive): (L, L+1, L+2, …, R-1, R). L and R are the range’s first
 * and last numbers.
 * 
 * The range [L1, R1] is a subrange of [L, R] if L <= L1 <= R1 <= R. Your job is to determine how many interesting 
 * subranges of [L, R] there are.
 * 
 * Data guarantee results in the range of int, and will not overflow
 * 
 * Example 1:
 * Input : L = 1, R = 2
 * Output : 1
 * 
 * Example 2:
 * Input : L = 1, R = 7
 * Output : 12
 * 
 * Example 3:
 * Input : L = 88, R = 88
 * Output : 0
 * 
 * Example 4:
 * Input : L = 87, R = 87
 * Output : 1
 * 
 * Example 5:
 * Input : L = 87, R = 88
 * Output : 1
 * 
 * Example 6:
 * Input : L = 12, R = 13
 * Output : 3
 * 
 * Example 7:
 * Input : L = 12, R = 14
 * Output : 6
 * 
 * Example 8:
 * Input : L = 12, R = 15
 * Output : 10
 */
public class PalindromicRanges {
    /**
     * @param L: A positive integer
     * @param R: A positive integer
     * @return  the number of interesting subranges of [L,R]
     */
    public int PalindromicRanges(int L, int R) {
        List<Integer> counts = new ArrayList<>();
        int prePal = L - 1;
        for(int x = L; x <= R; x++){
            if( isPal(x) ){
                if(x - prePal > 1){
                    counts.add(x - prePal - 1);
                }
                prePal = x;
                counts.add(0);
            }
        }

        if(R > prePal){
            counts.add(R - prePal);
        }

        int sum = 0;
        for(int i = 0, m = counts.size(); i < m; i++){
            int left = counts.get(i);

            if(left > 0){ //case, it contains 0 palindromes. e.g. {12,13,14}, it's 6 interesting subranges 
                sum += left * (left + 1) / 2;
            }

            int zeroNum = (left == 0? 1 : 0);
            int right;
            for(int j = i + 1; j < m; j++){
                right = counts.get(j);
                zeroNum += (right == 0? 1 : 0);

                if( (zeroNum & 1 ) == 0 ){ // case, it contains even number of palindromes
                    sum += (left == 0? 1 : left) * (right == 0? 1 : right);
                }
            }
        }

        return sum;
    }

    private boolean isPal(int x){
        String s = Integer.toString(x);

        for(int left = 0, right = s.length() - 1; left < right; left++, right-- ){
            if(s.charAt(left) != s.charAt(right)){
                return false;
            }
        }

        return true;
    }
    
    public static void main(String[] args){
        
        int[][] inputs = {
            {1, 2, 1},
            {1, 7, 12},
            {88, 88, 0},
            {87, 87, 1},
            {87, 88, 1},
            {12, 13, 3},
            {12, 14, 6},
            {12, 15, 10},
            {1, 13, 43}
        };
        
        PalindromicRanges sv = new PalindromicRanges();
        
        for(int i = 0; i < inputs.length; i++){
            System.out.println(String.format("-%d- L: %d, \tR: %d, \tExpects: %d ", i, inputs[i][0], inputs[i][1], inputs[i][2]));
            
            Assert.assertEquals(inputs[i][2], sv.PalindromicRanges(inputs[i][0], inputs[i][1]));
            
        }
    }
    
}
