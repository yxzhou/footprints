package datastructure.stack.monotonous;

import java.util.Stack;

import util.Misc;

/**
 * 
 * Given n non-negative integers representing the histogram's bar height where the width of each bar is 1, 
 * find the area of largest rectangle in the histogram.
 * 
 * For example,
 * Given height = [2,1,5,6,2,3],
 * return 10, the rectangle is (5,6).
 *
 */

public class Histogram
{

  /*
   * Time O(n^2)
   */
  public int largestRectArea2(int[] height) {
    int result = 0;
    
    //check
    if(height == null || height.length == 0)
      return result;
    
    int w;
    int h;
    int minH = height[0];
    result = minH * getWidth(height, 0) ;
    for(int i=1; i<height.length; i++){
      h = height[i];
      //System.out.println("=="+h +"=="+minH);
      if(h == minH)
        continue;
      
      //System.out.print("=="+i);
      
      minH = Math.min(minH, h);   
      w = getWidth(height, i);
      
      result = Math.max(result, w * h);
      
    }
    
    return result;
  }
  
  private int getWidth(int[] height, int index){
    int result = 1;
    
    for(int i = index+1; i< height.length; i++ ){
      if(height[i] >= height[index])
        result++;
      else 
        break;
    }
    
    for(int i = index-1; i>=0; i--){
      if(height[i] >= height[index])
        result++;
      else 
        break;
    }
    
    return result;
  }

  
  /**
   * Find the biggest rectangle with in a histogram <br> 
   *  
   * Basic Idea: <br> 
   * Keep stack increasing order. if new element is less than a bunch of elements
   * at the top of the stack, calculate the rectangle with
   * height same to the "bigger" elements. The right boundary
   * is current index, the left boundary is next element of the stack
   * 
   */

  /* Time O(n) */
    public int largestRectangleArea_n(int[] height) {
        if(null == height || 0 == height.length){
            return 0;
        }
        
        int max = 0;
        int index = 0;
        int width;
        Stack<Integer> stack = new Stack<>();
        for(int i = 0; i < height.length; ){
            if(stack.isEmpty() || height[i] >= height[stack.peek()]){
                stack.add(i);
                i++;
            }else{
                index = stack.pop();
                width = stack.isEmpty() ? i : i - 1 - stack.peek();
                max = Math.max(max, height[index] * width );
            }
        }
        
        while(!stack.isEmpty()){
            index = stack.pop();
            width = stack.isEmpty() ? height.length : height.length - 1 - stack.peek();
            max = Math.max(max, height[index] * width );
        }
        
        return max;
    }
	
    
    public int largestRectangleArea_n2(int[] heights) {
        if(null == heights || 0 == heights.length){
            return 0;
        }

        int maxArea = 0;
        Stack<Integer> positions = new Stack<>();
        
        int i = 0;
        while(i < heights.length){
            if(!positions.isEmpty() && heights[i] < heights[positions.peek()]){
                int top = positions.pop();
                int width = positions.isEmpty()? i : i - positions.peek() - 1;
                maxArea = Math.max(maxArea, heights[top] * width);
            }else{
                positions.push(i);
                i++;
            }
        }
        
        while(!positions.isEmpty()){
            int top = positions.pop();
            int width = positions.isEmpty()? i : i - positions.peek() - 1;
            maxArea = Math.max(maxArea, heights[top] * width);
        }
        
        return maxArea;
    }
    
  /**
   * @param args
   */
  public static void main(String[] args) {
//    int[] heights = new int[60000];
//    for(int i=0; i<heights.length; i++){
//      heights[i] = 1;
//    }
    
    int[][] heights = {{2147483647}, {2, 4}, {2, 4, 6}, {4,2},{6,4,2}, {2, 1, 2}, {2, 2, 1, 2}, {4,2,3}, {3,6,5,7,4,8,1,0}, {2, 1, 5, 6, 2, 3}, {1,1}};
    
    Histogram sv = new Histogram();
    
    for(int i=0; i< heights.length; i++){
      
      System.out.println("\n -"+i+"-"+ Misc.array2String(heights[i]));
            
      System.out.println("getBiggestRect         \t" + sv.largestRectangleArea_n2(heights[i]) );
      System.out.println("largestRectArea2       \t" + sv.largestRectArea2(heights[i]) );
    }
  }

}
