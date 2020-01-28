package fgafa.game.waterTrap;

import fgafa.util.Misc;

/**
 * 
 * You are given an array of non-negative integers that represents a two-dimensional elevation map where each element is unit-width wall and the integer is the height.
 * Suppose it will rain and all spots between two walls get filled up. Compute how many units of water remain trapped on the map .
 *
 * For example:
 *  Given [2, 1, 2], we can hold 1 unit of water in the middle.
 *  Given [3, 0, 1, 3, 0, 5], we can hold 3 units in the first index, 2 in the second, and 3 in the fourth index (we cannot hold 5 since it would run off to the left), so we can trap 8 units of water.
 *  Given [0,1,0,2,1,0,1,3,2,1,2,1], returns 6.
 * 
 * Follow up:
 *   What is the complexity of your solution?
 *   Can you do it in O(N) time and O(1) space
 * 
 */

public class WaterTrap {
  
  /**
   * Time O(n)  Space O(1)
   */
    public int waterTrap(int[] height) {
        int result = 0;

        // check
        if (height == null || height.length == 0)
            return result;

        int size = height.length;
        int max = 0;

        for (int i = 1; i < size; i++) {
            if (height[i] >= height[max]) { 
                result += calArea(height, max, i);

                max = i;
            }
        }

        int secondMax = size - 1;
        for (int j = size - 2; j >= max; j--) {
            if (height[j] >= height[secondMax]) { // if(secondMax != 0)
                result += calArea(height, j, secondMax);

                secondMax = j;
            }

        }
        return result;
    }


    private int calArea(int[] height,
                        int left,
                        int right) {
        int result = Math.min(height[right], height[left]) * (right - left - 1);

        if (result <= 0) {
            return 0;
        }

        for (int i = left + 1; i < right; i++) {
            result -= height[i];
        }

        return Math.max(0, result);
    }


    /**
     * one point,
     * Time complexity O(2n)
     */
    public int trapRainWater_n(int[] heights) {
        if(null == heights || 0 == heights.length){
            return 0;
        }

        //find the highest one
        int highestIndex = 0;
        for(int i = 1; i < heights.length; i++){
            if(heights[i] > heights[highestIndex]){
                highestIndex = i;
            }
        }

        long sum = 0;  //** long instead of int
        //from left to the highest one
        int left = 0;
        for(int i = 1; i<= highestIndex; i++){
            if(heights[i] < heights[left]){
                sum -= heights[i];
            }else{
                sum += heights[left] * (i - left - 1);
                left = i;
            }
        }

        //from right to the heightest one
        int right = heights.length - 1;
        for(int i = right - 1; i>= highestIndex; i--){
            if(heights[i] < heights[right]){
                sum -= heights[i];
            }else{
                sum += heights[right] * (right - i - 1);
                right = i;
            }
        }

        return (int)sum;
    }


    /**
     * two points.
     *
     * Time complexity: O(n)
     *
     */
    public int trapRainWater_x2(int[] heights) {
        if(heights == null || heights.length < 3){
            return 0;
        }

        int sum = 0;
        int level = 0;
        int min;
        for(int l = 0, r = heights.length - 1; l < r; ){
            min = Math.min(heights[l], heights[r]);

            sum += Math.max(0, level - min);
            level = Math.max(level, min);

            if(heights[l] <= heights[r]){
                l++;
            }else{
                r--;
            }
        }

        return sum;
    }

    public int trapRainWater_x(int[] heights) {
        if(null == heights || 0 == heights.length){
            return 0;
        }

        int sum = 0;
        int min;
        for(int l = 0, r = heights.length - 1; l < r;  ){
            min = Math.min(heights[l], heights[r]);

            for( ; heights[l] <= min && l < r; l++ ){
                sum += min - heights[l];
            }

            for( ; heights[r] <= min && l < r; r--){
                sum += min - heights[r];
            }
        }

        return sum;
    }





    /**
     * @param args
     */
    public static void main(String[] args) {
        WaterTrap sv = new WaterTrap();

        int[][] arr = {
                {},
                {1, 1},
                {0, 1, 1, 0},
                {0,1,0,2,1,0,1,3,2,1,2,1},
                {5, 2, 1, 1, 3, 4},
                {5, 2, 1,0, 1, 3, 4},
                {1,5,1,2,3,2,4,3,2}};
        int[] expectation = {0, 0, 0, 6, 9, 13, 8};

        for(int i=0; i<arr.length; i++){
            System.out.println("\n Input the elevation map: "+ Misc.array2String(arr[i]));

            int ret1 = sv.waterTrap(arr[i]);
            int retN = sv.trapRainWater_n(arr[i]);
            int retX = sv.trapRainWater_x(arr[i]);

            System.out.println(String.format("It can trap after a enough big rain: %d \t%d \t%d \t%b", ret1, retN, retX, retN == expectation[i]));

        }
    }


}
