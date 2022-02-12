package game.waterTrap;

import org.junit.Assert;
import util.Misc;

/**
 * _https://www.lintcode.com/problem/363
 * 
 * You are given an array of non-negative integers that represents a two-dimensional elevation map where each element is
 * unit-width wall and the integer is the height. Suppose it will rain and all spots between two walls get filled up.
 * Compute how many units of water remain trapped on the map .
 *
 * For example: 
 * Given [2, 1, 2], we can hold 1 unit of water in the middle. 
 * Given [3, 0, 1, 3, 0, 5], we can hold 3 units in the first index, 2 in the second, and 3 in the fourth index 
 * (we cannot hold 5 since it would run off to the left), so we can trap 8 units of water. 
 * 
 *Given [0,1,0,2,1,0,1,3,2,1,2,1], returns 6.
 *
 * Follow up: 
 * What is the complexity of your solution? 
 * Can you do it in O(N) time and O(1) space
 * 
 */

public class WaterTrap {
  

    /**
     * one point, Time complexity O(2n)
     */
    public int trapRainWater(int[] heights) {
        if (null == heights || 0 == heights.length) {
            return 0;
        }

        //find the highest one
        int highestIndex = 0;
        for (int i = 1; i < heights.length; i++) {
            if (heights[i] > heights[highestIndex]) {
                highestIndex = i;
            }
        }

        long sum = 0;  //** long instead of int
        //from left to the highest one
        int left = 0;
        for (int i = 1; i <= highestIndex; i++) {
            if (heights[i] < heights[left]) {
                sum -= heights[i];
            } else {
                sum += heights[left] * (i - left - 1);
                left = i;
            }
        }

        //from right to the heightest one
        int right = heights.length - 1;
        for (int i = right - 1; i >= highestIndex; i--) {
            if (heights[i] < heights[right]) {
                sum -= heights[i];
            } else {
                sum += heights[right] * (right - i - 1);
                right = i;
            }
        }

        return (int) sum;
    }


    /**
     * two points.
     *
     * Time complexity: O(n)
     *
     */
    public int trapRainWater_x2(int[] heights) {
         if(heights == null || heights.length  < 3 ){
            return 0;
        }

        int sum = 0;
        int min = 0;
        int diff; 
        for(int l = 0, r = heights.length - 1; l < r;  ){

            if(heights[l] < heights[r]){
                diff = min - heights[l];
                if(diff > 0){
                    sum += diff;
                }else{
                    min = heights[l];
                }

                l++;
            }else{
                diff = min - heights[r];
                if(diff > 0){
                    sum += diff;
                }else{
                    min = heights[r];
                }

                r--;
            }
        }
        
        return sum;
    }
    
    public int trapRainWater_x3(int[] heights) {
        if (null == heights || heights.length < 3) {
            return 0;
        }

        int sum = 0;
        int diff;
        for (int l = 0, r = heights.length - 1; l < r; ) {
            if(heights[l] < heights[r]){
                diff = heights[l] - heights[l + 1];
                if(diff > 0){
                    sum += diff;
                    heights[l + 1] = heights[l];
                }

                l++;
            }else{
                diff = heights[r] - heights[r - 1];
                if(diff > 0){
                    sum += diff;
                    heights[r - 1] = heights[r];
                }

                r--;
            }
        }

        return sum;
    }

    public int trapRainWater_x(int[] heights) {
        if (null == heights || heights.length < 3) {
            return 0;
        }

        int sum = 0;
        int min;
        int diff;
        for (int l = 0, r = heights.length - 1; l < r;) {
            min = Math.min(heights[l], heights[r]);

            for ( ; l < r && (diff = min - heights[l] ) >= 0; l++) {
                sum += diff;
            }

            for ( ; l < r && (diff = min - heights[r] ) >= 0; r--) {
                sum += diff;
            }
        }

        return sum;
    }
    

    /**
     * @param args
     */
    public static void main(String[] args) {
        WaterTrap sv = new WaterTrap();

        int[][][] inputs = {
            //{test_case, expectation}
            {{},{0}},
            {{1, 1}, {0}},
            {{0, 1, 1, 0}, {0}},
            {{0, 1, 2, 3, 4}, {0}},
            {{4, 3, 2, 2, 1}, {0}},
            {{0, 1, 2, 2, 1, 0}, {0}},
            {{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1}, {6}},
            {{5, 2, 1, 1, 3, 4}, {9}},
            {{5, 2, 1, 0, 1, 3, 4}, {13}},
            {{1, 5, 1, 2, 3, 2, 4, 3, 2}, {8}}
        };
       

        for (int[][] input : inputs ) {
            System.out.println(String.format("\n%s", Misc.array2String(input[0])) );

            Assert.assertEquals(input[1][0], sv.trapRainWater(input[0]));
            Assert.assertEquals(input[1][0], sv.trapRainWater_x(input[0]));
            Assert.assertEquals(input[1][0], sv.trapRainWater_x2(input[0]));
            Assert.assertEquals(input[1][0], sv.trapRainWater_x3(input[0]));
        }
        
    }

}
