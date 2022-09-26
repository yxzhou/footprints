package sweepLine;

import org.junit.Assert;
import util.Misc;

/**
 * 
 * LeetCode Q370: Range Addition
 * _https://www.lintcode.com/problem/903
 * 
 * Assume you have an array of length n initialized with all 0's and are given k update operations. Each operation is
 * represented as a triplet: [startIndex, endIndex, inc] which increments each element of subarray A[startIndex ...
 * endIndex](startIndex and endIndex inclusive) with inc. Return the modified array after all k operations were
 * executed.
 *  
 *  Example:
 *  Given: length = 5,
    updates = [
        [1,  3,  2],
        [2,  4,  3],
        [0,  2, -2]
    ]
 * 
 *  Output: [-2, 0, 3, 5, 3]
 *  Explanation:
 *  Initial state: 
 *  [ 0, 0, 0, 0, 0 ]
 *  After applying operation [1, 3, 2]:
    [ 0, 2, 2, 2, 0 ]
 *  After applying operation [2, 4, 3]:
    [ 0, 2, 5, 5, 3 ]
 *  After applying operation [0, 2, -2]:
    [-2, 0, 3, 5, 3 ]
 *  
 *  Thoughts:
 *    define n as the length, m as the length of updates
 *    s1:  simulation
 *       1) init a int array, default all are 0
 *       2) to every update [startIndex, endIndex, inc], update the array from [startIndex, endIndex]
 *       Time complexity O(n * m), Space O(1)
 * 
 *    s2:  sweepLine,  
 *      For each update operation, do you really need to update all elements between i and j? 
 *      Define a int array, to store the changes, changes[i] is the change start from i_th
 * 
 *      Time complexity is O(m + n) and uses O(1) extra space.
 * 
 *
 */

public class RangeAddition {
    
    /**
     * @param length: the length of the array
     * @param updates: update operations
     * @return: the modified array after all k operations were executed
     */
    public int[] getModifiedArray(int length, int[][] updates) {
        if(length <= 0){
            return new int[0];
        }
        
        int[] result = new int[length];

        if(updates == null || updates.length == 0){
            return result;
        }

        int end;
        for(int[] update : updates){
            result[update[0]] += update[2];

            end = update[1] + 1;
            if( end < length){
                result[end] -= update[2];
            }
        }

        for(int i = 1; i < length; i++){
            result[i] += result[i - 1];
        }

        return result;
    }
    
    public static void main(String[] args) {
        int[][][][] inputs = {
            {
                {{5}},
                {
                    {1,  3,  2},
                    {2,  4,  3},
                    {0,  2, -2}
                },
                {{-2, 0, 3, 5, 3}}
            }
        };
        
        RangeAddition sv = new RangeAddition();
        
        for(int[][][] input : inputs){
            System.out.println(String.format("-Input-\nlength = %d, \nupdate: %s, \n ", input[0][0][0], Misc.array2String(input[1]).toString() ));
            
            Assert.assertArrayEquals(input[2][0], sv.getModifiedArray(input[0][0][0], input[1]));
        }
        
    }

}
