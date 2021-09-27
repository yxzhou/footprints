package array.subSum;

import java.util.*;


/**
 * Given a set S of n integers, are there elements a, b, c in S such that a + b + c = sum? 
 *
 * For example, given set S = {-1 0 1 2 -1 -4} and sum=0, One possible solution set is: (-1, 0, 1) and (-1, 2, -1)
 * 
 * Solution:
 * 1) make S in ascending sorting.   
 * 2) using two index first and last, i and j, each pointing to the first and last element, 
 *  using third index p, pointing to the element from i and j. 
 * 3) if (s[i] + s[j]) + s[p] = sum, successfully.  p++, j--
 *    else if (s[i] + s[j]) + s[p] > sum, j -- ;
 *    else if (s[i] + s[j]) + s[p] < sum, p ++ ;
 * 
 * Time,  O(nlogn + n*n)
 * 
 * Further: 
 * 1) in step#3, when s[i]+s[j] is same, we can do binary search to locate p.  O(n*logn)  
 * 2) There is duplicate in S, and the solution should not be duplicate. 
 *   1.1) in step#3 involve a Set<> to filter the solution 
 *   1.2) after step#1, if there are 3 s[i], and 3*s[i] = sum, successfully,  remove all s[i] from S, else keep 2 s[i].  
 *   check (s[i] + s[i]) + s[x] = sum.
 *        if equal, successfully,  
 *        just keep 1 s[i] in S   
 */

public class ThreeSum
{
  
  /**
   * Given an array S of n integers, are there elements a, b, c in S such that a + b + c = 0?
   * Find all unique triplets in the array which gives the sum of zero.
   *
   * Example,
   * Given array S = {-1 0 1 2 -1 -4},
   * Return:
   *   (-1, 0, 1)
   *   (-1, -1, 2)
   *
   * Note:
   * Elements in a triplet (a,b,c) must be in non-descending order. (ie, a ≤ b ≤ c)
   * The solution set must not contain duplicate triplets.
   *
   * Time O(nlogn) + O(n^2), Space O(1)
   */
    public List<List<Integer>> sumOf3(int[] numbers) {
        if(numbers == null || numbers.length < 3){
            return Collections.EMPTY_LIST;
        }

        Arrays.sort(numbers);

        List<List<Integer>> r = new LinkedList<>();
        int target; //remain
        int sum;
        for(int i = 0, end = numbers.length - 2; i < end;  ){
            target = 0 - numbers[i];
            for(int j = i + 1, k = numbers.length - 1; j < k; ){
                sum = numbers[j] + numbers[k];
                if( sum == target ){
                    r.add(Arrays.asList(new Integer[]{ numbers[i],numbers[j],numbers[k] }));
                    for( j++; j < k && numbers[j] == numbers[j - 1]; j++);
                }else if( sum < target ){
                    j++;
                }else{
                    k--;
                }
            }

            for( i++; i < end && numbers[i] == numbers[i - 1]; i++);
        }

        return r;
    }

	  
  /**
   * 
   * Given an array S of n integers, find three integers in S such that the sum is closestPair to a given number, target.
   * Return the sum of the three integers. 
   * You may assume that each input would have exactly one solution.
   * e.g: 
   *   input:  S = {-1 2 1 -4}, and target = 1.
   *   output: The sum that is closestPair to the target is 2. (-1 + 2 + 1 = 2).
   * 
   */
    /*Time O(nlogn) + O(n^2), Space O(1)*/
    public int sumOf3Closest(int[] num, int target) {

        int min = Integer.MAX_VALUE;
        int result = 0;

        Arrays.sort(num);

        for (int i = 0; i < num.length - 2; i++) {
            // avoid duplicate solutions
            if (i > 0 && i < num.length - 2 && num[i] == num[i - 1]) {
                i++;
            }

            for (int j = i + 1, k = num.length - 1; j < k;) {
                int sum = num[i] + num[j] + num[k];
                int diff = Math.abs(sum - target);

                if (diff == 0){
                    return target;
                }

                if (diff < min) {
                    min = diff;
                    result = sum;
                }

                if (sum < target) {
                    j++;
                } else {
                    k--;
                }
            }
        }

        return result;
    }
  
  /**
   * Given an array of n integers nums and a target, find the number of index 
   * triplets i, j, k with 0 <= i < j < k < n that satisfy the condition nums[i] + nums[j] + nums[k] < target.

    For example, given nums = [-2, 0, 1, 3], and target = 2.
    
    Return 2. Because there are two triplets which sums are less than 2:
    
    [-2, 0, 1]
    [-2, 0, 3]
    Follow up: Could you solve it in O(n^2) runtime
   */
  /*Time O(nlogn) + O(n^2), Space O(1)*/
    public int threeSumSmaller(int[] nums,int target) {
        // check
        if (null == nums || nums.length < 3) {
            return 0;
        }

        Arrays.sort(nums);

        int result = 0;
        int diff;

        for (int i = 0; i < nums.length - 2; i++) {
            diff = target - nums[i];
            for (int left = i + 1, right = nums.length - 1; left < right;) {
                if (nums[left] + nums[right] >= diff) {
                    right--;
                } else {
                    result += right - left;
                    left++;
                }
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
