/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tree;

import java.util.List;

/**
 *
 * @author yuanxi
 */
public class LongestConsecutiveSequenceIII {
    int max = 0;

    /**
     * @param root the root of k-ary tree
     * @return the length of the longest consecutive sequence path
     */
    public int longestConsecutive3(MultiTreeNode root) {
        if(root == null){
            return 0;
        }

        helper(root);

        return max;
    }

    private int[] helper(MultiTreeNode node){

        int asc = 1;
        int desc = 1;

        int[] paths;
        for(MultiTreeNode child : node.children){
            //child != null 
            paths = helper(child);

            if(child.val + 1 == node.val ){
                asc = Math.max(asc, paths[0] + 1);
            }else if(child.val - 1 == node.val){
                desc = Math.max(desc, paths[1] + 1);
            }
        }

        max = Math.max(max, asc + desc - 1);

        return new int[]{asc, desc};
    } 
    
    /**
     * Definition for a multi tree node.
     */
    public class MultiTreeNode {

        int val;
        List<MultiTreeNode> children;

        MultiTreeNode(int x) {
            val = x;
        }
    }
}
