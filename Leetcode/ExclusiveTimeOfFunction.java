package fgafa.Leetcode;

import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 * https://leetcode.com/problems/exclusive-time-of-functions/
 *
 *
 *
 */

public class ExclusiveTimeOfFunction {

    public int[] exclusiveTime(int n, List<String> logs){
        if(null == logs || logs.size() < 2 || logs.size() != 2 * n){
            throw new IllegalArgumentException("error");
        }

        int[] result = new int[n];
        LogLine pre;
        LogLine curr;
        Stack<LogLine> stack = new Stack<>();

        for(int i = 0; i < logs.size(); i++){
            curr = new LogLine(logs.get(i));

            if(stack.isEmpty()){
                stack.push(curr);
            }else {
                if(curr.actionId == 0){
                    pre = stack.peek();
                    //pre.actionTime += curr.actionTimestamp - pre.actionTimestamp;
                    result[pre.functionId] += curr.actionTimestamp - pre.actionTimestamp;

                    stack.push(curr);
                }else{
                    pre = stack.pop();

                    //check pre.functionId == curr.functionId
                    //pre.actionTime += curr.actionTimestamp - pre.actionTimestamp;
                    result[pre.functionId] += curr.actionTimestamp - pre.actionTimestamp + 1;

                    if(!stack.isEmpty()){
                        stack.peek().actionTimestamp = curr.actionTimestamp + 1;
                    }
                }
            }
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

}
