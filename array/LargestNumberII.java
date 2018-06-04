package fgafa.array;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import fgafa.util.Misc;


/**
 * 
 * leetcode Create Maximum Number

    Given two arrays of length m and n with digits 0-9 representing two numbers. 
    Create the maximum number of length k <= m + n from digits of the two. 
    The relative order of the digits from the same array must be preserved. 
    Return an array of the k digits. You should try to optimize your time and space complexity.
    
    Example 1:
    nums1 = [3, 4, 6, 5]
    nums2 = [9, 1, 2, 5, 8, 3]
    k = 5
    return [9, 8, 6, 5, 3]
    
    Example 2:
    nums1 = [6, 7]
    nums2 = [6, 0, 4]
    k = 5
    return [6, 7, 6, 0, 4]
    
    Example 3:
    nums1 = [3, 9]
    nums2 = [8, 9]
    k = 3
    return [9, 8, 9]
 *
 */

public class LargestNumberII {

   public int[] maxNumber_n(int[] nums1, int[] nums2, int k) {
        int[] ans = new int[k];
        for (int i = Math.max(k - nums2.length, 0); i <= Math.min(nums1.length, k); i++) {
            int[] res1 = get_max_sub_array(nums1, i);
            int[] res2 = get_max_sub_array(nums2, k - i);
            int[] res = new int[k];
            int pos1 = 0, pos2 = 0, tpos = 0;
 
            while (pos1 < res1.length || pos2 < res2.length) {
                res[tpos++] = greater(res1, pos1, res2, pos2) ? res1[pos1++] : res2[pos2++];
            }
 
            if (!greater(ans, 0, res, 0))
                ans = res;
        }
 
        return ans;
    }
 
    private boolean greater(int[] nums1, int start1, int[] nums2, int start2) {
        for (; start1 < nums1.length && start2 < nums2.length; start1++, start2++) {
            if (nums1[start1] > nums2[start2]) return true;
            if (nums1[start1] < nums2[start2]) return false;
        }
        return start1 != nums1.length;
    }
 
    private int[] get_max_sub_array(int[] nums, int k) {
        int[] res = new int[k];
        int len = 0;
        for (int i = 0; i < nums.length; i++) {
            while (len > 0 && len + nums.length - i > k && res[len - 1] < nums[i]) {
                len--;
            }
            if (len < k)
                res[len++] = nums[i];
        }
        return res;
    }
    
    public int[] maxNumber(int[] nums1, int[] nums2, int k) {
        //check
        if(null == nums1 || 0 == nums1.length){
            return maxNumber(nums2, k);
        }else if(null == nums2 || 0 == nums2.length){
            return maxNumber(nums1, k);
        }
        
        if(nums1.length + nums2.length < k){
            return new int[0];
        }
        
        if(nums1.length > nums2.length){
            //return maxNumber(nums2, nums1, k);
        }
        
        int[] result = new int[k];
        IntervalTree tree1 = new IntervalTree(nums1);
        IntervalTree tree2 = new IntervalTree(nums2);
        
        maxNumber(result, 0, tree1, 0, nums1.length, tree2, 0, nums2.length);
        
        return result;
    }
    
    private void maxNumber(int[] result, int index, IntervalTree tree1, int start1, int n1, IntervalTree tree2, int start2, int n2){
        int[] max1;
        int[] max2;
        int k = result.length;
        int max;
        
        for(int i = index; i < k; i++){
            max1 = tree1.getMax(start1, n1 + n2 - k + i - start2);
            max2 = tree2.getMax(start2, n1 + n2 - k + i - start1);
            
            if(max1[0] > max2[0] || (max1[0] == max2[0] && max1[1] < max2[1]) ){
                max = max1[0];
                start1 = max1[1] + 1;
            }else if(max1[0] < max2[0] || (max1[0] == max2[0] && max1[1] > max2[1])){
                max = max2[0];
                start2 = max2[1] + 1;
            }else{ // max1[0] == max2[0] && max1[1] == max2[1]
                max = max2[0];

            }

            if(max > result[i]){
                result[i] = max;
                if(i < k- 1){
                    result[i + 1] = 0;
                }

            }else if(max < result[i]){
                return;
            }
            
            if(max1[0] == max2[0] && max1[1] == max2[1] && max1[1] == n1 - 1){
                maxNumber(result, i + 1, tree1, max1[1] + 1, n1, tree2, start2, n2);
                maxNumber(result, i + 1, tree1, start1, n1, tree2, max2[1] + 1, n2);
                return;
            }
        }
    }
    
