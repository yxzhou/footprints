package fgafa.datastructure.segmenttree.inversions;

import java.util.Arrays;

/**
 *  Further improvement, define a AVL tree to store (number, inversion number),
 *               4
 *            /     \
 *          2        8
 *         /
 *        1
 *
 *   Define a int array to store the AVL tree. Node is {value, left sub tree node number}
 *
 */

public class CountInversions2 {
    /*Time Complexity O(nlogn)*/
    public int countInversions(int[] nums){
        if(null == nums){
            return -1;
        }else if(nums.length < 2){
            return 0;
        }

        int length = nums.length;
        Node[] tree = new Node[length];
        int[] orderedInput = new int[length];
        System.arraycopy(nums, 0, orderedInput, 0, length);
        Arrays.sort(orderedInput);
        initTree(tree, orderedInput, length);

        int count = 0;
        for(int i = length - 1; i >= 0; i--){
            count += addTreeNodeAndCountSmaller(tree, 0, nums[i]);
        }

        return count;
    }

    private void initTree(Node[] tree, int[] orderedInput, int length){
        int x = 1;
        while( x <= length ){
            x <<= 1;
        }

        if(length + 1 < x){
            x >>= 1;
        }
        int count = length - x + 1;

        int[] indexes = new int[length];
        int i = 0;
        for(int y = 0, index = 0; index < length; index++ ){
            if(y < count && index == y * 2){
                y++;
            }else{
                indexes[i++] = index;
            }
        }
        initTree(tree, 0, orderedInput, indexes, 0, x - 2);

        for(int y = 0; y < count; y++){
            tree[i++] = new Node(orderedInput[y * 2]);
        }
    }

    private void initTree(Node[] tree, int nodePosition, int[] orderedInput, int[] indexes, int start, int end){
        if(start <= end){
            int middle = start + (end - start) / 2;

            tree[nodePosition] = new Node(orderedInput[indexes[middle]]);

            int leftSon = nodePosition * 2 + 1;
            initTree(tree, leftSon, orderedInput, indexes, start, middle - 1);
            initTree(tree, leftSon + 1, orderedInput, indexes, middle + 1, end);
        }
    }

    private int addTreeNodeAndCountSmaller(Node[] tree, int nodePosition, int target){
        if(target == tree[nodePosition].value){
            return tree[nodePosition].smallerCount;
        }else if(target < tree[nodePosition].value){
            tree[nodePosition].smallerCount++;
            return addTreeNodeAndCountSmaller(tree, nodePosition * 2 + 1, target);
        }else{
            int result = 0;
            result += tree[nodePosition].smallerCount;
            result += addTreeNodeAndCountSmaller(tree, nodePosition * 2 + 2, target);
            return result;
        }
    }

    class Node{
        int value;
        int smallerCount = 0;

        Node(int value){
            this.value = value;
        }
    }
}
