package fgafa.datastructure.interval;

/**
 * 
 * LeetCode Q370: Range Addition
    Assume you have an array of length n initialized with all 0's and are given k update operations.
    Each operation is represented as a triplet: [startIndex, endIndex, inc] which increments each element of subarray A[startIndex ... endIndex](startIndex and endIndex inclusive) with inc.
    Return the modified array after all k operations were executed.
    
    Example:
    Given:
    
        length = 5,
        updates = [
            [1,  3,  2],
            [2,  4,  3],
            [0,  2, -2]
        ]
    
    Output:
        [-2, 0, 3, 5, 3]
        
    Explanation:
    Initial state:
    [ 0, 0, 0, 0, 0 ]
    
    After applying operation [1, 3, 2]:
    [ 0, 2, 2, 2, 0 ]
    
    After applying operation [2, 4, 3]:
    [ 0, 2, 5, 5, 3 ]
    
    After applying operation [0, 2, -2]:
    [-2, 0, 3, 5, 3 ]
    
    Hint:
    Thinking of using advanced data structures? You are thinking it too complicated.
    For each update operation, do you really need to update all elements between i and j?
    Update only the first and end element is sufficient.
    The optimal time complexity is O(k + n) and uses O(1) extra space.
 *
 */

public class RangeAddition {

    /**
     *  Time O(k + n), Space O(1)
     */
    public int[] getModifiedArray_diff(int length, int[][] updates) {  
        int[] results = new int[length];  
        for(int[] update : updates) {  
            results[update[0]] += update[2];  
            if (update[1] + 1 < length) results[update[1] + 1] -= update[2];  
        }  
        
        int value = 0;  
        for(int i = 0; i < length; i++) {  
            value += results[i];  
            results[i] = value;  
        }  
        return results;  
    }
    
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
