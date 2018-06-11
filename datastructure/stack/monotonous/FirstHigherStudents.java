package fgafa.datastructure.stack.monotonous;

import java.util.Stack;

import fgafa.util.Misc;

/**
 * 
 * There are some students who stand in a line. Given n non-negative integers representing the height of the students, 
 * to every student, find the first higher student's position in his right.  return the position as -1 if not found.
 * 
 * For example,
 * Given heights = [3,1,6,4,5,2],
 * return [2, 2, -1, 4, -1, -1 ].
 *
 */
public class FirstHigherStudents {

    //单调栈   从左往右
    public int[] getFirstHigher(int[] heights){
        if(null == heights || 0 == heights.length){
            return new int[0];
        }
        
        int[] result = new int[heights.length];
        for(int i = 0; i < heights.length; i++){
            result[i] = -1;
        }
        
        Stack<Integer> positions = new Stack<>();

        for(int i = 0; i < heights.length; ){
            if(positions.isEmpty() || heights[i] <= heights[positions.peek()]){
                positions.push(i);
                i++;
            }else{
                result[positions.pop()] = i;
            }
        }
        
        return result;
    }
    
    public static void main(String[] args){
        int[] input = {3, 1, 6, 4, 5, 2};
        
        FirstHigherStudents sv = new FirstHigherStudents();
                    
        System.out.println(String.format("Input: %s", Misc.array2String(input)));
        System.out.println(String.format("Output: %s", Misc.array2String(sv.getFirstHigher(input))));
    }
    
}
