/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datastructure.stack.monotonous;

import java.util.Stack;

/**
 * _https://www.lintcode.com/problem/1060
 *
 * Given a list of daily temperatures, produce a list that, for each day in the input, tells you how many days you would
 * have to wait until a warmer temperature. If there is no future day for which this is possible, put 0 instead.
 *
 * Constraints:
 *   1.The length of temperatures will be in the range [1, 30000]. Each temperature will be an integer in the range [30,
 * 100]  
 * 
 * Example 1: 
 * Input: temperatures = [73, 74, 75, 71, 69, 72, 76, 73] 
 * Output: [1, 1, 4, 2, 1, 1, 0, 0]
 * Explanation: Just find the first day after it which has higher temperatures than it.
 *
 * Example 2: 
 * Input: temperatures = [50, 40, 39, 30] 
 * Output: [0,0,0,0]
 *
 *
 */
public class DailyTemperatures {
    /**
     * @param temperatures: a list of daily temperatures
     * @return a list of how many days you would have to wait until a warmer temperature
     */
    public int[] dailyTemperatures(int[] temperatures) {
        if (temperatures == null) {
            return new int[0];
        }

        int n = temperatures.length;
        int[] result = new int[n];

        Stack<Integer> stack = new Stack<>();

        for (int i = n - 1; i >= 0; i--) {
            while (!stack.isEmpty() && temperatures[i] >= temperatures[stack.peek()]) {
                stack.pop();
            }

            if (!stack.isEmpty()) {
                result[i] = stack.peek() - i;
            }

            stack.add(i);

        }

        return result;
    }
    
    /**
     * @param temperatures: a list of daily temperatures
     * @return a list of how many days you would have to wait until a warmer temperature
     */
    public int[] dailyTemperatures_n(int[] temperatures) {
        if(temperatures == null){
            return new int[0];
        }

        int n = temperatures.length;
        int[] result = new int[n];

        int[] stack = new int[n];
        int top = -1;

        for(int i = n - 1; i >= 0; i--){
            while(top > -1 && temperatures[i] >= temperatures[stack[top]]){
                top--;
            }

            if(top > -1){
                result[i] = stack[top] - i;
            }

            stack[++top] = i;
        }

        return result;
    }
}
