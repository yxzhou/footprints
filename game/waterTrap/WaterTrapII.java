package game.waterTrap;

import junit.framework.Assert;
import util.Misc;

/**
 * _https://www.lintcode.com/problem/383
 * 
 * Given n non-negative integers a1, a2, ..., an, where each represents a point at coordinate (i, ai). 
 * n vertical lines are drawn such that the two endpoints of line i is at (i, ai) and (i, 0). 
 * Find two lines, which together with x-axis forms a container, such that the container contains the most water.
 *
 * Note: You may not slant the container.
 * 
 * e.g:
 * input                    expected    
 * [1,1]                    1   
 * [1,2]                    1   
 * [2,1]                    1   
 * [0,2]                    0                   
 * [2,0]                    0                   
 * [1,2,1]                  2      
 * [1,2,4,3]                4   [2,4,3]                     
 * [3,2,1,3]                9      
 * [4,3,2,1,4]              16     
 * [2,3,10,5,7,8,9]         36  [10,5,7,8,9]   
 * [1,2,3,4,5,6,7,8,9,10]   25  [5,6,7,8,9,10]   
 * [10,9,8,7,6,5,4,3,2,1]   25  [10,9,8,7,6,5]
 *
 */

public class WaterTrapII {

    /*
     * Time O(n)
     *
     * @param heights: a vector of integers
     * @return: an integer
     */
    public int maxArea(int[] heights) {
        if(heights == null || heights.length < 2){
            return 0;
        }

        int max = 0;
        int min;
        for(int l = 0, r = heights.length - 1; l < r; ){
            min = Math.min(heights[l], heights[r]);
            max = Math.max(max, min * (r - l));

            while(l < r && min >= heights[l]){
                l++;
            }
            while(l < r && min >= heights[r]){
                r--;
            }
        }

        return max;
    }
  
  
    /**
     * @param args
     */
    public static void main(String[] args) {
        WaterTrapII sv = new WaterTrapII();

        int[][][] inputs = {
            //{test-case, expertation}
            {{1, 1}, {1}},
            {{1, 2}, {1}},
            {{2, 1}, {1}},
            {{0, 2}, {0}},
            {{2, 0}, {0}},
            {{1, 2, 1}, {2}},
            {{1, 2, 4, 3}, {4}},
            {{3, 2, 1, 3}, {9}},
            {{4, 3, 2, 1, 4}, {16}},
            {{2, 3, 10, 5, 7, 8, 9}, {36}},
            {{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, {25}},
            {{10, 9, 8, 7, 6, 5, 4, 3, 2, 1}, {25}},
            {{4, 4, 2, 11, 0, 11, 5, 11, 13, 8}, {55}}
        };

        for (int[][] input : inputs) {
            System.out.println(String.format("\n Input the elevation map: %s", Misc.array2String(input[0])));

            Assert.assertEquals(input[1][0], sv.maxArea(input[0]));

        }
    }

}
