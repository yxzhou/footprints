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
    
    /* Time O(  ) )*/
    public int kSum_dp(int[] num, int k, int target){
        int count = 0;
        
        if(null == num || k < 1 || num.length <= k){
            return count;
        }
        
        if(k == 1){
            for(int n : num){
                if(n == target){
                    count++;
                }
            }
            return count;
        }
        
        Arrays.sort(num);
        
        boolean[][][] dp = new boolean[num.length][k][target+1];//default all are false
        
        for(int i = 0; i < num.length; i++){
            for(int j = 1; j < i; j++){
                for(int p = 1; p <= target; p++){
                    for(int q = 0; q <= i; q++ ){
                        if(p < num[q]){
                            break;
                        }
                        
                        if( dp[i][j - 1][p - num[q]]){
                            dp[i][j][p] = true;
                            break;
                        }
                    }
                    
                }
            }
        }
        
        return count;
    }
    
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
