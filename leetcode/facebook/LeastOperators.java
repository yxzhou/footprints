package leetcode.facebook;

import junit.framework.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * Given a single positive integer x, we will write an expression of the form x (op1) x (op2) x (op3) x ... where each operator op1, op2, etc. is either addition, subtraction, multiplication, or division (+, -, *, or /).  For example, with x = 3, we might write 3 * 3 / 3 + 3 - 3 which is a value of 3.

 When writing such an expression, we adhere to the following conventions:

 The division operator (/) returns rational numbers.
 There are no parentheses placed anywhere.
 We use the usual order of operations: multiplication and division happens before addition and subtraction.
 It's not allowed to use the unary negation operator (-).  For example, "x - x" is a valid expression as it only uses subtraction, but "-x + x" is not because it uses negation.
 We would like to write an expression with the least number of operators such that the expression equals the given target.  Return the least number of operators used.



 Example 1:

 Input: x = 3, target = 19
 Output: 5
 Explanation: 3 * 3 + 3 * 3 + 3 / 3.  The expression contains 5 operations.
 Example 2:

 Input: x = 5, target = 501
 Output: 8
 Explanation: 5 * 5 * 5 * 5 - 5 * 5 * 5 + 5 / 5.  The expression contains 8 operations.
 Example 3:

 Input: x = 100, target = 100000000
 Output: 3
 Explanation: 100 * 100 * 100 * 100.  The expression contains 3 operations.


 Note:

 2 <= x <= 100
 1 <= target <= 2 * 10^8

 */

public class LeastOperators {
    public int leastOpsExpressTarget(int x, int target) {
        Map<Integer, Integer> operations = new HashMap<>();
        operations.put(1, 1); // x / x

        for(int i = x, counter = 0; i <= target; i *= x, counter++){
            operations.put(i, counter);
        }

        return helper(x, target, operations);
    }

    private int helper(int x, int target, Map<Integer, Integer> operations){
        if(target == 0){
            return 0;
        }
        if(operations.containsKey(target)){
            return operations.get(target);
        }
        if(target < x){
            return Math.min(2 * target - 1, 2 * (x - target));
        }

        long i = x; //for case, overflow on int
        int counter = 0;
        for( ; i <= target; i *= x, counter++){ }

        long nextTarget1 = target - i / x;

        int result = counter + helper(x, (int)nextTarget1, operations);

        long nextTarget2 = i - target;
        if(target > nextTarget2){
            result = Math.min(result, counter + 1 + helper(x, (int)nextTarget2, operations));
        }

        operations.put(target, result);
        return result;
    }

    @Test public void test(){
//        Assert.assertEquals(5, leastOpsExpressTarget(3, 19));
//
//        Assert.assertEquals(8, leastOpsExpressTarget(5, 501));
//        Assert.assertEquals(3, leastOpsExpressTarget(100, 100000000));
//
//        Assert.assertEquals(9, leastOpsExpressTarget(2, 1024));

//        Assert.assertEquals(7, leastOpsExpressTarget(100, 200000000));

        Assert.assertEquals(13, leastOpsExpressTarget(14, 5040));
    }
}