    private int[] maxNumber(int[] nums, int k){
        if(null == nums || 0 == nums.length || nums.length < k){
            return new int[0];
        }
        
        int[] result = new int[k];
        IntervalTree tree = new IntervalTree(nums);
        
        int n = nums.length;
        int start = 0;
        int[] max;
        for(int i = 0; i < k; i++){
            max = tree.getMax(start, n - k + i);
            result[i] = max[0];
            start = max[1] + 1;
        }
        
        return result;
    }
    
    class IntervalTree{
        Node root = null;
        
        public IntervalTree(int[] nums){
            if(null != nums && 0 != nums.length){
                root = build(nums, 0, nums.length - 1);
            }
        }
        
        private Node build(int[] nums, int start, int end){
            Node root = new Node(start, end);
            
            if(start == end){
                root.max = nums[start];
                root.index = start;
            }else{
                int mid = start + ((end - start) >> 1);
                root.left = build(nums, start, mid);
                root.right = build(nums, mid + 1, end);
                
                if(root.left.max < root.right.max){
                    root.max = root.right.max;
                    root.index = root.right.index;
                }else{ //
                    root.max = root.left.max;
                    root.index = root.left.index;
                }
            }

            return root;
        }
        
        public int[] getMax(int start, int end){
            return getMax(this.root, start, end);
        }
        
        private int[] getMax(Node node, int start, int end){
            if(null == node || node.start > end || node.end < start){
                return new int[]{Integer.MIN_VALUE, end};
            }else if(node.start >= start && node.end <= end){
                return new int[]{node.max, node.index};
            }
            
            //int mid = node.start + ((node.end - node.start) >> 1);
            
            int[] left = getMax(node.left, start, end);
            int[] right = getMax(node.right, start, end);
            
            if(left[0] < right[0]){
                return right;
            }else{ //
                return left;
            }
        }
        
        class Node{
            int start;
            int end;
            
            int max;
            int index; // the position of the max
            
            Node left;
            Node right;
            
            Node(int start, int end){
                this.start = start;
                this.end = end;
            }
        }
    }
    
    
    
