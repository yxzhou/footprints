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

public class ThreeSum {

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
        if (numbers == null) {
            return Collections.EMPTY_LIST;
        }

        Arrays.sort(numbers);

        List<List<Integer>> triplets = new LinkedList<>();

        int sum = 0;
        for (int i = 0, n = numbers.length; i < n; i++) {
            if (numbers[i] > 0) {
                break;
            }
            if (i > 0 && numbers[i] == numbers[i - 1]) {
                continue;
            }

            for (int l = i + 1, r = n - 1; l < r;) {
                sum = numbers[i] + numbers[l] + numbers[r];

                if (sum == 0) {
                    triplets.add(Arrays.asList(numbers[i], numbers[l], numbers[r]));

                    l++;
                    r--;
                    while (l < r && numbers[l] == numbers[l - 1]) {
                        l++;
                    }
                    while (l < r && numbers[r] == numbers[r + 1]) {
                        r--;
                    }

                } else if (sum < 0) {
                    l++;
                } else {
                    r--;
                }
            }
        }

        return triplets;
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
   * Time O(nlogn) + O(n^2), Space O(1)
   *
   */
    public int sumOf3Closest(int[] num, int target) {
        assert num == null && num.length < 3;

        Arrays.sort(num);

        long min = Integer.MAX_VALUE;
        long result = 0;

        long sum;
        long diff;
        for (int i = 0, n = num.length; i < n; i++) {
            // avoid duplicate solutions
            if (i > 0 && num[i] == num[i - 1]) {
                continue;
            }

            for (int l = i + 1, r = n - 1; l < r;) {
                sum = (long) num[i] + num[l] + num[r];
                diff = sum - target;

                if (diff == 0) {
                    return target;
                }

                if (diff < 0) {
                    l++;
                    while (l < r && num[l] == num[l - 1]) {
                        l++;
                    }

                    diff = -diff;
                } else {
                    r--;
                    while (l < r && num[r] == num[r + 1]) {
                        r--;
                    }
                }

                if (diff < min) {
                    min = diff;
                    result = sum;
                }
            }
        }

        return (int) result;
    }
  
    /**
     * Given an array of n integers nums and a target, find the number of index triplets i, j, k with 0 <= i < j < k < n
     * that satisfy the condition nums[i] + nums[j] + nums[k] < target.
     * 
     * Example1
     * Input: nums = [-2,0,1,3], target = 2 
     * Output: 2 
     * Explanation: Because there are two triplets which sums are less than 2: [-2, 0, 1] [-2, 0, 3] 
     * 
     * Example2 
     * Input: nums = [-2,0,-1,3], target = 2 
     * Output: 3 
     * Explanation: Because there are three triplets which sums are less than 2: [-2, 0, -1] [-2, 0, 3] [-2, -1, 3]
     *
     * Follow up: Could you solve it in O(n^2) runtime
     * 
     * Thoughts:
     *  m1) naive, check the sum of all triples. It takes time O(n^3)
     *  m2) sorted + two points.  
     *      Time O(nlogn) + O(n^2), Space O(1)
     */
    public int threeSumSmaller(int[] nums, int target) {
        if (null == nums) {
            return 0;
        }

        Arrays.sort(nums);

        int sum = 0; //result
        int diff1;
        int diff2;
        for (int i = 0, n = nums.length; i < n; i++) {
            diff1 = target - nums[i];
            for (int l = i + 1, r = n - 1; l < r; l++) {
                diff2 = diff1 - nums[l];

                while (l < r && nums[r] >= diff2) {
                    r--;
                }

                sum += r - l;
            }
        }

        return sum;
    }
  
    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
