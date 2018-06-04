package fgafa.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 
 * Question: Given an array of numbers, verify whether it is the correct preorder traversal sequence of a binary search tree. You may assume each number in the sequence is unique.

   Follow up: Could you do it using only constant space complexity?
 *
 */


public class BSTVerifyPreorder {

    /*Time O(nlogn -- n^2)  Space O(1)*/
    public boolean verifyPreorder(int[] preorder) {
        //check
        if(null == preorder || preorder.length < 3){
            return true;
        }
        
        return verifyPreorder(preorder, 0, preorder.length);
    }
    
    private boolean verifyPreorder(int[] preorder, int low, int high){
        
        if(low >= high - 1){
            return true;
        }
        
        int i = low + 1;
        while(i < high && preorder[low] > preorder[i] ){
            i++;
        }
        
        for(int j = i + 1; j < high; j++){
            if(preorder[low] > preorder[j]){
                return false;
            }
        }
        
        return verifyPreorder(preorder, low + 1, i) && verifyPreorder(preorder, i, high);
    }
    
    /*Time O(n)  Space O(n)*/
    public boolean verifyPreorder_2stack(int[] preorder) {
        if (preorder == null || preorder.length < 3) {
            return true;
        }

        Stack<Integer> stack = new Stack<>();
        List<Integer> inorder = new ArrayList<>();

        for (int i : preorder) {
            if (!inorder.isEmpty() && i < inorder.get(inorder.size() - 1)) {
                return false;
            }

            while (!stack.isEmpty() && i > stack.peek()) {
                inorder.add(stack.pop());
            }
            stack.add(i);
        }

        return true;
    }
    
    /*Time O(n)  Space O(n)*/
    public boolean verifyPreorder_1stack(int[] preorder) {
        if (preorder == null || preorder.length < 3) {
            return true;
        }
         
        Stack<Integer> stack = new Stack<Integer>();
        int max = Integer.MIN_VALUE;
         
        for (int num : preorder) {
            if (num < max) {
                return false;
            }
             
            while (!stack.isEmpty() && num > stack.peek()) {
                max = stack.pop();
            }
             
            stack.push(num);
        }
         
        return true;
    }
    
    /*Time O(n)  Space complexity  O(1)*/
    public boolean verifyPreorder_x(int[] preorder) {
        if (preorder == null || preorder.length <= 1) {
            return true;
        }
         
        int i = -1;
        int max = Integer.MIN_VALUE;
         
        for (int num : preorder) {
            if (num < max) {
                return false;
            }
             
            while (i >= 0 && num > preorder[i]) {
                max = preorder[i];
                i--;
            }
             
            i++;
            preorder[i] = num;
        }
         
        return true;
    }
}
