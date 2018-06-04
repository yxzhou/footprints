package fgafa.binarysearch;

import fgafa.util.Misc;

public class RotatedArray {

	  /*
	   * Suppose a sorted array is rotated at some pivot unknown to you beforehand.
	   * (i.e., 0 1 2 4 5 6 7 might become 4 5 6 7 0 1 2).
	   * 
	   * You are given a target value to search. If found in the array return its index, otherwise return -1.
	   * You may assume no duplicate exists in the array.
	   * 
	   * Time O(logn) Space O(1)
	   */
	  
	  public int binarySearch_Rotated(int[] seqNum, int num){
	    //initial
	    int returnValue = -1;
	    
	    //check
	    if( seqNum == null ) 
	      return returnValue;
	    
	    int left = 0;
	    int right = seqNum.length - 1;
	    int mid = 0; 
	    while (left <= right) {
	      mid = left + ((right - left) >> 1);  // Avoid overflow, (left + right )/2 
	      if (seqNum[mid] == num) 
	        return mid;
	      
	      if (seqNum[left] <= seqNum[mid]) { // the left half (left, mid) is sorted
	        if (seqNum[left] <= num && num < seqNum[mid])  // num is in [left, mid)
	          right = mid - 1;
	        else   // num is in (mid, right]
	          left = mid + 1;
	      }else {  // the right half is sorted
	        if (seqNum[mid] < num && num <= seqNum[right]) // num is in (mid, right]
	          left = mid + 1;
	        else   // num is in [left, mid)  
	          right = mid - 1;
	      }
	    }
	    
	    return returnValue;
	  }
	  
	  /** 
	   *@param A : an integer rotated sorted array
	   *@param target :  an integer to be searched
	   *return : an integer
	   */
	  /*Time O(logn) Space O(1)*/
	  public int binarySearch_Rotated_n(int[] A, int target) {
	      // check
	      if(null == A){
	          return -1;
	      }
	      
	      int low = 0;
	      int high = A.length - 1;
	      int mid;
	      
	      while(low <= high){
	          mid = low + ((high - low) >> 1);
	          
	          if(A[mid] == target){
	              return mid;
	          }else if(A[mid] < target){
	              if(A[low] <= target && A[low] > A[mid]){
	                  high = mid - 1;
	              }else{
	                  low = mid + 1;
	              }
	          }else{ // A[mid] > target
	              if(A[low] > target && A[low] < A[mid]){
	                  low = mid + 1;
	              }else{
	                  high = mid - 1;
	              }
	          }
	      }
	      
	      return -1;
	  }
	  
	    /** 
	     * param A : an integer ratated sorted array and duplicates are allowed
	     * param target :  an integer to be search
	     * return : a boolean 
	     */
	  /*Time O(n) Space O(1)*/
	    public boolean binarySearch_Rotated_WithDupl(int[] A, int target) {
	        // check
	        if(null == A){
	            return false;
	        }
	        
	        return binarySearch_Rotated_WithDupl(A, 0, A.length - 1, target);
	    }
	    
	    private boolean binarySearch_Rotated_WithDupl(int[] A, int low, int high, int target){
	        
	        int mid;
	        
	        while(low <= high){
	            mid = low + ((high - low) >> 1);
	            
	            if(A[mid] == target){
	                return true;
	            }else if(A[mid] < target){
	                if(A[low] <= target && A[low] > A[mid]){
	                    high = mid - 1;
	                }else if (A[low] == A[mid]){
	                    return binarySearch_Rotated_WithDupl(A, low, mid - 1, target) || binarySearch_Rotated_WithDupl(A, mid + 1, high, target);
	                }else { 
	                    low = mid + 1;
	                }
	                    
	            }else{ //A[mid] > target
	                  if(A[low] > target && A[low] < A[mid]){
		                  low = mid + 1;
		              }else if(A[low] == A[mid]){
		                  return binarySearch_Rotated_WithDupl(A, low, mid - 1, target) || binarySearch_Rotated_WithDupl(A, mid + 1, high, target);
		              }else {
		                  high = mid - 1;
		              }
	            }
	        }
	        
	        return false;
	    }
	    
