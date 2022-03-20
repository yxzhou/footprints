package datastructure.segmentTree.inversions;

import java.util.Arrays;
import junit.framework.Assert;


/**
 * Further improvement, define the perfect AVL to store (number, inversion number), 
 * 
 * m32) example {5, 2, 6, 1, 7}
 *  step 1, sorted, {1, 2, 5, 6, 7}
 *  step 2, Define a int array to store the AVL tree. Node is {value, left sub tree node number}
 *  
 * Example 1:
 *    nums  {5, 2, 6, 1}
 *  sorted  {1, 2, 5, 6}
 *  indexed [0, 1, 2, 3] -- pre-order 
 *  Define the perfect AVL only takes space O(n)
 *   (the index in sorted array, the index in arrayTree)
 *              2
 *            /   \
 *           1     3 
 *          / 
 *         0   
 *   so
 *     indexed level-order [2, 1, 3, 0]   
 *           the arrayTree {5, 2, 6, 1}
 * 
 * Example 1:
 *    nums  {5, 2, 6, 1, 7}
 *  sorted  {1, 2, 5, 6, 7}
 *  indexed [0, 1, 2, 3, 4] -- pre-order 
 *  Define the perfect AVL only takes space O(n)
 *   (the index in sorted array, the index in arrayTree)
 *              3
 *            /   \
 *           1     4 
 *          / \
 *         0   2
 *   so
 *     indexed level-order [3, 1, 4, 0, 2]   
 *           the arrayTree {6, 2, 7, 1, 5}
 * 
 *  Example 2: 
 *   sorted  {1, 2, 5, 6, 7, 8}
 *   indexed [0, 1, 2, 3, 4, 5] -- pre-order 
 *  Define the perfect AVL only takes space O(n)
 * 
 *   (the index in sorted array, the index in arrayTree)
 *                3
 *              /   \
 *             1     5
 *            / \   /
 *           0   2 4 
 * 
 *   so
 *     indexed level-order [3, 1, 5, 0, 2, 4]   
 *           the arrayTree {6, 2, 8, 1, 5, 7}
 * 
 *  Example 3: 
 *   sorted  {}
 *   indexed [0, 1, 2, 3, 4, 5, 6, 7] -- pre-order 
 *  Define the perfect AVL only takes space O(n)
 * 
 *   (the index in sorted array, the index in arrayTree)
 *                4
 *              /   \
 *             2     6
 *            / \   / \
 *           1   3 5   7
 *          /
 *         0
 * 
 *   so
 *     indexed level-order [4, 2, 6, 1, 3, 5, 7, 0]   
 *           the arrayTree {sorted[4], ... }
 * 
 *  Define n as the length of nums
 *  the space is O(n), the time complexity is O(n * log(n))
 *
 */

public class CountSmallerAfterSelf32 {

    public int countSmaller(int[] nums){
        if(null == nums || nums.length < 2){
            return 0;
        }

        int n = nums.length;
        int[] sorted = Arrays.copyOf(nums, n);
        Arrays.sort(sorted);

        Node[] tree = new Node[n];
        initTree(tree, sorted);

        int count = 0;
        for(int i = n - 1; i >= 0; i--){
            count += addTreeNodeAndCountSmaller(tree, 0, nums[i]);
        }

        return count;
    }

    private void initTree(Node[] tree, int[] sorted){
        int n = sorted.length;
        int x = (int)Math.pow(2, (int)Math.ceil(Math.log(n + 1)/Math.log(2)));
        x = (x > (n + 1) ? x >> 1 : x);
                
        int lowestLevelNodeNumber = n - x + 1;

        int[] indexes = new int[n];
        int i = 0;
        for(int y = 0, index = 0; index < n; index++ ){
            if(y < lowestLevelNodeNumber && index == y * 2){
                y++;
            }else{
                indexes[i++] = index;
            }
        }
        
        System.out.println(String.format("\n sorted:%s,\nindexes:%s", Arrays.toString(sorted), Arrays.toString(indexes)));
        
        initTree(tree, 0, sorted, indexes, 0, x - 2);

        for(int y = 0; y < lowestLevelNodeNumber; y++){
            tree[i++] = new Node(sorted[y * 2]);
        }
        
        Arrays.stream(tree).map(node -> node.value + ", ").forEach(System.out::print);
    }

    private void initTree(Node[] tree, int nodeIndex, int[] sorted, int[] indexes, int start, int end){
        if(start <= end){
            int middle = start + (end - start) / 2;

            tree[nodeIndex] = new Node(sorted[indexes[middle]]);

            int leftSon = nodeIndex * 2 + 1;
            initTree(tree, leftSon, sorted, indexes, start, middle - 1);
            initTree(tree, leftSon + 1, sorted, indexes, middle + 1, end);
        }
    }

    private int addTreeNodeAndCountSmaller(Node[] tree, int nodePosition, int target){
        Node node = tree[nodePosition];
        
        if(target == node.value){
            node.equal++;
            return tree[nodePosition].smallerCount;
        }else if(target < tree[nodePosition].value){
            tree[nodePosition].smallerCount++;
            return addTreeNodeAndCountSmaller(tree, nodePosition * 2 + 1, target);
        }else{
            return node.smallerCount + node.equal + addTreeNodeAndCountSmaller(tree, nodePosition * 2 + 2, target);
        }
    }

    class Node{
        int value;
        int smallerCount = 0;
        int equal = 0;

        Node(int value){
            this.value = value;
        }
    }
    
    public static void main(String[] args){
        
        /** basic test 
        int height;
        for(int n = 2; n < 18; n++){
            System.out.println(String.format("\n(nums.length)=%d, the height=%d, the AVT root=%d", n, height = (int)Math.ceil(Math.log(n + 1)/Math.log(2)), (int)Math.pow(2, height - 1) - 1  ));            
        }
         **/
        
        int[][][] inputs = {
            //{nums, {expect}}
            {{5, 2, 6, 1}, {4}},
            {{5, 2, 6, 1, 7}, {4}},
            {{3, 2, 2, 6, 1}, {6}},
            {{1, 2, 3, 4}, {0}},
            
            {{3, -2, -2, -6, -1}, {6}},
            {{3, -2, -3, -6, -1}, {7}},
            
            {{3, 2, 2, 6, Integer.MAX_VALUE - 1111, 1}, {7}},    //Memory Limit Exceeded Error
            {{3, -2, -3, -6, Integer.MAX_VALUE - 1111, -1}, {8}},
        };
        
        CountSmallerAfterSelf32 sv = new CountSmallerAfterSelf32();

        for(int[][] input : inputs){
            System.out.println(String.format("\nnums: %s", Arrays.toString(input[0])));

            Assert.assertEquals(input[1][0], sv.countSmaller(input[0]));
        }
    }
    
}
