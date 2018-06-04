package fgafa.binarysearch;

import java.util.ArrayList;
import java.util.Arrays;

import fgafa.util.Misc;

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

	public int[] searchRange(int[] A, int target) {
		int[] result = { -1, -1 };

		// check
		if (A == null)
			return result;

		int len = A.length;
		if (len == 0 || A[0] > target || A[len - 1] < target)
			return result;

		int left = 0;
		int right = len - 1;
		int mid;
		// while(left < right){
		while (left <= right) {
			mid = left + ((right - left) >> 1);

			if (A[mid] > target) {
				right = mid - 1;
			} else if (A[mid] < target) {
				left = mid + 1;
			} else {
				result[0] = binarySearchLeft(A, left, mid, target);
				result[1] = binarySearchRight(A, mid, right, target);
				return result;
			}
		}

		// if(A[right] == target ){
		// result[0] = right;
		// result[1] = right;
		// }

		return result;
	}

	private int binarySearchLeft(int[] A, int left, int right, int target) {

		int mid = -1;
		while (left < right) {
			mid = left + ((right - left) >> 1);

			if (A[mid] == target) {
				right = mid;

			} else if (A[mid] < target) {
				left = mid + 1;
			}

		}

		return left;
	}

	private int binarySearchRight(int[] A, int left, int right, int target) {
		int mid = -1;
		while (left < right) {
			mid = left + ((right - left) >> 1);

			if (A[mid] == target) {
				left = mid + 1;
			} else if (A[mid] > target) {
				right = mid - 1;
			}
		}

		if (A[right] > target)
			right--;

		return right;
	}

	public int[] searchRange_w(int[] A, int target) {
		int[] result = { -1, -1 };

		// check
		if ( null== A || 0 == A.length)
			return result;
		
		// Search for the left bound
		int start = 0;
		int end = A.length - 1;
		int mid;
		while (start <= end) {
			mid = start + ((end - start) >> 1);
			if (A[mid] == target) {
				end = mid;
			} else if (A[mid] < target) {
				start = mid + 1;
			} else {
				end = mid -1;
			}
		}
		if (A[end] == target) {
			result[0] = end;
		} else {
			return result;
		}
		// Search for the right bound.
		start = 0;
		end = A.length - 1;
		while (start <= end) {
			mid = start + ((end - start) >> 1);
			if (A[mid] > target) {
				end = mid -1;
			} else { // A[mid] <= target
				start = mid +1;
			} 
		}

		if (A[end] == target) {
			result[1] = end;
		}
		
		return result;
	}

	public int[] searchRange_n2(int[] A, int target) {
        int [] res = {-1,-1};
		if ( null== A || 0 == A.length)
            return res;
        
        //first iteration, find target wherever it is
        int low = 0;
        int high = A.length-1;
        int mid = 0;
        while(low <= high){
            mid = low + ((high - low) >> 1);
            if(A[mid] > target)
                high = mid - 1;
            else if(A[mid] < target)
                low = mid + 1;
            else{
                break;
            }
        }
        
        if(A[mid] != target)
            return res;
        
        int pos = mid;
        
        //second iteration, find the right boundary of this target
        low = pos;
        high = A.length-1;
        while(low <= high){
            mid = low + ((high - low) >> 1);
            if(A[mid] == target)
                low = mid + 1;
            else
                high = mid - 1;
        }
        res[1] = high;
        
        //third iteration, find the left boundary of this target
        low = 0;
        high = pos;
        while(low <= high){
            mid = low + ((high - low) >> 1);
            if(A[mid] == target)
                high = mid - 1;
            else
                low = mid + 1;
        }
        res[0] = low;
        
        return res;
    }
	
	  /** 
	   *@param A : an integer sorted array
	   *@param target :  an integer to be inserted
	   *return : a list of length 2, [index1, index2]
	   */
	  public int[] searchRange_n3(int[] A, int target) {
	      int[] result = {-1, -1};
	      
	      //check
	      if(null == A){
	          return result;
	      }
	      
	      //find one match, binary search
	      int low = 0;
	      int high = A.length - 1;
	      int mid;
	      
	      while(low <= high){
	          mid = low +((high - low) >> 1);
	          
	          if(A[mid] == target){
	              result[0] = searchFirst(A, target, mid);
	              result[1] = searchLast(A, target, mid);
	              break;
	          }else if(A[mid] > target){
	              high = mid - 1;
	          }else{ // midValue < target
	              low = mid + 1;
	          }
	      }
	      
	      return result;
	  }
	  
	  private int searchFirst(int[] A, int target, int before){
	      int low = 0;
	      int high = before;
	      int mid;
	      
	      while(low <= high){
	          mid = low +((high - low) >> 1);
	          
	          if(A[mid] == target){
	              high = mid - 1;
	          }else{ //  midValue < target
	              low = mid + 1;
	          }
	      }
	      
	      return high + 1;
	  }
	  
	  private int searchLast(int[] A, int target, int after){
	      int low = after;
	      int high = A.length - 1;
	      int mid;
	      
	      while(low <= high){
	          mid = low +((high - low) >> 1);
	          
	          if(A[mid] == target){
	        	  low = mid + 1;
	          }else{ // midValue > target
	              high = mid - 1;
	          }
	      }
	      
	      return low - 1;
	  }
	  
	    private int searchLast2(int[] nums, int target){
	        if(null == nums || 0 == nums.length ){
	            return -1; //complain
	        }
	        if(nums[nums.length - 1] < target){
	            return -1; //complain
	        }
	        
	        int low = 0;
	        int high = nums.length - 1;
	        int mid;
	        
	        while(low + 1 < high){
	            mid = low + ((high - low) >> 1);
	            
	            if(nums[mid] >= target){
	                high = mid;
	            }else{
	                low = mid;
	            }
	        }
	        
	        if (nums[low] == target) {
	            return low;
	        }
	         
	        if (nums[high] == target) {
	            return high;
	        }
	        
	        return -1; //no match
	    }
	    
	    private int searchFirst2(int[] nums, int target){
	        if(null == nums || 0 == nums.length ){
	            return -1; //complain
	        }
	        if(nums[nums.length - 1] < target){
	            return -1; //complain
	        }
	        
	        int low = 0;
	        int high = nums.length - 1;
	        int mid;
	        
	        while(low + 1 < high){
	            mid = low + ((high - low) >> 1);
	            
	            if(nums[mid] > target){
	                high = mid;
	            }else{
	                low = mid;
	            }
	        }
	        
	        if (nums[low] == target) {
	            return low;
	        }
	         
	        if (nums[high] == target) {
	            return high;
	        }
	        
	        return -1; // no match
	    }
  /**
   * @param args
   */
  public static void main(String[] args) {
    int[][] A = { null, {8}, {8}, {8}, {7,8}, {7,8}, {7,8}, {7,8}, {6,8},{6,8}, {5,7,7,8,8,10}, {5,7,7,8,8,10}};
    int[] target = {0, 8, 7, 9, 8, 7, 6, 9, 7, 9, 8, 10 };
    
    SearchForARange sv = new SearchForARange();
    
    for(int i=0; i<A.length; i++){
      System.out.println("\nInput :" + Misc.array2String(A[i]) + "\t" + target[i]);
      System.out.println("Output:" + Misc.array2String(sv.searchRange(A[i], target[i])));
      System.out.println("Output:" + Misc.array2String(sv.searchRange_w(A[i], target[i])));
      System.out.println("Output:" + Misc.array2String(sv.searchRange_n2(A[i], target[i])));
    }
  }

}
