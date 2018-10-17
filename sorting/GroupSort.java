package fgafa.sorting;

import fgafa.util.Misc;

/*
 * Given an array contains only 0s, 1s and 2s. Re-arrange the elements so that 1 follows 0 and 2 follows 1. 
 * E.g. 00011112222222. Do it in linear time.
 * 
 * More:
 * How to do if the array contains [0---k]
 * 
 * Approach #1, 
 * Quick Sorting  (especially k <= 3)
 *   Time O(n * k), Space O(1)
 * 
 * Approach #2,  Counting Sorting
 *   Time O(n), Space O(k)
 *   
 */

public class GroupSort
{

  /*
   * Given an array with n objects colored red, white or blue, sort them so that
   * objects of the same color are adjacent, with the colors in the order red,
   * white and blue. Here, we will use the integers 0, 1, and 2 to represent the
   * color red, white, and blue respectively.
   * 
   * example: {0,2,1,2,0,1} => {0,0,1,1,2,2}
   * 
   * Solution: move 0 to the left, move 2 to the right. 
   */
    /*Time O(n) Space O(1) */
    public void sortColors_n(int[] nums) {
        if (null == nums) {
            return;
        }

        //define i as the tail of 0, p as the tail of 1, j as the head of 2
        for (int i = 0, p = 0, j = nums.length - 1; p <= j; p++) {
            if (0 == nums[p]) {
                swap(nums, i, p);
                i++;
            } else if (2 == nums[p]) {
                swap(nums, j, p);
                j--;
                p--;  //note
            }
        }

    }

    private void swap(int[] nums,
                      int i,
                      int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
  
    /*Time O(n) Space O(1) */
    public void groupByCountSort(int[] nums) {
        // check
        if (nums == null || nums.length < 2){
            return;
        }

        int[] counts = new int[3]; //default all are 0

        for (int num : nums) {
            counts[num]++;
        }

        int k = 0;
        for (int i = 0; i < counts.length; i++) {
            while(counts[i] > 0){
                nums[k++] = i;
                
                counts[i]--;
            }
        }
    }
  
/**
 * Given an array contains only 0s, 1s, --, k. sorted the elements . 
 * 
 * solution: 
 *   move the 0 to the left, k to the right
 *   move the 1 to the left, k-1 to the right
 *   ---
 *   
 */
  /* Time O(n * k), Space O(1) */
  public void sortColorsK_n(int[] colors, int k) {
      if(null == colors || k < 2){
          return;
      }
      
      //main, 
      int left = 0;
      int right = colors.length - 1;
      for(int i = 0, j = k; i < j ; i++, j--){
          for(int p = left;  p <= right; p++ ){
              if( i == colors[p] ){
                  swap(colors, left, p);
                  left++;
              }else if(j == colors[p]){
                  swap(colors, right, p);
                  right--;
                  p--;
              }
          }
      }
      
  }
  
  /*Time O(n) Space O(k) */
  public void groupByCountSort(int[] nums, int k) {
      if (nums == null || nums.length < 2){
          return;
      }

      int[] counts = new int[k + 1]; //default all are 0

      for (int num : nums) {
          counts[num]++;
      }

      int j = 0;
      for (int i = 0; i < counts.length; i++) {
          while(counts[i] > 0){
              nums[j++] = i;
              
              counts[i]--;
          }
      }
  }
  
  /*Time O(n * logk) Space O(1) */
  public void groupByQuickSort(int[] nums, int k){
      
  }
  
  
  /**
   * @param args
   */
  public static void main(String[] args) {

    GroupSort serivce = new GroupSort();
    

    //init
    int[][] A = {{1,2,0}};
    for(int i = 0; i < A.length; i++){
      System.out.println("\n"+Misc.array2String(A[i]));
      
      serivce.sortColors_n(A[i]);
      System.out.println(""+Misc.array2String(A[i]));    
      
      serivce.groupByCountSort(A[i]);
      System.out.println(""+Misc.array2String(A[i]));   
    }
    
    //init
    int[][] arr = { null, {}, {0}, {0,0}, {0,1}, {0,2,1}, {0,3,1}, 
    		{5,9,7,8}
            , {0,2,1,2,0,1}, {0,0,0,2,2,2,2,2,2,2,1,1,1,1}
            };
    int[] k = {0,0,1,1,2,3,4,
    		10
    		,3,3
    		};
    
    for(int i = 0; i < arr.length; i++){
      System.out.println("\n"+Misc.array2String(arr[i]) + "\t k=" + k[i]);
      
      serivce.sortColorsK_n(arr[i], k[i]);
      System.out.println(""+Misc.array2String(arr[i]));      
      
      serivce.groupByCountSort(arr[i], k[i]);
      System.out.println(""+Misc.array2String(arr[i])); 
    }

  }

}
