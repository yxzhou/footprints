package fgafa.datastructure.segmenttree.Fenwick;

/**
 * Q: Input an array, count the inversions.
 *
 * Inversion Count for an array indicates:  how far (or close) the array is from being sorted.
 * If array is already sorted then inversion count is 0. If array is sorted in reverse order that inversion count is the maximum.
 *
 * Example:
 * Input: arr[] = {8, 4, 2, 1}
 * Output: 6
 *    because there are six inversions (8, 4) (8, 2) (8, 1) (4, 2) (4, 1) (2, 1)
 *
 * For simplicity, we may assume that all elements are unique.
 *
 *
 *  Note:  it's same as CountOfSmallerNumberAfterItself
 */

public class CountInversions {


    /*Time Complexity O(nlogn)*/
    public int countInversions(int[] nums){
        if(null == nums){
            return -1;
        }else if(nums.length < 2){
            return 0;
        }

        int count = 0;
        int end = nums.length - 1;
        BINode root = new BINode(nums[end], 1);

        for(int i = end - 1; i >= 0; i--){
            count += addAndCountInversions(nums[i], root);
        }

        return count;
    }

    /*Time complexity O(logn)*/
    private int addAndCountInversions(int currValue, BINode root){
        if(currValue == root.value){
            root.countOfEqualAndSmaller += 1;

            return null == root.left ? 0 : root.left.countOfEqualAndSmaller;
        }

        int result  = 0;

        if(currValue < root.value){
            root.countOfEqualAndSmaller ++;

            if(null == root.left){
                root.left = new BINode(currValue, 1);
            }else{
                result += addAndCountInversions(currValue, root.left);
            }
        }else{
            result += root.countOfEqualAndSmaller;

            if(null == root.right){
                root.right = new BINode(currValue, 1);
            }else{
                result += addAndCountInversions(currValue, root.right);
            }
        }

        return result;
    }

    class BINode{
        int value;
        int countOfEqualAndSmaller; //

        BINode left = null; //smaller
        BINode right = null; //bigger

        BINode(int value, int countOfEqualAndSmaller){
            this.value = value;
            this.countOfEqualAndSmaller = countOfEqualAndSmaller;
        }
    }
}
