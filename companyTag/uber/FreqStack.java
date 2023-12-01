package companyTag.uber;

import junit.framework.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 *
 * Implement FreqStack, a class which simulates the operation of a stack-like data structure.

 FreqStack has two functions:

 push(int x), which pushes an integer x onto the stack.
 pop(), which removes and returns the most frequent element in the stack.
 If there is a tie for most frequent element, the element closestPair to the top of the stack is removed and returned.


 Example 1:

 Input:
 ["FreqStack","push","push","push","push","push","push","pop","pop","pop","pop"],
 [[],[5],[7],[5],[7],[4],[5],[],[],[],[]]
 Output: [null,null,null,null,null,null,null,5,7,5,4]
 Explanation:
 After making six .push operations, the stack is [5,7,5,7,4,5] from bottom to top.  Then:

 pop() -> returns 5, as 5 is the most frequent.
 The stack becomes [5,7,5,7,4].

 pop() -> returns 7, as 5 and 7 is the most frequent, but 7 is closestPair to the top.
 The stack becomes [5,7,5,4].

 pop() -> returns 5.
 The stack becomes [5,7,4].

 pop() -> returns 4.
 The stack becomes [5,7].


 Note:

 Calls to FreqStack.push(int x) will be such that 0 <= x <= 10^9.
 It is guaranteed that FreqStack.pop() won't be called if the stack has zero elements.
 The total number of FreqStack.push calls will not exceed 10000 in a single test case.
 The total number of FreqStack.pop calls will not exceed 10000 in a single test case.
 The total number of FreqStack.push and FreqStack.pop calls will not exceed 150000 across all test cases.
 *
 */

public class FreqStack {

    Map<Integer, Integer> freqs = new HashMap<>();
    Map<Integer, Stack<Integer>> positions = new HashMap<>();
    int maxFreq = 0;

    public FreqStack() {

    }

    public void push(int x) {
        int f = freqs.getOrDefault(x, 0) + 1;
        freqs.put(x, f);

        if(!positions.containsKey(f)){
            positions.put(f, new Stack<>());
        }
        positions.get(f).add(x);

        maxFreq = Math.max(maxFreq, f);
    }

    public int pop() {
        if(positions.isEmpty()){
            throw new IllegalArgumentException("FreqStack is empty. ");
        }

        Stack<Integer> stack = positions.get(maxFreq);
        int result = stack.pop();
        if(stack.isEmpty()){
            maxFreq--;
        }

        freqs.put(result, freqs.get(result) - 1);

        return result;
    }


    @Test public void test(){

        push(5);
        push(7);
        push(5);
        push(7);
        push(4);
        push(5);

        Assert.assertEquals(5, pop());
        Assert.assertEquals(7, pop());
        Assert.assertEquals(5, pop());
        Assert.assertEquals(4, pop());
    }

}
