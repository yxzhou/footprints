package sorting;

import util.Misc;

/**
 * Lintcode #148, Leetcode #
 *
 * Given an array contains only 0s, 1s and 2s. Re-arrange the elements so that 1 follows 0 and 2 follows 1. 
 * E.g. 00011112222222. Do it in linear time.
 * 
 * More:
 * How to do if the array contains [0---k]
 *
 * when k = 3,  RGB, colors is in [0, 1, 2]
 * Approach #1,  Counting Sorting, two pass scan, count and set
 *   Time O(n), Space O(1)
 *
 * Approach #2,
 * Quick sort with 1,  put 0 on the left, 2 on the right, one pass scan
 *   Time O(n), Space O(1)
 *
 * when k > 3,  colors is in [1, k]
 * Approach #1,  Counting Sorting, two pass scan
 *   Time O(n), Space O(k)
 *
 * Approach #1,
 * pick 1 and k, 2 and k-1, ---
 *   Time O(n * k), Space O(1)
 *
 * Approach #3,
 * Quick sort, with mid =
 *   Time O(n * logn), Space O(1)
 *
 */

public class GroupSort
{

  /**
   * Given an array with n objects colored red, white or blue, sort them so that
   * objects of the same color are adjacent, with the colors in the order red,
   * white and blue. Here, we will use the integers 0, 1, and 2 to represent the
   * color red, white, and blue respectively.
   * 
   * example: {0,2,1,2,0,1} => {0,0,1,1,2,2}
   * 
   * Solution: move 0 to the left, move 2 to the right. 
   */
    /**Time O(n) Space O(1) */
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

    /**Time O(n) Space O(1) */
    public void sortColors_x(int[] nums) {
        if (nums == null || nums.length < 2) {
            return;
        }

        for(int l = 0, r = nums.length - 1, i = 0; i <= r; ){
            if (nums[i] == 2) {
                //swap(nums, i, r);
                nums[i] = nums[r];
                nums[r] = 2;
                r--;
            }else {
                if (nums[i] == 0) {
                    //swap(nums, l, i);
                    nums[i] = nums[l];
                    nums[l] = 0;
                    l++;
                }

                i++;
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
  
    /*Time O(n*2) Space O(1) */
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
     *   move the 1 to the left, k to the right
     *   move the 2 to the left, k-1 to the right
     *   ---
     *
     * Time O(n * k), Space O(1)
     */
    public void sortColorsK_n(int[] colors, int k) {
        if (null == colors || k < 2) {
            return;
        }

        int left = 0;
        int right = colors.length - 1;
        for (int small = 1, big = k; small < big; small++, big--) {
            for (int i = left; i <= right; i++) {
                if (small == colors[i]) {
                    //swap(colors, left, p);
                    colors[i] = colors[left];
                    colors[left] = small;
                    left++;
                } else if (big == colors[i]) {
                    //swap(colors, right, p);
                    colors[i] = colors[right];
                    colors[right] = big;
                    right--;
                    i--; //note
                }
            }
        }
    }

    /* Time O(n * k), Space O(1) */
    public void sortColorsK_n2(int[] colors, int k) {
        if (colors == null || k < 2) {
            return;
        }

        for (int l = 0, end = colors.length, i = 0, v = 1; i < end && v <= k; i = l, v++) {
            for (; i < end; i++) {
                if (colors[i] == v) {
                    colors[i] = colors[l];
                    colors[l] = v;
                    l++;
                }
            }
        }
    }

    /**
     * Given an array contains only 0s, 1s, --, k. sorted the elements .
     *
     * solution: similar with quick sort, quickSort(nums, 0, n, 1, k)
     *   compare with mid of 1 and k,
     *   move the smaller to the left, bigger to the right
     *   quickSort
     *
     *
     * Time O(n * logk), Space O(1)
     */
    public void sortColorsK_x(int[] colors, int k) {
        if (null == colors || k < 2) {
            return;
        }

        quickSort(colors, 0, colors.length - 1, 1, k);
    }

    private void quickSort(int[] colors, int left, int right, int smaller, int bigger) {
        if(left >= right){
            return;
        }
        int s = left;
        int e = right;
        int mid = smaller + (bigger - smaller) / 2;
        for(int i = s; i <= e; i++){
            if(colors[i] < mid ){
                swap(colors, left, i);
                s++;
            }else if(colors[i] > mid){
                swap(colors, i, e);
                e--;
                i--; //note
            }
        }

        quickSort(colors, left, s, smaller, mid - 1);
        quickSort(colors, e, right, mid + 1, bigger);
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
