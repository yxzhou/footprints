package fgafa.game.waterTrap;

import java.util.Stack;

import fgafa.util.Misc;

/*
 * 
 * Given a non-negative integer array representing an elevation map where the width of each bar is 1, 
 * compute how much water it can trap after raining. <br> 
 * For example, given [0,1,0,2,1,0,1,3,2,1,2,1], returns 6.<br>
 * 
 * Follow up:
 * What is the complexity of your solution?
 * 
 */

public class WaterTrap
{

 /*
  *  1 get the max height, get the maxR in the right, get the maxL in the left. 
  *  2 calculate the Rain between [maxL, max] and [max, maxR]
  *  3 recursive Step 1 and 2 in [0, maxL] and [maxR, length-1] 
  *  
  * Time O(n) Space O(n)
  * 
  */
  public int waterTrap_R(int[] arr){
    int ret = 0;
    
    //check
    if(arr == null || arr.length < 2)
      return ret; 
    
    //find the key elevation. e.g. if a[i] > a[i+1], a[i] is the key elevation.
    int len = arr.length;
    int keyIndex = 0;
    int[] keys = new int[len] ;
    int i = 0;
    if(arr[0] > arr[1])
      keys[keyIndex++] = i; 
    for(i=1; i< arr.length-1; i++){
      if(arr[i] >= arr[i+1] && arr[i] > arr[i-1] )
        keys[keyIndex++] = i; 
    }
    if(arr[i-1] < arr[i])
      keys[keyIndex++] = i; 
    
    len = keyIndex;
    
    System.out.println("the keys is : "+Misc.array2String(keys));
    
    //calculate 
    //find the max key index
    int maxKeyIndex = getMaxKeyIndex(arr, keys, 0, len-1);
    
    //cal the left part and right part
    ret += calWaterTrap(arr, keys, 0, maxKeyIndex, maxKeyIndex);
    ret += calWaterTrap(arr, keys, maxKeyIndex, len-1, maxKeyIndex);
    
    return ret;
  }
  
    
  private int calWaterTrap(int[] arr, int[] keys, int startKeyIndex, int endKeyIndex, int maxKeyIndex){
    int ret = 0;
    
    if(startKeyIndex >= endKeyIndex )
      return ret;

    int nextMaxKeyIndex = -1;
    
    
    if(maxKeyIndex == startKeyIndex){  // right to maxKeyIndex
      //find the max key index
      nextMaxKeyIndex = getMaxKeyIndex(arr, keys, startKeyIndex+1, endKeyIndex);
      
      //cal the right part
      ret += calWaterTrap(arr, keys[nextMaxKeyIndex], keys[maxKeyIndex]);
      ret += calWaterTrap(arr, keys, nextMaxKeyIndex, endKeyIndex, nextMaxKeyIndex);
      
    }else{ // left to maxKeyIndex,  endKeyIndex == maxKeyIndex
      //find the max key index
      nextMaxKeyIndex = getMaxKeyIndex(arr, keys, startKeyIndex, endKeyIndex-1);
      
      //cal the left part 
      ret += calWaterTrap(arr, keys[nextMaxKeyIndex], keys[maxKeyIndex]);
      ret += calWaterTrap(arr, keys, startKeyIndex, nextMaxKeyIndex, nextMaxKeyIndex);
    }
    
    return ret;
  }
  
/**
 * 
 * @param arr
 * @param keys
 * @param startKeyIndex
 * @param endKeyIndex
 * @return the max keys index in [startKeyIndex, endKeyIndex]
 */
  private int getMaxKeyIndex(int[] arr, int[] keys, int startKeyIndex, int endKeyIndex){
    //check
//    if(startKeyIndex > endKeyIndex)
//      return getMaxKeyIndex(arr, keys, endKeyIndex, startKeyIndex);
    
    int maxHigh = -1;
    int maxHighIndex = -1;
    
    for(;startKeyIndex <= endKeyIndex; startKeyIndex ++){
      if(arr[keys[startKeyIndex]] > maxHigh ){
        maxHigh = arr[keys[startKeyIndex]];
        maxHighIndex = startKeyIndex;
      }
      
    }
    
    return maxHighIndex;
  }
  
  /*
   *  end > start
   *  
   */
  private int calWaterTrap(int[] arr, int startIndex, int endIndex){
    if(startIndex > endIndex)
      return calWaterTrap(arr, endIndex, startIndex);
    
    int ret = 0;
       
    while(arr[startIndex] == arr[startIndex+1] && startIndex < endIndex)
      startIndex ++;
    while(arr[endIndex] == arr[endIndex-1] && endIndex > startIndex)
      endIndex --;  
    
    //main
    ret = Math.min(arr[startIndex], arr[endIndex]) * (endIndex - startIndex -1) ; 
    for(int k=startIndex+1; k < endIndex; k++){
      ret -= arr[k]; 
    }
    
    return ret;
  }
  