	  /*
	   * Suppose a sorted array is rotated at some pivot unknown to you beforehand.
	   * 
	   * (i.e., 0 1 2 4 5 6 7 might become 4 5 6 7 0 1 2).
	   * 
	   * Find the minimum element.
	   * 
	   * You may assume no duplicate exists in the array.
	   */
		/*Time O(logn) Space O(1)*/
	  public int findMin_Rotated(int[] num) {
	      //check
		  if(null == num)
			  return Integer.MIN_VALUE;
		  
		  int low = 0;
		  int high = num.length - 1;
		  int mid = 0;
		  
		  while(low < high){
			  mid = low + ( (high - low) >> 1);
			  if(num[low] <= num[mid]){
				  if(num[low] > num[high])
					  low = mid+1;
				  else
					  return num[low];
			  }else{ // num[left] > num[mid] and num[mid] < num[right])
				  high = mid;  
			  }
		  }
		  
		  return num[low];
	  }
	  
	  
	    public int findMin_Rotated_n(int[] num) {
	        //check
	        if(null == num || 0 == num.length){
	            return Integer.MIN_VALUE; // error code
	        }
	        
	        int low = 0;
	        int high = num.length - 1;
	        int mid;
	        
	        while(low < high){
	            mid = low + ((high - low) >> 1);
	            
	            if(num[mid] < num[high]){
//	                if(num[low] <= num[mid]){
//	                    return num[low];
//	                }
	                high = mid;
	            }else{
	                low = mid + 1; 
	            }
	        }
	        
	        return num[low];
	    }
	    
	  /*
	   * Suppose a sorted array is rotated at some pivot unknown to you beforehand.
	   * 
	   * (i.e., 0 1 2 4 5 6 7 might become 4 5 6 7 0 1 2).
	   * 
	   * Find the minimum element.
	   * 
	   * The array may contain duplicates.
	   * 
	   * (10,1, 10, 10, 10)  (10,10,10,1,10)
	   */
	  public int findMin_RotatedWithDupl(int[] num) {
	      //check
		  if(null == num)
			  return Integer.MIN_VALUE;
		  
		  int left = 0;
		  int right = num.length - 1;
		  int mid = 0;
		  
		  while(left < right){
			  mid = left + ( (right - left) >> 1);
			  if(num[left] < num[mid]){
				  if(num[left] >= num[right])
					  left = mid+1;
				  else if(num[left] < num[right])
					  return num[left];				  
					  
			  }else if (num[left] > num[mid]){ 
				  right = mid;  
			  }else{ //  num[left] == num[mid]
				  if(num[left] > num[right])
					  left = mid+1;
				  else if(num[left] < num[right])
					  right = mid;	
				  else 
					  left ++;
			  }
		  }
		  
		  return num[left];
	  }
	  
	  public int findMin_RotatedWithDupl_2(int[] num) {
		    if(num == null || num.length==0)
		        return 0;
		    int l = 0;
		    int r = num.length-1;
		    int min = num[0];
		    while(l<r-1) {
		        int m = (l+r)/2;
		        if(num[l]<num[m]) {
		            min = Math.min(num[l],min);
		            l = m+1;
		        } else if(num[l]>num[m]) {
		            min = Math.min(num[m],min);
		            r = m-1;
		        } else {
		            l++;
		        }
		    }
		    min = Math.min(num[r],min);
		    min = Math.min(num[l],min);
		    return min;
		}
	  
	  public int findMin_RotatedWithDupl_n(int[] num) {
		  //check
		  if(null == num || 0 == num.length){
			  return Integer.MIN_VALUE;
		  }
		  
		  int low = 0;
		  int high = num.length - 1;
		  int mid;
		  
		  while(low < high){
			  mid = low + ((high - low) >> 1);
			  if(num[mid] < num[high]){
				  high = mid;
			  }else if(num[mid] > num[high]){
				  low = mid + 1;
			  }else{
				  high--;
			  }
		  }
		  
		  return num[high];
	  }
	  
	public static void main(String[] args) {
		RotatedArray sv = new RotatedArray();
	    int[][] arr = {
	    		{1}, 
	    		{1,3}, 
	    		{3,1}, 
	    		{1,3,5}, 
	    		{3,5,1}, 
	    		{5,1,3}, 
	    		{1,3,5,6},
	    		{3,5,6,1},
	    		{5,6,1,3},
	    		{6,1,3,5},
	    		{1,3,5,6,7},
	    		{3,5,6,7,1},
	    		{5,6,7,1,3},
	    		{6,7,1,3,5},
	    		{7,1,3,5,6},
	       };
	    int[] num = {0, 1, 2, 3, 4, 5, 6, 7
	        , 0, 1, 2, 3, 4
	        , 0, 1, 2};

	    for(int i=0; i<arr.length; i++){
	      System.out.println("Input: " + Misc.array2String(arr[i]));
	      System.out.println("Output: " + sv.findMin_Rotated_n(arr[i]));
	    }

	}

}
