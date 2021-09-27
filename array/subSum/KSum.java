package array.subSum;

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
     * Define f[n][k][t]表示 前n个元素里面取k个元素之和为t的个数。
     *
     * 初始化：f[i][0][0] =1 (i:0~n)，即前i个元素里面取0个元素使其和为0的方法只有1种，那就是什么都不取
     *
     * 状态函数： f[in][k][t] = f[n - 1][k][t] + f[n - 1][k - 1][i-A[n-1]]
     * 即前n个元素里面取k个元素之和为target,  由两种情况组成：
     * 第一种情况为不包含第n个元素，即前n－1个元素里取k个元素使其和为t，f[n - 1][k][t]
     * 第二种情况为包含第n个元素，即前n－1个元素里取k－1个元素使其和为 t-A[n-1], f[n - 1][k - 1][i-A[n-1]]（ 前提是 t-A[n-1]>=0 ）。
     */
    /* Time O( n * k * target ) )*/
    public int  kSum_dp(int[] nums, int k, int target) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int n = nums.length;

        int[][][] f = new int[n + 1][k + 1][target + 1]; //
        for (int i = 0; i <= n; i++) {
            f[i][0][0] = 1;
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= k && j <= i; j++) {
                for (int t = 1; t <= target; t++) {
                    f[i][j][t] = 0;
                    if (t >= nums[i - 1]) {
                        f[i][j][t] = f[i - 1][j - 1][t - nums[i - 1]];
                    }
                    f[i][j][t] += f[i - 1][j][t];
                } // for t
            } // for j
        } // for i
        return f[n][k][target];
    }

    
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
