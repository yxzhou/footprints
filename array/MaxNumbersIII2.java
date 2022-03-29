package array;


/**
 * 
 * Continue on MaxNumbersIII, optimize the maxNumber(nums1, k) from O(n) to O(logn)
 *
 */

public class MaxNumbersIII2  {
 
    public int[] maxNumber(int[] nums1, int[] nums2, int k) {
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
                res[tpos++] = compare(res1, pos1, res2, pos2) ? res1[pos1++] : res2[pos2++];
            }
 
            if (!compare(result, 0, res, 0)){
                result = res;
            }
                
        }
        
        return result;
    }
    
    private boolean compare(int[] nums1, int start1, int[] nums2, int start2) { 
        
        for (; start1 < nums1.length && start2 < nums2.length; start1++, start2++) {
            if (nums1[start1] > nums2[start2]) {
                return true;
            }else if (nums1[start1] < nums2[start2]){
                return false;
            }
        }
        
        return start1 != nums1.length;
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
    

}
