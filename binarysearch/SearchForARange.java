package binarysearch;

import util.Misc;

/*
 * 
 * Given a sorted array of integers, find the starting and ending position of a given target value.
 * 
 * Your algorithm's runtime complexity must be in the order of O(log n).
 * 
 * If the target is not found in the array, return [-1, -1].
 * 
 * For example,
 * Given [5, 7, 7, 8, 8, 10] and target value 8,
 * return [3, 4].
 *
 */

public class SearchForARange
{
    public int[] searchRange(int[] nums, int target) {
        int[] result = {-1, -1};
        if(null == nums || 0 == nums.length){
            return result;
        }
        
        int lowerBound = lowerBound(nums, target);
        int upperBound = upperBound(nums, target);
        
        if(lowerBound >= 0 && lowerBound < nums.length && nums[lowerBound] == target){
            result[0] = lowerBound;
            result[1] = upperBound;
        }
        
        return result;
    }
    
    private int lowerBound(int[] nums, int target){
        int left = 0;
        int right = nums.length - 1;
        
        while(left <= right){
            int mid = left + (right - left) / 2;
            
            if(nums[mid] >= target){
                right = mid - 1;
            }else{
                left = mid + 1;
            }
        }
        
        return left;
    }
    
    private int upperBound(int[] nums, int target){
        int left = 0;
        int right = nums.length - 1;
        
        while(left <= right){
            int mid = left + (right - left) / 2;
            
            if(nums[mid] > target){
                right = mid - 1;
            }else{
                left = mid + 1;
            }
        }
        
        return right;
    }

	public int[] searchRange_x(int[] A, int target) {
		int[] result = {-1, -1};
		if (null == A || 0 == A.length || target < A[0] || target > A[A.length - 1]) {
			return result;
		}

		int low = 0;
		int high = A.length;
		int mid;

		//find lowest one
		while(low < high){
			mid = low + (high - low) / 2;

			if(A[mid] >= target){
				high = mid;
			}else { //(A[mid] < target)
				low = mid + 1;
			}
		}

		if(low < 0 || low >= A.length || A[low] != target){
			return result;
		}

		result[0] = low;

		//find the highest one
		high = A.length;
		while(low < high){
			mid = low + (high - low) / 2;

			if(A[mid] == target){
				low = mid + 1;
			}else {  //(A[mid] > target)
				high = mid;
			}
		}
		result[1] = low - 1;

		return result;
	}
	

  /**
   * @param args
   */
  public static void main(String[] args) {
	  int[][] A = {null, {8}, {8}, {8}, {7, 8}, {7, 8}, {7, 8}, {7, 8}, {6, 8}, {6, 8}, {5, 7, 7, 8, 8, 10}, {5, 7, 7, 8, 8, 10}};
	  int[] target = {0, 8, 7, 9, 8, 7, 6, 9, 7, 9, 8, 10};

	  SearchForARange sv = new SearchForARange();

	  for (int i = 0; i < A.length; i++) {
		  System.out.println("\nInput :" + Misc.array2String(A[i]) + "\t" + target[i]);

		  System.out.println("Output:" + Misc.array2String(sv.searchRange(A[i], target[i])));
		  System.out.println("Output:" + Misc.array2String(sv.searchRange_x(A[i], target[i])));

	  }
  }

}
