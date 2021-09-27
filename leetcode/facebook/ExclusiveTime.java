/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leetcode.facebook;

import java.util.List;
import java.util.Stack;

/**
 * _https://www.lintcode.com/problem/1116/description?_from=collection&fromId=29
 * 
 * Given the running logs of n functions that are executed in a nonpreemptive single threaded CPU, find the exclusive time of these functions.

 * Each function has a unique id, start from 0 to n-1. A function may be called recursively or by another function.
 * 
 * A log is a string has this format : function_id:start_or_end:timestamp. For example, 0:start:0 means function 0 starts from the very beginning of time 0. 0:end:0 means function 0 ends to the very end of time 0.
 *
 * Exclusive time of a function is defined as the time spent within this function, the time spent by calling other functions should not be considered as this function's exclusive time. 
 * You should return the exclusive time of each function sorted by their function id.
 *
 * Notes:
 * Input logs will be sorted by timestamp, NOT log id.
 * Your output should be sorted by function id, which means the 0th element of your output corresponds to the exclusive time of function 0.
 * Two functions won't start or end at the same time.
 * Functions could be called recursively, and will always end.
 * 1 <= n <= 100
 * 
 * Example 1:
 *
 * Input:
 *  2
 *  0:start:0
 *  1:start:2
 *  1:end:5
 *  0:end:6
 * Output:
 *  3 4
 *
 */



public class ExclusiveTime {
    /**
     * @param n: a integer
     * @param logs: a list of integers
     * @return: return a list of integers
     */
    public int[] exclusiveTime(int n, List<String> logs) {
        //if(logs == null){}
        int[] costs = new int[n];
        int startTimes = 0;
        Stack<Integer> stack = new Stack<>(); // <functionId>

        String[] triple;
        int id;
        int time;

        int top;
        for(String log : logs){
            triple = log.split(":");
            id = Integer.valueOf(triple[0]);
            time = Integer.valueOf(triple[2]);

            if(triple[1].equals("start")){
                if( !stack.isEmpty() ){
                    top = stack.peek();
                    costs[top] += time - startTimes;
                }

                stack.add(id);
                startTimes = time;
            }else{ //end
                top = stack.pop();
                costs[top] += time - startTimes + 1;
                startTimes = time + 1;
            }
        }

        return costs;
    }
}
