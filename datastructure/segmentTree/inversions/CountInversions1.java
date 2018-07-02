package fgafa.datastructure.segmentTree.inversions;

/**
 * Q: Input an array, leftChildrenCount the inversions.
 *
 * Inversion Count for an array indicates:  how far (or close) the array is from being sorted.
 * If array is already sorted then inversion leftChildrenCount is 0. If array is sorted in reverse order that inversion leftChildrenCount is the maximum.
 *
 * Example:
 * Input: arr[] = {8, 4, 2, 1}
 * Output: 6
 *    because there are six inversions (8, 4) (8, 2) (8, 1) (4, 2) (4, 1) (2, 1)
 *
 * For simplicity, we may assume that all elements are unique.
 *
 *
 * Solution:
 *   To {8, 4, 2, 1}, check from right to left.
 *   to 1, the inversion is 0,
 *   to 2, the inversion is 1,
 *   to 4, the inversion is 2,
 *   to 8, the inversion is 3.
 *   so total it's 1+2+3 = 6
 *
 *   How to get inversion to 4?
 *   Naive method is check every one in 4's right, {2, 1}, it takes O(n).
 *   A better way is define a tree to store {start, end, leftChildrenCount}, how many numbers appear in this range. the space is O(n), the checkIn is O(logn)
 *              (1, 8)
 *           /         \
 *        (1, 4)      (5, 8)
 *        /   \        /   \
 *     (1, 2) (3, 4) (5,6) (7, 8)
 *

 */

public class CountInversions1 {
    /*Time Complexity O(nlogn)*/
    public int countInversions(int[] nums){
        if(null == nums){
            return -1;
        }else if(nums.length < 2){
            return 0;
        }


        int length = nums.length;
        int min = nums[0];
        int max = nums[0];
        for(int num : nums){
            if(num > max){
                max = num;
            }else if(num < min){
                min = num;
            }
        }

        int[] tree = new int[(max - min + 1) * 2 - 1];

        int result = 0;
        for(int i = length - 1; i >= 0; i--){
            result += addNodeAndCountInversions(tree, 0, min, max, nums[i]);
        }

        return result;
    }

    /*Time complexity O(logn)*/
    private int addNodeAndCountInversions(int[] tree, int nodeIndex, int start, int end, int currValue){
        if(start == end) {
            return 0; //if care about equal, do return tree[position].leftChildrenCount.
        }

        int leftSon = nodeIndex * 2 + 1;
        int middle = start + (end -start) / 2;

        if(middle >= currValue){
            tree[nodeIndex]++;
            return addNodeAndCountInversions(tree, leftSon, start, middle, currValue);
        }else {
            int result  = 0;
            result += tree[nodeIndex];
            result += addNodeAndCountInversions(tree, leftSon + 1, middle + 1, end, currValue);
            return result;
        }
    }

}
