package array.subArray;

import java.util.Arrays;

import util.Misc;

public class SubarraySumBetween {

	
	/**
	 * 
	 * Given an integer array, find a subarray where the sum of numbers is
	 * between two given interval. Your code should return the number of
	 * possible answer.
	 * 
	 * Example Given [1,2,3,4] and interval = [1,3], return 4. 
	 * The possible answers are: [0, 0] [0, 1] [1, 1] [2, 2]
	 */
    /**
     * @param A an integer array
     * @param start an integer
     * @param end an integer
     * @return the number of possible answer
     */
	/*Time O(n^2),  Space O(1)*/
    public int subarraySumII(int[] A, int start, int end) {
        int count = 0;
        //check
        if(null == A || 0 == A.length){
            return count;
        }
        
        long sum = 0;
        for(int i = 0; i< A.length; i++){
        	sum = 0;
        	for(int j = i; j< A.length; j++){
                sum += A[j];
                
                if(sum >= start && sum <= end){
                	count++;
                }else if(sum > end){
                	break;
                }
        	}
        }
        
        return count;
    }
    
    /*Time O(nlogn),  Space O(n)*/
    public int subarraySumII_intervaltree(int[] nums, int start, int end) {
        int count = 0;
        //check
        if(null == nums || 0 == nums.length){
            return count;
        }
        
        long sum = 0;
        long[] sums = new long[nums.length + 1];
        sums[0] = 0;
        
        for(int i = 0; i< nums.length; i++){
            sum += nums[i];
            
            sums[i + 1] = sums[i] + nums[i];
        }
        
        SegmentTreeNode root = build(sums);
        
        for(int i = 0; i< sums.length; i++){
            
        }
        
        return count;
    }
    
    private int getCount(SegmentTreeNode node, long start, long end){
        int count = 0;
        
        if(null != node){
            if(start <= node.start && end >= node.end){
                count += node.count;
            }else if(!(end < node.start || start > node.end)){
                count += getCount(node.left, start, end);
                count += getCount(node.right, start, end);
            }
        }
        
        return count;
    }
    
    private void add(SegmentTreeNode node, long target){

        if(null != node && node.start <= target && node.end >= target){
            node.count++;
            
            add(node.left, target);
            add(node.right, target);
        }
    }
    
    private SegmentTreeNode build(long[] sums) {
        long[] copied = sums.clone();
        
        Arrays.sort(copied);
        
        //todo
        return build(sums, 0, sums.length - 1);
    }
    
    private SegmentTreeNode build(long[] sums, int start, int end) {
        if(start > end){
            return null;
        }
        
        SegmentTreeNode root = new SegmentTreeNode(sums[start], sums[end], 0);
        
        if(start < end){
            int mid = start + ((end - start) >> 1);
            
            root.left = build(sums, start, mid);
            root.right = build(sums, mid+1, end);            
        }

        return root;
    }
    
    public class SegmentTreeNode {
        public long start, end;
        public int count;
        public SegmentTreeNode left, right;

        public SegmentTreeNode(long start, long end, int count) {
            this.start = start;
            this.end = end;
            this.count = count;
            this.left = this.right = null;
        }
    }
    
    /* all number in A are non-negative 
     * 
     * Time O(n) ? O(n^2) ?,  Space O(1)*/
    /*wrong*/
    public int subarraySumII_w2(int[] A, int start, int end) {
        int count = 0;
        //check
        if(null == A || 0 == A.length || 0 > start || start > end){
            return count;
        }
        
        long[] sums = new long[A.length + 1]; //default all are 0
        sums[0] = 0;
        for(int i = 0; i < A.length; i++){
        	sums[i+1] += A[i];
        }
        
        //now the array sums is in ACE order.
        long diff = 0;
        for(int i = 0, j = 1; j < sums.length; ){
        	diff = sums[j] - sums[i];
        	
            if(diff < start){
            	j++;
            }else if(diff > end){
            	i++;
            }else{
                count++;
                j++;
            }
        }
        
        return count;
    }
    
    /* wrong */
    public int subarraySumII_w3(int[] A, int start, int end) {
        int count = 0;
        //check
        if(null == A || 0 == A.length || 0 > start || start > end){
            return count;
        }
        
        //main
        long sum = 0;
        for(int i = 0, j = 0; j < A.length; ){        	
            if(sum < start){
            	sum += A[j];
            	j++;
            }else if(sum > end){
            	sum -= A[i];
            	i++;
            }else{
                count++;
                sum += A[j];
                j++;
            }
        }
        
        return count;
    }
    
	public static void main(String[] args) {
		SubarraySumBetween sv = new SubarraySumBetween();
		
		int[][] input = {
				{1,2,3,4},
				{1,3,4,5,1,2,3,4,},
				{1,3,4,5,6,7,1,2,3,4,5}};
		int[] starts = {1,1,1};
		int[] ends = {3, 8, 19};

		for(int i = 0; i < input.length; i++){
			System.out.println(String.format("Input,  %s, [%d, %d]", Misc.array2String(input[i]), starts[i], ends[i]));
			
			System.out.println("Output: ");
			System.out.println(sv.subarraySumII(input[i], starts[i], ends[i]));
			System.out.println(sv.subarraySumII_w2(input[i], starts[i], ends[i]));
			System.out.println(sv.subarraySumII_w3(input[i], starts[i], ends[i]));
		}
	}

}
