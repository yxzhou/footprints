package fgafa.binarysearch;

/**
 * A peak element is an element that is greater than its neighbors.
 * 
 * Given an input array where num[i] ≠ num[i+1], find a peak element and
 * return its index.
 * 
 * The array may contain multiple peaks, in that case return the index to
 * any one of the peaks is fine.
 * 
 * You may imagine that num[-1] = num[n] = -∞.
 * 
 * For example, in array [1, 2, 3, 1], 3 is a peak element and your function
 * should return the index number 2.
 */


/**
 * 
 * There is an integer array which has the following features:

	The numbers in adjacent positions are different.
	A[0] < A[1] && A[A.length - 2] > A[A.length - 1].
	We define a position P is a peek if:
	
	A[P] > A[P-1] && A[P] > A[P+1]
	Find a peak element in this array. Return the index of the peak.
	
	Example
	Given [1, 2, 1, 3, 4, 5, 7, 6]
	
	Return index 1 (which is number 2) or 6 (which is number 7)
	
	Note
	The array may contains multiple peeks, find any of them.
	
	Challenge
	Time complexity O(logN)
 *
 */
public class PeakElement {


  public int findPeakElement(int[] num) {
      //check
	  if(null == num || 0 ==  num.length)
		  return -1;
	  
      return findPeakElement(num, 0, num.length - 1);
  }
  
  private int findPeakElement(int[] num, int left, int right){	  
	  int mid = left + ((right - left) >> 1);
	  
	  if(( mid == 0 || num[mid] > num[mid-1] ) &&
		  (mid == num.length - 1 || num[mid] > num[mid+1]) ){
		  return mid;
	  }
	  
	  if(num[mid] < num[mid+1])
		  return findPeakElement(num, mid+1, right);
	  else //num[mid] < num[mid-1]
		  return findPeakElement(num, left, mid-1);
  }
	
  /**
   * @param A: An integers array.
   * @return: return any of peek positions.
   */
  public int findPeak(int[] A) {
      //check
      if(null == A || 3 > A.length){
          return -1; // error code
      }
      
      int low = 1;
      int high = A.length - 2;
      int mid;
      
      int diff1, diff2;
      while(low < high){
          mid = low + ((high - low) >> 1);
          
          diff1 = A[mid] - A[mid - 1];
          diff2 = A[mid] - A[mid + 1];
          
          if(diff1 > 0 && diff2 > 0){
              return mid;
          }else if(diff1 < 0){
              high = mid - 1;
          }else{
              low = mid + 1;
          }
      }
      
      return low;
  }
}
