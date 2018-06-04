package fgafa.todo;

import fgafa.util.Misc;

/**
 * Created by joeyz on 12/21/16.
 *
 * Given a integer array, implemnent a method, return true if the square of the minimum value is smaller than the maximum value.
 * or false.
 *
 * follow up 1, if you can delete the first element in the above array until the above method return true,
 * how many elements need be deleted?  return -1,
 *
 * follow up 2, if you can delete the elements in the above array from 2 sides, what is the minimum deletion?
 *
 */
public class MinAndMax {

    // is the minimum's square smaller than the maximum
    public boolean minSquaredSmallerThanMax(int[] nums){
        if(null == nums || nums.length < 2){
            return false;
        }

        //find the min and max
        int min = nums[0];
        int max = nums[0];

        for(int i = 1; i < nums.length; i++){
            if(nums[i] < min){
                min = nums[i];
            }else if(nums[i] > max){
                max = nums[i];
            }
        }

        long quare = (long) min * min;
        return quare >= max;
    }

    //how many deletion from left to make the minimum's square smaller than the maximum
    public int minDeletionFromHead(int[] nums){
        if(null == nums || nums.length < 2){
            return -1;
        }

        //check from the right to left
        long square = 0;
        int min = nums[nums.length - 1];
        int max = nums[nums.length - 1];
        int minDeletion = -1;
        for( int i = nums.length - 2 ; i >= 0;  i--){
            if(nums[i] > min && nums[i] < max){
                continue;
            }

            if(nums[i] < min){
                min = nums[i];
            }else if(nums[i] > max){
                max = nums[i];
            }

            square = (long)min * min;

            if(square < max){
                minDeletion = i;
            }
        }

        return minDeletion;
    }


    public int minDeletionFromTwoSides_dp(int[] nums) {
        if(null == nums || nums.length < 2){
            return -1;
        }

        int n = nums.length;
        int[] min = new int[n];
        int[] max = new int[n];
        int[] minDeletion = new int[n];

        for(int i = 0; i < n; i++){
            min[i] = nums[i];
            max[i] = nums[i];
            minDeletion[i] = -1;
        }

        int j = 0;
        long square = 0;
        for(int step = 1; step < n; step++){
            for(int i = 0; i < n - step; i++){

                j = i + step;
                if(nums[j] < min[i]){
                    min[i] = nums[j];
                }else if(nums[j] > max[i]){
                    max[i] = nums[j];
                }

                square = (long)min[i] * min[i];

                if(square < max[i]){
                    minDeletion[i] = 0;
                }else if(minDeletion[i] != -1 && minDeletion[i+ 1] != -1){
                    minDeletion[i] = 1 + Math.min(minDeletion[i], minDeletion[i + 1]);
                }else if(minDeletion[i] != -1){
                    minDeletion[i] = 1 + minDeletion[i];
                }else if(minDeletion[i + 1] != -1){
                    minDeletion[i] = 1 + minDeletion[i + 1];
                }//else if(minDeletion[i] == -1 && minDeletion[i+ 1] == -1)

            }
        }

        return minDeletion[0];
    }





    public int minDeletionFromTwoSides(int[] nums){
        if(null == nums || nums.length < 2){
            return -1;
        }

        return nums.length - maxSubsequenceLength(nums, 0, nums.length - 1, 0);
    }

    private int maxSubsequenceLength(int[] nums, int left, int right, int currMaxLength){
        if(currMaxLength > right - left){
            return currMaxLength;
        }

        //find the maximum element in current sub array
        int maxIndex = left;
        for(int i = left + 1; i <= right; i++){
            if(nums[maxIndex] < nums[i]){
                maxIndex = i;
            }
        }

        //count from the max to right, and from the max to left
        int count = 1;
        for(int i = maxIndex + 1; i <= right; i++ ){
            if((long)nums[i] * nums[i] >= nums[maxIndex]){
                break;
            }

            count++;
        }

        for(int i = maxIndex - 1; i >= left; i-- ){
            if((long)nums[i] * nums[i] >= nums[maxIndex]){
                break;
            }

            count++;
        }

        currMaxLength = Math.max(count, currMaxLength);
        int leftMaxLength = maxSubsequenceLength(nums, left, maxIndex, currMaxLength);
        int rightMaxLength = maxSubsequenceLength(nums, maxIndex, right, currMaxLength);
        return Math.max(currMaxLength, Math.max(leftMaxLength, rightMaxLength));
    }

    public static void main(String[] args){

        int min = Integer.MAX_VALUE;
        System.out.println( min);
        System.out.println( (long) min * min);


        int[][] input = {
            {-9, -8, -7, 4, 25, -11, -20}  //false, -1, 5);
            ,{-9, -8, -7, 4, 25, -11, -2}  //false, -1, 5);
            ,{-9, -8, -7, 4, 25, -1, -2}  //false, -1, 3);
            ,{-9, -8, -7, 4, 25, -1}  //false, -1, 3);
            ,{-7, 4, 25}  //false, 1, 1);
            ,{-9, -8, -7, 4, 25}  //false, 3, 3);
            ,{-49, 1, 4, -1}  //false, -1, 1);
            ,{-7, 1, 2, -1}  //false, -1, 1);
            ,{-9, 5, -8, -7, 2}  //false, -1, -1);
            ,{-9, 3, 2, -8, -7, 5}  //false, -1, -1);
            ,{-9, 2, -8, -7, 5}  //false, -1, -1);
            ,{-9, 2, -8, -7, 2, 5}  //false, 4, 4);
            ,{-9, -8, -7, 2, 5}  //false, 3, 3);
            ,{ -7, 2, 5}  //false, 1, 1);
            ,{ 9, -7, 2, 5}  //false, 2, 2);
            ,{ 1, 2, 3, 4 }  //true, 0, 0);
            ,{ 5 }  //false, -1, -1);
            ,{ 1 }  //false, -1, -1);
            ,{ 5, 1, 3 }  //true, 0, 0);
            ,{ 1, 1, 1 }  //false, -1, -1);
            ,{ 5, 3, 1 }  //true, 0, 0);
            ,{ 2, 5, 3, 1 }  //true, 0, 0);
            ,{ -2 }  //false, -1, -1);
            ,{ 1, 5, 3 }  //true, 0, 0);

        };

        MinAndMax sv = new MinAndMax();

        for(int i = 0; i < input.length; i++){
            System.out.println(String.format("Input %s, \t Output %b", Misc.array2String(input[i]), sv.minSquaredSmallerThanMax(input[i])));

        }
    }

}
