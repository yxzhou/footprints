package fgafa.array;

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

public class LargestNumberIII {

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
        
        int[] result = new int[k]; //default all are 0
        for (int i = Math.max(k - nums2.length, 0); i <= Math.min(nums1.length, k); i++) {
            
            int[] res1 = maxNumber(nums1, i);
            int[] res2 = maxNumber(nums2, k - i);
            int[] res = new int[k];
            int pos1 = 0, pos2 = 0, tpos = 0;
 
            while (pos1 < res1.length || pos2 < res2.length) {
                res[tpos++] = greater(res1, pos1, res2, pos2) ? res1[pos1++] : res2[pos2++];
            }
 
            if (!greater(result, 0, res, 0)){
                result = res;
            }
                
        }
        
        return result;
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
                    },
                    {{6,4,7,8,6,5,5,3,1,7,4,9,9,5,9,6,1,7,1,3,6,3,0,8,2,1,8,0,0,7,3,9,3,1,3,7,5,9,4,3,5,8,1,9,5,6,5,7,8,6,6,2,0,9,7,1,2,1,7,0,6,8,5,8,1,6,1,5,8,4},
                     {3,0,0,1,4,3,4,0,8,5,9,1,5,9,4,4,4,8,0,5,5,8,4,9,8,3,1,3,4,8,9,4,9,9,6,6,2,8,9,0,8,0,0,0,1,4,8,9,7,6,2,1,8,7,0,6,4,1,8,1,3,2,4,5,7,7,0,4,8,4}
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
            100,
            70
        };
        
        LargestNumberIII sv = new LargestNumberIII();
        
        for(int i = 0; i < input.length; i++){
            System.out.println(String.format("\n%s and %s and k=%d,  \n %s \n %s", 
                        Misc.array2String(input[i][0]), Misc.array2String(input[i][1]), k[i],
                        Misc.array2String(sv.maxNumber(input[i][0], input[i][1], k[i])),
                        Misc.array2String(sv.maxNumber_n(input[i][0], input[i][1], k[i]))));
        }
        
    };
}
