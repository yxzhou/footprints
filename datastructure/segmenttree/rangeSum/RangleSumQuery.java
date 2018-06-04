package fgafa.datastructure.segmenttree.rangeSum;

/**
 * 
 * Given an integer array nums, find the sum of the elements between indices i and j (i ≤ j), inclusive.

    The update(i, val) function modifies nums by updating the element at index i to val.
    Example:
    Given nums = [1, 3, 5]
    
    sumRange(0, 2) -> 9
    update(1, 2)
    sumRange(0, 2) -> 8
    Note:
    The array is only modifiable by the update function.
    You may assume the number of calls to update and sumRange function is distributed evenly.
 *
 */

public class RangleSumQuery {
    int[] nums;
    Node root = null;

    public RangleSumQuery(int[] nums) {
    //public NumArray(int[] nums) {
        if(null == nums || 0 == nums.length){
            return;
        }
        
        this.nums = nums;
        root = build(nums, 0, nums.length - 1);
    }

    private Node build(int[] nums, int start, int end){
        Node root = new Node(start, end);
        
        if(start == end){
            root.sum = nums[start];
        }else{
            int mid = start + (end - start) / 2;
            root.left = build(nums, start, mid);
            root.right = build(nums, mid+1, end);
            
            root.sum = root.left.sum + root.right.sum;
        }
        
        return root;
        
    }
    
    void update(int i, int val) {
        if(null == root || i < 0 || i >= nums.length){
            return;
        }
        
        update(root, i, val);
        nums[i] = val;
    }

    private void update (Node node, int position, int val){
        int mid;
        while(null != node){
            node.sum += val - nums[position];
            
            mid = node.start + (node.end - node.start) / 2;
            
            if(position <= mid){
                node = node.left;
            }else{
                node = node.right;
            }
        }
    }
    
    public int sumRange(int i, int j) {
        if(null == root || i < 0 || i > j ||  j >= nums.length){
            throw new IllegalArgumentException();
        }
        
        return sumRange(root, i, j);
    }
    
    private int sumRange(Node node, int i, int j){
        
        if(null == node || node.start > j || node.end < i){
            return 0;
        }else if(node.start >= i && node.end <= j){
            return node.sum;
        }else{
            return sumRange(node.left, i, j) + sumRange(node.right, i, j);
        }
    }
    
    class Node{
        int start = 0;
        int end = 0;
        int sum = 0;
        
        Node left;
        Node right;
        
        Node(int start, int end){
            this.start = start;
            this.end = end;
        }
        
    }
    
}


// Your NumArray object will be instantiated and called as such:
// NumArray numArray = new NumArray(nums);
// numArray.sumRange(0, 1);
// numArray.update(1, 10);
// numArray.sumRange(1, 2);
