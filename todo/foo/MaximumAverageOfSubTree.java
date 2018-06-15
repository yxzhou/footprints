package fgafa.todo.foo;

import fgafa.tree.TreeNode;

/**
 * Q: _https://www.jiuzhang.com/solution/subtree-with-maximum-average/
 *    Given a binary tree, find the subtree with maximum average. Return the root of the subtree.
 *
 * S: similar with sum of subtree
 *
 */

public class MaximumAverageOfSubTree {

    public TreeNode maxAverageOfSubTree(TreeNode root){
        Summary result = helper(root);

        if(null == result){
            return null;
        }else{
            return result.maxAverageNode;
        }
    }

    private Summary helper(TreeNode curr){
        if(null == curr){
            return null;
        }

        Summary left = helper(curr.left);
        Summary right = helper(curr.right);

        if (null == left && null == right){
            return new Summary(1, curr.val);
        }

        Summary result = null;
        if (null == left || null == right){
            result = (null == left) ? right : left;
            double localAverage = (curr.val + result.sum) / (result.numberOfNodes + 1);

            if(localAverage > result.maxAverage){
                result.maxAverage = localAverage;
                result.maxAverageNode = curr;
            }

            result.sum += curr.val;
            result.numberOfNodes += 1;
        }else{
            result = (left.maxAverage > right.maxAverage) ? left : right;
            double localAverage = (curr.val + left.sum + right.sum) / (left.numberOfNodes + right.numberOfNodes + 1);
            if(localAverage > result.maxAverage ){
                result.maxAverage = localAverage;
                result.maxAverageNode = curr;
            }

            result.sum = curr.val + left.sum + right.sum;
            result.numberOfNodes = left.numberOfNodes + right.numberOfNodes + 1;
        }

        return result;
    }

    class Summary{
        int sum;
        int numberOfNodes;

        double maxAverage;
        TreeNode maxAverageNode;

        Summary(int sum, int numberOfNodes){
            this.sum = sum;
            this.numberOfNodes = numberOfNodes;

            resetAverage();
        }

        private void resetAverage(){
            this.maxAverage = sum / numberOfNodes;
        }
    }
}
