package datastructure.segmentTree.rangeSumCount;

import util.Misc;

import java.util.Arrays;
import java.util.TreeMap;

/**
 * 
 * Given an integer array nums, return the number of range sums that lie in [lower, upper] inclusive.
    Range sum S(i, j) is defined as the sum of the elements in nums between indices i and j (i ≤ j), inclusive.
    
    Note:
    A naive algorithm of O(n^2) is trivial. You MUST do better than that.
    
    Example:
    Given nums = [-2, 5, -1], lower = -2, upper = 2,
    Return 3.
    The three ranges are : [0, 0], [2, 2], [0, 2] and their respective sums are: -2, -1, 2.
 *
 *
 * Solution:
 *   It's a range sum issue.
 *   All the range would be [x, y], total it's n^2, so the worst case, the time complexity is O(n^2).
 *   To get a "better algorithm",  it's improve time complexity with space cost.
 *   A "better algorithm" is,
 *     1 define int[] sums, sums[i] = nums[0] + -- + nums[i]
 *     2 from sums[0] to sums[n-1],  count if it is in range [lower, upper]
 *     3 from sums[1] to sums[n-1],  count if it is in range [lower + sums[0], upper + sums[0]]
 *     4 from sums[2] to sums[n-1],  count if it is in range [lower + sums[1], upper + sums[1]]
 *     5 - - -
 *
 *     time complexity is O(n^2)
 *
 *   Further improve.
 *     Interval Tree
 *     1 To array of sums[0..n-1], build a interval tree from min and max of the array. The treeNode includes ( start, end, count ) , default the count is 0.
 *     2 insert sums[n-1] in the tree, update the treeNode's count that includes sums[n-1]
 *       get sum of the treeNode's count when it in the range of [lower + sums[n-2], upper + sums[n-2]]
 *     3 insert sums[n-2] in the tree, update the treeNode's count that includes sums[n-2]
 *       get sum of the treeNode's count when it in the range of [lower + sums[n-3], upper + sums[n-3]]
 *     4 - - -
 *
 *     Segment Tree
 *     1 To ranges of [lower, upper], [lower + sums[0], upper + sums[0]], - , [lower + sums[n-2], upper + sums[n-2]], build a Segment tree, The treeNode includes ( start, end, count ) , default the count is 0.
 *     2 insert range [lower + sums[n-2], upper + sums[n-2]], update the treeNode's count that is in the range.
 *       get sum of the count of treeNode [sums[n-1], sums[n-1]]
 *     3 - -
 *
 *   The following implements the Interval Tree
 */

public class CountOfRangeSum_immutable {

    public int countRangeSum_x(int[] nums, int lower, int upper) {

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

    /****          **/
    public int countRangeSum(int[] nums, int lower, int upper) {
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
            System.out.println(String.format("%s and [%d, %d] -- %d", Misc.array2String(input[i]), pairs[i][0], pairs[i][1], sv.countRangeSum(input[i], pairs[i][0], pairs[i][1])));

        }
     }

}
