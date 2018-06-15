package fgafa.todo.goo;

/**
 * _http://www.jiuzhang.com/solution/single-number-iv
 *
 *
 * Test cases:
 * --bad input--
 * null
 * {1,1}
 * --right input--
 * {1}
 * {1,2,2}
 * {1,1,2}
 * {1,2,2,3,3}
 * {1,1,2,3,3}
 * {1,1,2,2,3}
 *
 * Solutions:
 *
 */

public class SingleNumberIV {

    public int getSingleNumberIV(int[] nums){
        if(null == nums || 0 == (nums.length & 1)){
            throw new IllegalArgumentException("Can't find out the single number.");
        }

        int left = 0;
        int right = nums.length - 1;
        int mid;

        while(left < right){
            mid = left + (right - left) / 2;

            if(nums[mid] != nums[mid - 1] && nums[mid] != nums[mid + 1]){
                return nums[mid];
            }else{
                if(((mid - left) & 1) == 0){
                    if(nums[mid] == nums[mid + 1]){
                        left = mid + 2;
                    }else{
                        left = mid + 1;
                    }
                }else{
                    if(nums[mid] == nums[mid + 1]){
                        right = mid - 1;
                    }else{
                        right = mid - 2;
                    }
                }
            }
        }

        return nums[left];
    }
}
