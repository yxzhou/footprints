package fgafa.leetcode;

import java.util.*;

import org.junit.Assert;
import org.junit.Test;

/**
 * https://leetcode.com/problems/exclusive-time-of-functions/
 *
 * Given the running logs of n functions that are executed in a nonpreemptive single threaded CPU, find the exclusive time of these functions.
 * Each function has a unique id, start from 0 to n-1. A function may be called recursively or by another function.
 *
 * A log is a string has this format : function_id:start_or_end:timestamp.
 * For example, "0:start:0" means function 0 starts from the very beginning of time 0. "0:end:0" means function 0 ends to the very end of time 0.
 *
 * Exclusive time of a function is defined as the time spent within this function, the time spent by calling other functions should not be considered as this function's exclusive time.
 * You should return the exclusive time of each function sorted by their function id.
 * 
 * Example 1:
 * Input: n = 2
 * logs =
 *    ["0:start:0",
 *     "1:start:2",
 *     "1:end:5",
 *     "0:end:6"]
 * Output:[3, 4]
 *
 * Explanation:
 * Function 0 starts at time 0, then it executes 2 units of time and reaches the end of time 1.
 * Now function 0 calls function 1, function 1 starts at time 2, executes 4 units of time and end at time 5.
 * Function 0 is running again at time 6, and also end at the time 6, thus executes 1 unit of time.
 * So function 0 totally execute 2 + 1 = 3 units of time, and function 1 totally execute 4 units of time.
 *
 * Note:
 * Input logs will be sorted by timestamp, NOT log id.
 * Your output should be sorted by function id, which means the 0th element of your output corresponds to the exclusive time of function 0.
 * Two functions won't start or end at the same time.
 * Functions could be called recursively, and will always end.
 * 1 <= n <= 100
 *
 */

public class ExclusiveTimeOfFunction {

    public int[] exclusiveTime(int n, List<String> logs){
        if(null == logs || logs.size() < 2 || logs.size() != 2 * n){
            throw new IllegalArgumentException("error");
        }

        int[] result = new int[n]; // index is the function id, value is the function's execution time
        LogLine pre;
        LogLine curr;
        Stack<LogLine> stack = new Stack<>();

        for(int i = 0; i < logs.size(); i++){
            curr = new LogLine(logs.get(i));

            if(stack.isEmpty()){
                stack.push(curr);
            }else {
                if(curr.actionId == 0){ // 0 -start
                    pre = stack.peek();
                    result[pre.functionId] += curr.actionTimestamp - pre.actionTimestamp;

                    stack.push(curr);
                }else{ // it's 1 - end
                    pre = stack.pop();
                    result[pre.functionId] += curr.actionTimestamp - pre.actionTimestamp + 1;

                    if(!stack.isEmpty()){
                        stack.peek().actionTimestamp = curr.actionTimestamp + 1;
                    }
                }
            }
        }

        return result;
    }

    /**
     * Thought: it's a single thread system.
     *   case #1,  f1 start t0, fx start t1, f1 execution time has t1 - t0
     *   case #2,  f1 start t0, f1 end t1,  f1 execution time has t1 - t0 + 1
     *   case #3,  f1 end t0, fx start t1,  It's idle time from t0 to t1
     *   case #4,  f1 end t0, fx end t1, fx execution time has t1 - t0
     */
    public int[] exclusiveTime_2(int n, List<String> logs){
        if(null == logs || logs.size() < 2 || logs.size() != 2 * n){
            throw new IllegalArgumentException("error");
        }

        int[] result = new int[n]; // index is the function id, value is the function's execution time
        LogLine pre = null;
        LogLine curr ;

        for(int i = 0; i < logs.size(); i++){
            curr = new LogLine(logs.get(i));

            if(pre != null){
                if(pre.actionId == 0){ // case #1 and #2
                    result[pre.functionId] += curr.actionTimestamp - pre.actionTimestamp + (curr.actionId == pre.actionId? 0 : 1);
                }else if(curr.actionId == 1){ // case #4 f1 end t0, fx end t1, fx execution time has t1 - t0
                    result[curr.functionId] += curr.actionTimestamp - pre.actionTimestamp;
                }
            }

            pre = curr;
        }

        return result;
    }

    class LogLine{
        int functionId;
        int actionId; // 0, start; 1, end
        long actionTimestamp;
        //long actionTime = 0;

        LogLine(String log){
            String[] items = log.split(":");

            functionId = Integer.valueOf(items[0]);
            actionId = items[1].equalsIgnoreCase("start") ? 0 : 1;
            actionTimestamp = Long.valueOf(items[2]);

//            StringTokenizer curr = new StringTokenizer(log, ":");
//
//            functionId = Integer.valueOf(curr.nextToken()); //functionId
//            actionId = curr.nextToken().equalsIgnoreCase("start") ? 0 : 1; //
//            actionTimestamp = Long.valueOf(curr.nextToken());
        }
    }

    @Test public void test(){
        String[] logs = {
                "0:start:0",
                "1:start:2",
                "1:end:5",
                "0:end:6"
        };

        int[] result = exclusiveTime_2(2, Arrays.asList(logs));

        Assert.assertEquals(3, result[0]);
        Assert.assertEquals(4, result[1]);
    }

}
