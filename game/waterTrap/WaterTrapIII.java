package game.waterTrap;

import junit.framework.Assert;
import util.Misc;

/**
 * 
 * Given n non-negative integers a1, a2, ..., an, where each represents a point at coordinate (i, ai). 
 * n vertical lines are drawn such that the two end-points of line i is at (i, ai) and (i, 0). 
 * To every two lines, which together with x-axis forms a container, such that the container contains the most water.
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
 * [1,2,4,3]                6                     
 * [3,2,1,3]                9      
 * [4,3,2,1,4]              16     
 * [2,3,10,5,7,8,9]         41 (2+3+9*4)   
 * [1,2,3,4,5,6,7,8,9,10]   45 (1+2+3+--+9)    
 * [10,9,8,7,6,5,4,3,2,1]   45 (9+8+--+1) 
 *
 */

public class WaterTrapIII {
    
    

    public int maxArea(int[] height) {
        if (height == null || height.length < 2) {
            return 0;
        }

        int result = 0;
        int len = height.length;
        int max = 0;  // till to now, the index of max height
        for (int i = 1; i < len; i++) {
            if (height[i] >= height[max]) {
                result += height[max] * (i - max);

                max = i;
            }
        }

        int secondMax = len - 1;
        for (int i = len - 2; i >= max; i--) {
            if (height[i] >= height[secondMax]) {
                result += height[secondMax] * (secondMax - i);

                secondMax = i;
            }
        }

        return result;
    }


    public static void main(String[] args) {
        WaterTrapIII sv = new WaterTrapIII();

        int[][][] inputs = {
            {{1, 1}, {1}},
            {{1, 2}, {1}}, 
            {{2, 1}, {1}}, 
            {{0, 2}, {0}}, 
            {{2, 0}, {0}}, 
            {{1, 2, 1}, {2}}, 
            {{1, 2, 4, 3}, {6}}, 
            {{3, 2, 1, 3}, {9}}, 
            {{4, 3, 2, 1, 4}, {16}}, 
            {{2, 3, 10, 5, 7, 8, 9}, {41}}, 
            {{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, {45}}, 
            {{10, 9, 8, 7, 6, 5, 4, 3, 2, 1}, {45}}
        };

        for (int[][] input : inputs) {
            System.out.println(String.format("\n Input the elevation map: %s", Misc.array2String(input[0])));

            Assert.assertEquals(input[1][0], sv.maxArea(input[0]));

        }
    }

}
