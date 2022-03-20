package datastructure.segmentTree.inversions;

import java.util.Arrays;
import junit.framework.Assert;
import util.Misc;

/**
 * Further improvement, define a AVL tree to store (number, inversion number),
 * 
 * m32) example {5, 2, 6, 1, 7}
 *  step 1, sorted, {1, 2, 5, 6, 7}
 *  step 2, Define a int array to store the AVL tree. Node is {value, left sub tree node number}
 *  
 * Example 1:
 *  sorted  {1, 2, 5, 6, 7}
 *  indexed [0, 1, 2, 3, 4]
 * 
 *  Define a AVL tree, here middle_index = min + (max - min + 1) / 2
 *   (the index in sorted array, the index in arrayTree)
 *               (2, 0)
 *               /    \
 *            (1, 1) (4, 2)  
 *             /      /
 *         (0, 3)  (3, 5) 
 *   so the arrayTree {5, 2, 7, 1, *, 6}
 * 
 *  Example 2: 
 *   sorted  {1, 2, 5, 6, 7, 8}
 *   indexed [0, 1, 2, 3, 4, 5]
 * 
 *  Define a AVL tree, here middle_index = (left_index + right_index + 1) / 2
 *   (the index in sorted array, the index in arrayTree)
 *               (3,0)
 *               /     \
 *           (1, 1)   (5, 2)  
 *            /  \      /
 *        (0, 3) (2,4)(4, 5) 
 *  so the arrayTree {6, 2, 8, 1, 5, 7}
 * 
 *  Define n as the length of nums
 * 
 *  the space is O( n * 2), the time complexity is O(n * log(n))
 *
 */

public class CountSmallerAfterSelf33 {

    public int countSmaller(int[] nums){
        if(null == nums || nums.length < 2){
            return 0;
        }

        int n = nums.length;
        int[] sorted = Arrays.copyOf(nums, n);
        Arrays.sort(sorted);

        Node[] tree = new Node[n * 2];
        initTree(tree, 0, sorted, 0, n);

        int result = 0;
        for(int i = n - 1; i >= 0; i--){
            result += addTreeNodeAndCountSmaller(tree, 0, nums[i]);
        }

        return result;
    }

    private void initTree(Node[] tree, int nodeIndex, int[] sorted, int left, int right){
                
        if(left == right){
            return;
        }
        
        int middle = left + (right - left) / 2;
        //System.out.println(String.format("left: %d, right: %d, middle: %d", left, right, middle));
        
        tree[nodeIndex] = new Node(sorted[middle]);

        int leftSon = nodeIndex * 2 + 1;
        initTree(tree, leftSon, sorted, left, middle);
        initTree(tree, leftSon + 1, sorted, middle + 1, right);

    }

    private int addTreeNodeAndCountSmaller(Node[] tree, int nodeIndex, int target){
        
        Node node = tree[nodeIndex];
        
        if(target == node.value){
            node.equal++;
            return node.smallerCount;
        }else if(target < node.value){
            node.smallerCount++;
            return addTreeNodeAndCountSmaller(tree, nodeIndex * 2 + 1, target);
        }else{ //target > node.value
            return node.smallerCount + node.equal + addTreeNodeAndCountSmaller(tree, nodeIndex * 2 + 2, target);
        }
    }

    class Node{
        int value;
        int smallerCount = 0;
        int equal = 0; //for every node, 1 means itself

        Node(int value){
            this.value = value;
        }
    }
    
    public static void main(String[] args){
        
        int[][][] inputs = {
            //{nums, {expect}}
            {{5, 2, 6, 1}, {4}},
            {{3, 2, 2, 6, 1}, {6}},
            {{1, 2, 3, 4}, {0}},
            
            {{3, -2, -2, -6, -1}, {6}},
            {{3, -2, -3, -6, -1}, {7}},
            
            {{3, 2, 2, 6, Integer.MAX_VALUE - 1111, 1}, {7}},    //Memory Limit Exceeded Error
            {{3, -2, -3, -6, Integer.MAX_VALUE - 1111, -1}, {8}},
        };
        
        CountSmallerAfterSelf33 sv = new CountSmallerAfterSelf33();

        for(int[][] input : inputs){
            System.out.println(Arrays.toString(input[0]));

            Assert.assertEquals(input[1][0], sv.countSmaller(input[0]));
        }
    }
    
}
