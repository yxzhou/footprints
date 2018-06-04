package fgafa.sorting.intersection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import fgafa.util.Misc;

/**
 * Intersection of 2 array. 
 * Example:
 *   case1: strA = "ABC",  strB = "ECF"
 *   the intersection is at: C
 * 
 * 1 Find the intersection of 2 arrays,  A[m] and B[n]. 
 *  1.1, brute-force solution
 *   To every element in A[m], check if it's in B[n] (linear search). Time complexity is O(m*n)
 *  1.2, with hash cache,    
 *   hash cache B[n], to every element in A[m], check if it's in cache. Time complexity is O(m+n), Space is O(n)
 *   
 * 2 Find the intersection of 2 sorted arrays,  A[m] and B[n].   
 *  2.1, brute-force solution 
 *   To every element in A[m], check if it's in B[n] (binary search). Time complexity is O(m*logn)
 *  2.2, the above get benefit from one sorted array, how to get sorted from 2 arrays.
 *   Set 2 point, that both from the front of array. 
 *     If A[i] == B[j], return successfully
 *     else If A[i]  > B[j], check A[i] and B[j + 1],
 *     else check A[i+1] and B[j]
 *     
 *    Time complexity is O(m+n)
 *    
 * ?? Compare this approach with the binary search approach. O(m+n) and O(m*lg(n))
 * lg(n) is much smaller than n when n is very big. However, this does not necessarily means binary search is better in this case. In fact, binary search approach is only better when m << n (m is very small compared to n). 
 * For example, when m = 1 and n = one billion, which method will you choose? Binary search is definitely the winner here.
 * All of our above approaches assume that we have enough space to load both arrays to the memory. Here are some interesting questions to ponder about:
 *   i) What if elements of array B is stored on disk, and the memory is limited such that you cannot load all elements into the memory at once?
 *  ii) How will the complexity change in this case? Are there any factors you need to consider? 
 * iii) How do you change your solution to adapt to this situation?
 *        
 */

public class Intersection
{

  /*
   * Find the intersection of 2 sorted arrays,  A[m] and B[n]
   * 
   * Time O(m+n), Space O(1)
   */
  public int findIntersectionInSortedArray(int[] A, int[] B){
    //check
    if(A == null || B == null || A.length == 0 || B.length == 0) 
      //throw new IllegalArgumentException("-bla bla-");
      return Integer.MIN_VALUE;
    
    for(int i = 0, j = 0; i<A.length && j<B.length; ){
      if(A[i] == B[j])
        return A[i];
      else if (A[i] > B[j])
        j++;
      else 
        i++;
        
    }
    
    return Integer.MIN_VALUE;
  }
  
  
  /**
   * 
   * Given two arrays, write a function to compute their intersection.

    Example:
    Given nums1 = [1, 2, 2, 1], nums2 = [2, 2], return [2].
    
    Note:
    Each element in the result must be unique.
    The result can be in any order.
   */
  //brute force, Time (m*n), Space O(1)
  //sorted, Time(nlogn), Space O(1)
  //hashmap,  Time O(n), Space O(n)
  public int[] intersection(int[] nums1, int[] nums2) {
      //check
      if(null == nums1 || null == nums2){
          return new int[0];
      }
      
      if(nums1.length > nums2.length){
          return intersection(nums2, nums1);
      }
      
      //nums1.length <= nums2.length
      Set<Integer> result = new HashSet<>();
      Set<Integer> set = new HashSet<>();
      for(int num : nums1){
          set.add(num);
      }
      for(int num : nums2){
          if(set.contains(num)){
              result.add(num);
          }
      }
      
      //return
      return set2Array(result);
  }
  
  private int[] set2Array(Set<Integer> result){
      int[] ret = new int[result.size()];
      int i = 0;
      for(int num : result){
          ret[i++] = num;
      }
      
      return ret;
  }
  
  /**
   * 
   * Given two arrays, write a function to compute their intersection.
    
    Example:
    Given nums1 = [1, 2, 2, 1], nums2 = [2, 2], return [2, 2].
    
    Note:
    Each element in the result should appear as many times as it shows in both arrays.
    The result can be in any order.
    
    Follow up:
    What if the given array is already sorted? How would you optimize your algorithm?
    What if nums1's size is small compared to nums2's size? Which algorithm is better?
    What if elements of nums2 are stored on disk, and the memory is limited such that you cannot load all elements into the memory at once?
   */
  public int[] intersect(int[] nums1, int[] nums2) {
      //check
      if(null == nums1 || null == nums2){
          return new int[0];
      }
      
      if(nums1.length > nums2.length){
          return intersection(nums2, nums1);
      }
      
      //nums1.length <= nums2.length
      List<Integer> result = new ArrayList<>();
      Map<Integer, Integer> map = new HashMap<>();
      for(int num : nums1){
          if(map.containsKey(num)){
              map.put(num, map.get(num) + 1);
          }else{
              map.put(num, 1);
          }
      }
      for(int num : nums2){
          if(map.containsKey(num)){
              if(map.get(num) == 1){
                  map.remove(num);
              }else{
                  map.put(num, map.get(num) - 1);
              }
              result.add(num);
          }
      }
      
      //return
      return list2Array(result);
  }
  
  private int[] list2Array(List<Integer> result){
      int[] ret = new int[result.size()];
      int i = 0;
      for(int num : result){
          ret[i++] = num;
      }
      
      return ret;
  }
  
    /**
     * Follow up:
     * 
    What if the given array is already sorted? How would you optimize your algorithm?
    Solution 1, i.e., sorting,  would be better since it does not need extra memory. 
    
    What if nums1's size is small compared to num2's size? Which algorithm is better?
    If two arrays are sorted, we could use binary search, i.e., for each element in the shorter array, search in the longer one. 
    So the overall time complexity is O(nlogm), where n is the length of the shorter array, and m is the length of the longer array. 
    Note that this is better than Solution 1 since the time complexity is O(n + m) in the worst case. 
    
    What if elements of nums2 are stored on disk, and the memory is limited such that you cannot load all elements into the memory at once?
    If the two arrays have relatively the same length, we can use external sort to sort out the two arrays in the disk. 
    Then load chunks of each array into the memory and compare, by using the method 1. 
    If one array is too short while the other is long, in this case, if memory is limited and nums2 is stored in disk, 
    partition it and send portions of nums2 piece by piece. keep a pointer for nums1 indicating the current position, 
    and it should be working fine~
    Another method is, store the larger array into disk, and for each element in the shorter array, 
    use "Exponential Search" and search in the longer array. 
     */
  
  public static void main(String[] args) {
    Intersection s = new Intersection();
    
    int[][] A = {null, {},{1}, {1, 2, 3},{1, 2, 3}, {1, 3, 5} };
    int[][] B = {{},   {},{2}, {4},      {3},       {2, 5, 6} };

    for(int i=0; i< A.length; i++){
      System.out.println("The intersection of "+ Misc.array2String(A[i]) +" and "+ Misc.array2String(B[i]) + " is at: " + s.findIntersectionInSortedArray(A[i], B[i]));
       
    }
  }

}