    public static void main(String[] args) {

        int[][][] input = {
                    {{}, {9, 1, 2, 5, 8, 3}}, // 9, 8, 3
                    {{3, 4, 6, 5}, {9, 1, 2, 5, 8, 3}}, // 9, 8, 6, 5, 3
                    {{6, 7}, {6, 0, 4}}, //6, 7, 6, 0, 4
                    {{3, 9}, {8, 9}}, //9, 8, 9
                    {{3, 9}, {8, 9, 2}}, //9, 9, 2
                    {{8, 9, 2}, {3, 9}}, //9, 8, 9, 2
                    {{4, 9, 6}, {9, 7, 6}}, //9, 9, 7
                    {{2,8,0,4,5,1,4,8,9,9,0,8,2,9}, {5,9,6,6,4,1,0,7}}, //5,9,6,6,4,2,8,1,0,7,0,4,5,1,4,8,9,9,0,8,2,9
                    {{1,2,0,1,1,2,0,1,1,1,0,2,2,0,1,2,0,0,0,2,1,1,2,0,0,2,2,2,0,1,2,1,0,1,0,1,1,2,1,2,1,2,0,0,0,0,2,2,0,0,1,1,1,1,0,0,0,1,0,1,1,2,0,0,1,0,2,2,2,2,0,2,0,2,1,1,1,1,0,2,2,1,0,1,2,2,0,1,1,1,1,0,0,0,1,1,1,1,2,1,2,2,0,2,2,0,2,2,2,2,2,2,1,1,0,1,2,0,1,1,0,0,1,1,1,1,1,2,1,0,2,1,1,1,1,2,1,1,2,0,1,1,0,0,2,2,0,2,1,0,0,0,0,2,2,2,1,2,1,1,1,0,1,1,0,2,1,0,2,0,0,1,0,2,0,1,0,1,0,1,1,0,1,1,2,2,0,1,0,0,2,0,1,0,0,1,0,2,1,0,1,1,0,2,1,2,1,1,2,1,0,1,0,1,0,0,1,2,0,0,1,2,2,1,1,0,0,2,1,1,0,1,2,2,1,1,2,1,0,0,0,2,0,2,1,2,1,2,2,1,2,0,2,0,2,2,2,0,0,2,2,0,0,0,1,2,0,2,0,0,2,0,2,2,2,1,2,1,2,2,0,2,2,2,2,0,1,0,2,0,0,2,1,2,0,0,1,0,1,1,2,2,2,0,0,2,0,0,2,2,1,1,1,1,2,2,2,0,0,1,2,2,0,0,1,1,0,0,2,2,0,2,2,0,1,2,1,2,0,0,2,2,2,1,1,2,1,0,0,1},
                            {1,0,2,1,2,2,1,2,1,0,1,0,1,1,0,1,1,1,1,0,0,0,1,1,1,1,2,2,0,2,0,2,2,1,2,0,0,0,2,2,0,0,0,2,0,0,0,2,0,2,2,1,1,2,0,1,0,2,0,2,2,0,0,0,0,2,1,1,1,2,0,1,0,0,2,0,2,1,1,0,1,1,1,2,0,0,2,2,2,1,1,1,2,0,1,2,2,1,0,2,2,1,2,0,1,0,2,2,0,0,1,0,1,0,1,2,1,1,0,1,1,2,0,1,0,2,2,1,1,0,2,1,1,2,0,1,1,0,0,2,2,2,2,2,2,2,2,0,1,1,1,0,1,2,1,1,1,1,1,0,2,0,2,0,1,2,0,2,0,2,1,0,2,0,2,0,1,0,2,1,1,0,1,2,0,1,1,2,1,1,1,1,0,0,1,1,0,0,0,1,2,2,0,1,2,2,2,1,1,0,1,1,1,1,1,1,1,1,2,2,2,0,0,2,0,2,0,1,1,0,0,1,2,2,1,0,2,1,2,0,2,2,1,2,2,2,2,1,0,0,0,1,2,1,2,2,1,2,2,2,2,1,0,1,1,0,1,0,2,0,0,1,2,0,0,2,2,0,0,0,2,1,2,2,0,2,1,0,1,2,2,2,2,2,0,2,2,2,1,1,1,0,0,1,1,2,2,0,2,0,1,0,2,0,1,0,1,0,0,1,0,1,1,0,2,0,2,2,1,1,1,2,2,1,2,1,0,2,0,1,0,1,2,0,1,2,0,2,0,2}
                    },
                    {{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                     {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
                    }
        };
        
        int[] k = {
            3,
        	5, 
        	5,
        	3, 
        	3,
        	4,
        	3,
        	22,
        	700, 
        	100
        };
        
        LargestNumberII sv = new LargestNumberII();
        
        for(int i = 0; i < input.length; i++){
            System.out.println(String.format("\n%s and %s and k=%d,  \n %s \n %s", 
                        Misc.array2String(input[i][0]), Misc.array2String(input[i][1]), k[i],
                        Misc.array2String(sv.maxNumber(input[i][0], input[i][1], k[i])),
                        Misc.array2String(sv.maxNumber_n(input[i][0], input[i][1], k[i]))));
        }
        
    };

}
