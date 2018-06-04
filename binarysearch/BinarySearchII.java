package fgafa.binarysearch;



import fgafa.util.Misc;

public class BinarySearchII
{

  /*
   * Given a string of sorted integers, e.g. "1 52 69 456789 994546566";
   * and a  a number e.g. 69.
   * You need to tell if it is in the input, e.g. 69=>true.
   */
  
  public boolean SearchString(String s, int n){
    int mid = s.length();
    
    
    return false;
  }
  
 
  
  /**
   * 
   * @param nums, a sorted array
   * @param target
   * @return if found, return the index of the target in the array,  or return the close position, example
   *   nums[i] < target < nums[i+1] return i. 
   * 
   * 
   */
  public int search(int[] nums, int target){
      //check
      if(null == nums || 0 == nums.length ){
          return -1;
      }

      int low = 0;
      int high = nums.length - 1;
      int mid;
      while(low <= high){
          mid = low + ((high - low) >> 1);
          
          if(nums[mid] == target){
              return mid;
          }else if(nums[mid] < target){
              low = mid + 1;
          }else{ // >
              high = mid - 1;
          }
      }
      
      return low - 1;
  }
  
  
  /*
   * 
   * Given a sorted array and a target value, return the index if the target is found. 
   * If not, return the index where it would be if it were inserted in order.
   * 
   * You may assume no duplicates in the array.
   * 
   * Here are few examples.
   * [1,3,5,6], 5 → 2
   * [1,3,5,6], 2 → 1
   * [1,3,5,6], 7 → 4
   * [1,3,5,6], 0 → 0
   * 
   */
  public int searchInsert(int[] seqNum, int target) {
    //initial
    int returnValue = 0;
    
    //check
    if( seqNum == null || seqNum.length == 0 ) 
      return returnValue;
    
    int left = 0;
    int right = seqNum.length - 1;
    int mid = 0;
    
    //if(target > seqNum[right])
    //  return right + 1;
    //if(target <= seqNum[left])
    //  return left;
    
    while( left <= right){
      mid = left + ((right - left) >> 1);  // Avoid overflow, (left + right )/2 
      if(seqNum[mid] == target){
          return mid;
      } else if( seqNum[mid] > target ){
        right = mid-1;
      } else{
        left = mid+1;    
      }
    }
        
    if(seqNum[mid] < target)
        return mid+1;
    else
        return mid;
  }
  
  /** 
   * param A : an integer sorted array
   * param target :  an integer to be inserted
   * return : an integer
   */
  public int searchInsert_n(int[] A, int target) {
      // check
      if(null == A || 0 == A.length){
          return 0;
      }
      
      int low = 0;
      int high = A.length;
      int mid;
      while(low < high){
          mid = low + ((high - low) >> 1);
          
          if(target == A[mid]){
              return mid;
          }else if ( target < A[mid] ){
              high = mid - 1;
          }else{
              low = mid + 1;
          }
      }
      
      return low;
  }
  
  
  /**
   * @param args
   */
  public static void main(String[] args) {

    int[][] arr = {
                {1,3,5,6}
                ,{1, 3}
                ,{1} 
               };
    int[][] num = {
                {0, 1, 2, 3, 4, 5, 6, 7}
                , {0, 1, 2, 3, 4}
                , {0, 1, 2}
                };

    BinarySearchII sv2 = new BinarySearchII();
    BinarySearch sv = new BinarySearch();
    
    int x;
    for(int i=0; i<arr.length; i++){
        for(int target : num[i]){
            System.out.println("\nInput: " + Misc.array2String(arr[i]) + " " + target);
            System.out.println(String.format("The target is between %d and %d: ", x = sv2.search(arr[i], target), x + 1));
            System.out.println(String.format("The target can be inserted at: %d, %d", sv2.searchInsert(arr[i], target), sv2.searchInsert_n(arr[i], target)));

            System.out.println(String.format("findTheInteger: %d ", sv.locate(arr[i], target)));
            System.out.println(String.format("lowerBound: %d ", sv.lowerBound(arr[i], target)));
            System.out.println(String.format("upperBound: %d ", sv.upperBound(arr[i], target)));
        }
    }
    

    
//    for(int i=0; i<arr.length; i++){
//        for(int target : num[i]){
//            System.out.println(String.format("\nInput: %s %d", Misc.array2String(arr[i]), target));
//            
//            System.out.println(String.format("findTheInteger: %d ", sv.locate(arr[i], target)));
//            System.out.println(String.format("lowerBound: %d ", sv.lowerBound(arr[i], target)));
//            System.out.println(String.format("upperBound: %d ", sv.upperBound(arr[i], target)));
//        }
//    }
  }

}
