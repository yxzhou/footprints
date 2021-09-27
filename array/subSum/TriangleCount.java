package array.subSum;

import java.util.Arrays;

public class TriangleCount {

    /**
     * Given an array of integers, how many three numbers can be found in the array,
     * so that we can build an triangle whose three edges length is the three numbers that we find?
     *
     * Example
     * Given array S = [3,4,6,7], return 3. They are:
     * [3,4,6]
     * [3,6,7]
     * [4,6,7]
     *
     * Given array S = [4,4,4,4], return 4. They are:
     * [4(1),4(2),4(3)]
     * [4(1),4(2),4(4)]
     * [4(1),4(3),4(4)]
     * [4(2),4(3),4(4)]
     */
    /* Time O(nlogn) + O(n^3), Space O(1) */
    public int triangleCount(int nums[]) {
        // check
        if (null == nums || 0 == nums.length) {
            return 0;
        }

        Arrays.sort(nums);

        long sum;
        int count = 0;
        for (int i = 0; i < nums.length - 2; i++) {
            for (int j = i + 1; j < nums.length - 1; j++) {
                sum = nums[i] + nums[j];

                for (int k = j + 1; k < nums.length; k++) {
                    if (sum > nums[k]) {
                        count++;
                    } else {
                        break;
                    }
                }
            }
        }

        return count;
    }

    /**
     * refer to TwoSumClosest.twoSumBiggerThan
     * Time O(nlogn) + O(n^2), Space O(1)
     */
    public int triangleCount_n(int nums[]) {
        if (null == nums || 0 == nums.length) {
            return 0;
        }

        Arrays.sort(nums);

        long sum;
        int count = 0;
        for (int k = 2; k < nums.length; k++) {

            int left = 0;
            int right = k - 1;

            while (left < right) {
                sum = nums[left] + nums[right];

                if (sum > nums[k]) {
                    count += right - left;

                    right--;
                } else {
                    left++;
                }
            }
        }

        return count;
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
