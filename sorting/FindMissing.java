package fgafa.sorting;

public class FindMissing
{

  /**
   * Given an array a[], the array should be filled from 0 to n-1, while now one num missed, 
   * please find it out.
   * 
   * Example
   * input [0,1,3,4] , output 2 
   * 
   */
  
  public int findMissing_wrong(int[] arr){
    //check input
    if(arr == null || arr.length == 0)
      return -1; //error code
    
    int left = 0, right = arr.length - 1, mid;
    while(left <= right){
      mid = left + ((right - left) >> 1);
      
      if(mid == 0 && arr[mid] != 0 )
        return mid;
      if(mid > 0 && arr[mid - 1] != arr[mid] - 1)
        return arr[mid] - 1;
      if(mid < arr.length - 1 && arr[mid + 1] != arr[mid] + 1)      
        return arr[mid] + 1;
      
      if(arr[mid] == mid)
        left = mid + 1;
      else 
        right = mid - 1;
    }
    
    return -1;
  }
  
  
  /**    
   * @param nums: an array of integers
   * @return: an integer
   */
  public int findMissing_n(int[] nums) {
      for(int i = 0, end = nums.length; i < end; ){
          if(i != nums[i] && nums[i] >= 0 && nums[i] < end && nums[i] != nums[nums[i]]){
              //swap
              int tmp = nums[i];
              nums[i] = nums[tmp];
              nums[tmp] = tmp;
          } else{
              i++;
          }
      }

      for(int i = 0, end = nums.length; i < end; i++ ){
          if(i != nums[i]){
              return i;
          }
      }

      return nums.length;
  }

  
  /**
   * 
   * Solution: 
   * suppose int[] nums is a1, a2, ---, a(i-1),a(i+1) ---, an
   * define, 
   *   x1 = a1 ^ a2 ^ --- a(i-1) ^ a(i+1)^ ---an
   *   x2 = a1 ^ a2 ^ --- a(i-1) ^ ai ^ a(i+1)^ ---an = x1 ^ ai
   *   x2 = x1 ^ ai  => ai = x1 ^ x2
   * 
   */
  public int findMissing_XOR(int[] nums) {
	  if(null == nums || 0 == nums.length){
		  return 0; // error code
	  }

      // get xor from 0 to N excluding missing number
      int x1 = 0;
      for (int i : nums) {
          x1 ^= i;
      }

      // get xor from 0 to N
      int x2 = 0;
      for (int i = 0; i <= nums.length; i++) {
          x2 ^= i;
      }

      int missing = x1 ^ x2;
      return missing == 0 ? nums.length : missing;
  }

    public int findMissing_XOR_n(int[] nums) {
        int result = nums.length;

        for(int i = 0, end = result; i < end; i++){
            result ^= i;
            result ^= nums[i];
        }

        return result;
    }
  
  /**
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub

  }

}
