package datastructure.segmentTree.inversions;

import junit.framework.Assert;
import util.Misc;

/**
 * m22) store segments with int[] instead of Nodes, 
 *
 *     (min, max, the index in binaryIndexedTree)
 *            (0, 5, 0)
 *           /         \
 *      (0, 2, 1)     (3, 5, 2)
 *        /   \         /   \
 *   (0,1,3) (2,2,4) (3,4,5) (5,5,6)
 *    /   \           /   \
 * (0,0,7)(1,1,8) (3,3,11)(4,4,12) 
 *  
 *  Define w as the max - min + 1, 
 *  the height of the binaryIndexedTree, h is Math.ceil(log(2, w)),  
 *  total space is 2^0 + 2^1 + ... + 2^h = 2^h * 2 - 1 = 2^Math.ceil(log(2, w))*2 - 1 < w * 4 
 *  the space is O( (max - min) * 4), the time complexity is O(n * log(max - min))
 */

public class CountSmallerAfterSelf22 {

    public int countSmaller(int[] nums){
        if(null == nums || nums.length < 2){
            return 0;
        }

        int n = nums.length;
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for(int num : nums){
            max = Math.max(max, num);
            min = Math.min(min, num);
        }
        
        int h = (int)Math.ceil(Math.log(max - min + 1)/Math.log(2));
        if(h >= 30 ){
            throw new UnsupportedOperationException("It will takes unacceptable space. Please try with m4 on CountSmallerAfterSelf2.");
        }

        int[] tree = new int[(int)Math.pow(2, h + 1)]; 

        int result = 0;
        for(int i = n - 1; i >= 0; i--){
            result += addAndCount(tree, 0, min, max, nums[i]);
        }

        return result;
    }

    /**
     * add Node And Count Inversions
     * 
     * Time complexity O(logn)
     * 
    */
    private int addAndCount(int[] tree, int index, int min, int max, int currValue){
        if(min == max) {
            return 0; //if it need count smaller and equal, return tree[min]
        }

        int leftSon = index * 2 + 1;
        int middle = min + (max -min) / 2;

        if(middle >= currValue){
            tree[index]++;
            return addAndCount(tree, leftSon, min, middle, currValue);
        }else {
            return tree[index] + addAndCount(tree, leftSon + 1, middle + 1, max, currValue);
        }
    }
    
    public static void main(String[] args){
        /** basic test  **/
        for(int w = 0; w < 18; w++){
            System.out.println(String.format("\n(delta = max - min)=%d, log(2,delta + 1)=%.2f, Math.ceil(log(2,delta + 1))=%d", w, Math.log(w + 1)/Math.log(2), (int)Math.ceil(Math.log(w + 1)/Math.log(2))  ));
        }
        
        int w = Integer.MAX_VALUE - 1111;
        int size = (int)Math.pow(2, (int)Math.ceil(Math.log(w + 1)/Math.log(2)) + 1);
        System.out.println(String.format("\n(delta = max - min)=%d, log(2,delta + 1)=%.2f, Math.ceil(log(2,delta + 1))=%d, size =%d", w, Math.log(w + 1)/Math.log(2), (int)Math.ceil(Math.log(w + 1)/Math.log(2)), size  ));

        
        int[][][] inputs = {
            //{nums, {expect}}
            {{5, 2, 6, 1}, {4}},
            {{3, 2, 2, 6, 1}, {6}},
            {{1, 2, 3, 4}, {0}},
            
            {{3, -2, -2, -6, -1}, {6}},
            {{3, -2, -3, -6, -1}, {7}},
            //{{3, -2, -2, -6, -1, Integer.MAX_VALUE - 1111}, {6}},  //Memory Limit Exceeded Error
        };
        
        CountSmallerAfterSelf22 sv = new CountSmallerAfterSelf22();

        for(int[][] input : inputs){
            System.out.println(Misc.array2String(input[0]));

            Assert.assertEquals(input[1][0], sv.countSmaller(input[0]));
        }

    }

}