  /**
   * @param args
   */
  public static void main(String[] args) {

    WaterTrap s = new WaterTrap();
    
    int[][] arr = {{}, {1, 1}, {0, 1, 1, 0},{0,1,0,2,1,0,1,3,2,1,2,1}, {5, 2, 1, 1, 3, 4}, {5, 2, 1,0, 1, 3, 4}, {1,5,1,2,3,2,4,3,2}};
    int[] expectation = {0, 0, 0, 6, 9, 13, 8}; 
    
    for(int i=0; i<arr.length; i++){
      System.out.println("\n Input the elevation map: "+ Misc.array2String(arr[i]));
      int ret = s.waterTrap_R(arr[i]);
      
      int ret2 = s.waterTrap_Stack(arr[i]);
      
      int ret3 = s.waterTrap_X(arr[i]);
      
      int ret4 = s.trapRainWater_n(arr[i]);
      
      System.out.println("It can trap after a enough big rain: " + ret + "\t" + ret2 + "\t" + ret3 + "\t" + ret4 + "\t" + (ret == expectation[i]));
      
    }
  }
  

 /**
  *   Wrong!! to case [5,4,1,2]
  *
  *   Time O(n)  Space O(n)
  */
  public int waterTrap_Stack(int[] height) {
    int result = 0;
    int len = height.length, lm1 = len - 1, start = -1, bottom = -1;
    Stack<Integer> peaks = new Stack<Integer>();
    if (len < 3)
      return 0;
    for (int i = 0; i < len; i++) {
      if (start < 0) {
        if (i < lm1) {
          if (height[i + 1] < height[i]) {
            start = i;
            peaks.add(i);
          }
        }
      }
      else if (bottom < 0) {
        if (i < lm1 && height[i + 1] > height[i]) {
          bottom = i;
        }
      }
      else {
        if (i == lm1 || height[i + 1] < height[i]) {
          int last = -1;
          while (!peaks.isEmpty() && height[peaks.peek()] <= height[i]) {
            last = peaks.pop();
          }
          if (last > -1) {
            if (peaks.isEmpty()) {
              result += calcRain(height, last, i, last);
            }
          }
          peaks.add(i);
          start = i;
          bottom = -1;
        }
      }
    }

    while (!peaks.isEmpty()) {
      int last = peaks.pop();
      if (!peaks.isEmpty()) {
        result += calcRain(height, peaks.peek(), last, last);
      }
    }

    return result;
  }

  private int calcRain(int[] input, int start, int end, int maxIndex) {
    int result = 0;
    int max = input[maxIndex];
    for (int i = start + 1; i < end; i++) {
      result += max - input[i];
    }
    return result;
  }
  
  /**
   * Time O(n)  Space O(1)
   */
    public int waterTrap_X(int[] height) {
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


    public int trapRainWater_x(int[] heights) {
        int result = 0;

        // check
        if (null == heights || 0 == heights.length){
            return result;
        }

        //from left to right
        int left = 0;
        for (int i = 1; i < heights.length; i++) {
            if (heights[i] >= heights[left]) { 
                result += calArea(heights, left, i);

                left = i;
            }else{
                
            }
        }

        //from right to the max
        int secondMax = heights.length - 1;
        for (int j = heights.length - 2; j >= left; j--) {
            if (heights[j] >= heights[secondMax]) { // if(secondMax != 0)
                result += calArea(heights, j, secondMax);

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
   * @param heights: an array of integers
   * @return: a integer
   */
  public int trapRainWater_n(int[] heights) {
      //check
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
      
      long sum = heights[highestIndex] << 1; //** long instead of int
      //from left to the highest one
      int left = 0;
      for(int i = 0; i<= highestIndex; i++){
          if(heights[i] <= heights[left] && i != highestIndex){
              sum -= heights[i];
          }else{
              sum += heights[left] * (i - left) - heights[i];
              left = i;
          }
      }
  
      //from right to the heightest one
      int right = heights.length - 1;
      for(int i = right; i>= highestIndex; i--){
          if(heights[i] <= heights[right] && i != highestIndex){
              sum -= heights[i];
          }else{
              sum += heights[right] * (right - i) - heights[i];
              right = i;
          }
      }
      
      return (int)sum;
  }
}
