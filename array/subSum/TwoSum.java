package fgafa.array.subSum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*
 * Given a set S of n integers, find all pairs of integers (a and b) in S such that a + b = target?
 * 
 * Solution:    (pre-process, without cache)
 * 1) make S in ascending sorting.   
 * 2) using two index first and last, i and j, each pointing to the first and last element, 
 * 3) if s[i] + s[j] = sum, successfully.  
 *    else if s[i] + s[j] > sum, j -- ;
 *    else if s[i] + s[j] < sum, i ++ ;
 * 
 * Time,  O(nlogn + n) Space O(1)
 * 
 * Solution 2:  (with cache)
 * 1) put n in Hashtable or bitmap. ( it will filter the duplicate )
 * 2) to every s[i], to see if there is sum - s[i] in the Hashtable or bitmap. 
 * 
 * Time O(n)  Space O(n)
 * 
 * Further: 
 * 1) There is duplicate in S, and the solution should not be duplicate. 
 *   1.1) in step#3 involve a Set<> to filter the solution 
 *   1.2) after step#1, get all duplicate s[i] and put all unique in S',
 *    compare (s[i] + s[i], sum)
 *        if equal, successfully,  remove all s[i] from S
 *        if not, just keep 1 s[i] in S   
 *        
 */

public class TwoSum{
  
  /*
   * @return all pairs of integers (a and b) in S such that a + b = target, no duplicate pairs.
   * 
   * Time O(nlogn+n)  Space O(1)
   */
  public List<List<Integer>> twoSums(int[] numbers, int target) {
	  List<List<Integer>> ret = new ArrayList<List<Integer>>();
    //check
    if(numbers == null || numbers.length == 0)
      return ret;
    
    //sorted
    Arrays.sort(numbers);
    
    //
    int left = 0;
    int right = numbers.length - 1;
    int sum;
    while(left < right){
      sum = numbers[left] + numbers[right]; 
      if( sum == target){
    	List<Integer> pair = new ArrayList<Integer>();
        ret.add(pair);
        pair.add(numbers[left]);
        pair.add(numbers[right]);
        
        while(left < right && numbers[left] == numbers[++left]);
        while(left < right && numbers[right] == numbers[--right]);
      }else if ( sum < target )
        left ++;
      else //sumTmp > target
        right --;
    }
    
    return ret;
  }
  
  
  /*
   * @return all pairs of integers (a and b) in S such that a + b = target, no duplicate pairs.
   * 
   * Time O(n)  Space O(n)
   */
  public Set<List<Integer>> twoSums_Hash(int[] numbers, int target) {
      //init
      Set<List<Integer>> ret = new HashSet<List<Integer>>();      
	  //check
      if(null == numbers || 0 == numbers.length){
    	  return ret;
      }
    	  
      Hashtable<Integer/*number*/, Integer/*count*/> ht = new Hashtable<>();
      for(int num : numbers){
    	  ht.put(num, ht.containsKey(num)? ht.get(num) + 1 : 1 );
      }  	  
      
      int halfTarget = (target >> 1);
      if(target != (halfTarget << 1)){
    	  halfTarget = Integer.MIN_VALUE;
      }
      
      for(int num : ht.keySet()){
    	  if( num == halfTarget && ht.get(num) > 1 ){
    		  ret.add(build(num, num));
    	  }else if ( ht.containsKey(target - num) ){
    		  ret.add(build(num, target - num));
    	  }
      }
      
      return ret;
  }
  
  private List<Integer> build(int i, int j){
	  List<Integer> pair = new ArrayList<>();
	  pair.add(i);
	  pair.add(j);
	  return pair;
  }
  
/**
 * 
 * Given an array of integers, find two numbers such that they add up to a
 * specific target number.
 * 
 * The function twoSum should return indices of the two numbers such that
 * they add up to the target, where index1 must be less than index2. Please
 * note that your returned answers (both index1 and index2) are not
 * zero-based.
 * 
 * You may assume that each input would have exactly one solution.
 * 
 * Input: numbers={2, 7, 11, 15}, target=9 Output: index1=1, index2=2
 */
  /* Time O(n)  Space O(n)*/
  public int[] twoSum_hash(int[] numbers, int target) {
      //check input,  bypass, I can assume that each input would have exactly one solution
      
      //put all in a hashtable, map<value, position>
      Map<Integer, Integer> map = new HashMap<>(); //<value, index>
      for(int i=0; i<numbers.length; i++){
          if(map.containsKey(target - numbers[i])){ 
              return new int[]{map.get(numbers[i]), i+1};
          }else{
              map.put(numbers[i], i+1);
          }
      }
      
      //return, for case: not found
      return new int[2];
  }
  
  /*Time O(nlogn)  Space O(1)*/
  public int[] twoSum_sorted(int[] numbers, int target) {
      int[] result = new int[2];
      
      if (numbers == null || numbers.length < 2) {
          return result;
      }
       
      //sorted
      Arrays.sort(numbers);
      
      int left = 0;
      int right = numbers.length - 1;
       
      int sum;
      while (left < right) {
          sum = numbers[left] + numbers[right];
                      
          if ( sum == target) {
              result[0] = left + 1;
              result[1] = right + 1;
              return result;
          } else if (sum > target) {
              right--;
          } else {
              left++;
          }
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
