package fgafa.tree;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Stack;

/**
 * 
 * Q1, Given a non-empty binary search tree and a target value, find the value in the BST that is closest to the target.
    Note:
    Given target value is a floating point.
    You are guaranteed to have only one unique value in the BST that is closest to the target.
 *
 * Q2, 
    Given a non-empty binary search tree and a target value, find k values in the BST that are closest to the target.
    
    Note:
    Given target value is a floating point.
    You may assume k is always valid, that is: k ≤ total nodes.
    You are guaranteed to have only one unique set of k values in the BST that are closest to the target.
    Follow up:
    Assume that the BST is balanced, could you solve it in less than O(n) runtime (where n = total nodes)? 
 *
 */

public class BSTClosestValue {


    public int closestValue(TreeNode root, double target) {
        if (root == null) {
            throw new NullPointerException("Tree must be non-empty");
        }
         
        int result = 0;
        double gap = Double.MAX_VALUE;
         
        while (root != null) {
            if (root.val == target) {
                return root.val;
            }
             
            double dist = Math.abs(root.val - target);
            if (dist < gap) {
                result = root.val;
                gap = dist;
            }
             
            if (target > root.val) {
                root = root.right;
            } else if (target < root.val) {
                root = root.left;
            }
        }
        return result;
    }
    
    /**
     * maxHeap
     * 
     */
    public List<Integer> closestKValues_1(TreeNode root, double target, int k) {
        PriorityQueue<Pair> maxHeap = new PriorityQueue<Pair>(k, new Comparator<Pair>(){
            @Override
            public int compare(Pair p1, Pair p2){
                return Double.compare( p2.diff, p1.diff);
            }
        });
        
        rec(root, target, k, maxHeap);
        
        List<Integer> result = new ArrayList<Integer>(k);
        
        while(!maxHeap.isEmpty()){
            result.add(maxHeap.poll().val);
        }
        
        return result;
    }
    
    private void rec(TreeNode node, double target, int k, PriorityQueue<Pair> maxHeap){
        if(null == node){
            return;
        }
        
        double diff = Math.abs(node.val-target); 
        
        
        if(maxHeap.size() < k){
            maxHeap.offer(new Pair(node.val, diff));
        }else if(diff < maxHeap.peek().diff){
            maxHeap.poll();
            maxHeap.offer(new Pair(node.val, diff));
        }else{
            if(node.val > target){
                rec(node.left, target, k, maxHeap);
            }else{
                rec(node.right, target, k, maxHeap);
            }
            return;
        }
        
        rec(node.left, target, k, maxHeap);
        rec(node.right, target, k, maxHeap);
    }
    
    class Pair{
        int val;
        double diff;
        
        Pair(int value, double diff){
            this.val = value;
            this.diff = diff;
        }
    }
    
    /**
     * inorder traversal
     * 
     */
    public List<Integer> closestKValues_2(TreeNode root, double target, int k) {
        List<Integer> result = new LinkedList<Integer>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode curr = root;
        
        while(null != curr || !stack.isEmpty()){
            if(null == curr){
                curr = stack.pop();
                
                if(result.size() == k){
                    if(Math.abs(target - result.get(0)) < Math.abs(target - curr.val)){
                        break;
                    }else{
                        result.remove(0);
                    }
                }
                
                result.add(curr.val);

                curr = curr.right;
            }else{
                stack.push(curr);
                curr = curr.left;
            }
        }
        
        return result;
    }
     
}
