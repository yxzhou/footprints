package fgafa.array.subSum;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public final class KSum {
    
    /* Time O( n^(k-1) )*/
    public List<List<Integer>> kSum(int[] num, int k, int target){
        List<List<Integer>> result = new LinkedList<List<Integer>>();
        
        if(null == num || k < 1 || num.length <= k){
            return result;
        }
        
        if(k == 1){
            for(int n : num){



                if(n == target){
                    Set<Integer> tmp = new HashSet<>();
                    tmp.add(n);
                    result.add(new LinkedList<>(tmp));
                }
            }
            return result;
        }
        
        Arrays.sort(num);
        
        return kSum(num, k, target, 0);
    }

    private List<List<Integer>> kSum(int[] num, int length, int target, int start_index) {
        List<List<Integer>> result = new LinkedList<List<Integer>>();

        if (length == 2) {
            return twoSum(num, target, start_index);
        }
        
        for (int i = start_index; i < num.length - length + 1; i++) {
            if ( i > start_index && (num[i] == num[i - 1]) ) {
                continue;
            }
            for (List<Integer> partial_result : kSum(num, length - 1, target - num[i], i+1)) {
                partial_result.add(0, num[i]);
                result.add(partial_result);
            }
        }
        return result;
    }
 
    private List<List<Integer>> twoSum(int[] num, int target, int start_index) {
        List<List<Integer>> result = new LinkedList<List<Integer>>();
        
        for (int left=start_index, right = num.length - 1; right < left; ) {
            int sum = num[left] + num[right];
            if (sum == target ) {
                List<Integer> tmp = new LinkedList<>();
                tmp.add(num[left]);
                tmp.add(num[right]);
                result.add(tmp);
                
                do{
                    left++;
                }while(num[left] == num[left - 1]);
                    
                right--;
            }else if(sum < target){
                do{
                    left++;
                }while(num[left] == num[left - 1]);
            }else{
                right--;
            }
        }
        return result;
    }

    /**
     * Solution:
     * Define ksum[i][j][l]表示前j个元素里面取l个元素之和为i。
     *
     * 初始化：ksum[0][j][0] =1(j:0~n)，即前j个元素里面取0个元素使其和为0的方法只有1种，那就是什么都不取
     * 状态函数： ksum[i][j][l] = ksum[i][j-1][l] + ksum[i-A[i-1]][j-1][l-1]
     * 即前j个元素里面取l个元素之和为i由两种情况组成：
     * 第一种情况为不包含第i个元素，即前j－1个元素里取l个元素使其和为i，
     * 第二种情况为包含第i个元素，即前j－1个元素里取l－1个元素使其和为i-A[i-1]（前提是i-A[i-1]>=0）。
     */
    /* Time O(  ) )*/
    public int kSum_dp(int[] nums, int k, int target) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int n = nums.length;
        int[][][] ksum = new int[target + 1][n + 1][k + 1];

        for (int j = 0; j <= n; j++) {
            ksum[0][j][0] = 1;
        }

        for (int i = 1; i <= target; i++) {
            for (int j = 1; j <= n; j++) {
                for (int l = 1; l <= j && l <= k; l++) {
                    ksum[i][j][l] = ksum[i][j - 1][l];
                    if (i - nums[j - 1] >= 0) {
                        ksum[i][j][l] += ksum[i - nums[j - 1]][j - 1][l - 1];
                    }
                }
            }
        }

        return ksum[target][n][k];
    }

    
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
