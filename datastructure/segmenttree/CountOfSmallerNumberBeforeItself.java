package fgafa.datastructure.segmenttree;

import java.util.ArrayList;


/**
 * 
 * Give you an integer array (index from 0 to n-1, where n is the size of this array, value from 0 to 10000) . 
 * For each element Ai in the array, count the number of element before this element Ai is smaller than it and return count number array.

    Example
    For array [1,2,7,8,5], return [0,1,2,3,2]
 *
 *  Challenge
    Could you use different ways to do it.
    
    Just loop
    Sort and binary search
    Build Segment Tree and Search.
 */

public class CountOfSmallerNumberBeforeItself {
	 /**
     * @param A: An integer array
     * @return: Count the number of element before this element 'ai' is 
     *          smaller than it and return count number array
     */ 
    public ArrayList<Integer> countOfSmallerNumberII(int[] A) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        //check
        if(null == A || 0 == A.length){
            return result;
        }
        
        SegmentTreeNode root = build(0, 10000);
        for(int a : A){
        	result.add(getCount(root, a));
            add(root, a);
        }
        
        return result;
    }
    
    private int getCount(SegmentTreeNode node, int target){
        int count = 0;
        
        if(null != node){
            if(target > node.end){
                count += node.count;
            }else if(target > node.start){
                count += getCount(node.left, target);
                count += getCount(node.right, target);
            }
        }
        
        return count;
    }
    
    private void add(SegmentTreeNode node, int target){

        if(null != node && node.start <= target && node.end >= target){
            node.count++;
            
            add(node.left, target);
            add(node.right, target);
        }
    }
    
    private SegmentTreeNode build(int start, int end) {
        if(start > end){
            return null;
        }
        
        SegmentTreeNode root = new SegmentTreeNode(start, end, 0);
        
        if(start < end){
            int mid = start + ((end - start) >> 1);
            root.left = build(start, mid);
            root.right = build(mid+1, end);            
        }

        return root;
    }
    
    public class SegmentTreeNode {
		public int start, end, count;
		public SegmentTreeNode left, right;

		public SegmentTreeNode(int start, int end, int count) {
			this.start = start;
			this.end = end;
			this.count = count;
			this.left = this.right = null;
		}
	}
}
