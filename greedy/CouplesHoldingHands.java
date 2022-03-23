package greedy;

import java.util.Arrays;
import org.junit.Assert;

/**
 * _https://www.lintcode.com/problem/1043
 * Leetcode #765
 *
 * N couples sit in 2N seats arranged in a row and want to hold hands. We want to know the minimum number of swaps so
 * that every couple is sitting side by side. A swap consists of choosing any two people, then they stand up and switch
 * seats.
 *
 * The people and seats are represented by an integer from 0 to 2N-1, the couples are numbered in order, the first
 * couple being (0, 1), the second couple being (2, 3), and so on with the last couple being (2N-2, 2N-1).
 *
 * The couple's initial seating is given by row[i] being the value of the person who is initially sitting in the i-th
 * seat.
 *
 * Example 1:
 * Input: row = [0, 2, 1, 3]
 * Output: 1
 * Explanation: We only need to swap the second (row[1]) and third (row[2]) person.
 * Example 2:
 *
 * Input: row = [3, 2, 0, 1]
 * Output: 0
 * Explanation: All couples are already seated side by side.
 * 
 * 
 * Note:
 * len(row) is even and in the range of [4, 60].
 * row is guaranteed to be a permutation of 0...len(row)-1.
 *
 * Thoughts{
 *   [a1, a2, b1, b2, c1, c2, d1, d2] 0
 *   [a1, b1, a2, b2, c1, c2, d1, d2] 1 
 *   [a1, b1, c1, b2, a2, c2, d1, d2] 3 
 *   [a1, b1, c1, b2, d1, c2, a2, d2] 4
 *
 *   greedy
 *   [a1, b1, c1, b2, d1, c2, a2, d2] 
 *      -> [a1, a2, c1, b2, d1, c2, b1, d2] vs [b2, b1, c1, a1, d1, c2, a2, d2] is same. algebra replace 'a' with 'b' 
 * 
 */ 

public class CouplesHoldingHands {



    //greedy
    public int minSwapsCouples(int[] row) {
        int n = row.length;

        int[] indexes = new int[n]; //row[i] is index to value, here indexes[i] is value to index
        for(int i = 0; i < n; i++){
            indexes[row[i]] = i;
        }

        int count = 0;
        int x; //define x and y as a couple
        int y;
        for(int i = 0, j = 1; j < n; i +=2, j +=2 ){
            x = row[i];
            y = (x ^ 1); // better than y = x + ((x & 1) == 0? 1 : -1); example: [0 -> 1, 1 -> 0] [2 -> 3, 3 -> 2]

            if(row[j] == y){
                continue;
            }

            count++;

            row[indexes[y]] = row[j];
            indexes[row[j]] = indexes[y];

            //row[j] = y;
            //indexes[y] = j;
        }

        return count;
    }
    
    public static void main(String[] args){
        
        int[][][] inputs = {
            {{0, 2, 1, 3}, {1}},
            {{3, 2, 0, 1}, {0}},
            {{4, 5, 3, 2, 0, 1}, {0}},
            {{4, 0, 3, 2, 1, 5}, {1}},
            {{4, 0, 5, 2, 1, 3}, {2}},
            {{4, 0, 5, 2, 3, 1}, {2}}
        };
        
        CouplesHoldingHands sv = new CouplesHoldingHands();
        
        for(int[][] input : inputs){
            System.out.println(Arrays.toString(input[0]));
            
            Assert.assertEquals(input[1][0], sv.minSwapsCouples(input[0]));
        }
                
    }

}
