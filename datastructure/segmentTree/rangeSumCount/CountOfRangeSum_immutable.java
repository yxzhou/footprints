package datastructure.segmentTree.rangeSumCount;

import util.Misc;

import java.util.Arrays;
import java.util.TreeMap;

/**
 * _https://www.lintcode.com/problem/1293
 * 
 * Given an integer array nums, return the number of range sums that lie in [lower, upper] inclusive.
 * Range sum S(i, j) is defined as the sum of the elements in nums between indices i and j (i ≤ j), inclusive.
 *   
 *  Note:
 *  A naive algorithm of O(n^2) is trivial. You MUST do better than that.
 *  
 *  Example:
 *  Given nums = [-2, 5, -1], lower = -2, upper = 2,
 *  Return 3.
 *  Explanation: The three ranges are : [0, 0], [2, 2], [0, 2] and their respective sums are: -2, -1, 2.
 *
 *
 * Solution:
 *   It's a range sum issue.
 *   Example for [a, b, c, d], the naive solution is check the following with a range [lower, upper]
 *     a, 
 *     a+b, b
 *     a+b+c, b+c, c
 *     a+b+c+d, b+c+d, c+d, d 
 *   Total it's n^2, so the worst case, the time complexity is O(n^2).
 * 
 *   To do better, it cannot calculate all combination.  only way is reuse the prefix sum.  
 * 
 *   M1) it's improve time complexity with space cost. Build a segment tree to store the prefix sunm, sums[i] = nums[0] + -- + nums[i]
 *     a+b+c+d                  with [l+a+b+c, u+a+b+c]
 *     a+b+c+d, a+b+c           with [l+a+b, u+a+b]  
 *     a+b+c+d, a+b+c, a+b      with [l+a, u+a]
 *     a+b+c+d, a+b+c, a+b, a   with [l, u]
 * 
 *   As same as
 *     from sums[0] to sums[n-1],  count if it is in range [lower, upper]
 *     from sums[1] to sums[n-1],  count if it is in range [lower + sums[0], upper + sums[0]]
 *     from sums[2] to sums[n-1],  count if it is in range [lower + sums[1], upper + sums[1]]
 *      - - -
 *      
 *   M2) count with the TreeMap.subMap(K fromKey, boolean fromInclusive, K toKey, boolean toInclusive)
 *     a                with [l, u]
 *     a, b-b           with [l-b, u-b]
 *     a, 0, c-b-c      with [l-b-c, u-b-c]
 *     a, 0, c-b-c, d-b-c-d  with [l-b-c-d, u-b-c-d]
 * 
 *   M3) count with the TreeMap.subMap(K fromKey, boolean fromInclusive, K toKey, boolean toInclusive)
 *     a        with [l, u]
 *     a+b      with [l, u]     and     a          with [a+b-u , a+b-l]
 *     a+b+c    with [l, u]     and     a, a+b     with [a+b+c-u, a+b+c-l]
 *     a+b+c+d  with [l, u]     and     a, a+b, a+b+c     with [a+b+c+d-u, a+b+c+d-l]
 *  
 *   
 */

public class CountOfRangeSum_immutable {

    /**
     * with TreeMap
     * 
     * define n as the length of nums
     * Time O(n * logn), space O(n)
     * 
     * @param nums
     * @param lower
     * @param upper
     * @return 
     */
    public int countRangeSum_m2(int[] nums, int lower, int upper) {
        if(nums == null || nums.length == 0){
            return 0;
        }
        
        int count = 0;
        TreeMap<Long, Integer> treeMap = new TreeMap<>();
        
        long sum = -nums[0];
        
        for(int x : nums){
            sum += x;
            
            treeMap.put(x - sum, treeMap.getOrDefault(x - sum, 0) + 1);
            
            for(int c : treeMap.subMap(lower - sum, true, upper - sum, true).values()){
                count += c;
            }
        }
        
        return count;
    }
    
    public int countRangeSum_m3(int[] nums, int lower, int upper) {

        TreeMap<Long, Integer> treeMap = new TreeMap<>();

        int count = 0;

        long sum = 0l;
        for(int x : nums){
            sum += x;

            if(sum >= lower && sum <= upper){
                count++;
            }

            for(int c : treeMap.subMap(sum - upper, true, sum - lower, true).values() ){
                count += c;
            }
            //count += treeMap.subMap(sum - upper, true, sum - lower, true).values().stream().mapToInt(Integer::valueOf).sum();

            treeMap.put(sum, treeMap.getOrDefault(sum, 0) + 1);
        }

        return count;
    }

    /**
     * with Binary Search Tree
     * 
     * @param nums
     * @param lower
     * @param upper
     * @return 
     */
    public int countRangeSum_m1(int[] nums, int lower, int upper) {
        //check
        if(null == nums || 0 == nums.length){
            return 0;
        }

        int length = nums.length;
        long[] sums = new long[length];
        sums[0] = nums[0];
        for(int i = 1; i < nums.length; i++){
            sums[i] = sums[i - 1] + nums[i];
        }

        //build balanced tree of sums
        Arrays.sort(sums);

        BSTNode[] tree = new BSTNode[length];
        buildTree(sums, 0, sums.length - 1, tree, 0);

        int result = 0;
        for(int i = length - 1; i >= 0; i--){
            //add sums[i]
            add(tree, 0, sums[i]);

            //get the count of [lower ]
            long diff = i == 0 ? 0 : sums[i - 1];
            result += countRangeSum(tree, 0, lower + diff, upper + diff);

        }
        return result;
    }
    
    private void buildTree(long[] sums, int start, int end, BSTNode[] tree, int i){
        if (start > end || i >= sums.length){
            return;
        }

        tree[i] = new BSTNode(sums[start], sums[end]);

        int middle = start + (end - start) / 2;
        buildTree(sums, start, middle, tree, i * 2 + 1);
        buildTree(sums, middle + 1, end, tree, i * 2 + 2);
    }
    
    private void add(BSTNode[] tree, int pos, long sum){
        if(pos < tree.length && tree[pos].isInclude(sum)){
            tree[pos].count ++;

            add(tree, pos * 2 + 1, sum);
            add(tree, pos * 2 + 2, sum);
        }
    }
    
    private int countRangeSum(BSTNode[] tree, int pos, long lower, long upper){

        int result = 0;

        if(pos >= tree.length || upper < tree[pos].start || tree[pos].end < lower){
            //do nothing
        }else if(lower <= tree[pos].start && tree[pos].end <= upper){
            result += tree[pos].count;
        }else{
            result += countRangeSum(tree, pos * 2 + 1, lower, upper);
            result += countRangeSum(tree, pos * 2 + 2, lower, upper);
        }
            
        return result;
    }
    
    class BSTNode{
        long start;
        long end;
        int count = 0; // how many subsum fell in this range.
        
        BSTNode(long start, long end){
            this.start = start;
            this.end = end;
        }

        boolean isInclude(long sum){
            return start <= sum && sum <= end;
        }

    }
    
    public static void main(String[] args) {
        CountOfRangeSum_immutable sv = new CountOfRangeSum_immutable();

        int[][] input = {
                    { -2, 5, -1 },  // 3
                    {2147483647,-2147483648,-1,0}  //4
        };
        
        int[][] pairs = {
                    {-2, 2},
                    {-1, 0}
        };
        
        for(int i = 0; i < input.length; i++){
            System.out.println(String.format("%s and [%d, %d] -- %d", Misc.array2String(input[i]), pairs[i][0], pairs[i][1], sv.countRangeSum_m1(input[i], pairs[i][0], pairs[i][1])));

        }
     }

}
