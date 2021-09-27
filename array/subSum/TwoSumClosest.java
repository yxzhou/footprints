package array.subSum;

import java.util.Arrays;

public class TwoSumClosest {

    /**
     *   Problem:
     *   Given an array nums of n integers, find two integers in nums such that the sum is closestPair to a given number, target.
     *   Return the difference between the sum of the two integers and the target.
     *
     *   Example"
     *   Given array nums = [-1, 2, 1, -4], and target = 4.
     *   The minimum difference is 1. (4 - (2 + 1) = 1).
     */
    /*Time O(nlogn) + O(n), Space O(1)*/
    public int[] twoSumClosest(int[] numbers, int target) {
        int[] result = new int[2];
        if(numbers == null || numbers.length == 0){
            return result;
        }

        Arrays.sort(numbers);

        int left = 0;
        int right = numbers.length - 1;
        int minDistance = Integer.MAX_VALUE;
        while(left < right){
            int diff = numbers[left] + numbers[right] - target;

            if( Math.abs(diff) < minDistance) {
                  result[0] = numbers[left];
                  result[1] = numbers[right];

                  minDistance = Math.abs(diff);
            }

            if(diff == 0){
                return result;
            }else if (diff < 0 ){
                left ++;
            }else{
                right --;
            }
         }
         return result;
     }

    /**
     *   Problem:
     *   Given an array integer of n integers, find two integers in the array such that the sum is bigger than a given number, target.
     *   Return how many such pairs.
     */
    /*Time O(nlogn) + O(n^2), Space O(1)*/
    public int twoSumBiggerThan(int[] numbers, int target) {
        int count = 0;
        if(numbers == null || numbers.length == 0){
            return count;
        }

        Arrays.sort(numbers);

        int left = 0;
        int right = numbers.length - 1;
        while(left < right){
            int sum = numbers[left] + numbers[right];

            if (sum > target){
                count += 1;
                
                right--;
            } else {
                left++;
                
                right = numbers.length - 1;
            }
         }

         return count;
     }

    /*Time O(nlogn) + O(n), Space O(1)*/
    public int twoSumBiggerThan_n(int[] numbers, int target) {
        int count = 0;
        if(numbers == null || numbers.length == 0){
            return count;
        }

        Arrays.sort(numbers);

        int left = 0;
        int right = numbers.length - 1;
        while(left < right){
            int sum = numbers[left] + numbers[right];

            if (sum > target){
                count += right - left;
                
                right--;
            } else {  //sum <= target
                left++;
            }
         }

         return count;
     }


    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
