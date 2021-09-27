package easy;

import util.Misc;

public class MinDivident
{

  /**
   * @param args
   */
  public static void main(String[] args) {
    MinDivident m = new MinDivident();
       
    int[][] a = {null, {0},{0,0}, {0, 1}, {1,1}, {-1,-1}, {-1,-2}, {-2,3,4}, {-2,0,2}, {-5,-2,0,2,4}, {-5,-1,0,2,4}};
    double[] ans = {0, 0, Double.NaN, 0, 1, 1, 0.5, -2, -1, -2.5, -4};
    
    for(int i=0; i<a.length; i++){
      System.out.print("\nInput: " + Misc.array2String(a[i]));
      System.out.println("Output: " + m.lowdiv_n(a[i]) + " " + m.lowdiv(a[i]) + " " + ans[i] );
    }
  }
  
  /*
   * brute force 
   * 
   * Time  O(n^2)
   * */
  double lowdiv(int nums[]) {
    //check
    if(nums == null || nums.length < 2 )
      return 0;
    
    int len = nums.length;
    double min = Double.MAX_VALUE;
    for(int i=0; i<len; i++ ){
      for(int j=0; j<len; j++ ){
        min = Math.min(min, getMinDiv(nums[i], nums[j]));
        
      }
    }
    return min;
  }
  
  /* Time O(n) */
  double lowdiv_n(int nums[]) {
    //check
    if(nums == null || nums.length < 2 ){
      return 0;
    }
    
    long minPosition = Long.MAX_VALUE;
    long maxPosition = 0;
    
    long minNegative = 0;
    long maxNegative = Long.MIN_VALUE;
    
    boolean hasZero = false;
    
    for(int i=0; i< nums.length; i++){
      if(nums[i] > 0){
        minPosition = Math.min(minPosition, nums[i]);
        maxPosition = Math.max(maxPosition, nums[i]);
        
      }else if(nums[i] < 0){
        minNegative = Math.min(minNegative, nums[i]);
        maxNegative = Math.max(maxNegative, nums[i]);
        
      }else{
        hasZero = true;
      }
        
    }
    
    if(minNegative == 0){  // 0 or positive num
      if(hasZero ){
        return 0;
      }else {
        return minPosition / maxPosition; 
      }
    }else{
      
      if(minPosition == Long.MAX_VALUE){  // 0 and negative num, no positive num
         if(hasZero ){
           return 0;
         }else{
           return minNegative / maxNegative; 
         }
      } else {    // 0 and negative num and positive 
        return Math.min(getMinDiv(minNegative, minPosition), getMinDiv(maxPosition, maxNegative));
      }
    }
  }
  
  private double getMinDiv(double x, double y){   
    if(x ==0 || y == 0)
      return 0;
    if(x ==0 && y == 0)
      return 0;
    
    return Math.min( x / y, y / x);
  }
  
}
